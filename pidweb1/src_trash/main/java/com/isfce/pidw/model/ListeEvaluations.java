package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.List;

import com.isfce.pidw.filter.EvaluationKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class ListeEvaluations {




	//		evaluation.put( evaluations ,  resultat ) 
//	private List<EvaluationKey> evaluations = new ArrayList<>();
	
//	private String resultat ;
	
//	private Map<EvaluationKey, Integer> infos = new HashMap<EvaluationKey, Integer>();
	
	
	private List<Evaluation> evaluations = new ArrayList<>();
	
	
	
/*
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	Integer resultat ;
	
	List<Integer> listResultat ;
*/

/*
	public ListeEvaluations( Map<EvaluationKey, Integer> infos   ) {		 List<EvaluationKey> evaluations  
		super();
		this.infos = infos ;
	}
*/
	
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
	
	
	
	
	
	
/*	
	public void setResultat(Integer i) {
		listResultat.add(i);
	}
	
	public Integer getResultat( ) {
		return 7 ;
	}
	
*/
	
	
	
/*
	public void addInfos(String section) {
		sections.add(section);
	}
*/
	
	
}






