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
package com.evolutionary.solver;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.solver.EAsolver;

/**
 * Created on 18/abr/2016, 17:23:20
 *
 * @author zulu - computer
 */
public class HBOA extends EAsolver {

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604181723L;

    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void startEvolution(boolean verbose) {
        // init random generator
        if (randomSeed == 0) {
            random.setSeed(random.nextLong());
        } else {
            random.setSeed(randomSeed);
        }
        //update atribute solver of operators to this
        updateSolverAtributes();
        //create random population
        parents = new SimplePopulation();
        parents.createRandom(problem);
        parents.evaluate();
        //start statistics
        report.startStats(this, verbose);
        updateEvolutionStats();
    }

    @Override
    public void iterate() {
        SimplePopulation offspring = selection.execute(parents);
        offspring = recombination.execute(offspring);
        offspring = mutation.execute(offspring);
        offspring.evaluate();
        parents = replacement.execute(parents, offspring);
        parents = rescaling.execute(parents);
        updateEvolutionStats();

    }

    public String getInformation() {
        return "HBOA";
    }
}
