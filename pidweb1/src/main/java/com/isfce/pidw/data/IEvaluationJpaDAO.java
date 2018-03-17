package com.isfce.pidw.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Evaluation;


@Repository
public interface IEvaluationJpaDAO extends JpaRepository<Evaluation, String> {
	
	@Query(value="select distinct SESSION from TEVALUATION where FKMODULE = ?", nativeQuery=true)
	List<Evaluation.SESSION> getSessionsOfModule(String module);

	
	@Query(value=" select coalesce(MAX( ID ), 0) from TEVALUATION ", nativeQuery=true) 
	Long generateId();

	@Query(value="select *  from TEVALUATION where FKMODULE = ? and SESSION = ?", nativeQuery=true)
	List<Evaluation> getEvaluationsOfModule(String code, Integer session);
	
	@Query(value="select *  from TEVALUATION where FKMODULE = ? and SESSION = ? and FKETUDIANT = ?", nativeQuery=true)
	Evaluation getEvaluationOfModuleOfEtudiant(String code, Integer session, String etudiant);


	@Query(value="select CASE WHEN COUNT(ID)  > 0  THEN 'True' ELSE 'False' END   from TEVALUATION where ID = ?", nativeQuery=true)
	boolean exists( Long id );

	@Query(value="select *  from TEVALUATION where ID = ?", nativeQuery=true)
	Evaluation findOne(Long id);
	

}
