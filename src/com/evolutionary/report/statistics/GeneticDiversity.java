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
package com.evolutionary.report.statistics;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import com.evolutionary.solver.EAsolver;

/**
 * A. Manso, L. Correia Genetic Algorithms using Populations based on Multisets
 * EPIA'2009 - Fourteenth Portuguese Conference on Artificial Intelligence
 * Aveiro , Portugal, Outubro 2009
 *
 * Created on 15/mar/2016, 15:47:45
 *
 * @author zulu - computer
 */
public class GeneticDiversity extends AbstractStatistics {

    public GeneticDiversity() {
        super("Diversity");
        higherIsBetter=true;
    }

    /**
     * calculate the statistic
     *
     * @param s solver
     * @return value
     */
    public double execute(EAsolver s) {
        SimplePopulation pop = s.parents;

        return binaryDiversity(pop);
    }

    public double binaryDiversity(SimplePopulation pop) {
        //calculate histogram of population
        int hist[] = new int[pop.getIndividual(0).getSize()];
        boolean[] bits;
        for (Solution ind : pop.getList()) {
            bits = ((BinaryString) ind).getBitsGenome();
            for (int i = 0; i < bits.length; i++) {
                if (bits[i]) {
                    hist[i]++;
                }

            }
        }
        //calculate statistic
        double val = 0.0;
        //number of individuals
        double numPop = pop.getSize();
        for (int i = 0; i < hist.length; i++) {
            double ones = hist[i];
            double zeros = (numPop - hist[i]);
//            System.out.print("<" + (ones * zeros) + "> ");
            val += ones * zeros;
        }
        return (val * 4) / (hist.length * numPop * numPop);

    }

    @Override
    public String getInformation() {
        StringBuffer txt = new StringBuffer();
        txt.append("Genetic diversity of the parents population\n");
        txt.append("\nBinaryString : sum of ratios 1/0 for each allel\n");
        txt.append("\nREF: Genetic Algorithms using Populations based on Multisets\n");
        txt.append("A. Manso, L. Correia");
        return txt.toString();

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603151547L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
