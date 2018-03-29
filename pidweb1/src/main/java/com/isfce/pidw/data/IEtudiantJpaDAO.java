package com.isfce.pidw.data;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Etudiant;

@Repository
public interface IEtudiantJpaDAO extends IUsersJpaDAO<Etudiant> {

	@Query(value="select nom from TCOURS", nativeQuery=true)
	List<String> getCoursNomList();
	
	
	@Query(value="select e.EMAIL,e.NOM,e.PRENOM,e.TEL, u.* from TETUDIANT e inner join TINSCRIPTION i on i.FKETUDIANT=e.USERNAME inner join TUSERS u on u.USERNAME=e.USERNAME  where i.FKMODULE=?", nativeQuery=true)
	List<Etudiant> getEtudiantsOfModule(String code);
	

	@Query(value="	select  e.USERNAME   from TETUDIANT e \r\n" + 
			"	  inner join TEVALUATION ev on i.FKMODULE =ev.FKMODULE AND ev.FKETUDIANT=e.USERNAME \r\n" + 
			"	  inner join TINSCRIPTION i on i.FKETUDIANT=e.USERNAME\r\n" + 
			"	  inner join TUSERS u on u.USERNAME=e.USERNAME \r\n" + 
			"	where ev.RESULTAT BETWEEN 0 AND  49 AND i.FKMODULE=?", nativeQuery=true)
	List<String> get2ndSessionFkEtudiantsOfModule(String code);
	
	
}
