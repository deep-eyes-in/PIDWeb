package com.isfce.pidw.web.ctrl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.web.CentralExceptionHandle;
import com.isfce.pidw.web.CoursController;
import com.isfce.pidw.web.DuplicateException;

public class CoursControllerTest {
	@Autowired
	ICoursJpaDAO mockDAO;
	CoursController controller;

	@Before
	public void initMockDAO() {
		Cours coursPID = new Cours("PID", "Projet de développement", (short) 60);
		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);
		when(mockDAO.exists("PID")).thenReturn(true);
		when(mockDAO.findAll()).thenReturn(Arrays.asList(coursPID));
		when(mockDAO.findOne("PID")).thenReturn(coursPID);
		controller=new CoursController(mockDAO);

	}

	@Test
	public void testListeCours() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/cours/liste")).andExpect(view().name("cours/listeCours"));
	}

	@Test
	public void testAddCoursGetURL() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/cours/add")).andExpect(view().name("cours/addCours"));

	}

	@Test
	public void testAddCoursGet() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/cours/add")).andExpect(view().name("cours/addCours"));

	}

	@Test
	public void testDetailCours() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/cours/PID")).andExpect(view().name("cours/cours"));
	}

	@Test
	public void testUpdateCoursGetPost() throws Exception {
		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);
		Cours oldSaved = new Cours("SO2", "Structure", (short) 40);
		Cours newSaved = new Cours("ISO2", "Structure des ordinateurs", (short) 60);
		newSaved.setLangue("Français");
		when(mockDAO.findOne("SO2")).thenReturn(oldSaved);
		when(mockDAO.exists("SO2")).thenReturn(true);
		when(mockDAO.exists("ISO2")).thenReturn(false);
		when(mockDAO.save(newSaved)).thenReturn(newSaved);

		CoursController controller = new CoursController(mockDAO);

		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/cours/SO2/update")).andExpect(view().name("cours/addCours"));

		mockMvc.perform(post("/cours/add").param("code", "ISO2").param("nom", "Structure des ordinateurs")
				.param("nbPeriodes", "60").param("langue", "Français").param("savedId", "SO2"))
				.andExpect(redirectedUrl("/cours/ISO2"));

		verify(mockDAO, atLeastOnce()).save(newSaved);
		verify(mockDAO, atLeastOnce()).delete(oldSaved.getCode());

	}

	@Test

	public void testAddCoursBadData() throws Exception {

		Cours saved = new Cours("SO2", "Structure", (short) 40);

		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);
		when(mockDAO.exists("SO2")).thenReturn(true);
		when(mockDAO.findOne("SO2")).thenReturn(saved);

		CoursController controller = new CoursController(mockDAO);
		MockMvc mockMvc = standaloneSetup(controller)
				// .setValidator(validator())
				// .setViewResolvers(viewResolver())
				.build();

		// Test avec les 3 champs en erreur et retour à la vue d'encodage
		mockMvc.perform(post("/cours/add").param("code", "I").param("nom", "S").param("nbPeriodes", "-1"))
				.andExpect(view().name("cours/addCours")).andExpect(model().errorCount(3))
				.andExpect(model().attributeHasFieldErrors("cours", "code", "nom", "nbPeriodes"))
				.andExpect(status().isOk());

		// Test avec les 3 champs vides et le type de message pour chaque champ et
		// retour à la vue d'encodage
		mockMvc.perform(post("/cours/add").param("code", "    ")).andExpect(view().name("cours/addCours"))
				.andExpect((model().attributeHasFieldErrorCode("cours", "nom", "NotNull")))
				.andExpect((model().attributeHasFieldErrorCode("cours", "code", "Pattern")))
				.andExpect((model().attributeHasFieldErrorCode("cours", "nbPeriodes", "Min")))
				.andExpect(status().isOk());

	}

	// private Validator validator() {
	//
	// Validator validator = new LocalValidatorFactoryBean();
	// return validator;
	// }

	// private ViewResolver viewResolver() {
	// InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	// // spécifie le chemin des vues
	// resolver.setPrefix("/WEB-INF/views/");
	// // spécifie l'extension .jsp au nom logique
	// resolver.setSuffix(".jsp");
	// // rends les beans du contexte accessible dans les pages JSP avec ${...}
	// resolver.setExposeContextBeansAsAttributes(true);
	// // Si on utilise JSTL. permet d'utiliser les locales dans les vues
	// resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
	// return resolver;
	// }

	@Test
	public void testCoursInconnu() throws Exception {
		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);

		when(mockDAO.exists("BROL")).thenReturn(false);

		CoursController controller = new CoursController(mockDAO);
		MockMvc mockMvc = standaloneSetup(controller).build();

		mockMvc.perform(get("/cours/BROL/update")).andExpect(status().isNotFound());
		mockMvc.perform(post("/cours/BROL/delete")).andExpect(status().isNotFound());
		mockMvc.perform(get("/cours/BROL")).andExpect(status().isNotFound());
	}

	@Test
	public void testDuplicateCours() throws Exception {

		Cours aModif = new Cours("IBD", "Projet d'intégration", (short) 60);
		Cours saved = new Cours("PID", "Projet d'intégration", (short) 60);
		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);
		when(mockDAO.exists("PID")).thenReturn(true);
		when(mockDAO.findOne("PID")).thenReturn(saved);
		when(mockDAO.save(saved)).thenReturn(saved);
		when(mockDAO.exists("IBD")).thenReturn(true);
		when(mockDAO.findOne("IBD")).thenReturn(aModif);

		CoursController controller = new CoursController(mockDAO);
		MockMvc mockMvc = standaloneSetup(controller).setControllerAdvice(new CentralExceptionHandle()).build();
		// Verifie que l'on retourne sur la vue en cas de doublon pour un add
		mockMvc.perform(
				post("/cours/add").param("code", "PID").param("nom", "Projet d'intégration").param("nbPeriodes", "60"))
				.andExpect(view().name("cours/addCours"));

		// Verifie que l'on va sur une page d'erreur en cas de doublon pour un update
		MvcResult result = mockMvc.perform(post("/cours/add").param("code", "PID").param("nom", "Projet d'intégration")
				.param("nbPeriodes", "60").param("savedId", "IBD")).andReturn();
		assertThat(result.getResolvedException(), instanceOf(DuplicateException.class));
		assertThat(result.getResolvedException().getMessage(), is("Le cours PID existe déjà"));
	}

	@Test
	public void testDeleteCours() throws Exception {
		ICoursJpaDAO mockDAO = mock(ICoursJpaDAO.class);
		Cours saved = new Cours("ISO2", "Structure des ordinateurs", (short) 60);
		when(mockDAO.findOne(saved.getCode())).thenReturn(saved);
		when(mockDAO.exists(saved.getCode())).thenReturn(true);
		CoursController controller = new CoursController(mockDAO);

		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(post("/cours/ISO2/delete").param("code", "ISO2")).andExpect(redirectedUrl("/cours/liste"));

		verify(mockDAO, atLeastOnce()).delete("ISO2");
	}

}
