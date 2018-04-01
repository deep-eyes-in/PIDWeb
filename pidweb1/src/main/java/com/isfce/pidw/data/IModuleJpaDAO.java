package com.isfce.pidw.data;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Module;

@Repository
public interface IModuleJpaDAO extends JpaRepository<Module, String> {
	
	
	@Query(value="select m.* from TMODULE m where FKCours=?", nativeQuery=true)
	List<Module> getModulesOfCours(String codeCours);
		
	@Query(value="select code, nom from TCOURS order by nom ASC", nativeQuery=true)
	List<Object[]> getCoursCodeNomList();
	
	@Query(value="select code from TCOURS", nativeQuery=true)
	List<String> getCoursCodeList();
	
	@Query(value="select code from TMODULE", nativeQuery=true)
	List<String> getModuleCodeList();
		
	@Query("select m from TMODULE m  where m.prof.username=?1")
	List<Module> readByProfesseurIsNotNull(String username);
	
	@Query(value="select section from TSECTION where FKCours=?", nativeQuery=true)
	Set<String> getCoursSection(String codeCours);
	
	@Query(value="select m.* from TMODULE m inner join TINSCRIPTION i on i.FKMODULE=m.CODE where i.FKETUDIANT=?", nativeQuery=true)
	List<Module> getModulesOfEtudiant(String username);
		
	@Query(value="select FKETUDIANT from TINSCRIPTION where FKMODULE=?", nativeQuery=true)
	List<String> getFkEtudiantsOfModule(String code);
	
	@Query(value="	select  e.USERNAME   from TETUDIANT e \r\n" + 
			"	  inner join TEVALUATION ev on i.FKMODULE =ev.FKMODULE AND ev.FKETUDIANT=e.USERNAME \r\n" + 
			"	  inner join TINSCRIPTION i on i.FKETUDIANT=e.USERNAME\r\n" + 
			"	  inner join TUSERS u on u.USERNAME=e.USERNAME \r\n" + 
			"	where ev.RESULTAT BETWEEN 0 AND  49 AND i.FKMODULE=?", nativeQuery=true)
	List<String> get2ndSessionFkEtudiantsOfModule(String code);
	
	@Query(value="select * from TINSCRIPTION ORDER BY FKMODULE", nativeQuery=true)
	List<Object[]> getAllInscriptions( );
	
	@Query(value="select * from TINSCRIPTION where FKMODULE=? and FKETUDIANT=?", nativeQuery=true)
	List<Object[]> testModuleOfEtudiantExist(String username, String module);
		
}





















