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

/**
 * Created on 20/abr/2016, 19:07:35 
 * @author zulu - computer
 */
public class BICMetric extends IBayesianMetric{
	
	// DEPRECATED: public BICMetric(int NS){this.NS = NS;
	
	protected double computeLeafGain(int m, int mX){
		if(m == mX || mX == 0) return 0; 										// NOTE: The case m = 0 is always included because 0 � mX � m, and so m = 0 => mX = 0. The reverse is not necessarily true.
		double freq = ((double)mX)/((double)m); 								// NOTE: The return value 0 is limit-based! Is this correct, even for m = mX = 0 ?
		return (double)((m-mX)*Math.log(1-freq) + mX*Math.log(freq));
	}
	
	public String toString(){
		return "BAYESIAN INFORMATION CRITERION";
	}

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604201907L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}