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
package com.evolutionary.operator.replacement;

import com.evolutionary.Genetic;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.population.UniquePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import com.evolutionary.solverUtils.HBOA.HBOA;
import com.evolutionary.solverUtils.HBOA.Individual;
import com.evolutionary.solverUtils.HBOA.Population;
import java.util.List;

/**
 * Created on 5/abr/2016, 10:43:38
 *
 * @author zulu - computer
 */
public class RestrictedReplacement extends Replacement {

//    @Override
//    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {
//        List<Solution> lstOff = offspring.getList(); // list of offspring
//        List<Solution> lstParens = parents.getList();//list of parents
//
//        int w = Math.min(parents.getIndividual(0).getSize(), parents.getSize() / 20 + 1);
//        for (int i = 0; i < lstOff.size(); i++) {
//            Solution child = lstOff.get(i);
//            int mostSimilar = getSimilar(child, lstParens, w); // parent most similar
//            Solution parent = lstParens.get(mostSimilar);
//            if (parent.compareTo(child) < 0) { // parent is worst than the child  
//                //preserve population size in Mulsiset and Uniqueset
//                if ((parents instanceof MultiPopulation || parents instanceof UniquePopulation)
//                        && parents.indexOf(child) >= 0) { // preserve population size
//                    parents.addIndividual(child); //add child
//                } else {
//                    parents.removeIndividual(mostSimilar); //remove parent
//                    parents.addIndividual(child); //add child
//                }
//            }
//        }
//        return parents;
//    }
//
//    private int getSimilar(Solution child, List<Solution> parents, int windowSize) {
//        int index2, bestIndex = 0;
//        double distance, minDistance = Double.POSITIVE_INFINITY;
//        for (int i = 0; i < windowSize; i++) { // execute the attempts
//            index2 = random.nextInt(parents.size());
//            distance = child.distance(parents.get(index2)); // distance
//            if (minDistance < distance) {
//                minDistance = distance;
//                bestIndex = index2;
//            }
//        }
//        return bestIndex;
//    }
    public SimplePopulation execute(SimplePopulation parents, SimplePopulation offspring) {
        parents.evaluate();
        List<Solution> newIndividuals = offspring.getList(); // list of offspring
        List<Solution> population = parents.getList();//list of parents
        int N = population.size();
        int windowSize = Math.min(population.get(0).getSize(), population.size() / 20 + 1);
        for (int i = 0; i < newIndividuals.size(); i++) {
            Solution candidate = newIndividuals.get(i);
            int bestPosition = -1;
            double bestDistance = Integer.MAX_VALUE;
            for (int j = 1; j < windowSize; j++) {			// Find within the window, the individual CLOSEST to the candidate.
                int picked = random.nextInt(N);
                Solution individual = population.get(picked);
                double distance = individual.distance(candidate);
                if (distance < bestDistance) {
                    bestPosition = picked;
                    bestDistance = distance;
                }
            }
            candidate.evaluate();
            double candidateFit = candidate.getFitness();
            if (candidateFit > population.get(bestPosition).getFitness()) {
                population.set(bestPosition, candidate);
            }
        }
//        parents.clear();
//        for(Solution i : population){
//            parents.addIndividual(i);
//        }
        return parents;
    }// END: replace()

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(Genetic.getName(this));
        txt.append("\n" + getClass().getSimpleName() + " (window size)\n");
        txt.append("\nRestricted Tournament ");
        txt.append("\nReplace similar parents with offprings");
        txt.append("\n\nForeach child in Offspring population");
        txt.append("\n    calculate the most similar parent in a window");
        txt.append("\n    replace parent to child if this is better ");
        txt.append("\n\n    similarity widows = min( individual.lenght, parentsPop.size");
        txt.append("\n\n    similarity widows = min( individual.lenght, parentsPop.size");
        txt.append("\n\n   Martin Pelikan,Hierarchical Bayesian Optimization Algorithm,  p. 123");
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604050955L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new MultiPopulation();
        pop.setParameters("10");
        Solution ind = new OneMax();
        ind.setParameters("100");

//        for (int k = 0; k < 100; k++) {
        pop.createRandom(ind);
        pop.evaluate();
        for (int i = 0; i < 10; i++) {
            pop.addIndividual(pop.getRandom());
        }
        pop.sort();
        System.out.println("POP \n1 " + pop);

        SimplePopulation pop2 = pop.getCleanClone();
        pop2.createRandom(ind);
        pop2.evaluate();

        for (int i = 0; i < 10; i++) {
            pop2.addIndividual(pop2.getRandom());
        }
        pop2.sort();
        System.out.println("POP2 \n1 " + pop2);
        Replacement t = new RestrictedReplacement();
        pop = t.execute(pop, pop2);
        pop.sort();
        System.out.println("REPLACE \n" + pop);
    }
    // }

}
