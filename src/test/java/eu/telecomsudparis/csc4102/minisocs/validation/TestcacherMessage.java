//CHECKSTYLE:OFF
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

class TestcacherMessage {
	
	private MiniSocs miniSocs;
	private String idMessage;
	private String pseudonyme;
    private String pseudoMembre;
    private String nomReseau;

	@BeforeEach
	void setUp() throws OperationImpossible {
		miniSocs = new MiniSocs("MiniSocs");
		idMessage = "1";
		pseudonyme = "pseudo1";
		pseudoMembre = "pseudomembre1";
		nomReseau = "nomReseau1";
		
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
		
		try {
			 miniSocs.ajouterUtilisateur("pseudonyme2", "nom2", "prenom2", "bon2@courriel.com");
			 }
		catch (OperationImpossible e) {
			 e.printStackTrace();
		}
		
		try {
			 miniSocs.ajouterMembreAuReseau(nomReseau,pseudonyme, pseudoMembre, "pseudonyme2", "pseudoMembre2", EtatMembre.MEMBRE);
			 }
		catch (OperationImpossible e) {
			 e.printStackTrace();
		}
		
		try {
			miniSocs.posterMessage("contenu2", "pseudonyme2", "pseudoMembre2", nomReseau);
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
		pseudonyme = null;
		pseudoMembre = null;
		nomReseau = null;
		idMessage=null;
	}
	
	@Test
	void cacherMessageTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(null, pseudoMembre, nomReseau, idMessage));
	}
	
	@Test
	void cacherMessageTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage("", pseudoMembre, nomReseau, idMessage));
	}
	@Test
	void cacherMessageTest2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage("autrepseudo", pseudoMembre, nomReseau, idMessage));
	}
	
	@Test
	void cacherMessageTest3() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.BLOQUE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).bloquerCompte();	
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, idMessage));
	}
	@Test
	void cacherMessageTest4() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.DESACTIVE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();	
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, idMessage));
	}
	@Test
	void cacherMessageTest5Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, null, nomReseau, idMessage));
	}
	
	@Test
	void cacherMessageTest5Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, "", nomReseau, idMessage));
	}
	@Test
	void cacherMessageTest6() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, "autrepseudomembre", nomReseau, idMessage));
	}
	@Test
	void cacherMessageTest7Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, null, idMessage));
	}
	@Test
	void cacherMessageTest7Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, "", idMessage));
	}
	@Test
	void cacherMessageTest8() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, "autreNomreseau", idMessage));
	}
	@Test
	void cacherMessageTest9() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.getReseaux().get(nomReseau).fermerReseau();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, idMessage));
	}
	
	@Test
	void cacherMessageTest10Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, null));
	}
	@Test
	void cacherMessageTest10Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, ""));
	}
	@Test
	void cacherMessageTest11() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, "0"));
	}
	
	//tester avec un message non visible (en attente)
	@Test
	void cacherMessageTest12() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, "2"));
	}
	
	//tester avec un message deja caché
	@Test
	void cacherMessageTest13() throws Exception {
		miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, idMessage);
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.cacherMessage(pseudonyme, pseudoMembre, nomReseau, idMessage));
	}
	
}
