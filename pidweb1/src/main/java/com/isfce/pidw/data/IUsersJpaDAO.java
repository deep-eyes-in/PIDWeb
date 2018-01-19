package com.isfce.pidw.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.config.security.Roles;
import com.isfce.pidw.model.Users;

@Repository
public interface IUsersJpaDAO extends JpaRepository<Users, String> {
	@Query(value = "select count(*)=1 from TUSERS m where m.USERNAME= ?1 and m.ROLE= ?2", nativeQuery = true)
	boolean readByUsernameAndRoleEquals(String username, Roles roles);

	@Query(value = "select ROLE from TUSERS  where USERNAME= ?1", nativeQuery = true)
	Roles getUserNameRole(String username);
}
