package com.isfce.pidw.data;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Cours;


@Repository
public interface ICoursJpaDAO extends JpaRepository<Cours, String> {
	
	List<Cours> readBySectionsIgnoringCase(String section);
	
	
	@Query(value="select section from TSECTION where FKCOURS=?", nativeQuery=true)
	List<String> coursSection(String codeCours);
	
	@Query(value="select section from TSECTION where FKCOURS=?", nativeQuery=true)
	Set<String> coursSection2(String codeCours);	
	
	@Query(value="select distinct upper(section) from TSECTION ", nativeQuery=true)
	List<String> listeSections();
	
	
	@Query(value= "SELECT * FROM TCOURS " , nativeQuery=true) 
	List< Cours >  getCours( );
	
	
	

}
