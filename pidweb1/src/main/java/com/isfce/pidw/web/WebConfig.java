package com.isfce.pidw.web;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc  //active SpringMVC
@ComponentScan(" com.isfce.pidw.web") //scan des ctrls
public class WebConfig extends WebMvcConfigurerAdapter {
 //map entre nom logique et vues jsp
  @Bean
  public ViewResolver viewResolver() {
	  
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    //spécifie le chemin des vues 
    resolver.setPrefix("/WEB-INF/views/");
    //spécifie l'extension .jsp au nom logique
    resolver.setSuffix(".jsp");
    //rends les beans du contexte accessible dans les pages JSP avec ${...}
    resolver.setExposeContextBeansAsAttributes(true);
    //Si on utilise JSTL. permet d'utiliser les locales dans les vues
    resolver.setViewClass(
    		org.springframework.web.servlet.view.JstlView.class);
    return resolver;
  }
  
  
  // ne pas traiter les ressources statiques
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
  
  //définit le mapping pour les ressources statiques
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
       //super.addResourceHandlers(registry);
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
  
  
  /**
   * Définition de l'endroit où trouver les fichiers de traduction
   * @return
   */
  @Bean
  public MessageSource messageSource() {
	  ResourceBundleMessageSource messageSource =
			  new ResourceBundleMessageSource();
			  messageSource.setBasename("messages");// messagesxx_XX.properties
			  messageSource.setDefaultEncoding("UTF-8");
			  return messageSource;
 /* configuration pour avoir les fichiers de messages en dehors de l'application*/
//    ReloadableResourceBundleMessageSource messageSource = 
//    new ReloadableResourceBundleMessageSource();
//    messageSource.setBasename("file:///Didier//messages");
//    messageSource.setCacheSeconds(10);
//    return messageSource;
  }
  
  
  // Comment et où mémoriser la locale
  @Bean
  public LocaleResolver localeResolver(){
	CookieLocaleResolver resolver = new CookieLocaleResolver();
	resolver.setDefaultLocale(new Locale("fr"));
//	resolver.setCookieName("maLocaleCookie");
	resolver.setCookieName("language");
	resolver.setCookieMaxAge(3600);
	return resolver;
  }
  
  
  // Changer la locale en fonction d'un paramètre de l'URL
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
	  //permet de changer la locale au niveau d'une requête
	LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	//interceptor.setParamName("malocale");// par défaut le nom est "locale"
	interceptor.setParamName("locale");  //  language
	registry.addInterceptor(interceptor);
  } 
  
  
//permet d'activer un contrôleur pour notre page de login personnelle
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  } 
}
