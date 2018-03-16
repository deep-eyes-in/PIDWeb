package com.isfce.pidw.config.security;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

//permet d'introduire un filtre pour dispatcher les requêtes vers SpringSecurity
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
	final static Logger logger = Logger.getLogger(SecurityWebInitializer.class);

	@Bean
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		Filter characterEncodingFilter = new CharacterEncodingFilter();
		try {
			characterEncodingFilter = servletContext.createFilter(CharacterEncodingFilter.class);
			logger.debug("DONE : lors de la création du filtre mettant l'encodage des requêtes POST en UTF-8");	
		} catch (ServletException s) {
			logger.debug("ERREUR lors de la création du filtre mettant l'encodage des requêtes POST en UTF-8");			
		}
		FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
		filterRegistration.addMappingForUrlPatterns(null, false, "/*");
	}
	
	
	
}
