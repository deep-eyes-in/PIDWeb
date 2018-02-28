package com.isfce.pidw.filter;

import java.io.Serializable;

import com.isfce.pidw.model.Etudiant;
import com.isfce.pidw.model.Evaluation;
import com.isfce.pidw.model.Module;
import com.isfce.pidw.model.Evaluation.SESSION;

import lombok.Data;




@Data
public class EvaluationKey implements Serializable {
	   private Module module ;
	   private Etudiant etudiant;
	   private Evaluation.SESSION session;
	}


