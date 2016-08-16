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
import java.util.Collections;
import java.util.List;

/**
 * Created on 2/out/2015, 8:05:06
 *
 * @author zulu - computer
 */
public class TournamentNoReposition extends Selection {

    /**
     * Size of tournament 1- uniform 2- linear 3- quadratic (DJONG pp 58)
     */
    protected int tourSize = 4;

    @Override
    public SimplePopulation execute(SimplePopulation pop) {
        //make new population
        SimplePopulation selected = pop.getCleanClone();
        // calculate number of selected individuals
        int numberOfSelected = (int) (pop.getSize() * parentsProportion);
        //array of expanded individuals ( multiset individuals are splited in many single individuals)
        List<Solution> toSelect = null;
        int indexToSelect = 0; // decrease index ( 0 = shuffle population)
        Solution best;
        
        //select individuals        
        while (selected.getNumberOfIndividuals() < numberOfSelected) {
            //shuffle population when index is not large enought to perform selection
            if( indexToSelect < tourSize){ 
                //get individuals clones
                toSelect = pop.getIndividualtClonesList();
                //shuffle individuals
                shuffle(toSelect);
                //update index
                indexToSelect = toSelect.size()-1; // last postion
            }
            
            //best is the firs
            best = toSelect.get(indexToSelect);
            indexToSelect--;
            //select others
            for (int i = 1; i < tourSize; i++) {
                if( best.compareTo(toSelect.get(indexToSelect))<0){
                    best = toSelect.get(indexToSelect);
                }
                indexToSelect--;
            }
            //select
            selected.addIndividual(best);
        }
        //return selected population
        return selected;
    }

    private void shuffle(List<Solution> pop) {
        int n = pop.size();
        for (int i = 1; i < n; i++) {
            int r = i + random.nextInt(n - i);
            Solution temp = pop.get(i - 1);
            pop.set(i - 1, pop.get(r));
            pop.set(r,temp);
        }
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
         txt.append("\n"+getClass().getSimpleName() +" (proportion , tournamentSize)\n");
         txt.append("\n proportion      - Proportion of population size (default 1.0)");
         txt.append("\n tournamentSize  - Size of tournament (default 1.0)");

        txt.append("\n\nDeterministic tournament selection with no reposition:");
        txt.append("\nrepeat until selection is done");
        txt.append("\n    Shuffle population");
        txt.append("\n    Divide population in clusters of tournamentSize individuals");
        txt.append("\n    Select best of each cluster");
        txt.append("\nnext");                

        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020805L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("100");
        Solution ind = new OneMax();
        ind.setParameters("100");
        pop.createRandom(ind);
        pop.evaluate();
        for (int i = 0; i < 10; i++) {
            pop.addIndividual(pop.getRandom());

        }
      //  System.out.println(pop);
        Selection t = new TournamentNoReposition();
        t.setParameters("1 4");
        pop = t.execute(pop);
        pop.sort();
        System.out.println(pop);
    }

}
