package com.isfce.pidw.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CentralExceptionHandle {
	
	@ExceptionHandler({DuplicateException.class, NoAccessException.class,BDException.class})
	private ModelAndView doublonHandler(HttpServletRequest req, Exception e) {
		ModelAndView m = new ModelAndView();
		m.addObject("exception", e);
		m.addObject("url", req.getRequestURL());
		m.setViewName("error");// nom logique de la page d'erreur
		return m;
	}
}
