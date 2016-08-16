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
package com.evolutionary.operator.selection;

import com.evolutionary.Genetic;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;

/**
 * Created on 4/abr/2016, 2:05:28
 *
 * @author zulu - computer
 */
public class TournamentUnbias extends Selection {

    protected int sizeOfTournament = 3;

    public int[][] generatePermutaion(SimplePopulation pop, int numChilds) {
        int numInd = pop.getNumberOfIndividuals();
        int sizeOfPermutation = (int) Math.ceil((double) numChilds / (double) pop.getNumberOfIndividuals());
        //global permutation
        int perm[][] = new int[sizeOfTournament][sizeOfPermutation * pop.getNumberOfIndividuals()];
        //permutation of one population
        int[] localPerm = new int[pop.getNumberOfIndividuals()];
        //Tournament size
        for (int tournament = 0; tournament < sizeOfTournament; tournament++) {
            //generate sequencial numbers
            for (int numPerm = 0; numPerm < sizeOfPermutation; numPerm++) {
                //generate sequencial numbers
                for (int k = 0; k < numInd; k++) {
                    localPerm[k] = k;
                }
                //permutate
                for (int i = localPerm.length - 1; i > 1; i--) {
                    //random position
                    int pos = random.nextInt(i);
                    //exchange value to position i
                    int aux = localPerm[pos];
                    localPerm[pos] = localPerm[i];
                    localPerm[i] = aux;
                }
                //copy permutation to global permutation
                for (int k = 0; k < numInd; k++) {
                    perm[tournament][numPerm * numInd + k] = localPerm[k];
                }
            }
        }
        return perm;
    }

    @Override
    public SimplePopulation execute(SimplePopulation pop) {
        //make new population
        SimplePopulation selected = pop.getCleanClone();
        // calculate number of selected individuals
        int numberOfSelected = (int) (pop.getNumberOfIndividuals() * parentsProportion);
        //generate permutation
        int perm[][] = generatePermutaion(pop, numberOfSelected);
        //population to array
        Solution[] parents = pop.getIndividualsReferences();
        //select  Individuals
        for (int count = 0; count < numberOfSelected; count++) {
            //get first individual of permutation
            Solution best = parents[perm[0][count]];
            //get other individuals
            for (int i = 1; i < sizeOfTournament; i++) {
                //get other individual of permutation
                Solution ind = parents[perm[i][count]];
                //select the best of the tournament
                if (ind.compareTo(best) > 0) {
                    best = ind;
                }
            }
            //append one copie of the best
            Solution sel = best.getClone();
            sel.setNumberOfCopies(1);
            selected.addIndividual(sel);
        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (proportion of population)\n");

        txt.append("\n\nUnbiased tournament selection ");
        txt.append("\nby Artem Sokolov , Darrell Whitley GECCO 2005");

        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604040205L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("10");
        Solution ind = new OneMax();
        ind.setParameters("100");
        pop.createRandom(ind);
        pop.evaluate();
        for (int i = 0; i < 10; i++) {
            pop.addIndividual(pop.getRandom());

        }
        System.out.println(pop);
        Selection t = new TournamentReposition();
        pop = t.execute(pop);
        System.out.println(pop);
    }

}
