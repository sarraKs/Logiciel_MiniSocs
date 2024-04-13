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

class TestAjouterMembreAuReseau {
	private MiniSocs miniSocs;
	private String nomReseau;
	private String pseudonyme;
	private String monPseudoMembre;
	private String pseudonymeAjout;
	private String pseudoMembreAjout;
	private EtatMembre etatMembreAjout;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		nomReseau = "reseau1";
		pseudonyme = "utilisateur1";
		monPseudoMembre = "membre1";
		pseudonymeAjout = "utilisateurAjout";
		pseudoMembreAjout = "membreAjout";
		etatMembreAjout = EtatMembre.MEMBRE;
		try {
			miniSocs.ajouterUtilisateur(pseudonyme, "nom", "prenom", "bon@courriel.fr");
		}
		catch (OperationImpossible e) {
			e.printStackTrace();
		}
		
		try {
			miniSocs.ajouterUtilisateur(pseudonymeAjout, "nom1", "prenom1", "bon1@courriel.fr");
		}
		catch (OperationImpossible e) {
			e.printStackTrace();
		}
		//ajout du membre monPseudoMembre se fait dans creer reseau
		
		try {
			miniSocs.creerReseau(nomReseau, pseudonyme, monPseudoMembre);
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
		monPseudoMembre = null;
		pseudoMembreAjout = null;
		pseudoMembreAjout = null;
		etatMembreAjout = null;
	}

	@Test
	void AjouterMembreAuReseauTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(null, pseudonyme, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau("", pseudonyme, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest2() throws Exception {
		Assertions.assertFalse(miniSocs.listerReseaux().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseaux().size());
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(nomReseau));
		Assertions.assertTrue(miniSocs.listerReseaux().get(0).contains(EtatReseau.OUVERT.toString()));
		miniSocs.getReseaux().get(nomReseau).fermerReseau();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest3() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau("autreReseau", pseudonyme, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	
	@Test
	void AjouterMembreAuReseauTest4Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, null, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest4Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, "", monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	
	@Test
	void AjouterMembreAuReseauTest5() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, "autrePseudonyme", monPseudoMembre,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest6() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(2, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(1).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(1).contains(EtatCompte.BLOQUE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).bloquerCompte();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest7() throws Exception {
		Assertions.assertFalse(miniSocs.listerUtilisateurs().isEmpty());
		Assertions.assertEquals(2, miniSocs.listerUtilisateurs().size());
		Assertions.assertTrue(miniSocs.listerUtilisateurs().get(1).contains(pseudonyme));
		Assertions.assertFalse(miniSocs.listerUtilisateurs().get(1).contains(EtatCompte.DESACTIVE.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).desactiverCompte();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	
	@Test
	void AjouterMembreAuReseauTest8Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , null,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest8Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , "",  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest9() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, "autreMonPseudoMembre", pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest10() throws Exception {
		Assertions.assertFalse(miniSocs.listerMembres(pseudonyme).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMembres(pseudonyme).size());
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(monPseudoMembre));
		Assertions.assertTrue(miniSocs.listerMembres(pseudonyme).get(0).contains(EtatMembre.MODERATEUR.toString()));
		miniSocs.getUtilisateurs().get(pseudonyme).getMembre().get(monPseudoMembre).rendreNonModerateur();
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest11Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , monPseudoMembre, null, pseudoMembreAjout , etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest11Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , monPseudoMembre, "" , pseudoMembreAjout , etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest12Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , monPseudoMembre, pseudonymeAjout, null , etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest12Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , monPseudoMembre, pseudonymeAjout, "" , etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest13Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme , monPseudoMembre, pseudonymeAjout, pseudoMembreAjout , null));
	}
	
	@Test
	void AjouterMembreAuReseauTest14() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre,  "autrepseudonymeAjout", pseudoMembreAjout, etatMembreAjout));
	}
	
	@Test
	void AjouterMembreAuReseauTest15Puis16() throws Exception {
		Assertions.assertTrue(miniSocs.listerMembres(pseudonymeAjout).isEmpty());
		miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre, pseudonymeAjout, pseudoMembreAjout, etatMembreAjout);
		Assertions.assertFalse(miniSocs.listerMembres(pseudonymeAjout).isEmpty());
		Assertions.assertEquals(1, miniSocs.listerMembres(pseudonymeAjout).size());
		Assertions.assertTrue(miniSocs.listerMembres(pseudonymeAjout).get(0).contains(pseudoMembreAjout));
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreAuReseau(nomReseau, pseudonyme, monPseudoMembre,  pseudonymeAjout, pseudoMembreAjout, etatMembreAjout));
	}
}
