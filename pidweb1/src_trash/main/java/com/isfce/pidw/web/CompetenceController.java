package com.isfce.pidw.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.isfce.pidw.model.CompetenceValid;
//import com.isfce.pidw.model.CompetenceValid;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Module;
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
	public String etudiantCompetence(@PathVariable String code, @PathVariable String username,
			Model model, @ModelAttribute CompetenceValid CompetenceValid, Authentication authentication) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[etudiantCompetence]" + "[] \n");
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		List<Competence> competencesList = null;
		Etudiant etudiant = null;
		Module m = null;
		
		if(etudiantDAO.exists(username)) {
			etudiant = etudiantDAO.findOne(username);
			if(moduleDAO.exists(code)) {
				m = moduleDAO.findOne(code) ;
				competencesList = competenceDAO.getCompetencesOfCours( m.getCours().getCode() );
				System.out.println(competencesList.toString());
			} else {

			}
		} else 
			throw new NotFoundException("L'étudiant n'existe pas", username);

			
		if(!authentication.isAuthenticated()) {
			throw new NotFoundException("L'étudiant n'existe pas", username);
		}
		
		List<CompetenceValid> validedCompetences = new ArrayList<>();
		
		System.out.println("uuuuuuuuuuuuuuuuuuuuu");
		
		
		for ( Competence comp : competencesList  )  {
			CompetenceValid cv = new CompetenceValid();
			
			System.out.println(comp.getDescription());
			cv.setValided(false);
			cv.setCompetenceId(comp.getId());
			cv.setDescription(comp.getDescription());
			
			
			for ( Etudiant etud : comp.getEtudiants()  )  {
				
				System.out.println(etud.getUsername());
				System.out.println(etudiant.getUsername());
				
				if(etud.getUsername().equals(etudiant.getUsername()) ) {
					cv.setUsername(etud.getUsername());
					cv.setValided(true);
					
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxx");
					break;
				}
				
			}
			validedCompetences.add(cv);
		}
		
		System.out.println(validedCompetences.toString());
		model.addAttribute("validedCompetences", validedCompetences );
		model.addAttribute("module", m );
		model.addAttribute("etudiant", etudiantDAO.findOne(username) );


		return "/competence/updateCompetence" ;

	}








	
//	competence/${module.code}/${competenceId}/${etudiant.username}/true
//  competence/${module.code}/${competenceId}/${etudiant.username}/false
	
	
	@RequestMapping(value = { "/{code}/{competenceId}/{username}/{value}"  })
	public String updateEtudiantCompetence(
			@PathVariable String code, 
			@PathVariable Long competenceId,
			@PathVariable String username, 
			@PathVariable Boolean value,
			Model model ) {
		
		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[updateEtudiantCompetence]" + "[] \n");
		
		List<Etudiant> etudiantList = etudiantDAO.getEtudiantsOfModule( code );
		
		Competence competence = competenceDAO.findOne( competenceId ) ;


			//	VERIFIER TOUT LES ETUDIANTS INSCRITS
			for ( Etudiant et : etudiantList  )  {
				
				System.out.println( et.getUsername()  );
				System.out.println( username  );
				
				// SI ETUDIANT EST BIEN INSCRIT AU MODULE
				if ( et.getUsername().equals( username ) ) {
					// ON AJOUTE SA COMPETENCE DANS LA LISTE
					
					if ( value ) {
						// ON AJOUTE
						competence.getEtudiants().add( et );
					}else {
						// ON RETIRE
						competence.getEtudiants().remove( et ) ;
					}
					competenceDAO.save(competence);
					break ;
				}
			}
		

		
		return "redirect:/competence/" + code + "/"+ username  ;    //IVTE-1-A/SM
	}
	
	


	@RequestMapping(value = { "/etudiantupdate" } , method = RequestMethod.POST )
	public String updateEtudiantCompetence2(@PathVariable Optional<String> code, @PathVariable Optional<String> username,
			Model model, @ModelAttribute CompetenceValid CompetenceValid) {
	
		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[etudiantCompetence]" + "[] \n");
		
		List<Etudiant> etudiantList = etudiantDAO.getEtudiantsOfModule( code.get() );
	
		System.out.println( etudiantList.toString() );
		
		

		
		for ( Etudiant et : etudiantList  )  {
			
			System.out.println( et.getUsername()  );
			System.out.println( username.get()  );
			
			
			if ( et.getUsername().equals( username.get() ) ) {
				Etudiant etudiantX = etudiantDAO.findOne(username.get());
				//competence = competenceDAO.findOne(2L);
				//competence.getEtudiants().add(etudiantX);
				//competenceDAO.save(competence);
			}
		}
		
		
	
	
		// return "/competence/addCompetence" ;
		return "";
	
	}






}













