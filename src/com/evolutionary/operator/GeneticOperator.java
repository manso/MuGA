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
package com.evolutionary.operator;

import com.evolutionary.Genetic;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.solver.EAsolver;
import java.io.Serializable;

/**
 * Created on 1/out/2015, 10:56:43
 *
 * @author zulu - computer
 */
public abstract class GeneticOperator extends Genetic implements Serializable {

    public EAsolver solver;

    public abstract SimplePopulation execute(SimplePopulation... pop); //variable number of parameters population

    public void setSolver(EAsolver s) {
        this.solver = s;
        this.random = s.random;
    }
    
    public GeneticOperator getClone(){
        return (GeneticOperator) Genetic.getClone(this);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510011056L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
