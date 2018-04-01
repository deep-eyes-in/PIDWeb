package com.isfce.pidw.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.isfce.pidw.filter.ConsoleColors;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Evaluation;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {
	// Logger
	final static Logger logger = Logger.getLogger(EvaluationController.class);

	private IModuleJpaDAO moduleDAO;
	private ICoursJpaDAO coursDAO;
	private IEtudiantJpaDAO etudiantDAO;
	private IEvaluationJpaDAO evaluationDAO;
	private ICompetenceJpaDAO competenceDAO ;


	@Autowired
	public EvaluationController(IModuleJpaDAO moduleDAO,
			ICoursJpaDAO coursDAO, IEtudiantJpaDAO etudiantDAO,
			IEvaluationJpaDAO evaluationDAO, ICompetenceJpaDAO competenceDAO  ) {
		super();
		this.moduleDAO	 = moduleDAO;
		this.coursDAO	 = coursDAO;
		this.etudiantDAO	 = etudiantDAO ;
		this.evaluationDAO = evaluationDAO;
		
		this.competenceDAO = competenceDAO ; 

	}

	

	// Méthode GET pour afficher toutes les évaluations pour la session X d'un module Y
	@RequestMapping(value = { "/{code}/{session}"  },  method = RequestMethod.GET)  
	public String updateEvaluation(
			@PathVariable Integer session,
			@PathVariable String code,
			Model model, HttpServletRequest request	) {
		
		System.out.printf( "*["+  this.getClass().getSimpleName() + "]"  +  "[addUpdateEvaluationTest]"  +  "[] \n" );
		
		// On vérifie que le module indiqué dans l'URL existe
		if(!moduleDAO.exists(code)) {
			throw new NotFoundException("Le module n'existe pas : ", code);
		// On vérifie que la session indiquée dans l'URL est cohérente
		} else if(session > 2 || session < 1) {
			throw new NotFoundException("La session n'existe pas : ", session.toString());
		}
		
		List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		evaluationList = evaluationDAO.getEvaluationsOfModule(code, session - 1);
		
		model.addAttribute("evaluationList", evaluationList);


		return "evaluation/listeEvaluation" ;
		
	}

	// Méthode GET pour créer une nouvelle session d'évaluation d'un module
	@RequestMapping(value = "/{code}/add" )
	public String addEvaluation(@PathVariable String code, Model model) {
		
		System.out.printf( "*["+  this.getClass().getSimpleName() + "]"  +  "[addEvaluation]"  +  "[]" );
		
		// On vérifie que le module existe
		if(!moduleDAO.exists(code)) {
			throw new NotFoundException("Le module n'existe pas : ", code);
		}
		
		// On récupère le module en question
		Module module = moduleDAO.findOne(code);
		//On récupère son cours
		Cours c = coursDAO.findOne(module.getCours().getCode());
		c.setSections(coursDAO.coursSection(c.getCode()));
		module.setCours(c);
		module.setEtudiants(etudiantDAO.getEtudiantsOfModule(code));
		
		// On vérifie dans la BDD quelle est la session a créer
		Integer nbrSession = getSessionOfEvaluation(code);
		Evaluation.SESSION theSession = null;
		
		List<String> etudiantOfModule = new ArrayList<>();
		
		// Suivant la session renvoyée, on transforme celle-ci en format 'SESSION'
		if(nbrSession == 0) {
			theSession = Evaluation.SESSION.PREMIERE;
			// On récupère tout les étudiants inscrits au module
			etudiantOfModule = moduleDAO.getFkEtudiantsOfModule(code);
		} else if(nbrSession == 1) {
			theSession = Evaluation.SESSION.DEUXIEME;
			// On récupère tout les étudiants inscrits au module qui n'ont PAS réussi la 1ere session (la condition se trouve dans la requète SQL)
			etudiantOfModule = moduleDAO.get2ndSessionFkEtudiantsOfModule(code);
		} else {
			theSession = null;
		}

		// Dans le cas ou le module possèderait déjà 2 session, 'theSession' sera à null et donc on n'exécutera pas le code
		if(theSession != null) {
			// On passe en revue tout les étudiants concernés
			for(int i=0 ; i < etudiantOfModule.size() ; i++) {

				Evaluation evaluation = new Evaluation();
				// On rempli l'évaluation de l'étudiant
				evaluation.setId(evaluationDAO.generateId() + 1);
				evaluation.setEtudiant(etudiantDAO.findOne(etudiantOfModule.get(i)));
				evaluation.setModule(module);
				evaluation.setResultat( 0 ); 
				evaluation.setSession(theSession);	
				
				// On enregistre l'évaluation remplie par les valeurs par défaut
				evaluationDAO.save(evaluation);
			}	
		}

		return "redirect:/module/liste";
	}
	
	
	// Méthode GET pour afficher le formulaire d'update d'une évaluation
	@RequestMapping(value = { "/{codeEval}" })
	public String viewEvaluation(
			@PathVariable Long codeEval, 
			Model model, 
			Authentication authentication
	) {
		logger.warn( ConsoleColors.f( "*["+  this.getClass().getSimpleName() + "]"  +  "[viewEvaluation]"  +  "[]" , 
				ConsoleColors.CYAN_BACKGROUND_BRIGHT  )   );
		
		Long id = new Long( codeEval ) ;
		
		// On vérifie que l'ID de l'évaluation existe
		if ( !evaluationDAO.exists (  id  ) ) {
			throw new NotFoundException("L'evaluation n'existe pas : ", id.toString());
		}
		
		Evaluation eva = evaluationDAO.findOne( id ) ;
		// On envoie l'évaluation vers le formulaire
		model.addAttribute("evaluation", eva );
		return "evaluation/evaluation" ;
	}	
	
	
	// Méthode POST pour mettre à joue une évaluation
	@RequestMapping(value = { "/update"  },  method = RequestMethod.POST)  
	public String addUpdateEvaluationPost(@ModelAttribute Evaluation evaluation, Model model) {
		System.out.printf( "*["+  this.getClass().getSimpleName() + "]"  +  "[addUpdateEvaluationPost]"  +  "[]" );
		
		// On récupère les données fournies dans le formulaire
		Integer resultat = evaluation.getResultat() ;
		String  comments = evaluation.getComments() ;
		
		// On séléctionne l'évaluation concernée et on remplace les données
		evaluation = evaluationDAO.findOne(evaluation.getId() ) ;
		evaluation.setResultat(resultat);
		evaluation.setComments(comments);
		
		
		if( resultat >= 50 ) {
			Etudiant etu = evaluation.getEtudiant()  ;
			Cours cours = evaluation.getModule().getCours()  ;
			
			// On récupère les compétences du cours ET les compétences acquisent par létudiant pour ce cours
			int nbrCompValid  = competenceDAO.getCompetencesValidOfEtudiant(etu.getUsername(), cours.getCode()).size() ;
			int nbrComp = competenceDAO.getCompetencesOfCours(cours.getCode()).size() ;

			// On vérifie que l'étudiant a bien validé toutes les compétences pour lui attribuer un résultat supérieur ou égal à 50%
			if ( nbrCompValid != nbrComp ) {
				throw new DuplicateException(" Impossible d'entrer une note supérieure à 50% si toutes les compétences ne sont pas acquises ");
			}
		}
		
		
		// ajoute le nouveau ou l evaluation nouvellement modifié
		evaluationDAO.save(   evaluation   );

	
		return "redirect:/evaluation/" + evaluation.getModule().getCode() + "/" +  (evaluation.getSession().ordinal()+1)  ;
	}
	

	
/////////////////////////////////////////////////////////////////////////
//   BASIC FUNCTION                                                    //
/////////////////////////////////////////////////////////////////////////
	
	
	public Integer getSessionOfEvaluation(String code) {
		
		System.out.printf( "*["+  this.getClass().getSimpleName() + "]"  +  "[getSessionOfEvaluation]"  +  "[]" );
		
		List<Evaluation.SESSION> allSessionOfModule = new ArrayList<>();
		allSessionOfModule = evaluationDAO.getSessionsOfModule(code);
		
		return allSessionOfModule.size();
	}	
	
	
}



