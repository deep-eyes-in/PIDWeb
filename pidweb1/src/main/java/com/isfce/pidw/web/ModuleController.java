package com.isfce.pidw.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import com.isfce.pidw.config.security.Roles;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.data.IUsersJpaDAO;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/module")
public class ModuleController {
	// Logger
	final static Logger logger = Logger.getLogger(ModuleController.class);

	private IModuleJpaDAO moduleDAO;
	private IUsersJpaDAO usersDAO;

	@Autowired
	public ModuleController(IModuleJpaDAO moduleDAO, IUsersJpaDAO usersDAO) {
		super();
		this.moduleDAO = moduleDAO;
		this.usersDAO = usersDAO;
	}

	/**
	 * Liste des modules ou liste de l'utilisateur précisé (Prof ou étudiant) un
	 * étudiant ne peut pas voir les modules d'un autre élève que lui-même
	 * 
	 * @param codeUser
	 *            username 1) si vide ==> retourne la liste de tous les modules 2)
	 *            si prof ==> retourne la liste des modules donnés par le prof 3) si
	 *            étudiant ==> retourne la liste des modules dans lesquels il est
	 *            inscrit (ne peut pas que ses inscriptions)
	 * 
	 * @param model
	 * @param authentication
	 *            droits de l'utilisateur connecté
	 * @return nom logique de la vue
	 */

	@RequestMapping(value = { "/liste/{codeUser}", "/liste" })
	public String listeModules(@PathVariable Optional<String> codeUser, Model model, Authentication authentication) {
		List<Module> lm = null;
		String texte = null;
		logger.debug(" user connecté: " + (authentication == null ? " NULL " : authentication.getName()));
		// si on ne précise pas de "codeUser"
		if (!codeUser.isPresent()) {
			lm = moduleDAO.findAll();
			texte = "de l'école";

		} else {
			// role de codeUser
			Roles roleUser = usersDAO.getUserNameRole(codeUser.get());

			// si le code user est un prof retourne les modules du prof
			if (Roles.ROLE_PROF.equals(roleUser)) {
				lm = moduleDAO.readByProfesseurIsNotNull(codeUser.get());
				texte = "du professeur: " + codeUser.get();
				logger.debug("Modules du prof " + codeUser.orElse("vide: ") + "NB Modules :" + lm.size());
			} else // renvoie la liste des modules de l'étudiant (et contrôle les droits)
			if (Roles.ROLE_ETUDIANT.equals(roleUser)) {
				// il faut être authentifié et si on est un étudiant on ne peut voir que ses
				// propres modules. Déclenche une exception autrement!
				if (authentication == null || 
						(!authentication.getName().equals(codeUser.get()) && authentication
						.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Roles.ROLE_ETUDIANT.name()))))
					throw new NoAccessException("Doit être connecté comme prof, admin ou l'étudiant " + codeUser.get());
				
				lm = moduleDAO.getModulesOfEtudiant(codeUser.get());
				texte = "de l'étudiant: " + codeUser.get();
				logger.debug("Modules de l'étudiant " + codeUser.orElse("vide: ") + "NB Inscriptions :" + lm.size());
			} else texte= " (user inconnu: "+codeUser.get()+")";
		}
		if ( lm != null ) {
			for(Module c : lm){  
				c.getCours().setSections(moduleDAO.getCoursSection( c.getCours().getCode() ));
				c.setEtudiants(moduleDAO.getEtudiantsOfModule( c.getCode() ));
			}
		}
		
		model.addAttribute("userModules", texte);// le texte pour indiquer de qui sont les modules
		model.addAttribute("moduleList", lm);// liste des modules
		return "module/listeModules";
	}
	
	
	
	// Méthode Get pour ajouter un module
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addModuleGet(@ModelAttribute Module module, Model model) {
		logger.debug("affiche la vue pour ajouter un module ");
		
		Map<String, String> coursListLabelled = new LinkedHashMap<String, String>();

		List<Object[]> coursCodeNomList = moduleDAO.getCoursCodeNomList();
		
		for (int i = 0; i < coursCodeNomList.size(); i++) {
			coursListLabelled.put(coursCodeNomList.get(i)[0].toString(), coursCodeNomList.get(i)[1].toString());
			System.out.println(coursCodeNomList.get(i)[0] + " " + coursCodeNomList.get(i)[1]);
		}
		
		model.addAttribute( "coursList", coursListLabelled );
		model.addAttribute( "momentList", Module.MAS.values()  );
		
		// Attribut maison pour distinguer un add d'un update
		// model.addAttribute("savedId", null);
		return "module/addModule";
	}
	
	public Map<String, String> getCoursListLabelled() {
		Map<String, String> coursListLabelled = new LinkedHashMap<String, String>();

		List<Object[]> coursCodeNomList = moduleDAO.getCoursCodeNomList();
		
		for (int i = 0; i < coursCodeNomList.size(); i++) {
			coursListLabelled.put(coursCodeNomList.get(i)[0].toString(), coursCodeNomList.get(i)[1].toString());
			System.out.println(coursCodeNomList.get(i)[0] + " " + coursCodeNomList.get(i)[1]);
		}
		
		return coursListLabelled;
	}

	// Méthode Get pour faire un update d'un module
	@RequestMapping(value = "/{code}/update", method = RequestMethod.GET)
	public String updateModuleGet(@PathVariable String code, Model model) {

		logger.debug("affiche la vue pour modifier un module:" + code);
		if (!moduleDAO.exists(code))
			throw new NotFoundException("Le module n'existe pas", code);
		
		

		
		
		// recherche le module dans la liste
		Module module = moduleDAO.findOne(code);
		module.getCours().setSections(moduleDAO.getCoursSection(module.getCours().getCode() ));
		
		model.addAttribute( "coursList", getCoursListLabelled() );
		// Ajout au Modèle
		model.addAttribute("module", module);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", module.getCode());

		return "module/addModule";

	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUpdateModulePost( Module module, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) throws ParseException {
		
		
		System.out.println( "_________________" );
		System.out.println(module.toString());
		System.out.println(module.getDateDebut());
		//module.setDateDebutTemp("2001-01-01");
		//System.out.println(module.getDateDebut());
		
		//System.out.println(module.toString());
		
		
		System.out.println( "_________________" );
		
		
		Module m = moduleDAO.findOne(savedId);
		String str = m.getCours().getCode() ;
		Cours c = moduleDAO.getCoursByCode( str ) ;
		module.setCours( c );
		
		System.out.println( "0000000000000000000000000" );
		
		Set<String> theSection = moduleDAO.getCoursSection(module.getCours().getCode() ) ;
		module.getCours().setSections(theSection);
		
		//System.out.println(module.toString());
		

		
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);
			
			// ajoute les données de la liste déroulante
			model.addAttribute("module", module);
			model.addAttribute( "coursList", getCoursListLabelled() );

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

		System.out.println( "°°°°°°°°°°°°°°°°°°°°");
		System.out.println( module.getCours().getCode()  );
		
		
		
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
			if (module == null) {
				logger.debug("Problème : Not found");
				throw new NotFoundException("Ce module existe déjà ", code);
			}
			
			
			// Ajout au Modèle
			model.addAttribute("module", module);
		} else
			logger.debug("Utilisation d'un FlashAttribute pour le module: " + code);
		
		return "module/module";
	}

}