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
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;

/**
 * Created on 2/out/2015, 8:05:06
 *
 * @author zulu - computer
 */
public class TournamentReposition extends Selection {

    /**
     * Size of tournament 1- uniform 2- linear 3- quadratic (DJONG pp 58)
     */
    protected int tourSize = 3;

    @Override
    public SimplePopulation execute(SimplePopulation pop) {
        //make new population
        SimplePopulation selected = pop.getCleanClone();
        // calculate number of selected individuals
        int numberOfSelected = (int) (pop.getNumberOfIndividuals() * parentsProportion);
        //select NumChilds Individuals
        while (selected.getNumberOfIndividuals() < numberOfSelected) {
            //get one individual
            Solution best = pop.getRandom();
            //get other individuals
            for (int i = 1; i < tourSize; i++) {
                Solution ind = pop.getRandom();
                //select the best of the tournament
                if (ind.compareTo(best) > 0) {
                    best = ind;
                }
            }
            //append one copie of the best
            selected.addIndividual(best.getClone());
        }
        return selected;
    }
    
     //----------------------------------------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return parentsProportion + " " + tourSize;
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
        try {
            tourSize = Integer.parseInt(aParams[1]);
        } catch (Exception e) {
        }
       
    }//----------------------------------------------------------------------------------------------------------
    

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (proportion of population)\n");

        txt.append("\n\nDeterministic tournament selection:");
        txt.append("\nThe selection pressure of tournament selection ");
        txt.append("\ndirectly varies with the tournament size");
        txt.append("\nAlgorithm:");
        txt.append("\n  Parents = empty population");
        txt.append("\n  SIZE = PROPORTION * parents.size");
        txt.append("\n  Repeat <SIZE> times");
        txt.append("\n     Select <TOURN> random individuals from population");
        txt.append("\n     parents +=  best indidivual of tornament");

        txt.append("\n\n Refs: http://en.wikipedia.org/wiki/Tournament_selection");
        txt.append("\nRefs: Miller, Brad L.; Goldberg, David E.");
        txt.append("\nGenetic Algorithms, Tournament Selection, and the Effects of Noise");

        return txt.toString();
    }

   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020805L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    
     public static void main( String[] args){
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("100");
        Solution ind = new OneMax();
        ind.setParameters("100");
        pop.createRandom(ind);
        pop.evaluate();
        for (int i = 0; i < pop.getSize(); i++) {
            pop.addIndividual(pop.getRandom());
            
        }
        System.out.println(pop);
        Selection t = new TournamentReposition();
        pop =  t.execute(pop);
        System.out.println(pop);
    }

}
