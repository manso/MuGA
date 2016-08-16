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
package com.evolutionary.operator.replacement;

import com.evolutionary.Genetic;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;

/**
 * Created on 1/out/2015, 12:40:50
 *
 * @author zulu - computer
 */
public class TournamentReplace extends Replacement {

    @Override
    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {
        SimplePopulation newPop = parents.getCleanClone();
        int SIZE = parents.getSize();
        while (newPop.getSize() < SIZE && !offspring.isEmpty()) {
            Solution parent = parents.getRandom();
            Solution child = offspring.getRandom();
            if (parent.compareTo(child) > 0) {
                parents.removeIndividual(parent);
                newPop.addIndividual(parent);
            } else {
                offspring.removeIndividual(child);
                newPop.addIndividual(child);
            }
        }
        //complete population if needed
        while (newPop.getSize() < SIZE && !parents.isEmpty()) {
            newPop.addIndividual(parents.removeRandom());
        }

        return newPop;
    }
    
     @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + "( )\n");

        txt.append("\n\nTruncationt selection:");
        txt.append("\nSelect the best parents");      
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510011240L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
