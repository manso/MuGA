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
package com.evolutionary.operator.replacement;

import com.evolutionary.Genetic;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.List;

/**
 * Created on 5/abr/2016, 9:39:46
 *
 * @author zulu - computer
 */
public class Decimation extends Replacement {

    protected int tourSize = 3;

    @Override
    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {
        //join populations
        parents.addPopulation(offspring);
        Solution best = parents.getBest();

        //select NumChilds Individuals        
        while (parents.getSize() > parents.maximumSize) {
            //-------------------------------------------------------------
            //------------ get parents ------------------------------------
            //-------------------------------------------------------------
            int indexWorst = random.nextInt(parents.getSize());
            Solution worst = parents.getIndividual(indexWorst);
            int index2 = 0;
            for (int i = 1; i < tourSize; i++) {
                //peek one individual
                index2 = random.nextInt(parents.getSize());
                Solution probe = parents.getIndividual(index2);
                int compare = worst.compareTo(probe);
                if( compare > 0){
                     indexWorst = index2;
                     worst = probe;
                }
//                //update worst
//                if (parents.getIndividual(indexWorst).compareTo(parents.getIndividual(index2)) >= 0) {
//                    indexWorst = index2;
//                }
            }
            //remove worst
            parents.removeIndividual(indexWorst);
        }
        Solution best2 = parents.getBest();
        //System.out.println(best + " => " + best2);
//        assert (best.compareTo(best2) == 0);
//        assert (parents.getSize() == parents.maximumSize);
        return parents;
    }

    //----------------------------------------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return "" + tourSize;
    }

    /**
     * update parameters of the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    @Override
    public void setParameters(String params) throws RuntimeException {
        //split string by withe chars
        String[] aParams = params.split("\\s+");
        try {
            tourSize = Integer.parseInt(aParams[1]);
        } catch (Exception e) {
        }

    }//----------------------------------------------------------------------------------------------------------

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (tournament size)\n");
        txt.append("\nDecimation Replacement ");
        txt.append("\nJoin parents and children");
        txt.append("\n\nMake tornaments with size T");
        txt.append("\n\nremove the worst individual");
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604050939L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("10");
        Solution ind = new OneMax();
        ind.setParameters("100");

//        for (int k = 0; k < 100; k++) {
        pop.createRandom(ind);
        pop.evaluate();
        for (int i = 0; i < 10; i++) {
            pop.addIndividual(pop.getRandom());
        }
        System.out.println("POP \n1 " + pop);

        SimplePopulation pop2 = pop.getCleanClone();
        pop2.createRandom(ind);
        pop2.evaluate();
        for (int i = 0; i < 10; i++) {
            pop2.addIndividual(pop2.getRandom());
        }
        System.out.println("POP2 \n1 " + pop2);
        Replacement t = new Decimation();
        pop = t.execute(pop, pop2);
        System.out.println("REPLACE \n" + pop);
    }
    // }

}
