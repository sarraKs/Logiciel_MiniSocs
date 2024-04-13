//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

/**
 * Cette classe réalise le concept d'utilisateur du système, à ne pas confondre
 * avec le concept de participant, sous-entendu à un réséeau social.
 * 
 * @author Denis Conan
 */
public class Utilisateur {
	/**
	 * le pseudonyme de l'utilisateur.
	 */
	private final String pseudonyme;
	/**
	 * le nom de l'utilisateur.
	 */
	private String nom;
	/**
	 * le prénom de l'utilisateur.
	 */
	private String prenom;
	/**
	 * l'adresse courriel de l'utilisateur.
	 */
	private String courriel;
	/**
	 * état du compte de l'utilisateur.
	 */
	private EtatCompte etatCompte;
	/**
	 * les membres.
	 */
	private final Map<String, Membre> membres;

	/**
	 * consommateur de notifications.
	 */
	private MonConsommateur monConsommateur;
	
	/**
	 * construit un utilisateur.
	 * 
	 * @param pseudonyme le pseudonyme.
	 * @param nom        le nom.
	 * @param prenom     le prénom.
	 * @param courriel   l'adresse courriel de l'utilisateur.
	 */
	public Utilisateur(final String pseudonyme, final String nom, final String prenom, final String courriel) {
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new IllegalArgumentException("pseudonyme ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new IllegalArgumentException("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new IllegalArgumentException("prenom ne peut pas être null ou vide");
		}
		if (!EmailValidator.getInstance().isValid(courriel)) {
			throw new IllegalArgumentException("courriel ne respecte pas le standard RFC822");
		}
		this.pseudonyme = pseudonyme;
		this.nom = nom;
		this.prenom = prenom;
		this.courriel = courriel;
		this.etatCompte = EtatCompte.ACTIF;
		this.membres = new HashMap<>();
		this.monConsommateur = null;
		assert invariant();
	}

	/**
	 * rendre l'utilisateur consommateur.
	 */
	public void rendreConsommateur(final MonConsommateur monConsommateur) {
		this.monConsommateur = monConsommateur;
	}
	
	
	/**
	 * vérifie l'invariant de la classe.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return pseudonyme != null && !pseudonyme.isBlank() && nom != null && !nom.isBlank() && prenom != null
				&& !prenom.isBlank() && EmailValidator.getInstance().isValid(courriel) && etatCompte != null;
	}

	/**
	 * obtient le pseudonyme.
	 * 
	 * @return le pseudonyme.
	 */
	public String getPseudonyme() {
		return pseudonyme;
	}

	/**
	 * l'état du compte.
	 * 
	 * @return l'énumérateur.
	 */
	public EtatCompte getEtatCompte() {
		return etatCompte;
	}

	/**
	 * rend inactif le compte de l'utilisateur. L'opération est
	 * idempotente. L'opération est refusée si le compte n'est pas actif.
	 */
	public void desactiverCompte() {
		if (etatCompte.equals(EtatCompte.DESACTIVE)) {
			return;
		}
		if (!etatCompte.equals(EtatCompte.ACTIF)) {
			throw new IllegalStateException("le compte n'est pas actif");
		}
		this.etatCompte = EtatCompte.DESACTIVE;
		assert invariant();
	}

	/**
	 * bloque le compte de l'utilisateur. L'opération est idempotente.
	 */
	public void bloquerCompte() {
		this.etatCompte = EtatCompte.BLOQUE;
		assert invariant();
	}
	
	/**
	 * retourne l'etat du membre (moderateur ou pas)
	 * @return 
	 * @throws OperationImpossible 
	 */
	public EtatMembre monEtatMembre(final String pseudo_membre) throws OperationImpossible {
		
		Membre m = membres.get(pseudo_membre);
		if (m == null) {
			throw new OperationImpossible("membre inexistant avec ce pseudo : " + pseudo_membre);
		}
		return m.getEtatMembre();
	}
	
	/**
	 * ajoute un membre 
	 */
	public void ajouterMembre(final String pseudo_membre, final Membre m) {
		
		this.membres.put(pseudo_membre, m);
		assert invariant();
	}

	/**
	 * liste les Membres.
	 * 
	 * @return la liste des pseudoMembre.
	 */
	public List<String> listerMembres() {
		return membres.values().stream().map(Membre::toString).toList();
	}
	
	/**
	 * getter des Membres.
	 * 
	 * @return la liste des membres.
	 */
	public Map<String, Membre> getMembre() {
	    return membres;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pseudonyme);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Utilisateur)) {
			return false;
		}
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(pseudonyme, other.pseudonyme);
	}

	@Override
	public String toString() {
		return "Utilisateur [pseudonyme=" + pseudonyme + ", nom=" + nom + ", prenom=" + prenom + ", courriel="
				+ courriel + ", etatCompte=" + etatCompte + "]";
	}

	public MonConsommateur getMonConsommateur() {
		return monConsommateur;
	}
}
