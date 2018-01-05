package com.isfce.pidw.web;


import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/module")
public class ModuleController  {

	// Logger
	final static Logger logger = Logger.getLogger(ModuleController.class);

	private IModuleJpaDAO moduleDAO;
	


	// Création de la liste de données pour le 1er exemple
	@Autowired
	public ModuleController(IModuleJpaDAO moduleDAO) {
		this.moduleDAO = moduleDAO;
		
	}

	
	
	public static Object[] getArray(IModuleJpaDAO bean, String key) {
	    return bean.getArray(key);
	}
	
	
	
	// Liste des module
	@RequestMapping("/liste")
	public String listemMdule(Model model) {
		model.addAttribute("moduleList", moduleDAO.findAll() );
		
//		Object[] array = bean.getArray("foo") ;
		model.addAttribute("array", array);			//			setAttribute
//		model.addAttribute("intitule", moduleDAO  );
		
		return "module/listeModule";
	}
	
	
	
	
	

	// Méthode Get pour ajouter un module
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addModuleGet(@ModelAttribute Module module, Model model) {
		logger.debug("affiche la vue pour ajouter un module ");
		
		model.addAttribute( "coursList", moduleDAO.getCoursCodeList() );
		model.addAttribute( "momentList", Module.MAS.values()  );
		
		// Attribut maison pour distinguer un add d'un update
		// model.addAttribute("savedId", null);
		return "module/addModule";
	}

	// Méthode Get pour faire un update d'un module
	@RequestMapping(value = "/{code}/update", method = RequestMethod.GET)
	public String updateModuleGet(@PathVariable String code, Model model) {

		logger.debug("affiche la vue pour modifier un module:" + code);
		if (!moduleDAO.exists(code))
			throw new NotFoundException("Le module n'existe pas", code);
		// recherche le module dans la liste
		Module module = moduleDAO.findOne(code);
		// Ajout au Modèle
		model.addAttribute("module", module);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", module.getCode());
		model.addAttribute( "coursList", moduleDAO.getCoursCodeList()  );
		
		
		// model.addAttribute("nouveau",false);
		return "module/addModule";

	}

	/**
	 * Méthode POST pour un ajout ou un update de module. La distinction d'un add et
	 * d'un update se fait sur le paramètre savedId (Id du module avant l'update) En
	 * cas de doublon une exception est générée et traitée sur une page
	 * personnalisée En cas d'erreur de validation, on retourne sur la même vue.
	 * 
	 * @param module
	 *            le module crée
	 * @param errors
	 *            les erreurs suite à une validation
	 * @param savedId
	 *            le code du module avant un update sinon null si un add
	 * @param model
	 *            l'objet modèle à retourner en cas d'erreur
	 * @param rModel
	 *            permet de mettre des flashs attributes pour la redirection
	 * @return l'adresse URI de redirection
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUpdateModulePost(@Valid Module module, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) {
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);
			
			// ajoute les données de la liste déroulante
			model.addAttribute( "coursList", moduleDAO.getCoursCodeList() );

			
			logger.debug("Erreurs dans les données du module:" + module.getCode());
			return "module/addModule";
		}

		// distinction d'un update ou d'un add
		if (savedId == null) {
			// cas ADD
			// Vérification doublon
			if (moduleDAO.exists(module.getCode())) {
				logger.debug("Le module Existe:" + module.getCode() + " savedId " + savedId);
				// Exemple de gestion d'erreur en modifiant l'objet 'errors' et
				// en retournant la vue
				errors.rejectValue("code", "module.code.doubon", "Existe déjà!");
				
				model.addAttribute( "coursList", moduleDAO.getCoursCodeList() );
				return "module/addModule";
				// Autre solution en générant une exception
				// throw new DuplicateException("Le module " + module.getCode() + " existe déjà
				// ");
			}
		} else      // cas d'un Update
			
		{ // Est ce que le code a changé?
			if (!savedId.equals(module.getCode())) {
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (moduleDAO.exists(module.getCode())) {
					logger.debug("Le module Existe:" + module.getCode() + " savedId " + savedId);
					throw new DuplicateException("Le module " + module.getCode() + " existe déjà");
				}
				// retire le module avec l'ancien code
				moduleDAO.delete(savedId);
			} else
				// retire le module de la liste
				moduleDAO.delete(module.getCode());
			logger.debug("Mise à jour du module:" + savedId);

		}

		// ajoute le nouveau ou le module nouvellement modifié
		moduleDAO.save(module);

		// Préparation des attribut Flash pour survivre à la redirection
		rModel.addFlashAttribute(module);
		// Gestion de la redirection pour l'UTF8 en cas d'accents
		String adr = "redirect:/module/" + module.getCode();
		// encode l'URI en percent-encoding pour les accents
		try {
			adr = UriUtils.encodePath(adr, "UTF8");
			logger.debug("UriUtils:Module Adresse de redirection: " + adr);
		} catch (UnsupportedEncodingException e) {

		}

		return adr;
	}

	/**
	 * Supression d'un module
	 * 
	 * @param code
	 *            du module
	 * @return le mapping de redirection
	 */
	@RequestMapping(value = "/{code}/delete", method = RequestMethod.POST)
	public String deleteModule(@PathVariable String code) {
		// Vérifie si le module existe
		if (!moduleDAO.exists(code))
			throw new NotFoundException("module non trouvé pour suppression", code);
		moduleDAO.delete(code);
		logger.debug("Supression du module: " + code);
		return "redirect:/module/liste";
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

	// Affichage du détail d'un module
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public String detailModule(@PathVariable String code, Model model) {
		// Vérifie si on ne recoit pas le module suite à une redirection
		if (!model.containsAttribute("module")) {
			logger.debug("Recherche le module: " + code);
			// recherche le module dans la liste
			// Vérifie si le module existe
			Module module = moduleDAO.findOne(code);
			// gestion spécifique pour la non présence du module.
			if (module == null)
				throw new NotFoundException("Ce module existe déjà ", code);
			
			
//			module.setSections(moduleDAO.moduleSection2(module.getCode()));
			
			// Ajout au Modèle
			model.addAttribute("module", module);
		} else
			logger.debug("Utilisation d'un FlashAttribute pour le module: " + code);
		return "module/module";
	}

	
	
	
	

	

}
