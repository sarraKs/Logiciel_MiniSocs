//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

import java.util.Objects;

/**
 * Cette classe definit le type des publications transmises entre les
 * producteurs et les consommateurs.
 * 
 * @author Denis Conan
 */
public class Notification {
	/**
	 * l'information.
	 */
	private String contenu;
	
	/**
	 * constructeur.
	 */
	public Notification(final String contenu) {
		this.contenu = contenu;
	}

	/**
	 * obtient le contenu.
	 * @return la chaîne de caractères.
	 */
	public String getContenu() {
		return contenu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contenu);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Notification other)) {
			return false;
		}
		return Objects.equals(contenu, other.contenu);
	}

	@Override
	public String toString() {
		return "Notification [contenu=" + contenu + "]";
	}
}
