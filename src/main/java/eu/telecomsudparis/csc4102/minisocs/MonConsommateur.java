//CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * Cette classe définit le consommateur. Les notifications sont typées.
 * 
 * @author Denis Conan
 */
public class MonConsommateur implements Subscriber<Notification> {
	/**
	 * identifiant du consommateur : pour les messages a la console qui servent a
	 * suivre l'execution.
	 */
	private String id;
	/**
	 * la souscription. Cet objet sert a controler le flux entre le producteur et le
	 * consommateur.
	 */
	private Subscription souscription;
  
	/**
	 * la strategie de reception de messages.
	 */
	private EtatStrategie strategie;
	
	/**
	 * instant du dernier message posté.
	 */
	private Instant lastMessageTime = null;
	 
 
  // on pourrait ajouter une collection pour garder les publications recues

	/**
	 * constructeur.
	 * 
	 * @param id identifiant pour les affichages.
	 */
  public MonConsommateur(final String id, final EtatStrategie etatStrategie) { // ...
		this.id = id;
		this.strategie = etatStrategie;
		
  }

  @Override 
  public void onSubscribe(final Subscription souscription) {
    this.souscription = souscription;
    // on consomme un message des qu'il arrive ; un a la fois
    // on declare qu'on est pret a recevoir un message
    this.souscription.request(1);
	System.out.println("Consommateur " + id + " pret a recevoir la premiere publication");
  }

  @Override
  public void onNext(final Notification publication) {
	  
	  Instant now = Instant.now();
	  
	  switch (strategie) {
      case IMMEDIAT:
    	// reception d'une publication...
    	  System.out.println("Consommateur " + id + " a recu une nouvelle publication : " + publication);
          souscription.request(1); 
          break;
      case QUOTIDIEN:
    	  if (lastMessageTime == null || ChronoUnit.DAYS.between(lastMessageTime, now) >= 1) {
              // Il s'est écoulé un jour ou plus depuis le dernier message, ou aucun message n'a été reçu auparavant.
    		  // reception d'une publication...
    		  System.out.println("Consommateur " + id + " a recu une nouvelle publication : " + publication);
              souscription.request(1);
              lastMessageTime = now;
          } 
    	  break;
      case PASDENOTIF:
          // demander aucune notification
          souscription.cancel();
          break;
      default:
          System.out.println("Stratégie de notification invalide.");
  }
	  
  }

  @Override
  public void onError(final Throwable throwable) { //...
    // erreur sur la gestion du flux, par exemple producteur.subscribe
    // d'un consommateur qui est deja un subscriber du producteur
    throwable.printStackTrace();
  }

  @Override
  public void onComplete() { // lorsque le producteur ferme le flux
		// on affiche la fin a la console
		System.out.println("Consommateur " + id + " est desabonne suite a la fermeture du flux par le producteur");
	}

}
