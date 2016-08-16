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
package com.evolutionary.operator.mutation;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;

/**
 * Created on 2/out/2015, 7:50:41
 *
 * @author zulu - computer
 */
public class FlipBit extends Mutation {

   
    @Override
    public SimplePopulation execute(SimplePopulation offspring) {

        double mutProbability = pMutation == -1 ? 1.0 / offspring.getIndividualsSize() : pMutation;
        if (mutProbability > 0) // Perform mutation only if pMutation > 0.
        {
           //for all the offsping individuals
            for (int i = 0; i < offspring.getSize(); i++) {
                //get individual
                BinaryString mut = (BinaryString) offspring.getIndividual(i);
                //get bits
                boolean[] bits = ((BinaryString) offspring.getIndividual(i)).getBitsGenome();
                for (int j = 0; j < bits.length; j++) {
                    //get chance
                    if (random.nextDouble() < mutProbability) {
                        //change bit
                        bits[j] = !bits[j];
                        //remove evaluation
                        mut.setNotEvaluated();
                    }
                }
            }
        }
        return offspring;
    }
    
    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(getName());
        txt.append("\n\nParameters: <P_MUT>");
        txt.append("\n    <P_MUT>  - probability to mutate one bit");
        txt.append("\n      -1 = 1.0 / genome.lenght");

        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020750L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
