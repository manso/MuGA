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

import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.List;
import java.util.Random;

/**
 * Created on 2/out/2015, 8:38:28
 *
 * @author zulu - computer
 */
public class M_Crossover extends NPointCrossover {

    @Override
    public String getParameters() {
        return pCrossover + "";
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
            this.pCrossover = Double.parseDouble(aParams[0]);
        } catch (Exception e) {
        }        
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public  SimplePopulation execute(SimplePopulation parents) {
        //clean population 
        SimplePopulation newPopulation = parents.getCleanClone();
        Solution indiv1;
        Solution indiv2;
         // get list of individuals ( [copies] [genome] )
        List<Solution> pop = parents.getList(); // raw list of population (parents.pop)
        int index = 0;
        while (index < pop.size()) { //iterate all the individuals
            //first parent
            indiv1 = pop.get(index++);
            if (index == pop.size() || random.nextDouble() >= pCrossover) { //insert clone
                newPopulation.addIndividual(indiv1);
                continue;
            }// END: if
            //second parent
            indiv2 = pop.get(index++);
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //-------------------- multiset recombination ----------------------
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //number of recombinations
            int numberOfRecombinations = Math.min(indiv1.getNumberOfCopies(), indiv2.getNumberOfCopies());
            //insert clones of the individual with greather number of copies
            if (indiv1.getNumberOfCopies() > indiv2.getNumberOfCopies()) {
                Solution clone = indiv1.getClone();
                clone.setNumberOfCopies(indiv1.getNumberOfCopies() - numberOfRecombinations);
                newPopulation.addIndividual(clone); // add clone
            } else if (indiv2.getNumberOfCopies() > indiv1.getNumberOfCopies()) {
                Solution clone = indiv2.getClone();
                clone.setNumberOfCopies(indiv2.getNumberOfCopies() - numberOfRecombinations);
                newPopulation.addIndividual(clone); // add clone
            }
            //recombine multiset individuals
            for (int nCuts = 1; nCuts <= numberOfRecombinations; nCuts++) {
                // clone multiset individuals to simple individuals
                Solution clone1 = indiv1.getClone();
                clone1.setNumberOfCopies(1);
                Solution clone2 = indiv2.getClone();
                clone2.setNumberOfCopies(1);
                //execute crossover
                doCrossover((BinaryString) clone1, (BinaryString) clone2, nCuts);
                //add individuals
                newPopulation.addIndividual(clone1);
                newPopulation.addIndividual(clone2);
            }

        }// END :  list
        return newPopulation;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020838L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        Random rnd = new Random(1223);
        SimplePopulation mp = new MultiPopulation();
        mp.setParameters("500");
        mp.setRandomGenerator(rnd);

        Solution i = new OneMax();
        i.setRandomGenerator(rnd);
        i.setParameters("1000");
        mp.createRandom(i);

        for (int j = 0; j < mp.getNumberOfIndividuals(); j++) {
            Solution i1 = mp.removeRandom();
            Solution i2 = mp.removeRandom();
            if (i1.compareTo(i2) > 0) {
                mp.addIndividual(i1);
                mp.addIndividual(i1);
            } else {
                mp.addIndividual(i2);
                mp.addIndividual(i2);
            }

        }
        M_Crossover x = new M_Crossover();
        x.setRandomGenerator(rnd);

        for (int j = 0; j < 2; j++) {
            System.out.println(" i " + j);
            mp = x.execute(mp);
            mp.evaluate();

        }

        System.out.println("" + mp);

    }

}
