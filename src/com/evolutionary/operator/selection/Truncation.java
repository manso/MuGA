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

import com.evolutionary.Genetic;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/out/2015, 8:05:06
 *
 * @author zulu - computer
 */
public class Truncation extends Selection {


    @Override
    public SimplePopulation execute(SimplePopulation pop) {
        //make new population
        SimplePopulation selected = pop.getCleanClone();
        // calculate number of selected individuals
        int numberOfSelected = (int) (pop.getSize() * parentsProportion);
        //sort population
        pop.sort();
        //array of expanded individuals ( multiset individuals are splited in many single individuals)
        List<Solution> toSelect = Arrays.asList(pop.getIndividualsReferences());
        //not enought individuals
        if (toSelect.size() <= numberOfSelected) {
            //return all the population
            return pop;
        }        
        //select numberOfSelected Individuals        
        for (int count = 0; count < numberOfSelected; count++) {          
            selected.addIndividual(toSelect.get(count));
        }
        return selected;
    }

    //----------------------------------------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return parentsProportion + "";
    }

    /**
     * update parameters of the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    @Override
    public void setParameters(String params) throws RuntimeException {
        //split string by withe chars
        String[] aParams = params.split("\\s+");
        try {
            parentsProportion = Double.parseDouble(aParams[0]);
        } catch (Exception e) {
        }       
    }//----------------------------------------------------------------------------------------------------------

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (proportion of population)\n");

        txt.append("\n\nTruncationt selection:");
        txt.append("\nSelect the best parents");      
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020805L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
