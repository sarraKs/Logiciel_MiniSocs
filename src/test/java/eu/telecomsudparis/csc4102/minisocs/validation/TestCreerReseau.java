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

class TestCreerReseau {
	private MiniSocs miniSocs;
	private String nomReseau;
	private String pseudonyme;
	private String pseudo_membre;
	private String nom;
	private String prenom;
	private String courriel;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		nomReseau = "reseau1";
		pseudonyme = "utilisateur1";
		pseudo_membre = "membre1";
		nom = "klabi";
		prenom= "yakine";
		courriel = "yakine.klabi@gmail.com";

		try {
			 miniSocs.ajouterUtilisateur(pseudonyme, nom, prenom, courriel);
			 }
		catch (OperationImpossible e) {
			 e.printStackTrace();
		}
		
	}

	@AfterEach
	void tearDown() {
		miniSocs = null;
		nomReseau = null;
		pseudonyme = null;
		pseudo_membre = null;
		nom = null;
		prenom= null;
		courriel = null;
	}

	@Test
	void creerReseauTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(null, pseudonyme, pseudo_membre));
	}

	@Test
	void creerReseauTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau("", pseudonyme, pseudo_membre));
	}

	
	@Test
	void creerReseauTest2Puis8() throws Exception {
		Assertions.assertTrue(miniSocs.listerReseaux().isEmpty());
		miniSocs.creerReseau(nomReseau, pseudonyme, pseudo_membre);
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, pseudonyme, pseudo_membre));
	}
	
	
	@Test
	void creerReseauTest3() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, "autrePseudonyme", pseudo_membre));
	}
	
	
	@Test
	void creerReseauTest4Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, null, pseudo_membre));
	}
	
	@Test
	void creerReseauTest4Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, "", pseudo_membre));
	}
	
	@Test
	void creerReseauTest5Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, pseudonyme, null));
	}
	
	@Test
	void creerReseauTest5Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, pseudonyme, ""));
	}
	
	@Test
	void creerReseauTest6() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.BLOQUE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).bloquerCompte();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, pseudonyme, pseudo_membre));
	}
	
	@Test
	void creerReseauTest7() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.DESACTIVE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseau(nomReseau, pseudonyme, pseudo_membre));
	}
	
}
