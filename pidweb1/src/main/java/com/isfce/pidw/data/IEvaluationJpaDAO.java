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
}
