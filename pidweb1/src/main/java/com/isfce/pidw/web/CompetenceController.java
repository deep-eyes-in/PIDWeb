package com.isfce.pidw.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isfce.pidw.data.ICompetenceJpaDAO;
import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.data.IEvaluationJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.data.IProfesseurJpaDAO;
import com.isfce.pidw.data.IUsersJpaDAO;
import com.isfce.pidw.model.Competence;
//import com.isfce.pidw.model.CompetenceValid;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Users;

@Controller
@RequestMapping("/competence")
public class CompetenceController {
	// Logger
	final static Logger logger = Logger.getLogger(CompetenceController.class);

	private IModuleJpaDAO moduleDAO;
	private ICoursJpaDAO coursDAO;
	private IProfesseurJpaDAO professeurDAO;
	private IEtudiantJpaDAO etudiantDAO;
	private IUsersJpaDAO<Users> usersDAO;
	private IEvaluationJpaDAO evaluationDAO;
	private ICompetenceJpaDAO competenceDAO;
	// private ICompetenceValidJpaDAO competenceValidDAO ;

	private String className = "CompetenceController";

	@Autowired
	public CompetenceController(IModuleJpaDAO moduleDAO, IProfesseurJpaDAO professeurDAO, ICoursJpaDAO coursDAO,
			IEtudiantJpaDAO etudiantDAO, IUsersJpaDAO<Users> usersDAO, IEvaluationJpaDAO evaluationDAO,
			ICompetenceJpaDAO competenceDAO // , ICompetenceValidJpaDAO competenceValidDAO

	) {
		super();
		this.moduleDAO = moduleDAO;
		this.professeurDAO = professeurDAO;
		this.coursDAO = coursDAO;
		this.etudiantDAO = etudiantDAO;
		this.evaluationDAO = evaluationDAO;
		this.competenceDAO = competenceDAO;
		// this.competenceValidDAO =competenceValidDAO ;

		this.usersDAO = usersDAO;
	}

	@RequestMapping(value = { "/{code}/add", "/update/{id}" })
	public String addCompetence(@PathVariable Optional<String> code, @PathVariable Optional<Long> id, Model model,
			@ModelAttribute Competence competence) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[addUpdateEvaluation]" + "[] \n");
		Cours cours = new Cours();

		if (id.isPresent()) {
			competence = competenceDAO.findOne(id.get());
			cours = competence.getCours();
			model.addAttribute("competence", competence);
		} else {

			cours = coursDAO.findOne(code.get());
		}

		model.addAttribute("cours", cours);

		return "/competence/addCompetence";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCompetence(Model model, @ModelAttribute Competence competence) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[addUpdateEvaluation]" + "[] \n");

		System.out.println(competence.toString());

		String coursCode = competence.getCours().getCode();

		List<Competence> competences = competenceDAO.getCompetencesOfCours(coursCode);

		if ((competence.getId() == null && competences.size() < 6)
				|| (competence.getId() != null && competences.size() < 7)) {
			Cours cours = new Cours();
			cours = coursDAO.findOne(coursCode);

			competence.setCours(cours);

			competenceDAO.save(competence);
		} else {

		}

		return "redirect:/cours/liste";
	}
	
	
	
	
	

	@RequestMapping(value = { "/{code}/{username}" })
	public String etudiantCompetence(@PathVariable Optional<String> code, @PathVariable Optional<String> username,
			Model model, @ModelAttribute Competence competence) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[etudiantCompetence]" + "[] \n");
		
		List<Etudiant> etudiantList = etudiantDAO.getEtudiantsOfModule( code.get() );

		System.out.println( etudiantList.toString() );
		
		
		
//		model.addAttribute("competenceList", competenceDAO.getCompetencesOfModule( module.getCours.getCode() ));
		

		
		


		// return "/competence/addCompetence" ;
		return "";

	}











	@RequestMapping(value = { "/{code}/{username}" } , method = RequestMethod.POST )
	public String updateEtudiantCompetence(@PathVariable Optional<String> code, @PathVariable Optional<String> username,
			Model model, @ModelAttribute Competence competence) {
	
		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[etudiantCompetence]" + "[] \n");
		
		List<Etudiant> etudiantList = etudiantDAO.getEtudiantsOfModule( code.get() );
	
		System.out.println( etudiantList.toString() );
		
		
	
		
		
		
		for ( Etudiant et : etudiantList  )  {
			
			System.out.println( et.getUsername()  );
			System.out.println( username.get()  );
			
			
			if ( et.getUsername().equals( username.get() ) ) {
				Etudiant etudiantX = etudiantDAO.findOne(username.get());
				competence = competenceDAO.findOne(2L);
				competence.getEtudiant().add(etudiantX);
				competenceDAO.save(competence);
			}
		}
		
		
	
	
		// return "/competence/addCompetence" ;
		return "";
	
	}






}













