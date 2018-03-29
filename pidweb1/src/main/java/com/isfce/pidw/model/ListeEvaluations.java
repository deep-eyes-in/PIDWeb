package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.List;

import com.isfce.pidw.filter.EvaluationKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class ListeEvaluations {


	
	private List<Evaluation> evaluations = new ArrayList<>();
	

	
	public ListeEvaluations( List<Evaluation> evaluations   ) {		/* List<EvaluationKey> evaluations */ 
		super();
		this.evaluations = evaluations ;

	}
	
	
	public List<Evaluation>  findAll( ) {
		return evaluations ;
	}
	
	public boolean  add( Evaluation e  ) {
		return evaluations.add(e) ;
	}
	
	public boolean  add( Long id, EvaluationKey keyTemp, Integer resultat  ) {
		Evaluation e = new Evaluation (  keyTemp,  resultat  ) ;
		e.setId(id);
		return evaluations.add(e) ;
	}
	

	
	public int  size(    ) {
		return evaluations.size() ;
	}

	
}






