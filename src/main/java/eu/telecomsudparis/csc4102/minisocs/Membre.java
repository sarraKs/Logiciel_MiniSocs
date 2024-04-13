//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Cette classe réalise le concept de membre d'un réseau social. Ce membre relie un utilisateur 
 * à un seul réseau.
 * 
 * @author Denis Conan
 */
public class Membre {
	/**
	 * le pseudonyme du membre.
	 */
	private final String pseudo_membre;
	/**
	 * état du membre (membre ou moderateur).
	 */
	private EtatMembre etatMembre;
	/**
	 * les messages postés par le membre
	 */
	private final Map<String, Message> PostedMessages;

	/**
	 * construit un membre.
	 * 
	 * @param pseudo_membre le pseudonyme du membre dans le reseau.
	 */
	public Membre(final String pseudo_membre, final EtatMembre etatMembre) {
		
		if (pseudo_membre == null || pseudo_membre.isBlank()) {
			throw new IllegalArgumentException("pseudo_membre ne peut pas être null ou vide");
		}
		this.pseudo_membre = pseudo_membre;
		this.etatMembre = etatMembre;
		this.PostedMessages= new HashMap<>();
		
		assert invariant();
	}

	/**
	 * vérifie l'invariant de la classe.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return pseudo_membre != null && !pseudo_membre.isBlank()
				&& etatMembre != null;
	}

	/**
	 * obtient le pseudonyme.
	 * 
	 * @return le pseudonyme.
	 */
	public String getPseudo_membre() {
		return pseudo_membre;
	}

	/**
	 * le status du membre.
	 * 
	 * @return l'énumérateur.
	 */
	public EtatMembre getEtatMembre() {
		return etatMembre;
	}

	/**
	 * ajoute un message existant au membre 
	 */
	public void ajouterPostedMessage(Message message) {
	    PostedMessages.put(message.getContenu(), message);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(pseudo_membre);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Membre)) {
			return false;
		}
		Membre other = (Membre) obj;
		return Objects.equals(pseudo_membre, other.pseudo_membre);
	}
	
	/**
	 * liste les messages postés du membre.
	 * 
	 * @return la liste des messages postés du membre.
	 */
	public List<String> listerMessages() {
		return PostedMessages.values().stream().map(Message::toString).toList();
	}
	
	/**
	 * rendre moderateur.
	 */
	public void rendreModerateur() {
		this.etatMembre = EtatMembre.MODERATEUR;
		assert invariant();
	}
	
	/**
	 * rendre non moderateur.
	 */
	public void rendreNonModerateur() {
		this.etatMembre = EtatMembre.MEMBRE;
		assert invariant();
	}
	
	
	
	/**
	 * getter des messages postés du membre.
	 * 
	 * @return les messages postés du membre.
	 */
	public Map<String, Message> getPostedMessages() {
		return PostedMessages;
	}

	@Override
	public String toString() {
		return "Membre [pseudo_membre=" + pseudo_membre 
				+ ", etatMembre=" + etatMembre + "]";
	}
}
