// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.EtatCompte;
import eu.telecomsudparis.csc4102.minisocs.EtatReseau;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestPosterMessage {
	private MiniSocs miniSocs;
	private String contenu;
	private String nomReseau;
	private String pseudonyme;
	private String pseudo_membre;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		pseudonyme = "pseudonyme1";
		contenu = "contenu1";
		nomReseau = "reseau1";
		pseudo_membre = "membre1";
		
		try {
			 miniSocs.ajouterUtilisateur(pseudonyme, "nom1", "prenom1", "user1@gmail.com");
			 }
		catch (OperationImpossible e) {
			 e.printStackTrace();
		}
		
		try {
			miniSocs.creerReseau(nomReseau, pseudonyme, pseudo_membre);
		}
		catch (OperationImpossible e) {
			e.printStackTrace();
		}
		
	}

	@AfterEach
	void tearDown() {
		miniSocs = null;
		contenu = null;
		nomReseau = null;
		pseudonyme = null;
		pseudo_membre = null;
	}
	
	//utiliser un nom de réseau qui n'existe pas
	@Test
	void PosterMessageTest1() throws Exception {
	     Assertions.assertThrows(OperationImpossible.class,
	               () -> miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, "autreNomReseau"));
	}
	
	//tester le nom réseau s'il est non null et non vide 
	@Test
	void PosterMessageTest2Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, null));
	}

	@Test
	void PosterMessageTest2Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, ""));
	}

	//utiliser un reseau fermé
	@Test
	void PosterMessageTest3() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.getReseaux().get(nomReseau).fermerReseau();
	    Assertions.assertThrows(OperationImpossible.class,
	         () -> miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, nomReseau));
	}
	
	
	//tester avec un utilisateur qui n'existe pas (le pseudonyme n'existe pas)
	@Test
	void PosterMessageTest4() throws Exception {
	     Assertions.assertThrows(OperationImpossible.class,
	         () -> miniSocs.posterMessage(contenu, "autrePseudonyme", pseudo_membre, nomReseau));
	}
	
	
	//utiliser un utilisateur ayant un compte non actif 
	@Test
	void PosterMessageTest5() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.ACTIF.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();
	    Assertions.assertThrows(OperationImpossible.class,
	         () -> miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, nomReseau));
	}
	
		
	//Tester si le pseudonyme est non null et non vide 
	@Test
	void PosterMessageTest6Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, null , pseudo_membre, nomReseau));
	}
	
	@Test
	void PosterMessageTest6Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, "" , pseudo_membre, nomReseau));
	}
	
	//tester si le pseudoMembre est non null et non vide 
	@Test
	void PosterMessageTest7Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, pseudonyme , null, nomReseau));
	}
	
	@Test
	void PosterMessageTest7Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, pseudonyme , "", nomReseau));
	}
	
	//utiliser un pseudoMembre inexistant
	@Test
	void PosterMessageTest8() throws Exception {
	    Assertions.assertThrows(OperationImpossible.class,
	         () -> miniSocs.posterMessage(contenu, pseudonyme, "autre_pseudo_membre", nomReseau));
	}
	
	//Tester si le contenu du message est non null et non vide 
	@Test
	void PosterMessageTest9Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(null, pseudonyme, pseudo_membre, nomReseau));
	}
		
	@Test
	void PosterMessageTest9Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage("", pseudonyme , pseudo_membre, nomReseau));
	}
	
	//pour ce test, on n'a rien à verifier car un message peut etre posté plusieurs fois, il n'aura jamais le meme id
	//on pourrait verifier que le nombre de messages a augmenté
	/*
	@Test
	void PosterMessageTest10() throws Exception {
		miniSocs.posterMessage(contenu, pseudonyme, pseudo_membre, nomReseau);
		Assertions.assertFalse(miniSocs.listerMessages(nomReseau).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMessages(nomReseau).size());
		Assertions.assertTrue(miniSocs.listerMessages(nomReseau).get(0).contains("1"));
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.posterMessage(contenu, pseudonyme , pseudo_membre, nomReseau));
	}
	*/
	
	
}
