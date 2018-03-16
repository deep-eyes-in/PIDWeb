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
	
	// on recupre la liste de cours et etudiant depois L'usine (factory)
//	private ICoursJpaDAO coursDAO;
	private IEtudiantJpaDAO etudiantDAO;
	private IModuleJpaDAO moduleDAO;
	private IEvaluationJpaDAO evaluationDAO;
	private ICompetenceJpaDAO competenceDAO;
	
//	private  List<Etudiant> listeEtudiant ;
//	private  List<Cours> listeCours  ;
	
	

	// Création de la liste de données pour le 1er exemple
	@Autowired
	public EtudiantController(IEtudiantJpaDAO etudiantDAO, IModuleJpaDAO moduleDAO, IEvaluationJpaDAO evaluationDAO, ICompetenceJpaDAO competenceDAO ) {
		this.etudiantDAO = etudiantDAO;
		this.moduleDAO = moduleDAO;
		this.evaluationDAO = evaluationDAO;
		this.competenceDAO = competenceDAO;

//		listeEtudiant = etudiantDAO.findAll() ;
//		listeCours = coursDAO.findAll() ;
		
	}

	
	// Liste des profs
	@RequestMapping("/liste")
	public String listeEtudiant(Model model) {
		
		System.out.println(   etudiantDAO.findAll().toString()   );
		
		System.out.println(   etudiantDAO.findAll().get(0).getUsername()   );
		
		
		model.addAttribute("etudiantList", etudiantDAO.findAll() );

		return "etudiant/listeEtudiant";
	}

	
	
	
	
	// Méthode Get pour ajouter un etudiant
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addEtudiantGet(@ModelAttribute Etudiant etudiant, Model model) {
		logger.debug("affiche la vue pour ajouter un Etudiant ");


		return "etudiant/addEtudiant";
	}
	

	
	
	
	
	// Méthode Get pour faire un update d'un prof
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
//			etudiant.setCode( etudiant.getCode() ) ;	
			
			if (!savedId.equals( etudiant.getUsername() )) {
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (etudiantDAO.exists(etudiant.getUsername())) {
					logger.debug("Le Etudiant Existe:" + etudiant.getUsername() + " savedId " + savedId);
					throw new DuplicateException("Le Etudiant " + etudiant.getUsername() + " existe déjà");
				}
				// retire le etudiant avec l'ancien code
				etudiantDAO.delete( savedId );
			} else {
				// retire le cours de la liste
				etudiantDAO.delete( etudiant.getUsername());
			}
			
			
			
			logger.debug("Mise à jour du etudiant: " + savedId);

		}
		


		String pwd = GeneratePassword.PasswordEncode( etudiant.getPassword()  )  ;
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
		
		// Vérifie si le cours existe
		if (!etudiantDAO.exists(code))  {
			throw new NotFoundException("Etudiant non trouvé pour suppression", code );
		}
		etudiantDAO.delete(code);
		logger.debug("Supression du etudiant: " + code);
		
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
	// @ExceptionHandler(DuplicateException.class)
	// private ModelAndView doublonHandler(HttpServletRequest req, Exception e) {
	// ModelAndView m = new ModelAndView();
	// m.addObject("exception", e);
	// m.addObject("url", req.getRequestURL());
	// m.setViewName("error");// nom logique de la page d'erreur
	// return m;
	// }

	// Affichage du détail d'un Etudiant
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public String detailEtudiant(@PathVariable String code, Model model,  Authentication authentication) {
		// Vérifie si on ne recoit pas le Etudiant suite à une redirection
		if (!model.containsAttribute("etudiant")) {
			logger.debug("Recherche le etudiant: " + code);
			// recherche le etudiant dans la liste
			// Vérifie si le prof existe
			Etudiant etudiant = getEtudiant(code);
			// gestion spécifique pour la non présence de etudiant.
			if (etudiant == null)
				throw new NotFoundException("Cet etudiant n'existe pas : ", code);
			
			String userConnected = new String();
			if(authentication != null) {
				
				userConnected = authentication.getName();	
			} else {
				userConnected = "";
			}

			boolean isAdmin = false ;
			if ( authentication != null )
				isAdmin = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Roles.ROLE_ADMIN.name()))  ;

			if(code.equals(userConnected) || isAdmin ) {
				
				List<Module> modules = moduleDAO.getModulesOfEtudiant(etudiant.getUsername());
				
				model.addAttribute("etudiant", etudiant );
								
				model.addAttribute("listModules", modules    );
				
				List<String> statusModules = getInfosOfModule(modules, etudiant);
				
				for(int i=0;i<statusModules.size();i++) {
					System.out.println(statusModules.get(i).toString());
				}
				
				model.addAttribute("statusModules", statusModules    );
				
			} else {
				throw new NoAccessException("Doit être connecté admin ou l'étudiant " + code);
			}
			
			// Ajout au Modèle

			
			
		} else
			logger.debug("Utilisation d'un FlashAttribute pour le etudiant: " + code);
		
		return "etudiant/etudiant";
	}
	
	
	
	
	private List<String> getInfosOfModule(List<Module> modules, Etudiant etudiant) {
		
		List<String> allInfos = new ArrayList<>();
		for(Module module : modules) {
			
			List<Evaluation.SESSION> sessions = evaluationDAO.getSessionsOfModule(module.getCode());
			//System.out.println(module.getCode() + " " + (sessions.size() - 1) + " " + etudiant.getUsername());
			Evaluation evaluation = evaluationDAO.getEvaluationOfModuleOfEtudiant(module.getCode(), (sessions.size()-1), etudiant.getUsername() );
			
			List<Competence> competences = competenceDAO.getCompetencesOfCours(module.getCours().getCode());
			List<Object[]> validedComp = competenceDAO.getCompetencesValidOfEtudiant(etudiant.getUsername(), module.getCours().getCode());
			//System.out.println(evaluation.size());
			
			if(evaluation != null) {
				if(evaluation.getResultat() >= 50) {
					allInfos.add("Réussi (" + evaluation.getResultat() + "%)");
				} else {
					allInfos.add("Raté");
				} 
			}
			else {
				allInfos.add("En cours");
			}
			System.out.println(allInfos.size());
		}
		
		return allInfos;
	}
	
	
	
	



	// Renvoie un Optional de Etudiant
	private Etudiant getEtudiant(String code) {
			return etudiantDAO.findOne(code) ;
		
	}

}
