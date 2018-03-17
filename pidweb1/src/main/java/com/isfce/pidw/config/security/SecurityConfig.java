package com.isfce.pidw.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	// Encodeur pour les passwords lors du login
	@Bean
	public PasswordEncoder encoder() {
		return new StandardPasswordEncoder("53cr3t");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "PROFESSEUR");
		auth.inMemoryAuthentication().withUser("VO").password("VO").roles("PROFESSEUR");
		auth.inMemoryAuthentication().withUser("Et1").password("Et1").roles("ETUDIANT");
		
//		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	// A redéfinir pour configurer la manière dont les requêtes doivent être
	// sécurisées
	// !! PROF ==> ROLE_PROF (ROLE_ est rajouté automatiquement)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//	module
			.antMatchers("/module/**/update", "/module/**/delete").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.POST, "/module/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/module/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.POST, "/module/signup").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/module/signup").hasAnyRole( "ADMIN")
		
			//	cours
			.antMatchers("/cours/**/update", "/cours/**/delete").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.POST, "/cours/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/cours/add").hasAnyRole( "ADMIN")
			
			//	professeur
			.antMatchers( "/professeur/**/update", "/professeur/**/delete" ).hasAnyRole( "ADMIN")	//
			.antMatchers(HttpMethod.POST, "/professeur/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/professeur/add").hasAnyRole( "ADMIN")
			
			//	etudiant
			.antMatchers( "/etudiant/**/update", "/etudiant/**/delete" ).hasAnyRole( "ADMIN")	//
			.antMatchers(HttpMethod.POST, "/etudiant/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/etudiant/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.POST, "/etudiant/liste").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/etudiant/liste").hasAnyRole( "ADMIN")
			
			//	etudiant
			.antMatchers( "/module/evaluation/**/update", "/module/evaluation/**/delete" ).hasAnyRole( "ADMIN")	//
			.antMatchers(HttpMethod.POST, "/module/evaluation/add").hasAnyRole( "ADMIN")
			.antMatchers(HttpMethod.GET, "/module/evaluation/add").hasAnyRole( "ADMIN")

			
			
			
			.antMatchers("/**").access("permitAll")
			.and()
				.formLogin().loginPage("/login")
			.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
			.and()
				.exceptionHandling().accessDeniedPage("/403");
	}

}
