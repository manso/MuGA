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

package com.evolutionary.report.compareMeans;

import com.evolutionary.report.ReportSolver;
import com.evolutionary.report.ReportSolverArray;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.solver.EAsolver;
import com.utils.MyStatistics;
import com.utils.MyString;
import com.utils.Ranking;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created on 13/abr/2016, 22:01:34 
 * @author zulu - computer
 */
public class Tstudent {
    
      public static String getReportComapareMeans(ArrayList<EAsolver> solvers, AbstractStatistics stat) {
        double[][] result = new double[solvers.size()][];
        for (int j = 0; j < solvers.size(); j++) {
            result[j] = ((ReportSolverArray) solvers.get(j).report).getStatisticResult(stat);
        }
        char[][] compare95 = MyStatistics.compareMeansTtest(result, 0.05, stat.higherIsBetter);
        int[] score95 = MyStatistics.sumMeanComparations(compare95);
        int[] rank95 = Ranking.rank(score95);

        char[][] compare99 = MyStatistics.compareMeansTtest(result, 0.01, stat.higherIsBetter);
        int[] score99 = MyStatistics.sumMeanComparations(compare99);
        int[] rank99 = Ranking.rank(score99);
        double[][] p_value = MyStatistics.p_values(result);

        //:::::::::::: HEADER :::::::::::::::::
        StringBuffer txt = new StringBuffer();
        txt.append(MyString.center("Confidence 95%", ReportSolver.FIELD_SIZE));
        for (int i = 0; i < solvers.size(); i++) {
            txt.append(" " + MyString.center(i + "", 4));
        }
        txt.append(" " + MyString.center("Score", 5));
        txt.append(" " + MyString.center("Rank", 5));

        txt.append(" " + MyString.center("Confidence 99%", ReportSolver.FIELD_SIZE));
        for (int i = 0; i < solvers.size(); i++) {
            txt.append(" " + MyString.center(i + "", 4));
        }
        txt.append(" " + MyString.center("Score", 5));
        txt.append(" " + MyString.center("Rank", 5));

        txt.append(" " + MyString.center("p-Values", ReportSolver.FIELD_SIZE));
        for (int i = 0; i < solvers.size(); i++) {
            txt.append(" " + MyString.center(i + "", 6));
        }
        txt.append("\n");
        //::::::::::::::::::::::::: DATA ::::::::::::::::::::::::::::::::::::::
        for (int i = 0; i < solvers.size(); i++) {
            txt.append(MyString.align(solvers.get(i).solverName, ReportSolver.FIELD_SIZE));
            for (int j = 0; j < compare95.length; j++) {
                txt.append(" " + MyString.center(compare95[i][j] + "", 4));
            }
            txt.append(" " + MyString.center(score95[i] + "", 5));
            txt.append(" " + MyString.center(rank95[i] + "", 5));
            txt.append(" " + MyString.align(solvers.get(i).solverName, ReportSolver.FIELD_SIZE));
            for (int j = 0; j < compare99.length; j++) {
                txt.append(" " + MyString.center(compare99[i][j] + "", 4));
            }
            txt.append(" " + MyString.center(score99[i] + "", 5));
            txt.append(" " + MyString.center(rank99[i] + "", 5));
            txt.append(" " + MyString.align(solvers.get(i).solverName, ReportSolver.FIELD_SIZE));
            for (int j = 0; j < p_value.length; j++) {
                txt.append(" " + MyString.align(String.format(Locale.US, "%4.3f", p_value[i][j]), 6));

            }
            txt.append("\n");
        }       
        txt.append("\n");
        return txt.toString();
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604132201L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}