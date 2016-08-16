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
package com.evolutionary.operator.recombination;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;

/**
 * Created on 2/out/2015, 8:38:28
 *
 * @author zulu - computer
 */
public class Uniform extends Recombination {

    private double pSwap = 0.5;

    @Override
    public String getParameters() {
        return pCrossover + " " + pSwap;
    }

    //----------------------------------------------------------------------------------------------------------
    /**
     * update parameters od the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    public void setParameters(String params) throws RuntimeException { //update parameters by string values
        //split string by withe chars
        String[] aParams = params.split("\\s+");
        try {
            pCrossover = Double.parseDouble(aParams[0]);
        } catch (Exception e) {
        }
        try {
            pSwap = Double.parseDouble(aParams[1]);
        } catch (Exception e) {
        }        
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public SimplePopulation execute(SimplePopulation offspring) {
        //clean population  
        SimplePopulation newIndividuals = offspring.getCleanClone();
        while (!offspring.isEmpty()) {
            //first parent
            Solution indiv1 = offspring.removeRandom();
            if (offspring.isEmpty() || random.nextDouble() >= pCrossover) {
                newIndividuals.addIndividual(indiv1);
                continue;
            }// END: if
            //second parent
            Solution indiv2 = offspring.removeRandom();
            //execute crossover
            doCrossover((BinaryString) indiv1, (BinaryString) indiv2,pSwap);
            newIndividuals.addIndividual(indiv1);
            newIndividuals.addIndividual(indiv2);
        }// END: population
        return newIndividuals;
    }

     protected void doCrossover(BinaryString i1, BinaryString i2, double pSwap ) {
        boolean[] g1 = i1.getBitsGenome();
        boolean[] g2 = i2.getBitsGenome();
        for (int i = 0; i < g1.length; i++) {
            if ((g1[i] != g2[i]) && random.nextDouble() < pSwap) { //different bits 
                g1[i] = !g1[i]; //inverse bit
                g2[i] = !g2[i]; //inversebit
            }
        }
        i1.setNotEvaluated();
        i2.setNotEvaluated();
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(getName());
        txt.append("\n\nParameters: <P_CROSSOVER><P_SWAP>");
        txt.append("\n    <P_CROSSOVER>  - probability of crossover");
        txt.append("\n    <P_SWAP>       - probability of bit swap");

        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020838L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
   
   
}
