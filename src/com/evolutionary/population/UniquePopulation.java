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
package com.evolutionary.population;

import com.evolutionary.problem.Solution;

/**
 * Created on 13/mar/2016, 10:44:12
 *
 * @author zulu - computer
 */
public class UniquePopulation extends SimplePopulation {

    /**
     * add one individual to the population if the individual not exists 
     *
     * @param ind
     */
    public void addIndividual(Solution ind) {
        if (indexOf(ind) < 0) {
            super.addIndividual(ind);
        }
    }    
     /**
     * create random individuals
     *
     * @param template instance of proble
     * @param N number of individuals in the population
     */
    @Override
    public void createRandom(Solution template) {
        this.random = template.random;
        int N = maximumSize;
        Solution tmp;
        pop.clear();
        int tries = 3 * N;
        for (int i = 0; i < N && tries > 0; i++) {
            tmp = template.getClone(); // clone template
            tmp.randomize(); // randomize genome
            if (indexOf(tmp) < 0) {//unique individual
                addIndividual(tmp); // add individual
            } else { //not unique
                N++; // do it again
                tries--;
            }
        }
    }
    
   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603131044L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
