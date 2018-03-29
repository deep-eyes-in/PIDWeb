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

import com.isfce.pidw.config.security.Roles;
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

	private String className = "CompetenceController";

	@Autowired
	public CompetenceController(IModuleJpaDAO moduleDAO, IProfesseurJpaDAO professeurDAO, ICoursJpaDAO coursDAO,
			IEtudiantJpaDAO etudiantDAO, IUsersJpaDAO<Users> usersDAO, IEvaluationJpaDAO evaluationDAO,
			ICompetenceJpaDAO competenceDAO

	) {
		super();
		this.moduleDAO = moduleDAO;
		this.professeurDAO = professeurDAO;
		this.coursDAO = coursDAO;
		this.etudiantDAO = etudiantDAO;
		this.evaluationDAO = evaluationDAO;
		this.competenceDAO = competenceDAO;
		this.usersDAO = usersDAO;
	}

	// Méthode GET pour ajouter ou modifier une compétence
	@RequestMapping(value = { "/{code}/add", "/update/{id}" })
	public String addCompetence(@PathVariable Optional<String> code, @PathVariable Optional<Long> id, Model model,
			@ModelAttribute Competence competence) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[addUpdateEvaluation]" + "[] \n");
		
		Cours cours = new Cours();

		// On vérifie si un ID existe dans l'url
		if (id.isPresent()) {
			// Si oui, on récupère la compétence
			competence = competenceDAO.findOne(id.get());
			// On récupère le cours qui lui est associé
			cours = competence.getCours();
			// On envoie la compétence à travers le model
			model.addAttribute("competence", competence);
		} else {
			// Si l'ID n'existe pas, on récupère le cours grâce à son code
			cours = coursDAO.findOne(code.get());
		}
		// On envoie le cours à travers le model
		model.addAttribute("cours", cours);

		return "/competence/addCompetence";
	}

	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCompetence(Model model, @ModelAttribute Competence competence) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[addUpdateEvaluation]" + "[] \n");
		
		// On récupère le code du cours associé à la compétence
		String coursCode = competence.getCours().getCode();
		// On récupère toutes les compétences du cours
		List<Competence> competences = competenceDAO.getCompetencesOfCours(coursCode);

		// On vérifie, dans le cas ou on ajoute une nouvelle compétence, qu'il n'existera pas plus de 6 compétences après l'ajout
		// Ou dans le cas ou on modifie la compétence, qu'il n'existe pas plus de 6 compétences
		if ((competence.getId() == null && competences.size() < 6)
				|| (competence.getId() != null && competences.size() < 7)) {
			//On récupère le cours à partir de son code
			Cours cours = new Cours();
			cours = coursDAO.findOne(coursCode);
			
			// On associe la compétence au cours correspondant
			competence.setCours(cours);
			// On enregistre les changements
			competenceDAO.save(competence);
		} else {
			// Dans le cas où il existe trop de compétences, on envoie un message d'erreur
			throw new NoAccessException("Le nombre de compétences pour ce cours est supérieur à la limite autorisée");
		}
		
		// Redirection vers la liste des cours
		return "redirect:/cours/liste";
	}
	
	
	

	
	// Méthode GET pour visualiser la liste des compétences d'un étudiant pour tel cours
	@RequestMapping(value = { "/{code}/{username}" })
	public String etudiantCompetence(@PathVariable String code, @PathVariable String username,
			Model model, @ModelAttribute CompetenceValid CompetenceValid ,  Authentication authentication) {

		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[etudiantCompetence]" + "[] \n");
		
		// On instancie les variables nécessaires
		List<Competence> competencesList = null;
		Etudiant etudiant = null;
		Module module = null;
		
		// On vérifie que le module ET l'étudiant fournis dans l'url existent et sont valides
		if(!(etudiantDAO.exists(username) && moduleDAO.exists(code))) {
			throw new NotFoundException("L'étudiant ou le module n'existe pas: étudiant : ", username + " / module :" + code);
		} 
		
		// On récupère le module ainsi que l'étudiant
		module = moduleDAO.findOne(code) ;
		etudiant = etudiantDAO.findOne(username);
		// On instancie une variable booléenne pour les droits de modification (et on la place à false par défaut)
		boolean isAdminOrProfOwner = false ;
		
		// On vérifie si l'utilisateur est connecté
		String userConnected = new String();
		if(authentication != null) {
			// Si c'est le cas, on récupère son nom
			userConnected = authentication.getName();
			
			// On vérifie si l'utilisateur connecté est soit un admin soit le professeur associé au cours
			if(authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Roles.ROLE_ADMIN.name())) || module.getProf().getUsername().equals(userConnected) ) {
				// Si oui, on place la variable de vérification à true
				isAdminOrProfOwner = true;
			} 
			
			// Etant donné que l'on ne peut pas gérer l'accès uniquement dans la sécurité, on vérifie manuellement que l'utilisateur est soit l'étudiant ciblé par la page soit un utilisateur avec les droits
			if(!(userConnected.equals(username) || isAdminOrProfOwner)) {
				// Si l'utilisateur ne correspond pas aux critères, on envoie une erreur de type "NoAccessException" avec le message explicatif
				throw new NoAccessException("Accès refusé : seul l'étudiant ciblé, le professeur du module ou l'admin peuvent afficher cette page");
			}
		// Dans le cas où l'on est pas authentifié
		} else {
			// On renvoie aussi une erreur de type "NoAccessException" en indiquant qu'il est nécessaire de s'identifier
			throw new NoAccessException("Accès refusé, veuillez vous identifier");
		}
		
		// On récupère la liste des compétences associées au cours
		competencesList = competenceDAO.getCompetencesOfCours( module.getCours().getCode() );
		
		// On instancie une variable tableau de compétences "validées ou non"
		List<CompetenceValid> allCompetences = new ArrayList<>();
		
		// On commence une boucle pour passer en revue chaque compétences du cours
		for ( Competence comp : competencesList  )  {
			// On crée une variable de type compétence valide
			CompetenceValid cv = new CompetenceValid();
			
			// On la rempli avec les informations de la compétence de la boucle
			cv.setValided(false);
			cv.setCompetenceId(comp.getId());
			cv.setDescription(comp.getDescription());
			
			// On rentre dans une nouvelle boucle pour passer en revue tout les étudiant qui ont réussi cette compétence
			for ( Etudiant etud : comp.getEtudiants()  )  {
				
				// Si l'étudiant ciblé par la page se trouve parmis la liste d'étudiant, on valide sa compétence				
				if(etud.getUsername().equals(etudiant.getUsername()) ) {
					cv.setUsername(etud.getUsername());
					cv.setValided(true);
					// Plus besoin de passer les autres étudiant en revue, on sort de la boucle
					break;
				}
				
			}
			// On ajoute à la variable tableau la compétence dont les informations sont completées
			allCompetences.add(cv);
		}
		
		// On envoie toutes les variables nécessaires à la page
		model.addAttribute("validedCompetences", allCompetences );
		model.addAttribute("module", module );
		model.addAttribute("etudiant", etudiantDAO.findOne(username) );
		model.addAttribute("editRight", isAdminOrProfOwner );

		
		return "/competence/updateCompetence" ;

	}

	
	// Méthode pour la modification de la validation d'une compétence pour tel élève pour tel cours
	@RequestMapping(value = { "/{code}/{competenceId}/{username}/{value}"  })
	public String updateEtudiantCompetence(
			@PathVariable String code, 
			@PathVariable Long competenceId,
			@PathVariable String username, 
			@PathVariable Boolean value,
			Authentication authentication,
			Model model ) {
		
		System.out.printf("*[" + this.getClass().getSimpleName() + "]" + "[updateEtudiantCompetence]" + "[] \n");
		
		// On vérifie que l'étudiant ainsi que le cours sont valides
		if(!(etudiantDAO.exists(username) && moduleDAO.exists(code))) {
			throw new NotFoundException("L'étudiant ou le module n'existe pas: étudiant : ", username + " / module :" + code);
		} 
		
		// On récupère le cours à partir de son code
		Module module = null;
		module = moduleDAO.findOne(code) ;
		
		// On vérifie manuellement les autorisations pour la modification
		String userConnected = new String();
		if(authentication != null) {
			userConnected = authentication.getName();
			if(!( authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Roles.ROLE_ADMIN.name())) || module.getProf().getUsername().equals(userConnected) ) ) {
				throw new NoAccessException("Accès refusé : seul l'étudiant ciblé, le professeur du module ou l'admin peuvent executer cette méthode");
			}
		} else {
			throw new NoAccessException("Accès refusé, veuillez vous identifier");
		}
		
		List<Etudiant> etudiantList = etudiantDAO.getEtudiantsOfModule( code );
		
		Competence competence = competenceDAO.findOne( competenceId ) ;

			//	On passe en revue les étudiant appartenant au cours
			for ( Etudiant et : etudiantList  )  {
				
				// On vérifie que l'étudiant appartient bien au cours
				if ( et.getUsername().equals( username ) ) {
					// On vérifie si l'ordre est de valider ou d'invalider sa compétence
					if ( value ) {
						// On ajoute
						competence.getEtudiants().add( et );
					}else {
						// On retire
						competence.getEtudiants().remove( et ) ;
					}
					// On enregistre
					competenceDAO.save(competence);
					break ;
				}
			}
		
		
		return "redirect:/competence/" + code + "/"+ username  ;  
	}
	

}













