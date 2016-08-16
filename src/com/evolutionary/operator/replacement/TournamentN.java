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
package com.evolutionary.operator.replacement;

import com.evolutionary.Genetic;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.List;

/**
 * Created on 2/out/2015, 8:05:06
 *
 * @author zulu - computer
 */
public class TournamentN extends Replacement {

    /**
     * Size of tournament 1- uniform 2- linear 3- quadratic (DJONG pp 58)
     */
    protected int tourSize = 3;

    @Override
    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {

        //make new population
        SimplePopulation selected = parents.getCleanClone();
        //array of expanded individuals ( multiset individuals are splited in many single individuals)
        List<Solution> lstParents = parents.getIndividualtClonesList();
        List<Solution> lstoffspring = offspring.getIndividualtClonesList();
        int indexOfBestParent, indexOfBestOffspring;
        //select NumChilds Individuals        
        while (selected.getSize() < selected.maximumSize) {
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //get one parent
            indexOfBestParent = selectByTorunament(lstParents, tourSize);
            //get one offspring
            indexOfBestOffspring = selectByTorunament(lstoffspring, tourSize);
            //select the best

            if (indexOfBestOffspring < 0) { // offsprinf populagem is empty
                selected.addIndividual(lstParents.remove(indexOfBestParent));
            } else if (indexOfBestParent < 0) { // parents population is empty
                selected.addIndividual(lstoffspring.remove(indexOfBestOffspring));
            } // parent is better than offspring
            else if (lstParents.get(indexOfBestParent).compareTo(lstoffspring.get(indexOfBestOffspring)) > 0) {
                selected.addIndividual(lstParents.remove(indexOfBestParent));
            } else { // offxpring is better than the parent
                selected.addIndividual(lstoffspring.remove(indexOfBestOffspring));
            }

        }
        assert (selected.getSize() == selected.maximumSize);
        return selected;
    }

    private int selectByTorunament(List<Solution> lst, int tour) {
        if (lst.isEmpty()) { // empty list
            return -1;
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //get one parent
        int indexOfBest = random.nextInt(lst.size());
        Solution best = lst.get(indexOfBest);
        //get other individuals
        for (int i = 1; i < tour; i++) {
            int indexNext = random.nextInt(lst.size());
            Solution ind = lst.get(indexNext);
            //select the best of the tournament
            if (ind.compareTo(best) > 0) {
                best = ind;
                indexOfBest = indexNext;
            }
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        return indexOfBest;
    }

    //----------------------------------------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return "" + tourSize;
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
            tourSize = Integer.parseInt(aParams[1]);
        } catch (Exception e) {
        }

    }//----------------------------------------------------------------------------------------------------------

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (tournament size)\n");

        txt.append("\n\nDeterministic tournament replacement:");
        txt.append("\n\nSelect the best between the one parent ");
        txt.append("\nand one offspring selected by tournament");

        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510020805L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("10");
        Solution ind = new OneMax();
        ind.setParameters("100");

        for (int k = 0; k < 100; k++) {

            pop.createRandom(ind);
            pop.evaluate();
            for (int i = 0; i < 10; i++) {
                pop.addIndividual(pop.getRandom());
            }
            System.out.println("POP \n1 " + pop);

            SimplePopulation pop2 = pop.getCleanClone();
            pop2.createRandom(ind);
            pop2.evaluate();
            for (int i = 0; i < 10; i++) {
                pop2.addIndividual(pop2.getRandom());
            }
            System.out.println("POP2 \n1 " + pop2);
            Replacement t = new TournamentN();
            pop = t.execute(pop, pop2);
            System.out.println("REPLACE \n" + pop);
        }
    }

}
