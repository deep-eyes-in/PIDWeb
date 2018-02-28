package com.isfce.pidw.filter;

import java.io.Serializable;

import com.isfce.pidw.model.Competence;
import com.isfce.pidw.model.Evaluation;

import lombok.Data;




@Data
public class CompetenceValidKey implements Serializable {
	   private Competence competence;
	   private Evaluation evaluation;
	}