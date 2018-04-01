package com.isfce.pidw.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	// Logger
		final static Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = GET)
	public String home(Locale locale, Model model) {
		
		// Formattage de la date et l'heure locale du client
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping("/403")
	public String error403() {
		return "/accessDenied";
	}
	
}
