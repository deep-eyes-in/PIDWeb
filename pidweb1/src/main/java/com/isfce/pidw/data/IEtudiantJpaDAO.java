package com.isfce.pidw.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Etudiant;

@Repository
public interface IEtudiantJpaDAO extends JpaRepository<Etudiant, Long> {
	
	
	
	
	@Query(value="select distinct upper(section) from TSECTION ", nativeQuery=true)
	List<String> listeSections();	

	@Query(value=" select  coalesce(MAX( ID ), 0)  from TETUDIANT ", nativeQuery=true)
	Long  generateId();	
	
	

}
