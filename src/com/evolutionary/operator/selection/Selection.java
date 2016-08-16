//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso  &   Luis Correia                    ::
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
package com.evolutionary.operator.selection;

import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.SimplePopulation;

/**
 * Created on 1/out/2015, 11:47:02
 *
 * @author zulu - computer
 */
public abstract class Selection extends GeneticOperator {

    /**
     * Selects a set of inviduals to reproduction
     *
     * @param parents parents population
     * @return parents selected to reproduction
     */
    public abstract SimplePopulation execute(SimplePopulation parents);
    /**
     * proportion of the parents that will be selected 1 - the same number of
     * parents
     */
    protected static double default_parentsProportion = 1;
    protected double parentsProportion;

    public Selection() {
        this(default_parentsProportion);
    }

    public Selection(double parentsProportion) {
        this.parentsProportion = parentsProportion;        
    }

    //----------------------------------------------------------------------------------------------------------
    /**
     * update parameters od the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    public void setParameters(String params) throws RuntimeException { //update parameters by string values
        super.setParameters(params);
        try {
            //split string by withe chars
            String[] aParams = params.split("\\s+");
            if (aParams.length > 0) {
                parentsProportion = Double.parseDouble(aParams[0]);
            }
            //validate parentsProportion 
            if (parentsProportion <= 0) {
                throw new RuntimeException("invalid parentsProportion of Selection : " + parentsProportion);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return parentsProportion + "";
    }

    @Override
    public SimplePopulation execute(SimplePopulation... pop) {
        return execute(pop[0]);
    }
    
    
    public double getParentProportion() {
        return parentsProportion ;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510011147L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
