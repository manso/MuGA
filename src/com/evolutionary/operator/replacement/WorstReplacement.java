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
import com.evolutionary.operator.replacement.Replacement;
import com.evolutionary.operator.replacement.RestrictedReplacement;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.population.UniquePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.List;

/**
 * Created on 5/abr/2016, 11:01:34
 *
 * @author zulu - computer
 */
public class WorstReplacement extends Replacement {

    @Override
    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {
        //replace all
        if (offspring.getSize() == parents.maximumSize) {
            return offspring;
        }
        
        SimplePopulation selected = parents.getCleanClone();
        offspring.sort();
        //insert offspring
        int index = 0;
        while (selected.getSize() < selected.maximumSize && index < offspring.getSize()) {
            selected.addIndividual(offspring.getIndividual(index++));
        }
        //complete with parents
        parents.sort();
        index = 0;
        while (selected.getSize() < selected.maximumSize) {
            selected.addIndividual(parents.getIndividual(index++));
        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (window size)\n");
        txt.append("\nWorst replace");
        txt.append("\nReplace the worst parents with offprings");
        txt.append("\nif offspring.size == parents.size -> full replace");  
        txt.append("\nif offspring.size > parents.size -> select best offsprings");  
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604050955L;
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
        pop.sort();
        System.out.println("POP \n1 " + pop);

        SimplePopulation pop2 = pop.getCleanClone();
        pop2.createRandom(ind);
        pop2.evaluate();

        for (int i = 0; i < 10; i++) {
            pop2.addIndividual(pop2.getRandom());
        }
        pop2.sort();
        System.out.println("POP2 \n1 " + pop2);
        Replacement t = new RestrictedReplacement();
        pop = t.execute(pop, pop2);
        pop.sort();
        System.out.println("REPLACE \n" + pop);
    }
    // }

}
