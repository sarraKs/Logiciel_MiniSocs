package eu.telecomsudparis.csc4102.minisocs;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.SubmissionPublisher;
import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;

//import eu.telecomsudparis.csc4102.cours.seance8.patrondeconception.publiersouscrire.MonConsommateur;
//import eu.telecomsudparis.csc4102.cours.seance8.patrondeconception.publiersouscrire.Publication;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

/**
 * Cette classe est la façade du logiciel.
 * 
 * @author Denis Conan
 */
public class MiniSocs {
	/**
	 * le nom du système.
	 */
	private final String nomDuSysteme;
	/**
	 * les utilisateurs.
	 */
	private final Map<String, Utilisateur> utilisateurs;
	/**
	 * les réseaux.
	 */
	private final Map<String, Reseau> reseaux;
	/**
	 * le temps d'attente d'envoi de notification.
	 */
	private static final int TEMPS_NOTIF = 100;

	/**
	 * construit une instance du système.
	 * 
	 * @param nomDuSysteme le nom.
	 */
	public MiniSocs(final String nomDuSysteme) {
		this.nomDuSysteme = nomDuSysteme;
		this.utilisateurs = new HashMap<>();
		this.reseaux = new HashMap<>();
	}

	/**
	 * l'invariant de la façade.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return nomDuSysteme != null && !nomDuSysteme.isBlank() && getUtilisateurs() != null;
	}

	/**
	 * ajoute un utilisateur.
	 * 
	 * @param pseudo   le pseudo de l'utilisateur.
	 * @param nom      le nom de l'utilisateur.
	 * @param prenom   le prénom de l'utilisateur.
	 * @param courriel le courriel de l'utilisateur.
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void ajouterUtilisateur(final String pseudo, final String nom, final String prenom, final String courriel)
			throws OperationImpossible {
		if (pseudo == null || pseudo.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new OperationImpossible("prenom ne peut pas être null ou vide");
		}
		if (courriel == null || courriel.isBlank()) {
			throw new OperationImpossible("courriel ne peut pas être null ou vide");
		}
		if (!EmailValidator.getInstance().isValid(courriel)) {
			throw new OperationImpossible("courriel ne respecte pas le standard RFC822");
		}
		Utilisateur u = getUtilisateurs().get(pseudo);
		if (u != null) {
			throw new OperationImpossible(pseudo + "déjà un utilisateur");
		}
		getUtilisateurs().put(pseudo, new Utilisateur(pseudo, nom, prenom, courriel));
		assert invariant();
	}

	/**
	 * liste les utilisateurs.
	 * 
	 * @return la liste des pseudonymes des utilisateurs.
	 */
	public List<String> listerUtilisateurs() {
		return getUtilisateurs().values().stream().map(Utilisateur::toString).toList();
	}

	/**
	 * désactiver son compte utilisateur.
	 * 
	 * @param pseudo le pseudo de l'utilisateur.
	 * @throws OperationImpossible en cas de problèmes sur les pré-conditions.
	 */
	public void desactiverCompteUtilisateur(final String pseudo) throws OperationImpossible {
		if (pseudo == null || pseudo.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		Utilisateur u = getUtilisateurs().get(pseudo);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudo + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		u.desactiverCompte();
		assert invariant();
	}
	
	/**
	 * crée un réseau (ajoute un réseau).
	 * 
	 * @param nomReseau    le nom du réseau.
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui cree le réseau.
	 * @param pseudoMembre le pseudonyme du membre dans le reseau (l'utilisateur devient membre).
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void creerReseau(final String nomReseau, final String pseudonyme, final String pseudoMembre)
			throws OperationImpossible {
		
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("nomReseau ne peut pas être null ou vide");
		}
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (pseudoMembre == null || pseudoMembre.isBlank()) {
			throw new OperationImpossible("pseudo_membre ne peut pas être null ou vide");
		}
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
		
		Reseau r = getReseaux().get(nomReseau);
		if (r != null) {
			throw new OperationImpossible(nomReseau + "déjà un réseau");
		}
		//un nouveau membre se crée en moderateur et devient membre du reseau
		Membre m = new Membre(pseudoMembre, EtatMembre.MODERATEUR);
		reseaux.put(nomReseau, new Reseau(nomReseau));
		Reseau re = reseaux.get(nomReseau);
		re.ajouterMembre(pseudoMembre, m);
		u.ajouterMembre(pseudoMembre, m);
		
		//un consommateur est créé et associé à l'utilisateur puis souscris aux producteurs MessageEnAttente et MessagePoste
		MonConsommateur consommateur1 = new MonConsommateur("1", EtatStrategie.QUOTIDIEN);
		u.rendreConsommateur(consommateur1);
		re.getProducteurMessageEnAttente().subscribe(consommateur1);
		re.getProducteurMessagePoste().subscribe(consommateur1);
		
		assert invariant();
	}
	
	/**
	 * liste les reseaux.
	 * 
	 * @return la liste des noms des reseaux.
	 */
	public List<String> listerReseaux() {
		return getReseaux().values().stream().map(Reseau::toString).toList();
	}
	
	/**
	 * liste les membres d'un utilisateur.
	 * 
	 * @param pseudonyme le pseudonyme de l'utilisateur
	 * @return la liste des membres de l'utilisateur.
	 */
	public List<String> listerMembres(final String pseudonyme) {
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		return u.listerMembres();
	}
	
	/**
	 * liste les messages postés dans un réseau.
	 * 
	 * @param nomReseau le nom du réseau.
	 * @return la liste des messages du réseau.
	 */
	public List<String> listerMessages(final String nomReseau) {
		Reseau r = getReseaux().get(nomReseau);
		return r.listerMessages();
	}
	
	
	/**
	 * ajoute un membre au réseau.
	 * 
	 * @param nomReseau    le nom du réseau.
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui ajoute un membre.
	 * @param monPseudoMembre le pseudo_membre de l'utilisateur sert à vérifier si il est modérateur
	 * @param pseudonymeAjout    le pseudonyme de l'utilisateur à ajouter comme membre.
	 * @param pseudoMembreAjout   le pseudo_membre du membre à ajouter.
	 * @param etatMembreAjout l'état du membre à ajouter.
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void ajouterMembreAuReseau(final String nomReseau, final String pseudonyme, final String monPseudoMembre, final String pseudonymeAjout, 
			final String pseudoMembreAjout, final EtatMembre etatMembreAjout)
			throws OperationImpossible {
		
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("nomReseau ne peut pas être null ou vide");
		}
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (monPseudoMembre == null || monPseudoMembre.isBlank()) {
			throw new OperationImpossible("mon pseudo_membre ne peut pas être null ou vide");
		}
		if (pseudonymeAjout == null || pseudonymeAjout.isBlank()) {
			throw new OperationImpossible("pseudonyme de l'utilisateur à ajouter ne peut pas être null ou vide");
		}
		if (pseudoMembreAjout == null || pseudoMembreAjout.isBlank()) {
			throw new OperationImpossible("pseudo_membre à ajouter ne peut pas être null ou vide");
		}
		if (etatMembreAjout == null) {
			throw new OperationImpossible("l'état du membre à ajouter ne peut pas être null");
		}
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
		
		//vérifier que le réseau existe et ouvert
		Reseau r = getReseaux().get(nomReseau);
		if (r == null) {
			throw new OperationImpossible("réseau inexistant avec ce nom (" + nomReseau + ")");
		}
		
		if (r.getEtatRéseau().equals(EtatReseau.FERMÉ)) {
			throw new OperationImpossible("ce réseau (" + nomReseau + ") est férmé");
		}
		
		//vérifier que l'utilisateur est bien membre de ce réseau et modérateur
		Map<String, Membre> membres = r.getMembres();
		Membre n = membres.get(monPseudoMembre);
		if (n == null) {
			throw new OperationImpossible("Membre avec ce pseudonyme (" + monPseudoMembre + " ) n'existe pas dans ce réseau. ");
		}
		if (u.monEtatMembre(monPseudoMembre).equals(EtatMembre.MEMBRE)) {
			throw new OperationImpossible("vous n'êtes pas modérateur dans ce réseau, vous ne pouvez pas ajouter un membre");
		}
		
		//vérifier que l'utilisateur à rendre membre existe et qu'il n'est pas déja membre
		Utilisateur v = getUtilisateurs().get(pseudonymeAjout);
		if (v == null) {
			throw new OperationImpossible("utilisateur à ajouter inexistant avec ce pseudo (" + pseudonymeAjout + ")");
		}
		
		Map<String, Membre> membresv = v.getMembre();
		Membre m = membresv.get(pseudoMembreAjout);
		if (m != null) {
			throw new OperationImpossible(pseudoMembreAjout + "déjà un membre");
		}
		
		//créer un nouveau membre et l'associer à l'utilisateur et au reseau
		m = new Membre(pseudoMembreAjout, etatMembreAjout);
		v.ajouterMembre(pseudoMembreAjout, m);
		r.ajouterMembre(pseudoMembreAjout, m);
		
		//creer un nouveau consommateur et l'associer à l'utilisateur correspondant au nouveau membre
		MonConsommateur consommateur2 = new MonConsommateur("2", EtatStrategie.IMMEDIAT);
		v.rendreConsommateur(consommateur2);
		
		//si le nouveau membre est un moderateur, il souscri aux deux producteurs sinon il souscri seulement au producteur MessagePoste
		if (etatMembreAjout.equals(EtatMembre.MODERATEUR)) {
			r.getProducteurMessageEnAttente().subscribe(consommateur2);
			r.getProducteurMessagePoste().subscribe(consommateur2);
		} else {
			r.getProducteurMessagePoste().subscribe(consommateur2);
		}
		
		assert invariant();
	}
	
	/**
	 * Poster un Message dans un réseau social qui doit etre existant.
	 * 
	 * @param contenu    le contenu du message.
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui est membre et qui publie le message.
	 * @param pseudoMembre le pseudonyme du membre qui va publier le message dans le reseau .
	 * @param nomReseau le réseau dans lequel le message va etre publié 
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 * @throws InterruptedException 
	 */
	public void posterMessage(final String contenu, final String pseudonyme, final String pseudoMembre, final String nomReseau)
			throws OperationImpossible, InterruptedException {
		
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("nomReseau ne peut pas être null ou vide");
		}
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (pseudoMembre == null || pseudoMembre.isBlank()) {
			throw new OperationImpossible("pseudo_membre ne peut pas être null ou vide");
		}
		if (contenu == null || contenu.isBlank()) {
			throw new OperationImpossible("le contenu du message ne peut pas être null ou vide");
		}
		
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
	
		Reseau r = getReseaux().get(nomReseau);
		if (r == null) {
			throw new OperationImpossible("Réseau avec ce nom (" + nomReseau + " ) n'existe pas. ");
		}
		if (r.getEtatRéseau().equals(EtatReseau.FERMÉ)) {
			throw new OperationImpossible("le réseau est fermé");
		}
		
		r.posterMessageReseau(contenu, pseudoMembre);
		
		assert invariant();
	}
	
	/**
	 * Un modérateur accepte un Message (actuellement posté et en attente), il devient alors visible.
	 * 
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui va accepter le message.
	 * @param pseudoMembre   le pseudo membre de l'utilsateur.
	 * @param idMessage      l'id du message à accepter 
	 * @param nomReseau      le nom du reseau
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 * @throws InterruptedException 
	 */
	public void accepterMessage(final String pseudonyme, final String pseudoMembre, final String idMessage, final String nomReseau)
			throws OperationImpossible, InterruptedException {
		
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (pseudoMembre == null || pseudoMembre.isBlank()) {
			throw new OperationImpossible("pseudo_membre ne peut pas être null ou vide");
		}
		if (idMessage == null || idMessage.isBlank()) {
			throw new OperationImpossible("l'id du message ne peut pas être null");
		}
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("nomReseau ne peut pas être null ou vide");
		}
		
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
	
		Map<String, Membre> membres = u.getMembre();
		Membre n = membres.get(pseudoMembre);
		if (n == null) {
			throw new OperationImpossible("Membre avec ce pseudonyme (" + pseudoMembre + " ) n'existe pas dans ce réseau. ");
		}
		if (u.monEtatMembre(pseudoMembre).equals(EtatMembre.MEMBRE)) {
			throw new OperationImpossible("vous n'êtes pas modérateur, vous ne pouvez pas accepter un message");
		}
		
		Map<String, Message> postedMessages = n.getPostedMessages();
		Message mess = postedMessages.get(idMessage);
		if (mess == null) {
			throw new OperationImpossible("Message avec cet id (" + idMessage + " ) n'existe pas. ");
		}
		
		Reseau r = getReseaux().get(nomReseau);
		if (r == null) {
			throw new OperationImpossible("Réseau avec ce nom (" + nomReseau + " ) n'existe pas. ");
		}
		if (r.getEtatRéseau().equals(EtatReseau.FERMÉ)) {
			throw new OperationImpossible("le réseau est fermé");
		}
		
		mess.accepterMessage();
		
		r.getProducteurMessagePoste().submit(new Notification("Nouveau message! posté le " + LocalDateTime.now() + " <<" + mess.getContenu() + ">> "));
		Thread.sleep(TEMPS_NOTIF);
		
		assert invariant();
	}
	/**
	 * Un modérateur peut fermer le réseau social d'un membre. 
	 * 
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui va accepter le message.
	 * @param pseudoMembre   le pseudo membre de l'utilsateur.
	 * @param nomReseau     le nom du réseau à fermer 
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void fermerReseau(final String pseudonyme, final String pseudoMembre, final String nomReseau)
			throws OperationImpossible {
		
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (pseudoMembre == null || pseudoMembre.isBlank()) {
			throw new OperationImpossible("pseudo_membre ne peut pas être null ou vide");
		}
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("le nom de réseau ne peut pas être ni null ni vide");
		}
		
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
	
		Map<String, Membre> membres = u.getMembre();
		Membre n = membres.get(pseudoMembre);
		if (n == null) {
			throw new OperationImpossible("Membre avec ce pseudonyme (" + pseudoMembre + " ) n'existe pas dans ce réseau. ");
		}
		if (u.monEtatMembre(pseudoMembre).equals(EtatMembre.MEMBRE)) {
			throw new OperationImpossible("vous n'êtes pas modérateur, vous ne pouvez pas fermer le réseau");
		}
		
		Reseau r = getReseaux().get(nomReseau);
		if (r == null) {
			throw new OperationImpossible("Reseau avec ce nom (" + nomReseau + " ) n'existe pas. ");
		}
		
		if (r.getEtatRéseau().equals(EtatReseau.FERMÉ)) {
			throw new OperationImpossible("le réseau est fermé");
		}
		r.fermerReseau();
		assert invariant();
		
	}
	/**
	 * un membre du réseau social peut cacher un message déjà visible.
	 * 
	 * @param pseudonyme   le pseudonyme de l'utilisateur qui va cacher son message.
	 * @param pseudoMembre   le pseudo membre de l'utilsateur.
	 * @param nomReseau     le nom du réseau là ou on va cacher le message.
	 * @param idMessage  	l'id du message à cacher.
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void cacherMessage(final String pseudonyme, final String pseudoMembre, final String nomReseau, final String idMessage)
			throws OperationImpossible {
		
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new OperationImpossible("pseudonyme ne peut pas être null ou vide");
		}
		if (pseudoMembre == null || pseudoMembre.isBlank()) {
			throw new OperationImpossible("pseudo_membre ne peut pas être null ou vide");
		}
		if (nomReseau == null || nomReseau.isBlank()) {
			throw new OperationImpossible("le nom de réseau ne peut pas être ni null ni vide");
		}
		if (idMessage == null || idMessage.isBlank()) {
			throw new OperationImpossible("l'identifiant du message ne peut pas être ni null ni vide");
		}
		
		Utilisateur u = getUtilisateurs().get(pseudonyme);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudonyme + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte est desactivé");
		}
	
		Map<String, Membre> membres = u.getMembre();
		Membre n = membres.get(pseudoMembre);
		if (n == null) {
			throw new OperationImpossible("Membre avec ce pseudonyme (" + pseudoMembre + " ) n'existe pas dans ce réseau. ");
		}
		
		Reseau r = getReseaux().get(nomReseau);
		if (r == null) {
			throw new OperationImpossible("Reseau avec ce nom (" + nomReseau + " ) n'existe pas. ");
		}
		
		if (r.getEtatRéseau().equals(EtatReseau.FERMÉ)) {
			throw new OperationImpossible("le réseau est fermé");
		}
		
		Map<String, Message> postedMessages = n.getPostedMessages();
		Message mess = postedMessages.get(idMessage);
		
		if (mess == null) {
			throw new OperationImpossible("Message avec cet id (" + idMessage + " ) n'existe pas. ");
		}
		
		if (mess.getEtatMessage().equals(EtatMessage.CACHÉ)) {
			throw new OperationImpossible("le message est déja caché ");
		}
		
		if (!mess.getEtatMessage().equals(EtatMessage.VISIBLE)) {
			throw new OperationImpossible("le message n'est pas visible, il est enAttente ou refusé");
		}
		
		mess.cacherMessage();
		
		assert invariant();
		
	}

	/**
	 * obtient le nom du projet.
	 * 
	 * @return le nom du projet.
	 */
	public String getNomDeProjet() {
		return nomDuSysteme;
	}

	@Override
	public String toString() {
		return "MiniSocs [nomDuSysteme=" + nomDuSysteme + ", utilisateurs=" + getUtilisateurs() + "]";
	}

	/**
	 * obtient les utilisateurs.
	 * 
	 * @return les utilisateurs.
	 */
	public Map<String, Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	/**
	 * chercher un reseau.
	 * 
	 * @param nomReseau     le nom du réseau
	 * @return un reseau.
	 */
	public Optional<Reseau> chercherReseau(final String nomReseau) {
		
		return Optional.ofNullable(reseaux.get(nomReseau));
	}

	
	/**
	 * obtient les reseaux.
	 * 
	 * @return les reseaux.
	 */
	public Map<String, Reseau> getReseaux() {
		return reseaux;
	}
}
