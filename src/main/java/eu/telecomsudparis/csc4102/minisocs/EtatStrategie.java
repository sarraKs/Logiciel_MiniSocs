//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

/**
 * Ce type énuméré modélise la strategie de reception de notification par un consommateur.
 */
public enum EtatStrategie {
	/**
	 * l'utilisateur reçoit chaque notification immédiatement.
	 */
	IMMEDIAT("immédiat"),
	/**
	 * l'utilisateur reçoit une notification par jour.
	 */
	QUOTIDIEN("quotidien"),
	/**
	 * l'utilisateur ne reçoit aucune notification.
	 */
	PASDENOTIF("pas de notifications");

	/**
	 * le nom de l'état à afficher.
	 */
	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	EtatStrategie(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
