package com.evolutionary.solverUtils.HBOA;
// NOTE: Use constructor Population() to generate empty populations.

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;

public class Population {

    protected int N;                   		// Size for each population, in particular for SelectedSet (NS).
    public Individual[] individuals; 				// Population of N char[] of size xSize.
    protected int worstPos, bestPos;			// Position of Worst and Best individuals. computeFitnessValues() is responsible for computing these positions.
    protected double avgFit, worstFit, bestFit;	// Worst and Best fitnesses. computeFitnessValues() is responsible for computing these fitnesses.
    protected double[] fitness;          			// Array of N fitness values.	
    protected int[] uniFrequencies;				// Array of xSize univariate frequencies.

    public Population(int otherN) {
        this.N = otherN;
        individuals = new Individual[otherN];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
        fitness = new double[otherN];
    }

    public int getN() {
        return this.N;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int i) {
        return individuals[i];
    }

    public int getWorstPos() {
        return worstPos;
    }

    public int getBestPos() {
        return bestPos;
    }

    public double getAvgFit() {
        return avgFit;
    }

    public double getWorstFit() {
        return worstFit;
    }

    public double getBestFit() {
        return bestFit;
    }

    public double[] getFitness() {
        return fitness;
    }

    public double getFitness(int i) {
        return fitness[i];
    }

    public int[] getUniFrequencies() {
        return uniFrequencies;
    }

    public int getUniFrequencies(int j) {
        return uniFrequencies[j];
    }

    public void setWorstPos(int wPos) {
        worstPos = wPos;
    }

    public void setBestPos(int bPos) {
        bestPos = bPos;
    }

    public void setWorstFit(double wFit) {
        worstFit = wFit;
    }

    public void setBestFit(double bFit) {
        bestFit = bFit;
    }

    public void setIndividual(int position, Individual johnDoe, double fit) {
        individuals[position] = johnDoe;
        fitness[position] = fit;
    }

    public Individual getIndividualCopy(int i) {		// NOTE: Non shallow copy!
        Individual indiv = new Individual();
        for (int j = 0; j < Problem.n; j++) {
            indiv.setAllele(j, individuals[i].getAllele(j));
        }
        return indiv;
    }

    public void computeFitnessValues() {
        worstFit = Double.POSITIVE_INFINITY;
        bestFit = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < this.N; i++) {
            double newFit = individuals[i].computeFitness();
            fitness[i] = newFit;
            if (newFit < worstFit) {					// The current individual is also the worst.
                worstPos = i;						// Update information about the worst individual.
                worstFit = newFit;
            }
            if (newFit > bestFit) {					// The current individual is also the best.
                bestPos = i;						// Update information about the best individual.
                bestFit = newFit;
            }
        }
    }

    public double computeAvgFitness() {
        double sumFit = 0;
        for (int i = 0; i < this.N; i++) {
            sumFit += fitness[i];
        }
        avgFit = (double) sumFit / ((double) this.N);
        return avgFit;
    }

    ////////////////////////////////////////////////////////////
    // Computes the univariate absolute frequencies.
    // Use ((float)uniFrequencies[j])/(float)hBOASolver.N) to
    // compute relative frequencies.
    //
    ////////////////////////////////////////////////////////////
    public void computeUnivariateFrequencies() {
        uniFrequencies = new int[Problem.n]; 		// NOTE: uniFrequencies is initialized all zeros by default.
        for (int j = 0; j < Problem.n; j++) {
            for (int i = 0; i < this.N; i++) {
                if (individuals[i].getAllele(j) == '1') {
                    uniFrequencies[j]++;
                }
            }
        }
    }

    public String printStats() {
        return "## Statistics ##"
                + "\nMin Fitness = " + worstFit
                + "\nMax Fitness = " + bestFit
                + "\nAvg Fitness = " + avgFit
                + "\n\n";
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < Problem.n; j++) {
                str += individuals[i].getAllele(j);
            }
            str += "\n";
        }
        return str;		// + statistics.printStats();
    }

    public SimplePopulation getPopulation(BinaryString template) {
        SimplePopulation pop = new SimplePopulation();
        for (Individual ind : individuals) {
            pop.addIndividual(ind.getSolution(template));
        }
        return pop;
    }

    public static Population getPopulation(SimplePopulation pop) {
        Population newPop = new SelectedSet(pop.getNumberOfIndividuals());
        newPop.individuals = new Individual[pop.getNumberOfIndividuals()];
        for (int i = 0; i < newPop.individuals.length; i++) {
            newPop.individuals[i] = new Individual((BinaryString)pop.getIndividual(i));
        }
        newPop.computeUnivariateFrequencies();
        return newPop;
    }

}// END: super class Population

