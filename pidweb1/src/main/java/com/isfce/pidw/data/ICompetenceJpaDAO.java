package com.isfce.pidw.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Competence;


@Repository
public interface ICompetenceJpaDAO extends JpaRepository<Competence, String> {
	
	@Query(value="select * from TCOMPETENCE where FKCours=?", nativeQuery=true)
	List<Competence> getCompetencesOfCours(String cours);

	@Query(value="select *  from TCOMPETENCE where ID = ?", nativeQuery=true)
	Competence findOne(Long id);
	
	@Query(value="SELECT *  FROM TCOMPETENCE c , TCOMPETENCE_VALID cv  WHERE cv.FKETUDIANT = ? AND c.FKCOURS = ? AND c.ID = cv.FKCOMPETENCE", nativeQuery=true)
	List<Object[]> getCompetencesValidOfEtudiant(String etudiant, String cours );

}
