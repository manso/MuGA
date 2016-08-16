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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.utils;

import java.util.ArrayList;

/**
 * Created on 5/out/2015, 16:00:15
 *
 * @author zulu - computer
 */
public class Statistics {

    public static double mean(ArrayList<Integer> array) {
        double sum = 0;
        for (Integer value : array) {
            sum += value;
        }
        return sum / array.size();
    }

    public static double std(ArrayList<Integer> array) {
        double sum = 0;
        double m = mean(array);
        for (Integer value : array) {
            sum += Math.pow(value - m, 2);
        }
        return Math.sqrt(1.0 / (array.size() - 1) * sum);
    }

    public static double meanSuccessefull(ArrayList<Integer> evals, ArrayList<Integer> sucess) {
        //compute succefull runs
        ArrayList<Integer> successFull = new ArrayList<>();
        for (int i = 0; i < sucess.size(); i++) {
            if (sucess.get(i) == 1) {
                successFull.add(evals.get(i));
            }
        }
        if (successFull.isEmpty()) // zero sucess
        {
            return 0;
        }
        return mean(successFull);
    }

    public static double stdSuccessefull(ArrayList<Integer> evals, ArrayList<Integer> sucess) {
        //compute succefull runs
        ArrayList<Integer> successFull = new ArrayList<>();
        for (int i = 0; i < sucess.size(); i++) {
            if (sucess.get(i) == 1) {
                successFull.add(evals.get(i));
            }
        }
        if (successFull.isEmpty()) // zero sucess
        {
            return 0;
        }

        return std(successFull);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510051600L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
