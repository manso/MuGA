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
 * Created on 20/abr/2016, 19:05:23
 *
 * @author zulu - computer
 */
public abstract class IBayesianMetric {

    protected int NS;
    private static double logBase2 = ((double) 1) / Math.log(2);

    public double computeScoreGain(int mZero, int mOne, int m00, int m01, int m10, int m11) {
        double l0 = computeLeafGain(m00, m10);
        double l1 = computeLeafGain(m01, m11);
        double l = computeLeafGain(mZero, mOne);
        return logBase2 * (l0 + l1 - l - 0.5 * Math.log(NS));
    }

    abstract protected double computeLeafGain(int m, int mX);

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604201905L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
