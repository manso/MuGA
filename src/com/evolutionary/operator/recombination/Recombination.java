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

import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.SimplePopulation;

/**
 * Created on 1/out/2015, 11:47:02
 *
 * @author zulu - computer
 */
public abstract class Recombination extends GeneticOperator {

    public abstract SimplePopulation execute(SimplePopulation offspring);

    protected static double default_pCrossover = 0.5; // default probability of crossover
    protected double pCrossover;

    public Recombination() {
        this(default_pCrossover);
    }

    public Recombination(double probCrossover) {
        this.pCrossover = probCrossover;        
    }
    
    

    @Override
    public SimplePopulation execute(SimplePopulation... pop) {
        return execute(pop[0]);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510011147L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
