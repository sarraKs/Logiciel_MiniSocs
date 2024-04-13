// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.EtatMessage;
import eu.telecomsudparis.csc4102.minisocs.Message;

class TestMessage {

	@BeforeEach
	void setUp() {
	}
	@AfterEach
	void tearDown() {
	}

	@Test
	void constructeurMessageTest1Jeu1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Message (null, EtatMessage.EnAttente)) ;
	}

	@Test
	void constructeurMessageTest1Jeu2() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Message("", EtatMessage.EnAttente));
	}

	@Test
	void constructeurMessageTest2() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Message("contenu", null));
	}

	//Tester si le message est bien construit avec les arguments donnés
	//ce test peut etre fait avec l'état VISISBLE également
	@Test
	void constructeurMessageTest3() {
		Message message = new Message("contenu", EtatMessage.EnAttente);
		Assertions.assertNotNull(message);
		Assertions.assertEquals("contenu", message.getContenu());
		Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
	}
	
	
	
	/**On s'attend à ce qu'une IllegalStateException soit levée lors de la tentative d'acceptation du message, 
	 * car un message refusé ne peut pas être accepté. C'est pourquoi cette assertion vérifie que cette exception 
	 * est bien levée lorsque accepterMessage() est appelé après que le message ait été refusé.
	 */
	
	@Test
	void AccepterMessageTest1() {
			Message message = new Message("contenu", EtatMessage.EnAttente);
			Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
			message.refuserMessage();
			Assertions.assertEquals(EtatMessage.Refusé, message.getEtatMessage());
			Assertions.assertThrows(IllegalStateException.class, () -> message.accepterMessage());
	}
	
	@Test
	void AccepterMessageTest2() {
		Message message = new Message("contenu", EtatMessage.EnAttente);
		Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
		message.accepterMessage();
		Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
		message.accepterMessage();
		Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
	}
	
	@Test
	void RefuserMessageTest1() {
			Message message = new Message("contenu", EtatMessage.EnAttente);
			Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
			message.accepterMessage();
			Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
			Assertions.assertThrows(IllegalStateException.class, () -> message.refuserMessage());
	}
	@Test
	void RefuserMessageTest2() {
			Message message = new Message("contenu", EtatMessage.EnAttente);
			Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
			message.refuserMessage();
			Assertions.assertEquals(EtatMessage.Refusé, message.getEtatMessage());
			message.refuserMessage();
			Assertions.assertEquals(EtatMessage.Refusé, message.getEtatMessage());
	}
	
	
	//tester rendreVisible un message refusé
	@Test
	void RendreMessageVisibleTest1() {
		Message message = new Message("contenu", EtatMessage.EnAttente);
		Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
		message.refuserMessage();
		Assertions.assertEquals(EtatMessage.Refusé, message.getEtatMessage());
		Assertions.assertThrows(IllegalStateException.class, () -> message.rendreVisibleMessage());
	}
	
	@Test
	void RendreMessageVisibleTest2() {
		Message message = new Message("contenu", EtatMessage.CACHÉ);
		Assertions.assertEquals(EtatMessage.CACHÉ, message.getEtatMessage());
		message.rendreVisibleMessage();
		Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
		message.rendreVisibleMessage();
		Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
	}
	

	@Test
	void CacherMessageTest1() {
		Message message = new Message("contenu", EtatMessage.EnAttente);
		Assertions.assertEquals(EtatMessage.EnAttente, message.getEtatMessage());
		message.refuserMessage();
		Assertions.assertEquals(EtatMessage.Refusé, message.getEtatMessage());
		Assertions.assertThrows(IllegalStateException.class, () -> message.cacherMessage());
	}
	
	@Test
	void CacherMessageTest2() {
		Message message = new Message("contenu", EtatMessage.VISIBLE);
		Assertions.assertEquals(EtatMessage.VISIBLE, message.getEtatMessage());
		message.cacherMessage();
		Assertions.assertEquals(EtatMessage.CACHÉ, message.getEtatMessage());
		message.cacherMessage();
		Assertions.assertEquals(EtatMessage.CACHÉ, message.getEtatMessage());
	}
		
	

}
