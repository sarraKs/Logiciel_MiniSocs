// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.EtatCompte;
import eu.telecomsudparis.csc4102.minisocs.EtatMembre;
import eu.telecomsudparis.csc4102.minisocs.EtatReseau;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestAccepterMessage {
	private MiniSocs miniSocs;
	private String nomReseau;
	private String pseudonyme;
	private String pseudoMembre;
	private String idMessage;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		nomReseau = "reseau1";
		pseudonyme = "pseudonyme1";
		pseudoMembre = "pseudoMembre1";
		idMessage = "1";
		

		try {
			 miniSocs.ajouterUtilisateur(pseudonyme, "nom1", "prenom1", "bon@courriel.com");
			 }
		catch (OperationImpossible e) {
			 e.printStackTrace();
		}
		
		try {
			miniSocs.creerReseau(nomReseau, pseudonyme, pseudoMembre);
		}
		catch (OperationImpossible e) {
			e.printStackTrace();
		}
		
		try {
			miniSocs.posterMessage("contenu1", pseudonyme, pseudoMembre, nomReseau);
		}
		catch (OperationImpossible e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@AfterEach
	void tearDown() {
		miniSocs = null;
		nomReseau = null;
		pseudonyme = null;
		pseudoMembre = null;
		idMessage = null;
	}

	@Test
	void accepterMessageTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(null, pseudoMembre, idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage("", pseudoMembre, idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage("autrePseudonyme", pseudoMembre, idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest3() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.BLOQUE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).bloquerCompte();		
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest4() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.DESACTIVE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();		
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, nomReseau));
	}

	@Test
	void accepterMessageTest5Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, null, idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest5Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, "", idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest6() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, "autrePseudoMembre", idMessage, nomReseau));
	}
	
	@Test
	void accepterMessageTest7() throws Exception {
		Assertions.assertFalse(miniSocs.listerMembres(pseudonyme).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMembres(pseudonyme).size());
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(pseudoMembre));
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(EtatMembre.MODERATEUR.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).getMembre().get(pseudoMembre).rendreNonModerateur();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, nomReseau));
	}
	
	
	@Test
	void accepterMessageTest8Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, null));
	}
	
	@Test
	void accepterMessageTest8Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, ""));
	}
	
	@Test
	void accepterMessageTest9() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, "autreNomReseau"));
	}
	
	@Test
	void accepterMessageTest10() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.getReseaux().get(nomReseau).fermerReseau();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, nomReseau ));
	}
	
	
	@Test
	void accepterMessageTest11Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, null, nomReseau));
	}
	
	@Test
	void accepterMessageTest11Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, "", nomReseau));
	}
	
	@Test
	void accepterMessageTest12() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, "0", nomReseau));
	}
	
	//tester avec un message deja visible
	@Test
	void accepterMessageTest13et14() throws Exception {
		Assertions.assertFalse(miniSocs.listerMessages(nomReseau).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMessages(nomReseau).size());
		Assertions.assertTrue(miniSocs.listerMessages(nomReseau).get(0).contains(idMessage));
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.accepterMessage(pseudonyme, pseudoMembre, idMessage, nomReseau));
	}
	
	
	
}
