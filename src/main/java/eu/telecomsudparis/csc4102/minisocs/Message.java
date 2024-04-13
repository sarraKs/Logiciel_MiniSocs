
package eu.telecomsudparis.csc4102.minisocs;

import java.util.Objects;


import java.time.LocalDateTime;

/**
 * Cette classe est la façade du logiciel.
 * 
 * @author Yakine Klabi
 */
public class Message {
	/**
	 * l'id du message.
	 */
	private String id;
	/**
	 * la date de postage du message.
	 */
	private LocalDateTime date;
	/**
	 * le contenu du message.
	 */
	private String contenu;
	/**
	 * l'état du message.
	 */
	private EtatMessage etatMessage;
	/**
	 * l'id du dernier msg posté.
	 */
	private static int dernierIdUtilise = 0;


	/**
	 * construit une instance du message.
	 * 
	 * @param contenu le contenu du messgae.
	 * @param etatMessage l'état du message 
	*/
	public Message(final String contenu, final EtatMessage etatMessage) {
		
		if (contenu == null || contenu.isBlank()) {
			throw new IllegalArgumentException("le contenu d'un message ne peut pas etre ni vide ni null");
		}
		if (etatMessage == null) {
			throw new IllegalArgumentException("l'état d'un message ne peut pas etre null");
		}
		this.id = String.valueOf(++dernierIdUtilise);
		this.date = LocalDateTime.now();
		this.contenu = contenu;
		this.etatMessage = etatMessage;
		
		assert invariant();
	}


	/**
	 * l'invariant de la façade.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return contenu  != null  && !contenu.isBlank()  && etatMessage != null && id != null && date != null;
	}


	/**
	 * obtient le contenu.
	 * 
	 * @return le contenu.
	 */
	public String getContenu() {
		return contenu;
	}
	/**
	 * obtient l'état du message.
	 * 
	 * @return l'état du message
	 */
	
	public EtatMessage getEtatMessage() {
		return etatMessage;
	}
	
	 /**
	 * accepter le message posté du membre.
	 */
	public void accepterMessage() {
				
		if (etatMessage.equals(EtatMessage.VISIBLE)) {
			return;
		}
		if (!etatMessage.equals(EtatMessage.EnAttente)) {
			throw new IllegalStateException("le message doit etre en attente pour pouvoir etre accepté");
		}
		
		this.etatMessage = EtatMessage.VISIBLE;
		assert invariant();
	}
	
	 /**
	 * refuser le message posté du membre.
	 */
	public void refuserMessage() {
		
		if (etatMessage.equals(EtatMessage.Refusé)) {
			return;
		}
		if (!etatMessage.equals(EtatMessage.EnAttente)) {
			throw new IllegalStateException("le message doit etre en attente pour pouvoir etre refusé");
		}
		
		this.etatMessage = EtatMessage.Refusé;
		assert invariant();
	}
	
	 /**
	 * cacher le message posté du membre.
	 */
	public void cacherMessage() {
		
		if (etatMessage.equals(EtatMessage.CACHÉ)) {
			return;
		}
		if (!etatMessage.equals(EtatMessage.VISIBLE)) {
			throw new IllegalStateException("le message doit etre visible (donc accepté) pour pouvoir etre caché");
		}
		
		this.etatMessage = EtatMessage.CACHÉ;
		assert invariant();
	}
	
	 /**
	 * rendre visible le message posté du membre. 
	 */
	public void rendreVisibleMessage() {
		
		if (etatMessage.equals(EtatMessage.VISIBLE)) {
			return;
		}
		if (!etatMessage.equals(EtatMessage.CACHÉ)) {
			throw new IllegalStateException("le message doit etre caché (donc accepté) pour pouvoir etre rendu visible");
		}
		
		this.etatMessage = EtatMessage.VISIBLE;
		assert invariant();
	}
	
	@Override
	public String toString() {
		return "Message [id= " + id + " date= " + date + " contenu=" + contenu + " , etatMessage= " + etatMessage + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(contenu, date);
	}


	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Message other = (Message) obj;
		return Objects.equals(contenu, other.contenu) && Objects.equals(date, other.date);
	}

	
	
	
}
