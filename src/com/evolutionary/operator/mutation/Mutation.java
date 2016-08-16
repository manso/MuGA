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

import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.SimplePopulation;

/**
 * Created on 1/out/2015, 11:47:02
 *
 * @author zulu - computer
 */
public abstract class Mutation extends GeneticOperator {

    public abstract SimplePopulation execute(SimplePopulation offspring);

    protected double pMutation = -1; // default probability of mutation

    /**
     * gets the probability of mutation to number of bits
     *
     * @param lenght number of bits
     * @return
     */
    public double getProbability(int lenght) {
        if (pMutation == -1) {
            return 1.0 / lenght;
        }
        return pMutation;
    }

    

    @Override
    public String getParameters() {
        return pMutation + "";
    }

    //----------------------------------------------------------------------------------------------------------
    /**
     * update parameters od the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    @Override
    public void setParameters(String params) throws RuntimeException { //update parameters by string values
        super.setParameters(params);
        try {
            //split string by withe chars
            String[] aParams = params.split("\\s+");
            if (aParams.length > 0) {
                pMutation = Double.parseDouble(aParams[0]);
            }
            //validate crossover probability
            if (pMutation != -1 && (pMutation < 0 || pMutation > 1)) {
                throw new RuntimeException("invalid probability of Mutation : " + pMutation);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public SimplePopulation execute(SimplePopulation... pop) {
        return execute(pop[0]);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510011147L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
