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
package com.evolutionary.operator.rescaling;

import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.List;

/**
 * Created on 3/out/2015, 16:30:30
 *
 * @author zulu - computer
 */
public class FixedRescaling extends Rescaling {

    double maxProportion = 2.0;
    //----------------------------------------------------------------------------------------------------------

    @Override
    public SimplePopulation execute(SimplePopulation pop) {
        if (!(pop instanceof MultiPopulation)) {
            return pop;
        }
        MultiPopulation mpop = (MultiPopulation) pop;
        int maxInd = (int) (mpop.getSize() * maxProportion);
        int dif = mpop.getNumberOfIndividuals() - maxInd;
        List<Solution> array = mpop.getList();
        int index = pop.getSize() - 1;
        while (dif > 0) {
            if (array.get(index).getNumberOfCopies() > 1) {
                array.get(index).addCopies(-1);
                dif--;
            }
            index = index > 0 ? index - 1 : pop.getSize() - 1;
        }

        return pop;
    }

    //----------------------------------------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return maxProportion + "";
    }

    /**
     * update parameters of the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    public void setParameters(String params) throws RuntimeException {
        String[] aParams = params.split("\\s+");
        try {
            maxProportion = Double.parseDouble(aParams[0]);
            maxProportion = maxProportion < 1 ? 1 : maxProportion; //normalize
        } catch (Exception e) {
        }
    }//----------------------------------------------------------------------------------------------------------

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510031630L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        Solution ind = new OneMax();
        ind.setParameters("20");
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("10");
        pop.createRandom(ind);
        pop.evaluate();
        pop.sort();
        System.out.println(pop);
        for (int i = 0; i < 50; i++) {

            Solution ind1 = pop.getRandom().getClone();
            Solution ind2 = pop.getRandom().getClone();
            if (ind1.compareTo(ind2) > 0) {
                pop.addIndividual(ind1);
            } else {
                pop.addIndividual(ind2);
            }

        }
        System.out.println(pop);
        FixedRescaling a = new FixedRescaling();
        pop = a.execute(pop);
        System.out.println(pop);
    }

}
