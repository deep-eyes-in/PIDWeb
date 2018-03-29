package com.isfce.pidw.web;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.isfce.pidw.data.ICompetenceJpaDAO;
import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.filter.ConsoleColors;
import com.isfce.pidw.model.Competence;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Module;

@Controller
@RequestMapping("/cours")
public class CoursController {

	// Logger
	final static Logger logger = Logger.getLogger(CoursController.class);

	private ICoursJpaDAO coursDAO;
	private IModuleJpaDAO moduleDAO ;
	private ICompetenceJpaDAO competenceDAO;
	
	// Liste des langues
	private List<String> listeLangues;

	
	@Autowired
	public CoursController(ICoursJpaDAO coursDAO, IModuleJpaDAO moduleDAO, ICompetenceJpaDAO competenceDAO) {
		this.coursDAO = coursDAO;
		this.moduleDAO = moduleDAO ;
		this.competenceDAO = competenceDAO;
		listeLangues = creeListeLangues();
	}

	
	
	
	// Liste des cours
	@RequestMapping("/liste")
	public String listeCours(Model model,Principal principal) {
		System.out.printf( "*["+  this.getClass().getSimpleName() + "]"  +  "[listeCours]"  +  "[]" );
		
		model.addAttribute("coursList",coursDAO.findAll());
		
		
		return "cours/listeCours";
	}
	
	
	// Méthode Get pour ajouter un cours
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCoursGet(@ModelAttribute Cours cours, Model model, Authentication authentication ) {
		logger.debug("affiche la vue pour ajouter un cours ");
		
		logger.debug(" user connecté: " + (authentication == null ? " NULL " : authentication.getName()));
		
		
		model.addAttribute("languesList", listeLangues);
		// Attribut maison pour distinguer un add d'un update
		// model.addAttribute("savedId", null);
		return "cours/addCours";
	}

	// Méthode Get pour faire un update d'un cours
	@RequestMapping(value = "/{code}/update", method = RequestMethod.GET)
	public String updateCoursGet(@PathVariable String code, Model model) {

		logger.debug("affiche la vue pour modifier un cours:" + code);
		if (!coursDAO.exists(code))
			throw new NotFoundException("Le cours n'existe pas", code);
		// recherche le cours dans la liste
		Cours cours = coursDAO.findOne(code);
		// Ajout au Modèle
		model.addAttribute("cours", cours);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", cours.getCode());
		model.addAttribute("languesList", listeLangues);
		// model.addAttribute("nouveau",false);
		return "cours/addCours";

	}

	/**
	 * Méthode POST pour un ajout ou un update de cours. La distinction d'un add et
	 * d'un update se fait sur le paramètre savedId (Id du cours avant l'update) En
	 * cas de doublon une exception est générée et traitée sur une page
	 * personnalisée En cas d'erreur de validation, on retourne sur la même vue.
	 * 
	 * @param cours
	 *            le cours crée
	 * @param errors
	 *            les erreurs suite à une validation
	 * @param savedId
	 *            le code du cours avant un update sinon null si un add
	 * @param model
	 *            l'objet modèle à retourner en cas d'erreur
	 * @param rModel
	 *            permet de mettre des flashs attributes pour la redirection
	 * @return l'adresse URI de redirection
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUpdateCoursPost(@Valid Cours cours, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) {
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);
			// ajoute les données de la liste déroulante
			model.addAttribute("languesList", listeLangues);
			logger.debug("Erreurs dans les données du cours:" + cours.getCode());
			return "cours/addCours";
		}

		// distinction d'un update ou d'un add
		if (savedId == null) {
			// cas ADD
			// Vérification doublon
			if (coursDAO.exists(cours.getCode())) {
				logger.debug("Le cours Existe:" + cours.getCode() + " savedId " + savedId);
				// Exemple de gestion d'erreur en modifiant l'objet 'errors' et
				// en retournant la vue
				errors.rejectValue("code", "cours.code.doubon", "Existe déjà!");
				model.addAttribute("languesList", listeLangues);
				return "cours/addCours";
				// Autre solution en générant une exception
				// throw new DuplicateException("Le cours " + cours.getCode() + " existe déjà
				// ");
			}
		} else
		// cas d'un Update
		{ // Est ce que le code a changé?
			if (!savedId.equals(cours.getCode())) {
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (coursDAO.exists(cours.getCode())) {
					logger.debug("Le cours Existe:" + cours.getCode() + " savedId " + savedId);
					throw new DuplicateException("Le cours " + cours.getCode() + " existe déjà");
				}
				// retire le cours avec l'ancien code
				coursDAO.delete(savedId);
			} else
				// retire le cours de la liste
				coursDAO.delete(cours.getCode());
			logger.debug("Mise à jour du cours:" + savedId);

		}

		// ajoute le nouveau ou le cours nouvellement modifié
		coursDAO.save(cours);

		// Préparation des attribut Flash pour survivre à la redirection
		rModel.addFlashAttribute(cours);
		// Gestion de la redirection pour l'UTF8 en cas d'accents
		String adr = "redirect:/cours/" + cours.getCode();
		// encode l'URI en percent-encoding pour les accents
		try {
			adr = UriUtils.encodePath(adr, "UTF8");
			logger.debug("UriUtils:Cours Adresse de redirection: " + adr);
		} catch (UnsupportedEncodingException e) {

		}

		return adr;
	}

	/**
	 * Supression d'un cours
	 * 
	 * @param code
	 *            du cours
	 * @return le mapping de redirection
	 */
	@RequestMapping(value = "/{code}/delete", method = RequestMethod.POST)
	public String deleteCours(@PathVariable String code) {
		// Vérifie si le cours existe
		if (!coursDAO.exists(code))
			throw new NotFoundException("cours non trouvé pour suppression", code);
		try {
			coursDAO.delete(code);
		} catch (DataIntegrityViolationException e) {
			logger.error("SQL: ",e);
			throw new NoAccessException(" Suppression impossible: ce cours possède des dépendances");
		}
		logger.debug("Supression du cours: " + code);
		return "redirect:/cours/liste";
	}

	

	// Affichage du détail d'un cours
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8;pageEncoding=UTF-8")
	public String detailCours(@PathVariable String code, Model model) {
		
		logger.warn( ConsoleColors.f( "*["+  this.getClass().getSimpleName() + "]"  +  "[detailCours]"  +  "[]" , 
				ConsoleColors.BLUE_BACKGROUND_BRIGHT  )   );
		

		logger.debug("Recherche le cours: " + code);

		// gestion spécifique pour la non présence du cours.
		if ( !coursDAO.exists(code) )
			throw new NotFoundException("Ce cours n'existe pas : ", code);

		
		// On recupère les compétences du cours
		List<Competence> competences = competenceDAO.getCompetencesOfCours( code );

		// On récupère les modules du cours
		List<Module> moduleList = moduleDAO.getModulesOfCours(code) ;
		
		
		// Ajout des model
		model.addAttribute("cours", coursDAO.findOne(code) );
		model.addAttribute("competenceList", competences);
		model.addAttribute("moduleList",  moduleList );
		
		
		
		return "cours/cours";
	}
	
	


	// crée un vecteur avec la liste des langues
	private List<String> creeListeLangues() {
		List<String> langues = new ArrayList<>();
		langues.add("Français");
		langues.add("Anglais");
		langues.add("Néerlandais");
		langues.add("Espagnol");
		return langues;
	}

}
