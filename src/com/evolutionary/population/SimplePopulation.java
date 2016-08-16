//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manso  &  Luis Correia                                      ::
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
package com.evolutionary.population;

import com.evolutionary.Genetic;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Simple Population - Collection of individuals
 *
 * @author zulu
 */
public class SimplePopulation extends Genetic {

    protected ArrayList<Solution> pop = new ArrayList<>();
    public int maximumSize = 64; // maximum of individuals

    /**
     * create random individuals
     *
     * @param template instance of proble
     * @param N number of individuals in the population
     */
    public void createRandom(Solution template) {
        this.random = template.random;
        pop.clear();
        Solution tmp;
        for (int i = 0; i < maximumSize; i++) {
            tmp = template.getClone(); // clone template
            tmp.random = this.random;
            tmp.randomize(); // randomize genome            
            addIndividual(tmp); // add individual
        }
    }

    /**
     * ad one individual to the population
     *
     * @param ind
     */
    public void addIndividual(Solution ind) {
        ind.setNumberOfCopies(1);
        pop.add(ind);
    }

    /**
     * ad one individual to the population
     *
     * @param ind
     */
    public void addPopulation(SimplePopulation population) {
        addAll(population.pop);
    }
    /**
     * ad one individual to the population
     *
     * @param ind
     */
    public void addAll(Collection<Solution> pop) {
        for (Solution ind : pop) {
            addIndividual(ind);
        }
    }

    /**
     * remove one individual to the population
     *
     * @param ind
     */
    public void removeIndividual(Solution ind) {
        pop.remove(ind);
    }

    /**
     * remove one individual to the population
     *
     * @param index index of individual
     */
    public Solution removeIndividual(int index) {
        return pop.remove(index);
    }

    /**
     * size of population
     *
     * @return
     */
    public int getSize() {
        return pop.size();
    }

    public boolean isEmpty() {
        return pop.isEmpty();
    }

    /**
     * number of individuals in the population
     *
     * @return
     */
    public int getNumberOfIndividuals() {
        return pop.size();
    }

    /**
     * number of allels of the individuals in the population
     *
     * @return
     */
    public int getIndividualsSize() {
        return pop.get(0).getSize();
    }

    /**
     * index of the individual in the population
     *
     * @param ind individual to search
     * @return index of individual or -1 if not found
     */
    public int indexOf(Solution ind) {
        for (int i = 0; i < pop.size(); i++) {
            if (pop.get(i).equals(ind)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Array of individuals (not clones)
     *
     * @return Array of individuals
     */
    public Solution[] getIndividualsReferences() {
        Solution[] array = new Solution[pop.size()];
        int index = 0;
        for (Solution ind : pop) {
            array[index++] = ind;
        }
        return array;
    }
    
     /**
     * Array of clone individuals
     *
     * @return Array of individuals
     */
    public Solution[] getIndividuals() {
        Solution[] array = new Solution[getNumberOfIndividuals()];
        for (int i = 0; i < array.length; i++) {
           array[i] = getIndividual(i).getClone();            
        }
        return array;
    }

    /**
     * list of individuals (clones)
     *
     * @return List of individuals
     */
    public List<Solution> getIndividualtClonesList() {
        //list of clones
        List<Solution> list = new ArrayList<>();
        //create clones
        for (Solution ind : pop) {
            list.add(ind.getClone());
        }
        return list;
    }

    /* Array of individuals (not clones)
     *
     * @return Array of individuals
     */
    public List<Solution> getList() {
        return pop;
    }

    /**
     * gets the individual in the position i
     *
     * @param i position to get
     * @return individual in the position i
     */
    public Solution getIndividual(int i) {
        return pop.get(i);
    }

    /**
     * Evaluate all the individuals in the population this method increase the
     * number of fitness evaluations of solver
     */
    public void evaluate() {
        for (int i = 0; i < pop.size(); i++) {
            pop.get(i).evaluate();
        }
    }

    /**
     * textual representation
     *
     * @return text
     */
    @Override
    public String toString() {
        if (pop.isEmpty()) {
            return "Empty Population";
        }
        // sort();
        StringBuilder txt = new StringBuilder(getClass().getSimpleName() + " ");
        txt.append(" " + getNumberOfIndividuals());
        txt.append(" Individuals  ");
        txt.append(pop.get(0).getName() + "\n");
        for (int i = 0; i < pop.size(); i++) {
            txt.append(i + "\t" + pop.get(i).toStringIndividual() + "\n");
        }
        return txt.toString();
    }

    /**
     * sort the population from BEST --> WORST
     */
    public void sort() {
        Collections.sort(pop);
        Collections.reverse(pop);
    }

    /**
     * shuffle population
     */
    public void shuffle() {
        Collections.shuffle(pop);
    }

    /**
     * get best individual of the population
     *
     * @return best individual
     */
    public Solution getBest() {
        int max = 0;
        for (int i = 1; i < pop.size(); i++) {
            if (pop.get(i).compareTo(pop.get(max)) > 0) {
                max = i;
            }
        }
        return pop.get(max);
    }

    /**
     * get best individual of the population
     *
     * @return best individual
     */
    public Collection<Solution> getAllBest() {
        Collection<Solution> bestPop = new ArrayList<>();
        double bestValue = getBest().getFitness();
        for (int i = 0; i < pop.size(); i++) {
            if (pop.get(i).getFitness() == bestValue) {
                bestPop.add(pop.get(i));
            }
        }
        return bestPop;
    }

    public Solution getRandom() {
        return pop.get(
                random.nextInt(pop.size())
        );
    }

    public Solution removeRandom() {
        Solution rnd = getRandom();
        removeIndividual(rnd);
        return rnd;
    }

    /**
     * gets an population object clone without individuals
     *
     * @return
     */
    public SimplePopulation getCleanClone() {
        SimplePopulation pop = (SimplePopulation) Genetic.getClone(this);
        pop.maximumSize = maximumSize;
        return pop;
    }

    /**
     * gets a population clone
     *
     * @return
     */
    public  SimplePopulation getClone() {
        SimplePopulation clone = getCleanClone();
        clone.maximumSize = maximumSize;
        for (Solution ind : pop) {
            clone.pop.add(ind.getClone());
        }
        return clone;
    }

    /**
     * update parameters definition
     *
     * @param params individual parameters
     */
    @Override
    public void setRandomGenerator(Random rnd) {
        super.setRandomGenerator(rnd);
        for (Solution individual : pop) {
            individual.setRandomGenerator(rnd);
        }
    }

    /**
     * update parameters definition
     *
     * @param params individual parameters
     */
    public void setParameters(String params) throws RuntimeException {
        try {
            //update maximum size
            maximumSize = Integer.parseInt(params);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param params individual parameters
     */
    public String getParameters() {
        //do nothing
        return "" + maximumSize;
    }

    public int getMostSimilar(int index) {
        int similar = -1;
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pop.size(); i++) {
            if (i == index) { //same individual
                continue;
            }
            if (similar == -1) {
                similar = i;
            } else if (pop.get(similar).distance(pop.get(i)) < minDistance) {
                minDistance = pop.get(similar).distance(pop.get(i));
                similar = i;
            }
        }
        return similar;
    }

    ////////////////////////////////////////////////////////////
    // Computes the univariate absolute frequencies.
    // Use ((float)uniFrequencies[j])/(float)hBOASolver.N) to
    // compute relative frequencies.
    //
    ////////////////////////////////////////////////////////////
    protected int[] uniFrequencies;				// Array of xSize univariate frequencies.

    public void computeUnivariateFrequencies() {
        if (!(pop.get(0) instanceof BinaryString)) {
            return;
        }
        Solution[] individuals = getIndividualsReferences();
        uniFrequencies = new int[individuals[0].getSize()]; 		// NOTE: uniFrequencies is initialized all zeros by default.

        for (Solution individual : individuals) {
            boolean[] ellels = ((BinaryString) individual).getBitsGenome();
            for (int i = 0; i < ellels.length; i++) {
                if (ellels[i]) {
                    uniFrequencies[i]++;
                }

            }
        }
    }

    public int[] getUniFrequencies() {
        return uniFrequencies;
    }

    public int getUniFrequencies(int j) {
        return uniFrequencies[j];
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290908L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SimplePopulation pop = new SimplePopulation();
        pop.setParameters("4");
        pop.createRandom(new OneMax());
        pop.evaluate();
        System.out.println(pop);
        pop.computeUnivariateFrequencies();
        System.out.println(Arrays.toString(pop.getUniFrequencies()));
    }

    public void clear() {
       this.pop.clear();
    }

}
