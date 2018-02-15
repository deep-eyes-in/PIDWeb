package com.isfce.pidw.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.isfce.pidw.config.security.Roles;
import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.data.IProfesseurJpaDAO;
import com.isfce.pidw.data.IUsersJpaDAO;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Module;
import com.isfce.pidw.model.SignUp;
import com.isfce.pidw.model.Users;

@Controller
@RequestMapping("/module")
public class ModuleController {
	// Logger
	final static Logger logger = Logger.getLogger(ModuleController.class);

	private IModuleJpaDAO moduleDAO;
	private ICoursJpaDAO coursDAO;
	private IProfesseurJpaDAO professeurDAO;
	private IEtudiantJpaDAO etudiantDAO;
	private IUsersJpaDAO<Users> usersDAO;

	@Autowired
	public ModuleController(IModuleJpaDAO moduleDAO, IProfesseurJpaDAO professeurDAO, ICoursJpaDAO coursDAO, IEtudiantJpaDAO etudiantDAO, IUsersJpaDAO<Users> usersDAO ) {
		super();
		this.moduleDAO	 = moduleDAO;
		this.professeurDAO	 = professeurDAO;
		this.coursDAO	 = coursDAO;
		this.etudiantDAO	 = etudiantDAO ;

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
	
	
		
	
	@RequestMapping(value = { "/add", "/{code}/update"  },  method = RequestMethod.GET)  
	public String addUpdateModules(@PathVariable Optional<String> code, @ModelAttribute Module module, Model model /* , Authentication authentication */) {
		
//		logger.debug(" user connecté: " + (authentication == null ? " NULL " : authentication.getName()));
		
		System.out.println( "°addUpdateModules°°°°°°°°°°°°°°°°°°START°");
		
		// si on ne précise pas de "codeUser"
		if ( code.isPresent() ) {
			logger.debug("affiche la vue pour modifier un module:" + code);
			if (!moduleDAO.exists(code.get() ))
				throw new NotFoundException("Le module n'existe pas", code.get());
			
			// recherche le module dans la liste
			Module m = moduleDAO.findOne( code.get() );
			m.getCours().setSections(moduleDAO.getCoursSection(m.getCours().getCode() ));
			
//			m.setEtudiants(  moduleDAO.getEtudiantsOfModule( code.get() )  );
			
			
			// Attribut maison pour distinguer un add d'un update
			model.addAttribute("savedId", module.getCode());
			model.addAttribute("module", m);
		}   else {
			
//			Professeur prof =   usersDAO. ; //  profDAO.getOne("VO");
//			module.setProf(prof);
			
			model.addAttribute("module", module);
		}
		
		model.addAttribute( "coursList", getListLabelled( "cours" ) );
		
		model.addAttribute( "profList", getListLabelled( "professeur" ) );
		
		
		model.addAttribute( "etudiantList", getListLabelled( "etudiant" ) );
		
		
		System.out.println( "°addUpdateModules°°°°°°°°°°°°°°°°°°END°");
		
		return "module/addModule";
	}
	

	
	
	
	
	
	
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUpdateModulePost(@ModelAttribute Module module, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) throws ParseException {
		
		
		System.out.println( "_________________" );
		
		System.out.println( errors.getAllErrors().toString()     );
		
		
		
	
//		/*	
		
		
		// Gestion de la validation
		if ( errors.hasErrors() ) {			//			errors.hasErrors()    errors.getErrorCount()  > 1 
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);
			System.out.println("YOOOOOOOOOOO");
			// ajoute les données de la liste déroulante
			model.addAttribute("module", module);
			model.addAttribute( "coursList", getListLabelled( "cours" ) );
			model.addAttribute( "profList", getListLabelled( "professeur" ) );
			
			
System.out.println("COUCOUUUUUUUUUU");
			logger.debug("Erreurs dans les données du module:" + module.getCode());
			return "module/addModule";
		}
		
		
//		*/		
		
		
		System.out.println( "DONT HAVE hasErrors" );
		

		

		// distinction d'un update ou d'un add
		if (savedId == null) {
			System.out.println( "SAVEID = null " );
			
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
			System.out.println( "SAVEID != null " );
			
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
		
		
/*		
		Set<Etudiant> etudiants = new HashSet<>();
		
		etudiants.add( etudiantDAO.findAll().get(0) )  ;
		module.setEtudiants(etudiants);
*/
		
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

	
	
	
	
	
	
	
	
	
	
	



	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUpModulesPost(
			@ModelAttribute SignUp signUp,

			BindingResult errors,
			 Model model
			 ) throws ParseException {
		
		
		System.out.println( "_________________" ) ;
		System.out.println( model.toString() );
//		System.out.println( errors.getAllErrors().toString()     );
		
		List<Object[]> allInscriptions = new ArrayList<>();
		allInscriptions = moduleDAO.getAllInscriptions() ;
		

		String mCode = new String();
		String eUsername = new String();
		
		for (int i = 0; i < allInscriptions.size(); i++) {
			System.out.println(allInscriptions.get(i)[0] + " " + allInscriptions.get(i)[1]);
			
			if ( mCode.equals(  allInscriptions.get(i)[0]  ) ) {
				
				Module module =  moduleDAO.findOne( allInscriptions.get(i)[0].toString()  )  ;
				for (int j=0 ; j < allInscriptions.size()  ;  j++ )  {
					if( mCode.equals(  ) ) {
						
					}
					Etudiant e = ( Etudiant ) usersDAO.findOne( allInscriptions.get(i)[1].toString()  )  ;
				}
				
				module.getEtudiants().add( e ) ;
				
			}
			
			mCode = allInscriptions.get(i)[0].toString() ;
			eUsername = allInscriptions.get(i)[1].toString() ;
		}


		return "module/signup" ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = { "/signup", "/{code}/signup"  },  method = RequestMethod.GET)
	public String signUpModules(
			@PathVariable Optional<String> code, 
			@ModelAttribute SignUp signUp,
			Model model /* , Authentication authentication */
	) {
		
//		logger.debug(" user connecté: " + (authentication == null ? " NULL " : authentication.getName()));
		
		System.out.println( "SIGNUPMODULE°°°°°°°°°°°°°°°°°°START°");
		
		
		
		
		model.addAttribute("moduleList", moduleDAO.getModuleCodeList() );
//		model.addAttribute("etudiantList", moduleDAO.getModuleCodeList() );
		
		
		
		System.out.println(  getListLabelled( "etudiant" ).toString()  );
		
		model.addAttribute( "etudiantList", getListLabelled( "etudiant" ) );
		
		
		System.out.println( "°SIGNUPETUDIANT°°°°°°°°°°°°°°°°°°END°");
		
		return "module/signup";
	}
	
	
	
	
	
	
	
	
	
	
	

	

	public Map<String, String> getListLabelled( String obj ) {
		System.out.println( "°_START°°°°°°°°°°°°°°°°°°°");
		
		Map<String, String> listLabelled = new LinkedHashMap<String, String>();

		List<Object[]> codeNomList ;
		
		if ( obj == "cours"  ) {
			codeNomList = moduleDAO.getCoursCodeNomList();
		}else if( obj == "module"  ){
			codeNomList = null ;
		}else if( obj == "etudiant"  ){
			codeNomList = etudiantDAO.getEtudiantCodeNomList();
		}else {
			codeNomList = professeurDAO.getProfCodeNomList();		//		usersDAO
		}
		
		
		for (int i = 0; i < codeNomList.size(); i++) {
			listLabelled.put(codeNomList.get(i)[0].toString(), codeNomList.get(i)[1].toString());
			System.out.println(codeNomList.get(i)[0] + " " + codeNomList.get(i)[1]);
		}
		
		System.out.println( "°_END°°°°°°°°°°°°°°°°°°°");
		return listLabelled;
	}
	
	
	
	@ModelAttribute("dateFormat")
	public String dateFormat() {
	    return "yyyy-MM-dd" ;
	}
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormat());
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	
	
	

}
