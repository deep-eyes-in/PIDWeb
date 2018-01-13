package com.isfce.pidw.data;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Module;

@Repository
public interface IModuleJpaDAO extends JpaRepository<Module, String> {
	
	
	@Query(value="select * from TMODULE where FKCours=?", nativeQuery=true)
	List<Module> getModulesCours(String codeCours);
	
	@Query("SELECT m FROM TMODULE m WHERE m.moment= ?2 and (?1 MEMBER OF m.cours.sections)")
	List<Module> getModulesAPMFromSection(String section, Module.MAS mas);
	
	
	
	
	
//	CODE, LANGUE, NBPERIODES, NOM
//	CODE, NOM, NBPERIODES
	@Query(value= "SELECT * FROM TCOURS " , nativeQuery=true) 
	List< Object[] >  getCours( );		//	List<Cours> getCours( );	Object[]
	
	
	
	
	
	
	@Query(value= "SELECT code FROM TCOURS " , nativeQuery=true) 
	List<String> getCoursCodeList( );	
	
	@Query(value="select nom from TCOURS where code=?", nativeQuery=true)
	String getCoursName(String codeCours);	
	
	
	
	
	@Query(value="select section from TSECTION where FKCOURS=?", nativeQuery=true)
	Set<String> coursSection2(String codeCours);		
	
	
}














