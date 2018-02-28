package com.isfce.pidw.model;

import java.util.HashMap;
import java.util.Map;

import com.isfce.pidw.filter.EvaluationKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class ListeEvaluations {




	//		evaluation.put( evaluations ,  resultat ) 
//	private List<EvaluationKey> evaluations = new ArrayList<>();
	
//	private String resultat ;
	
	private Map<EvaluationKey, Integer> infos = new HashMap<EvaluationKey, Integer>();
	
		
	
	

	public ListeEvaluations( Map<EvaluationKey, Integer> infos /* List<EvaluationKey> evaluations */ /* , String resultat */ ) {
		super();
		this.infos = infos ;
//		this.evaluations = evaluations;
//		this.resultat = resultat;
	}
	

}






