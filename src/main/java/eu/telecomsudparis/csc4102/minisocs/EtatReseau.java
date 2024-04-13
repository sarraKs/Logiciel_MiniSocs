//CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs;
 

/**
 * Ce type énuméré modélise l'état du réseau
 */
public enum EtatReseau {
	/**
	 * le réseau est ouvert.
	 */
	OUVERT("ouvert"),
	/**
	 * Le réseau est fermé 
	 */
	FERMÉ("fermé");
	/**
	 * le nom de l'état à afficher.
	 */
	private String etatRes;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nomÉtat le nom de l'état.
	 */
	EtatReseau(final String etatRes) {
		this.etatRes = etatRes;
	}

	@Override
	public String toString() {
		return etatRes;
	}
}
