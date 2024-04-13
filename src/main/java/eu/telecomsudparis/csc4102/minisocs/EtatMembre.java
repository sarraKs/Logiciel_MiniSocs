//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

/**
 * Ce type énuméré modélise le status d'un membre, il est moderateur ou membre.
 */
public enum EtatMembre {
	/**
	 * le membre est non moderateur.
	 */
	MEMBRE("membre"),
	/**
	 * le membre est moderateur.
	 */
	MODERATEUR("modérateur");
	
	/**
	 * le nom de l'état à afficher.
	 */
	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	EtatMembre(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
