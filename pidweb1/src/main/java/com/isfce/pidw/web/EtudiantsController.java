package com.isfce.pidw.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.google.gson.Gson;
//import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/etudiant")
public class EtudiantsController {

	// Logger
	final static Logger logger = Logger.getLogger(EtudiantsController.class);
	
	// on recupre la liste de cours et etudiant depois L'usine (factory)
//	private ICoursJpaDAO coursDAO;
	private IEtudiantJpaDAO etudiantDAO;
	
//	private  List<Etudiant> listeEtudiant ;
//	private  List<Cours> listeCours  ;
	
	

	// Création de la liste de données pour le 1er exemple
	@Autowired
	public EtudiantsController(IEtudiantJpaDAO etudiantDAO) {
		this.etudiantDAO = etudiantDAO;

//		listeEtudiant = etudiantDAO.findAll() ;
//		listeCours = coursDAO.findAll() ;
		
	}

	
	
	
	
	// Liste des Etudiant
	@RequestMapping("/liste")
	public String listeEtudiant(Model model) {
		model.addAttribute("etudiantList", etudiantDAO.findAll() );

		return "etudiant/listeEtudiant";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/liste.json")
	public String jsonCours( Model model ) {	
	
        List<Etudiant> xl =  etudiantDAO.findAll()  ;
        
        for(Etudiant c : xl){			//  c.getCours().clearSection();  //  works to avoid lazy loading
//			c.getCours().setSections(moduleDAO.coursSection2( c.getCours().getCode() ));
        } 
        Gson gson = new Gson();
		return gson.toJson( xl ) ;
	}
	
	
	

	// Affichage du détail d'un module
	@ResponseBody
	@RequestMapping(value = "/{code}.json", method = RequestMethod.GET)
	public String jsonCoursDetail(@PathVariable Long code, Model model) {
		logger.debug("affiche json du module :" + code);
		if (!etudiantDAO.exists(code))
			throw new NotFoundException("Le module n'existe pas", code.toString() );
		
		Etudiant x = etudiantDAO.findOne(code);
		
		Gson gson = new Gson();
		return  gson.toJson( x ) ;
	}
	
	
	
	
	

	
	// Méthode Get pour ajouter un Etudiant
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addEtudiantGet(@ModelAttribute Etudiant etudiant, Model model) {
		logger.debug("affiche la vue pour ajouter un Etudiant ");


		return "etudiant/addEtudiant";
	}
	

	
	
	// Méthode Get pour faire un update d'un Etudiant
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String updateEtudiantGet(@PathVariable Integer id, Model model) {

		logger.debug("affiche la vue pour modifier un Etudiant:" + id);
		
		if (!etudiantDAO.exists( (long) id) )
			throw new NotFoundException("Le cours n'existe pas", id.toString()  );
		// recherche le Etudiant dans la liste
		Etudiant etudiant = etudiantDAO.findOne( (long) id );
		// Ajout au Modèle
		model.addAttribute("etudiant", etudiant);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", etudiant.getId()  );

		
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
		
		
		long savedIdL ;
		if (savedId != null ) {	savedIdL	= Long.parseLong( savedId, 10) ;	}
		else {	savedIdL =0;		}
		
		
		logger.debug("Etudiant Info:" + etudiant.getId() + " savedId " + savedId);
		
		
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);


			logger.debug("Erreurs dans les données de l'étudiant:" + etudiant.getId());
			return "etudiant/addEtudiant";
		}else {	logger.debug("Etudiant Info: no errors");		}
		

		// Vérifie si le id du Etudiant existe déjà
//		Etudiant oEtudiant = getEtudiant(etudiant.getId());
		
		
		
		
		// distinction d'un update ou d'un add
		if (savedId == null) {
			logger.debug("Etudiant Info: id = null ");
/*
			// cas ADD
			// Vérification doublon
			if (etudiantDAO.exists( oEtudiant.getId() )) {
				logger.debug("Le Etudiant Existe:" + etudiant.getId() + " savedId " + savedId);
				//Exemple de gestion d'erreur en modifiant l'objet 'errors' et 
				//en retournant la vue
				errors.rejectValue("id", "Etudiant.id.doubon", "Existe déjà!");

				return "etudiant/addEtudiant";
				//Autre solution en générant une exception
				// throw new DuplicateException("Le Etudiant " + Etudiant.getId() + " existe déjà
				// ");
			}
*/
			
//	NEW
			etudiant.setId( etudiantDAO.generateId() + 1L ) ;
		} else {			// cas d'un Update

			logger.debug("Etudiant Info: id != null ");		
		// Est ce que le id a changé?
			etudiant.setId( savedIdL ) ;
			
			
			etudiantDAO.delete( savedIdL );			
			
/*			
			String temp = etudiant.getId().toString() ;
			if (!savedId.equals( temp  )) {			//		??? LONG AND STRING ???
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (etudiantDAO.exists(etudiant.getId())) {
					logger.debug("Le Etudiant Existe:" + etudiant.getId() + " savedId " + savedId);
					throw new DuplicateException("Le Etudiant " + etudiant.getId() + " existe déjà");
				}
				// retire le cours avec l'ancien code
				etudiantDAO.delete( savedIdL );
			} else
				// retire le cours de la liste
				etudiantDAO.delete(etudiant.getId());
*/
			
			
			logger.debug("Mise à jour du cours:" + savedId);

		}
		
//			etudiant.setId( etudiantDAO.generateId() + 1L );					//					Long.valueOf( savedIdL )  );
			


		
		// ajoute le nouveau ou le Etudiant nouvellement modifié
		etudiantDAO.save(etudiant);

		// Préparation des attribut Flash pour survivre à la redirection
		rModel.addFlashAttribute(etudiant);
		// Gestion de la redirection pour l'UTF8 en cas d'accents
		String adr = "redirect:/etudiant/"   + etudiant.getId();
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
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String deleteEtudiant(@PathVariable Long id) {
		
		// Vérifie si le cours existe
		if (!etudiantDAO.exists(  id  )  )  {
			throw new NotFoundException("Etudiant non trouvé pour suppression", id.toString() );
		}
		etudiantDAO.delete( (long) id );
		logger.debug("Supression du Etudiant: " + id);
		
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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detailEtudiant(@PathVariable Integer id, Model model) {
		// Vérifie si on ne recoit pas le Etudiant suite à une redirection
		if (!model.containsAttribute("etudiant")) {
			logger.debug("Recherche le Etudiant: " + id);
			// recherche le Etudiant dans la liste
			// Vérifie si le Etudiant existe
			Etudiant etudiant = getEtudiant(id);
			// gestion spécifique pour la non présence du Etudiant.
			if (etudiant == null)
				throw new NotFoundException("Ce Etudiant existe déjà ", id.toString());

			// Ajout au Modèle
			model.addAttribute("etudiant", etudiant );
		} else
			logger.debug("Utilisation d'un FlashAttribute pour le Etudiant: " + id);
		
//		model.addAttribute("coursList", listeCours);
		
//		logger.debug( listeCours.size() );
		
		
		return "etudiant/etudiant";
	}



	// Renvoie un Optional de Etudiant
	private Etudiant getEtudiant(Long id) {
			return etudiantDAO.findOne(id) ;
		
//		Optional<Etudiant> etudiant = listeEtudiant.stream().filter(c -> c.getId().equals(id)).findFirst();
//		return etudiant;
	}
	private Etudiant getEtudiant(int id) {
		return getEtudiant( (long) id ) ;
	}
}
