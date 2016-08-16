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
import com.evolutionary.problem.bits.OneMax;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created on 2/out/2015, 8:38:28
 *
 * @author zulu - computer
 */
public class NPointCrossover extends Recombination {

    private int numberOfCuts = 2;// Number of cross points.1

    @Override
    public String getParameters() {
        return pCrossover + " " + numberOfCuts;
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
            numberOfCuts = Integer.parseInt(aParams[1]);
        } catch (Exception e) {
        }
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public SimplePopulation execute(SimplePopulation offspring) {
        //clean population  
        SimplePopulation newPopulation = offspring.getCleanClone();
        //iterate offspring population
        while (!offspring.isEmpty()) {
            //first parent
            Solution indiv1 = offspring.removeRandom();
            if (offspring.isEmpty() || random.nextDouble() >= pCrossover) {
                newPopulation.addIndividual(indiv1);
                continue;
            }// END: if
            //second parent
            Solution indiv2 = offspring.removeRandom();
            //execute crossover
            doCrossover((BinaryString) indiv1, (BinaryString) indiv2, numberOfCuts);
            //add individuals
            newPopulation.addIndividual(indiv1);
            newPopulation.addIndividual(indiv2);
        }// END: population
        return newPopulation;
    }

    protected void doCrossover(BinaryString i1, BinaryString i2, int cuts) {
        boolean[] g1 = i1.getBitsGenome();
        boolean[] g2 = i2.getBitsGenome();
        //create mask with cutpoints
        boolean[] mask = createMask(i1.getSize(), cuts);
        for (int i = 0; i < mask.length; i++) {
            if ((g1[i] != g2[i]) && mask[i]) { //different bits and mask=1
                g1[i] = !g1[i]; //inverse bit
                g2[i] = !g2[i]; //inversebit
            }
        }
        i1.setNotEvaluated();
        i2.setNotEvaluated();
    }

    /**
     * creates a mask to exchange genes
     *
     * @param size
     * @return
     */
    protected boolean[] createMask(int size, int cuts) {
        TreeSet<Integer> crossPoints = new TreeSet<Integer>(); // cut point positions
        int tryCut = size * 2; //number of tryes
        do {
            crossPoints.add(random.nextInt(size - 1) + 1);
            tryCut--;
        } while (crossPoints.size() < cuts && tryCut > 0);// Choose exactly nCrossover cross points.
        if (crossPoints.size() % 2 != 0) // If the number of cross points is odd then Problem.n is the "last" cross point.
        {
            crossPoints.add(size);
        }
        boolean[] mask = new boolean[size];
        while (!crossPoints.isEmpty()) {									// Perform the swapping for all positions between Kodd - Keven.
            int k1 = crossPoints.pollFirst();
            int k2 = crossPoints.pollFirst();
            for (int k = k1; k < k2; k++) {
                mask[k] = true;
            }
        }
        return mask;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020838L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        NPointCrossover x = new NPointCrossover();
        x.setParameters("0.5 15");
        x.setRandomGenerator(new Random());
        OneMax i1 = new OneMax();
        i1.setBits("0000");
        OneMax i2 = new OneMax();
        i2.setBits("1111");
        i1.evaluate();
        i2.evaluate();

        System.out.println(i1);
        System.out.println(i2);
        System.out.println("---------------------");
        x.doCrossover(i1, i2, 2);
        System.out.println(i1);
        System.out.println(i2);

    }

}
