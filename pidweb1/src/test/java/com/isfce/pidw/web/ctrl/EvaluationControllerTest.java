package com.isfce.pidw.web.ctrl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.isfce.pidw.data.ICompetenceJpaDAO;
import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.data.IEvaluationJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Evaluation;
import com.isfce.pidw.model.Evaluation.SESSION;
import com.isfce.pidw.model.Module;
import com.isfce.pidw.model.Professeur;
import com.isfce.pidw.web.EvaluationController;

// import antlr.collections.List;

public class EvaluationControllerTest {
	@Autowired

	IModuleJpaDAO moduleDAO ;
	ICompetenceJpaDAO competenceDAO;
	
	ICoursJpaDAO coursnDAO ;
	EvaluationController controller;
	
	IEtudiantJpaDAO IEtudiantJpaDAO;
	
	ICoursJpaDAO coursDAO;
	IEvaluationJpaDAO evaluationDAO;
	IEtudiantJpaDAO etudiantDAO;	
	

	@Before
	public void initMockDAO() throws ParseException {
		competenceDAO = mock(ICompetenceJpaDAO.class);
		when(competenceDAO.getCompetencesOfCours("PID")).thenReturn(null);
		



		
		Cours coursPID = new Cours("PID", "Projet de développement", (short) 60);
		Etudiant etudiant = new Etudiant( "userName", "password", "nom", "prenom", "email", "tel") ;
		Professeur prof1 = new Professeur("VO", "VO", "VO", "VO", "VO@VO.VO") ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Module module = new Module("4IPID-1-A", sdf.parse("4/9/2017"), sdf.parse("22/1/2018"), Module.MAS.SOIR, coursPID, prof1);
		Evaluation evaluation = new Evaluation( 1L, etudiant, module, SESSION.PREMIERE, 100) ;
		

		
		moduleDAO = mock(IModuleJpaDAO.class);
		coursDAO = mock(ICoursJpaDAO.class);
		IEvaluationJpaDAO evaluationDAO = mock(IEvaluationJpaDAO.class);
		
		when(coursDAO.findOne("PID")).thenReturn( coursPID );

				

		when(moduleDAO.getModulesOfCours("PID")).thenReturn(null);
		when(moduleDAO.findOne("4IPID-1-A")).thenReturn( module );
		when(moduleDAO.exists("4IPID-1-A")).thenReturn( true );	
		
		
		
		
		
		// evaluationDAO
		
		when(evaluationDAO.exists( 1L)).thenReturn(true);
		when(evaluationDAO.findAll()).thenReturn(Arrays.asList(evaluation));
		when(evaluationDAO.findOne( 1L )).thenReturn( evaluation );
		
		
		List<Evaluation> lst = new ArrayList<>() ;
		lst.add(evaluation);
		
		when(evaluationDAO.getEvaluationsOfModule("4IPID-1-A", 0) ).thenReturn( lst );
		
		
		controller=new EvaluationController( moduleDAO,
				 coursDAO,  etudiantDAO,
				 evaluationDAO,  competenceDAO  ) ;

	}
	
	//	see		evaluation/IIBD-1-B/1		->		evaluation/listeEvaluation
	//	see		evaluation/1				->		evaluation/evaluation
	//	add		evaluation/IIBD-1-B/add		->		redirect:/module/liste
	//	mod		evaluation/update			->		redirect:/evaluation/" + evaluation.getModule().getCode() + "/" +  (evaluation.getSession().ordinal()+1)  ;
	

	@Test
	public void testListeEtudiantOfEvaluation() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/evaluation/4IPID-1-A/1")).andExpect(view().name("evaluation/listeEvaluation"));
	}

	
	@Test
	public void testAddEvaluationGetURL() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/evaluation/1")).andExpect(view().name("evaluation/evaluation"));

	}


	@Test
	public void testDetailEvaluation() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		System.out.println( "*************************___________*********************" );
		System.out.println( get("/evaluation/4IPID-1-A/add")      ) ;
		
		mockMvc.perform(get("/evaluation/4IPID-1-A/add")).andExpect( redirectedUrl( "/module/liste"  ) ) ;
	}

}
