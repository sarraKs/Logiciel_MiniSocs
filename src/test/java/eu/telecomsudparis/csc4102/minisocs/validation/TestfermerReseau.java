// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.EtatCompte;
import eu.telecomsudparis.csc4102.minisocs.EtatReseau;
import eu.telecomsudparis.csc4102.minisocs.EtatMembre;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestfermerReseau {
	
	private MiniSocs miniSocs;
	private String nomReseau;
	private String pseudonyme;
	private String pseudo_membre;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		nomReseau ="reseau1";
		pseudonyme = "user1";
		pseudo_membre = "membre1";
		
		try {
			miniSocs.ajouterUtilisateur(pseudonyme, "nom", "prenom", "bon@courriel.fr");
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
		nomReseau = null;
		pseudonyme = null;
		pseudo_membre = null;
	}
	
	//tester avec un utilisateur inexistant
	@Test
	void fermerReseauTest1() throws Exception {
	     Assertions.assertThrows(OperationImpossible.class,
	         () -> miniSocs.fermerReseau("autrePseudonyme", pseudo_membre, nomReseau));
	}
	 
	//tester que le pseudonyme est non null et non vide  
	@Test
	void fermerReseauTest2Jeu1() throws Exception {
		 Assertions.assertThrows(OperationImpossible.class,
		     () -> miniSocs.fermerReseau(null, pseudo_membre, nomReseau));
	}
		
	@Test
	void fermerReseauTest2Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
			() -> miniSocs.fermerReseau("", pseudo_membre, nomReseau));
	}
	
	//tester avec un utilisateur non actif  
	@Test
	void fermerReseauTest3() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(pseudonyme));
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(0).contains(EtatCompte.ACTIF.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, nomReseau));
	}
	
	// Tester avec un membre non existant  
		@Test
		void fermerReseauTest4() throws Exception {
			Assertions.assertThrows(OperationImpossible.class,
			       () -> miniSocs.fermerReseau(pseudonyme, "autre_pseudo_membre", nomReseau));
		}
	
	// Tester avec un utilisateur non moderateur  
	@Test
	void fermerReseauTest5() throws Exception {
		Assertions.assertFalse(miniSocs.listerMembres(pseudonyme).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMembres(pseudonyme).size());
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(pseudo_membre));
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(EtatMembre.MODERATEUR.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).getMembre().get(pseudo_membre).rendreNonModerateur();
		Assertions.assertThrows(OperationImpossible.class,
		       () -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, nomReseau));
	}
	
		
	//pseudoMembre non null et non vide  
	@Test
	void fermerReseauTest6Jeu1() throws Exception {
		 Assertions.assertThrows(OperationImpossible.class,
		     () -> miniSocs.fermerReseau(pseudonyme, "", nomReseau));
	}

	@Test
	void fermerReseauTest6Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
			() -> miniSocs.fermerReseau(pseudonyme, null, nomReseau));
	}	
	

	//teste avec un reseau inexistant 
	@Test
	void fermerReseauTest7() throws Exception {	
	     Assertions.assertThrows(OperationImpossible.class,
	               () -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, "autreNomReseau"));
	}
	
	
	//teste si nomReseau est non null et non vide  
	@Test
	void fermerReseauTest8Jeu1() throws Exception {
		 Assertions.assertThrows(OperationImpossible.class,
		     () -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, null));
	}

	@Test
	void fermerReseauTest8Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
			() -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, ""));
	}	

	//teste avec un reseau deja fermé
	@Test
	void fermerReseauTest9() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.getReseaux().get(nomReseau).fermerReseau();
		Assertions.assertThrows(OperationImpossible.class,
			() -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, nomReseau));
	}	
	
	
	@Test
	void fermerReseauTest10() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.fermerReseau(pseudonyme, pseudo_membre, nomReseau);
		Assertions.assertThrows(OperationImpossible.class,
			() -> miniSocs.fermerReseau(pseudonyme, pseudo_membre, nomReseau));
	}	
	
	
	
	
}
