//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;

//import eu.telecomsudparis.csc4102.cours.seance8.patrondeconception.publiersouscrire.Publication;
//import eu.telecomsudparis.csc4102.cours.seance8.patrondeconception.publiersouscrire.Publication;
import eu.telecomsudparis.csc4102.util.OperationImpossible;



/**
 * Cette classe réalise le concept d'utilisateur du système, à ne pas confondre
 * avec le concept de participant, sous-entendu à un réséeau social.
 * @author Klabi Yakine
 */
public class Reseau {
	
	/**
	 * le nom du réseau.
	 */
	private  String nomRéseau;
	
	/**
	 * l'état du réseau.
	 */
	private EtatReseau etatRéseau;
	
	/**
	 * les membres
	 */
	private final Map<String, Membre> membres;

	/**
	 * les messages
	 */
	private final Map<String, Message> messages;
	
	/**
	 * producteur pour les notifs de messages en attente
	 */
	private SubmissionPublisher<Notification> producteurMessageEnAttente;
	
	/**
	 * producteur pour les notifs de messages postés
	 */
	private SubmissionPublisher<Notification> producteurMessagePoste;
	
	/**
	 * construit un réseau.
	 * 
	 * @param nom_réseau le nom du réseau 
	 */
	public Reseau(final String nomRéseau) {
		
		if (nomRéseau == null || nomRéseau.isBlank()) {
			throw new IllegalArgumentException("le nom de réseau ne peut pas être null ou vide");
		}
		
		this.nomRéseau= nomRéseau;
		this.etatRéseau= EtatReseau.OUVERT;
		this.membres= new HashMap<>();
		this.messages= new HashMap<>();
		this.producteurMessageEnAttente = new SubmissionPublisher<>();
		this.producteurMessagePoste = new SubmissionPublisher<>();
		assert invariant();
	}
	
	
	public SubmissionPublisher<Notification> getProducteurMessageEnAttente()
	{
		return producteurMessageEnAttente;
	}
	
	public SubmissionPublisher<Notification> getProducteurMessagePoste() {
		return producteurMessagePoste;
	}
	
	
	/**
	 * ajoute un membre au reseau.
	 */
	public void ajouterMembre(final String pseudo_membre, final Membre m) {
		
		this.membres.put(pseudo_membre, m);
		assert invariant();
	}
	

	/**
	 * vérifie l'invariant de la classe.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	
	public boolean invariant() {
		return nomRéseau != null && !nomRéseau.isBlank() && etatRéseau != null ;
	}

	/**
	 * obtient le nom du réseau.
	 * 
	 * @return le nom de ce réseau .
	 */
	public String getNom_Réseau() {
		return nomRéseau ;
	}

	/**
	 * l'état du réseau.
	 * 
	 * @return l'énumérateur.
	 */
	public EtatReseau getEtatRéseau() {
		return etatRéseau;
	}
	
	/**
	 * liste les membres du reseau.
	 * 
	 * @return la liste des pseudoMembres des membres.
	 */
	public List<String> listerMembres() {
		return membres.values().stream().map(Membre::toString).toList();
	}
	
	/**
	 * getter pour les membres du reseau.
	 * 
	 * @return les membres du reseau.
	 */
	public Map<String, Membre> getMembres() {
		return membres;
	}
	
	/**
	 * liste les messages dans le reseau.
	 * 
	 * @return la liste des messages dans ce reseau.
	 */
	public List<String> listerMessages() {
		return messages.values().stream().map(Message::toString).toList();
	}
	
	/**
	 * crée un message et l'ajoute au réseau 
	 * @throws OperationImpossible 
	 * @throws InterruptedException 
	 */
	public void posterMessageReseau(final String contenu, final String pseudo_membre) throws OperationImpossible, InterruptedException {
		Membre m = membres.get(pseudo_membre);
		if (m == null) {
			throw new OperationImpossible("Membre avec ce pseudonyme (" + pseudo_membre + " ) n'existe pas. ");
		}
		
		// associer un état au message en fonction de l'état du membre
		EtatMessage etatMessage;
		if (m.getEtatMembre().equals(EtatMembre.MODERATEUR)) 
		{
			etatMessage = EtatMessage.VISIBLE;
			//creer un nouveau message avec l'etatMessage correspondant et l'associer aux reseau et membre
			Message message=new Message(contenu, etatMessage);
			ajouterMessage(message);
			m.ajouterPostedMessage(message);
			producteurMessagePoste.submit(new Notification("Nouveau message! posté le " + LocalDateTime.now() + " <<" + contenu + ">> " ));
			
		} 
		else 
		{
			etatMessage = EtatMessage.EnAttente;
			//creer un nouveau message avec l'etatMessage correspondant et l'associer aux reseau et membre
			Message message=new Message(contenu, etatMessage);
			ajouterMessage(message);
			m.ajouterPostedMessage(message);
			producteurMessageEnAttente.submit(new Notification("Message en attente de modération! posté le " + LocalDateTime.now() + " <<" + contenu + ">> " ));
			
		}
				
		Thread.sleep(100);
	}
	
	/**
	 * ajoute un message existant au réseau 
	 */
	public void ajouterMessage(Message message) {
	    messages.put(message.getContenu(), message);
	}
	
	/**
	 * ferme le réseau 
	 */
	public void fermerReseau () {
		if(etatRéseau == EtatReseau.OUVERT) {
			etatRéseau = EtatReseau.FERMÉ;
		}
	}


	@Override
	public String toString() {
		return "Réseau [nom_Réseau=" + nomRéseau +  ", etatRéseau=" + etatRéseau + "]" ;
	}


	@Override
	public int hashCode() {
		return Objects.hash(nomRéseau);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reseau other = (Reseau) obj;
		return Objects.equals(nomRéseau, other.nomRéseau);
	}


	
}
