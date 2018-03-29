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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.isfce.pidw.config.security.GeneratePassword;
import com.isfce.pidw.config.security.Roles;
import com.isfce.pidw.data.ICoursJpaDAO;
//import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.data.IProfesseurJpaDAO;
import com.isfce.pidw.data.IUsersJpaDAO;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Professeur;
import com.isfce.pidw.model.Users;

@Controller
@RequestMapping("/professeur")
public class ProfesseurController {

	// Logger
	final static Logger logger = Logger.getLogger(ProfesseurController.class);
	
	private IProfesseurJpaDAO professeurDAO;


	// Création de la liste de données pour le 1er exemple
	@Autowired
	public ProfesseurController(IProfesseurJpaDAO professeurDAO) {
		this.professeurDAO = professeurDAO;		
	}

	
	// Liste des profs
	@RequestMapping("/liste")
	public String listeProfesseur(Model model) {
		
		System.out.println(   professeurDAO.findAll().toString()   );
		
		System.out.println(   professeurDAO.findAll().get(0).getUsername()   );
		
		
		model.addAttribute("professeurList", professeurDAO.findAll() );

		return "professeur/listeProfesseur";
	}

	
	
	
	
	// Méthode Get pour ajouter un professeur
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProfesseurGet(@ModelAttribute Professeur professeur, Model model) {
		logger.debug("affiche la vue pour ajouter un Professeur ");


		return "professeur/addProfesseur";
	}
	

	
	
	
	
	// Méthode Get pour faire un update d'un prof
	@RequestMapping(value = "/{code}/update", method = RequestMethod.GET)
	public String updateProfesseurGet(@PathVariable String code, Model model) {

		logger.debug("affiche la vue pour modifier un Professeur:" + code);
		
		if (!professeurDAO.exists( code ) )
			throw new NotFoundException("Le professeur n'existe pas", code  );
		// recherche le Professeur dans la liste
		Professeur professeur = professeurDAO.findOne( code );
		
		// Ajout au Modèle
		model.addAttribute("professeur", professeur);
		// Attribut maison pour distinguer un add d'un update
		model.addAttribute("savedId", professeur.getUsername() );

		
		// model.addAttribute("nouveau",false);
		return "professeur/addProfesseur";

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
	public String addUpdateProfesseurPost(@Valid Professeur professeur, BindingResult errors,
			@RequestParam(value = "savedId", required = false) String savedId, Model model, RedirectAttributes rModel) {
		
		
		
		logger.debug("Professeur Info:" + professeur.getUsername() + " savedId " + savedId);
		
		
		// Gestion de la validation
		if (errors.hasErrors()) {
			// Attribut maison pour distinguer un add d'un update
			if (savedId != null)
				model.addAttribute("savedId", savedId);


			logger.debug("Erreurs dans les données de professeur:" + professeur.getUsername());
			return "professeur/addProfesseur";
		}else {	logger.debug("Professeur Info: no errors");		}
		
		
		// distinction d'un update ou d'un add
		if (savedId == null) {
			logger.debug("Professeur Info: code = null ");

			// cas ADD
			// Vérification doublon
			if (professeurDAO.exists(professeur.getUsername() )) {
				logger.debug("Le professeur existe:" + professeur.getUsername() + " savedId " + savedId);
				//Exemple de gestion d'erreur en modifiant l'objet 'errors' et 
				//en retournant la vue
				errors.rejectValue("id", "Professeur.id.doubon", "Existe déjà!");

				return "professeur/addProfesseur";

			}
		} else {			// cas d'un Update

			logger.debug("professeur Info: code != null ");		
		// Est ce que le id a changé?
			
			if (!savedId.equals( professeur.getUsername() )) {
				// code à changé
				// Vérifie si pas en doublon avec un autre
				if (professeurDAO.exists(professeur.getUsername())) {
					logger.debug("Le Professeur Existe:" + professeur.getUsername() + " savedId " + savedId);
					throw new DuplicateException("Le Professeur " + professeur.getUsername() + " existe déjà");
				}
			}
			
			
			
			logger.debug("Mise à jour du professeur: " + savedId);

		}
		


		
		String pwd ;
		if(professeur.getPassword().equals("*******")) {
			pwd = professeurDAO.findOne(professeur.getUsername()).getPassword() ;
		}else {
			pwd = GeneratePassword.PasswordEncode( professeur.getPassword()  )  ;
		}
		professeur.setPassword(   pwd   );
		
		
		// ajoute le nouveau ou le Professeur nouvellement modifié
		professeur.setRole( Roles.ROLE_PROFESSEUR );
		professeurDAO.save(professeur);

		// Préparation des attribut Flash pour survivre à la redirection
		rModel.addFlashAttribute(professeur);
		// Gestion de la redirection pour l'UTF8 en cas d'accents
		String adr = "redirect:/professeur/"   + professeur.getUsername();
		// encode l'URI en percent-encoding pour les accents
		try {
			adr = UriUtils.encodePath(adr, "UTF8");
			logger.debug("UriUtils:Etudiant Adresse de redirection: " + adr);
		} catch (UnsupportedEncodingException e) {

		}

		return adr;
	}
	
	
	
	

	/**
	 * Supression d'un professeur
	 * 
	 * @param id
	 *            du professeur
	 * @return le mapping de redirection
	 */
	@RequestMapping(value = "/{code}/delete", method = RequestMethod.POST)
	public String deleteProfesseur(@PathVariable String code) {
		logger.debug("<DEBUT> Supression du professeur: " + code);
		
		// Vérifie si le cours existe
		if (!professeurDAO.exists(code))  {
			throw new NotFoundException("Professeur non trouvé pour suppression", code );
		}
		professeurDAO.delete(code);
		logger.debug("Supression du professeur: " + code);
		
		return "redirect:/professeur/liste";

	}
	

	

	// Affichage du détail d'un professeur
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public String detailProfesseur(@PathVariable String code, Model model) {
		// Vérifie si on ne recoit pas le professeur suite à une redirection
		if (!model.containsAttribute("professeur")) {
			logger.debug("Recherche le professeur: " + code);
			// recherche le professeur dans la liste
			// Vérifie si le prof existe
			Professeur professeur = professeurDAO.findOne(code);
			// gestion spécifique pour la non présence de professeur.
			if (professeur == null)
				throw new NotFoundException("Ce professeur existe déjà ", code);

			// Ajout au Modèle
			model.addAttribute("professeur", professeur );
		} else
			logger.debug("Utilisation d'un FlashAttribute pour le professeur: " + code);
		
		return "professeur/professeur";
	}


}
