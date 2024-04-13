//CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs;


/**
 * Ce type énuméré modélise le status d'un message.
 */
public enum EtatMessage {
	/**
	 * le memssage est accepté mais visible
	 */
	VISIBLE("visible"),
	/**
	 * le memssage est accepté mais caché 
	 */
	CACHÉ ("caché"),
	//Ici nrmlm on doit ajouter un autre type enuméré pour Accepté qui est visibl eet caché 
	/**
	 * le message est refusé.
	 */
	Refusé ("refusé"),
	/**
	 * le message est en attente.
	 */
	EnAttente ("enAttente");
	
	/**
	 * le nom de l'état à afficher.
	 */
	private String nomMess;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	EtatMessage (final String nomMess) {
		this.nomMess = nomMess;
	}

	@Override
	public String toString() {
		return nomMess;
	}
}
