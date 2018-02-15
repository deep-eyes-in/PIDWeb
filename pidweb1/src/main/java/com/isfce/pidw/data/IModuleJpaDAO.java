package com.isfce.pidw.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Module;

@Repository
public interface IModuleJpaDAO extends JpaRepository<Module, String> {
	@Query(value="select * from TMODULE where FKCours=?", nativeQuery=true)
	List<Module> getModulesCours(String codeCours);
	
	@Query(value="select code, nom from TCOURS order by nom ASC", nativeQuery=true)
	List<Object[]> getCoursCodeNomList();
	
	@Query(value="select code from TCOURS", nativeQuery=true)
	List<String> getCoursCodeList();
	
	@Query(value="select nom from TCOURS", nativeQuery=true)
	List<String> getCoursNomList();

	
	@Query(value="select code from TMODULE", nativeQuery=true)
	List<String> getModuleCodeList();
	
	
	@Query("SELECT m FROM TMODULE m WHERE m.moment= ?2 and (?1 MEMBER OF m.cours.sections)")
	List<Module> getModulesAPMFromSection(String section, Module.MAS mas);
	

	
	@Query("select m from TMODULE m  where m.prof.username=?1")
	List<Module> readByProfesseurIsNotNull(String username);
	
	@Query(value="select section from TSECTION where FKCours=?", nativeQuery=true)
	Set<String> getCoursSection(String codeCours);
	
	@Query(value="select * from TCOURS where code=?", nativeQuery=true)
	Cours getCoursByCode(String code);
	
	
	//@PreAuthorize("#username == principal.username")
	@Query(value="select m.* from TMODULE m inner join TINSCRIPTION i on i.FKMODULE=m.CODE where i.FKETUDIANT=?", nativeQuery=true)
	List<Module> getModulesOfEtudiant(String username);
	
	
	@Query(value="select * from TINSCRIPTION where FKMODULE=?", nativeQuery=true)
	List<Etudiant> getEtudiantsOfModule(String code);
	
	
	@Query(value="select FKETUDIANT from TINSCRIPTION where FKMODULE=?", nativeQuery=true)
	List<String> getFkEtudiantsOfModule(String code);	
	
	@Query(value="select * from TINSCRIPTION ORDER BY FKMODULE", nativeQuery=true)
	List<Object[]> getAllInscriptions( );


	
	
	
	
	
}


















