//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////

package com.evolutionary.solverUtils.HBOA.metric;

import java.util.ArrayList;

/**
 * Created on 20/abr/2016, 19:06:40 
 * @author zulu - computer
 */
public class BDMetric  extends IBayesianMetric{
	// Precomputed logarithm list -> 0, ln(1), ln(2),..., ln(NS+1).
	// Use this list to compute the score gain for each leaf.
	private static ArrayList<Double> preSumLogs = new ArrayList<Double>();		// Precomputed logarithm list -> 0, ln(1), ln(2),..., ln(NS+1).
																				// Use this list to compute the score gain for each leaf.
	
	public BDMetric(int NS){
		this.NS = NS;
		setPreSumLogs();
	}
	
	public void setPreSumLogs(){												// Precompute the logarithm list -> 0, ln(1), ln(1)+ln(2),..., ln(1)+ln(2)+...+ln(NS+1).
		int preSize = preSumLogs.size();		
		double preSumLog;
		if(preSize == 0)														// If this is the first time computing SumLogs, add a first element, just to align the position with the last logarithm.
			preSumLogs.add(preSize++, (double)0);	
		preSumLog = preSumLogs.get(preSize-1);									// Get the last computed SumLog.
		for(int i = preSize; i <= this.NS+1; i++){								// Compute only the new SumLogs.
			preSumLog += Math.log(i);
			preSumLogs.add(preSumLog);
		}
	}
	
	protected double computeLeafGain(int m0, int m1){
		return preSumLogs.get(m0) + preSumLogs.get(m1) - preSumLogs.get(m0+m1+1);
	}
	
	public String toString(){
		return "BAYESIAN-DIRICHELET METRIC";
	}

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604201906L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}