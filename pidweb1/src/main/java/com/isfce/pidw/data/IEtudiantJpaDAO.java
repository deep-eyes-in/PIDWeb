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
	
	
	//	e.*  //  e.EMAIL, e.NOM, e.PRENOM, e.TEL, e.USERNAME
	@Query(value="select e.EMAIL,e.NOM,e.PRENOM,e.TEL,e.USERNAME from TETUDIANT e inner join TINSCRIPTION i on i.FKETUDIANT=e.USERNAME where i.FKMODULE=?", nativeQuery=true)
	Set<Etudiant> getEtudiantsOfModule(String code);

	
	
}
