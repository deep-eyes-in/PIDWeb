package com.isfce.pidw.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isfce.pidw.data.IUsersJpaDAO;
import com.isfce.pidw.model.Users;

/**
 * Retourne la liste des utilisateurs pour obtenir  les "UserDetails" de l'utilisateur connecté
 * Nécessaire pour la configuration de la sécurité
 * @author Didier
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	private IUsersJpaDAO usersDAO;

	@Autowired
	public MyUserDetailsService(IUsersJpaDAO usersDAO) {
		super();
		this.usersDAO=usersDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = usersDAO.findOne(username);
		if (user != null)
			return user;
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

}
