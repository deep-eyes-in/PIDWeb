package com.isfce.pidw.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.isfce.pidw.config.security.GeneratePassword;
import com.isfce.pidw.config.security.Roles;
import com.isfce.pidw.data.ICompetenceJpaDAO;
//import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.data.IEvaluationJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.model.Competence;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Evaluation;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/etudiant")
public class EtudiantController {

	// Logger
	final static Logger logger = Logger.getLogger(EtudiantController.class);
	
	// on recupre la liste de cours et etudiant depuis L'usine (factory)
	private IEtudiantJpaDAO etudiantDAO;
	private IModuleJpaDAO moduleDAO;
	private IEvaluationJpaDAO evaluationDAO;
	private ICompetenceJpaDAO competenceDAO;
		

	// Création de la liste de données pour le 1er exemple
	@Autowired
	public EtudiantController(IEtudiantJpaDAO etudiantDAO, IModuleJpaDAO moduleDAO, IEvaluationJpaDAO evaluationDAO, ICompetenceJpaDAO competenceDAO ) {
		this.etudiantDAO = etudiantDAO;
		this.moduleDAO = moduleDAO;
		this.evaluationDAO = evaluationDAO;
		this.competenceDAO = competenceDAO;		
	}

	
	// Liste des étudiants
	@RequestMapping("/liste")
	public String listeEtudiant(Model model) {
				
		model.addAttribute("etudiantList", etudiantDAO.findAll() );

		return "etudiant/listeEtudiant";
	}


	// Méthode Get pour ajouter un etudiant
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addEtudiantGet(@ModelAttribute Etudiant etudiant, Model model) {
		logger.debug("affiche la vue pour ajouter un Etudiant ");


		return "etudiant/addEtudiant";
	}
	


	// Méthode Get pour faire un update d'un étudiant
	@RequestMapping(value = "/{code}/update", method = RequestMethod.GET)
	public String updateEtudiantGet(@PathVariable String code, Model model) {

		logger.debug("affiche la vue pour modifier un Etudiant:" + code);
		
		if (!etudiantDAO.exists( code ) )
			throw new NotFoundException("Le etudiant n'existe pas", code  );
		// recherche le Etudiant dans la liste
		Etudiant etudiant = etudiantDAO.findOne( code );
		
		// Ajout au Modèle
		model.addAttribute("etudiant", etudiant);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", etudiant.getUsername() );

		
		// model.addAttribute("nouveau",false);
		return "etudiant/addEtudiant";

	}
	
	
	
	
	
	
	

	/**
	 * Méthode POST pour un ajout ou un update de Etudiant. La distinction d'un add et
	 * d'un update se fait sur le paramètre savedId (Id du Etudiant avant l'update) En
	 * cas de doublon une exception est générée et traitée sur une page
	 * personnalisée En cas d'erreur de validation, on retourne sur la même vue.
	 * 
	 * @param Etudiant
	 *            le Etudiant crée
	 * @param errors
	 *            les erreurs suite à une validation
	 * @param savedId
	 *            le id du Etudiant avant un update sinon null si un add
	 * @param model
	 *            l'objet modèle à retourner en cas d'erreur
	 * @param rModel
	 *            permet de mettre des flashs attributes pour la redirection
	 * @return l'adresse URI de redirection
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUpdateEtudiantPost(@Valid Etudiant etudiant, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) {

		logger.debug("Etudiant Info:" + etudiant.getUsername() + " savedId " + savedId);
		
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);

			logger.debug("Erreurs dans les données de etudiant:" + etudiant.getUsername());
			return "etudiant/addEtudiant";
		}else {	logger.debug("Etudiant Info: no errors");		}
		
		
		// distinction d'un update ou d'un add
		if (savedId == null) {
			logger.debug("Etudiant Info: code = null ");

			// cas ADD
			// Vérification doublon
			if (etudiantDAO.exists(etudiant.getUsername() )) {
				logger.debug("Le etudiant existe:" + etudiant.getUsername() + " savedId " + savedId);
				//Exemple de gestion d'erreur en modifiant l'objet 'errors' et 
				//en retournant la vue
				errors.rejectValue("id", "Etudiant.id.doubon", "Existe déjà!");

				return "etudiant/addEtudiant";
				//Autre solution en générant une exception
				// throw new DuplicateException("Le Etudiant " + Etudiant.getId() + " existe déjà
				// ");
			}
		} else {			// cas d'un Update

			logger.debug("etudiant Info: code != null ");
			
			// Est ce que le id a changé?
			if (!savedId.equals( etudiant.getUsername() )) {
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (etudiantDAO.exists(etudiant.getUsername())) {
					logger.debug("Le Etudiant Existe:" + etudiant.getUsername() + " savedId " + savedId);
					throw new DuplicateException("Le Etudiant " + etudiant.getUsername() + " existe déjà");
				}
			}
			
			logger.debug("Mise à jour du etudiant: " + savedId);

		}
		

		String pwd ;
		if(etudiant.getPassword().equals("*******")) {
			pwd = etudiantDAO.findOne(etudiant.getUsername()).getPassword() ;
		}else {
			pwd = GeneratePassword.PasswordEncode( etudiant.getPassword()  )  ;
		}
		etudiant.setPassword(   pwd   );
		
		// ajoute le nouveau ou le Etudiant nouvellement modifié
		etudiant.setRole( Roles.ROLE_ETUDIANT);
		etudiantDAO.save(etudiant);

		// Préparation des attribut Flash pour survivre à la redirection
		rModel.addFlashAttribute(etudiant);
		// Gestion de la redirection pour l'UTF8 en cas d'accents
		String adr = "redirect:/etudiant/"   + etudiant.getUsername();
		// encode l'URI en percent-encoding pour les accents
		try {
			adr = UriUtils.encodePath(adr, "UTF8");
			logger.debug("UriUtils:Etudiant Adresse de redirection: " + adr);
		} catch (UnsupportedEncodingException e) {

		}

		return adr;
	}
	
	
	
	

	/**
	 * Supression d'un Etudiant
	 * 
	 * @param id
	 *            du Etudiant
	 * @return le mapping de redirection
	 */
	@RequestMapping(value = "/{code}/delete", method = RequestMethod.POST)
	public String deleteEtudiant(@PathVariable String code) {
		logger.debug("<DEBUT> Supression du etudiant: " + code);
		
		// Vérifie si l'étudiant existe
		if (!etudiantDAO.exists(code))  {
			throw new NotFoundException("Etudiant non trouvé pour suppression", code );
		}
		etudiantDAO.delete(code);
		logger.debug("Suppression de l'etudiant: " + code);
		
		return "redirect:/etudiant/liste";

	}
	

	/**
	 * Réceptionne le traitement de l'exception DuplicateException pour tous les
	 * déclenchements au sein de ce contrôleur. Cette méthode n'est pas apellée
	 * explicitement
	 * 
	 * @param req
	 *            la request Http. Nécessaire pour fournir des données à la vue
	 * @param e
	 *            l'objet exception
	 * @return un Modèle et une vue par le type ModelView
	 */

	// Affichage du détail d'un Etudiant
	@RequestMapping(value = "/{student}", method = RequestMethod.GET)
	public String detailEtudiant(@PathVariable String student, Model model,  Authentication authentication) {

			logger.debug("Recherche le etudiant: " + student);
			
			// Vérifie si l'étudiant existe
			if (!etudiantDAO.exists(student))
				throw new NotFoundException("Cet etudiant n'existe pas : ", student);
			
			// On récupère l'étudiant
			Etudiant etudiant = etudiantDAO.findOne(student);
			
			// On crée une variable booléenne pour indiquer si on est un admin
			boolean isAdmin = false ;
			String userConnected = new String();
			// On vérifie qu'on est connecté
			if(authentication != null) {
				// Si oui, on vérifie si on est admin
				isAdmin = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Roles.ROLE_ADMIN.name()))  ;
				// On récupère le nom d'utilisateur de la personne connectée
				userConnected = authentication.getName();	
			} else {
				userConnected = "";
			}

			// On vérifie soit si on est admin soit si on est l'étudiant ciblé
			if(student.equals(userConnected) || isAdmin ) {
				// On récupère la liste des modules de l'étudiant
				List<Module> modules = moduleDAO.getModulesOfEtudiant(etudiant.getUsername());
				
				// On envoie déjà ces informations basiques
				model.addAttribute("etudiant", etudiant );
				model.addAttribute("listModules", modules    );
				
				// On récupères les informations plus complexes en appelant les fonctions spécialisées
				List<String> statusModules = getResultOfModules(modules, etudiant);
				List<String> infosCompetences = getInfosOfCompetences(modules, etudiant);
				List<String> evalComments = getCommentOfEval(modules, etudiant);
				
				// On envoie les informations complémentaires
				model.addAttribute("statusModules", statusModules    );
				model.addAttribute("infosCompetences", infosCompetences    );
				model.addAttribute("evalComments", evalComments    );
				
			} else {
				throw new NoAccessException("Doit être connecté en admin ou l'étudiant " + student);
			}

			
		
		return "etudiant/etudiant";
	}
	
	
	
	
	
	// Fonction pour récupérer les informations concernant les compétences
	private List<String> getInfosOfCompetences(List<Module> modules, Etudiant etudiant) {
		List<String> allInfos = new ArrayList<>();
		String info = new String();
		
		// On passe en revue chaque modules
		for(Module module : modules) {
			// On récupère les compétences du modules
			List<Competence> competences = competenceDAO.getCompetencesOfCours(module.getCours().getCode());
			// On récupère les compétences VALIDÉES de l'étudiant pour ce module
			List<Object[]> validedComp = competenceDAO.getCompetencesValidOfEtudiant(etudiant.getUsername(), module.getCours().getCode());
			
			// On crée le texte qu'on affichera dans la page
			info = validedComp.size() +" sur " + competences.size();
			if(validedComp.size() == competences.size() && validedComp.size() > 0)
				info += " Bravo !";
			// On ajoute le texte dans la variable contenant toutes les informations
			allInfos.add(info);
		}

		
		return allInfos;
	}
	
	
	// Fonction pour récupérer les résultats des évaluations d'un étudiant pour tout ses modules
	private List<String> getResultOfModules(List<Module> modules, Etudiant etudiant) {
		
		List<String> results = new ArrayList<>();
		// On passe en revue tout ses modules
		for(Module module : modules) {
			
			// On vérifie combien de sessions existent pour ce module
			List<Evaluation.SESSION> sessions = evaluationDAO.getSessionsOfModule(module.getCode());
			// On récupère le résultat de l'étudiant pour le module en question en tenant compte de la session
			Evaluation evaluation = evaluationDAO.getEvaluationOfModuleOfEtudiant(module.getCode(), (sessions.size()-1), etudiant.getUsername() );
			
			// On vérifie si l'évaluation existe, si oui, on crée le texte correspondant à son résultat
			if(evaluation != null) {
				if(evaluation.getResultat() >= 50) 
					results.add("Réussi (" + evaluation.getResultat() + "%)");
				else if(evaluation.getResultat() < 0) 
					results.add("Abandon");	
				 else 
					 results.add("Raté");
				
			}
			// Si non, on indique que le module n'a pas encore d'évaluation
			else {
				results.add("En cours");
			}

		}
		
		return results;
	}
	
	// Fonction pour récupérer les éventuels commentaires du professeur
	private List<String> getCommentOfEval(List<Module> modules, Etudiant etudiant) {
		List<String> comments = new ArrayList<>();
		for(Module module : modules) {
			List<Evaluation.SESSION> sessions = evaluationDAO.getSessionsOfModule(module.getCode());
			Evaluation evaluation = evaluationDAO.getEvaluationOfModuleOfEtudiant(module.getCode(), (sessions.size()-1), etudiant.getUsername() );

			if(evaluation != null) {
				comments.add(evaluation.getComments());
			}
			else {
				comments.add("");
			}
				
		}
		return comments;
	}

}
