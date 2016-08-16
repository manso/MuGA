package com.evolutionary.solverUtils.HBOA;

import com.evolutionary.problem.BinaryString;

public class Individual {

    public static BinaryString template;
    private char[] individual = new char[Problem.n];

    public Individual() {
    }	// Default constructor	
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public BinaryString getSolution() {
        return getSolution(template);
    }

    public BinaryString getSolution(BinaryString ind) {
        BinaryString clone = (BinaryString) ind.getClone();
        clone.setBits(new String(individual));
        return clone;
    }

    public void setSolution(BinaryString ind) {
        individual = ind.toStringGenome().toCharArray();
    }

    public Individual(BinaryString ind) {
        setSolution(ind);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public Individual(Individual johnDoe) {
        individual = johnDoe.getIndividual();
    }	// Non shallow copy of an Individual	

    public Individual(char[] individual) {
        this.individual = individual;
    }				// Shallow copy of an Individual

    public char getAllele(int j) {
        return individual[j];
    }

    public char[] getIndividual() {
        return individual;
    }

    public void setAllele(int j, char c) {
        individual[j] = c;
    }

//    public float computeFitness(){return HBOASolver.problem.computeFitness(this);}
    public float computeFitness() {
        HBOASolver.problem.computeFitness(this);
        template.setBits(new String(individual));
        return (float) template.fitness();
    }

    public char[] copyIndividual() {
        char[] copy = new char[Problem.n];
        for (int i = 0; i < Problem.n; i++) {
            char c = individual[i];
            copy[i] = c;
        }
        return copy;
    }

    public int distance(Individual johnDoe) {										// Hamming Distance
        char[] thatIndividual = johnDoe.getIndividual();
        int dist = 0;
        for (int i = 0; i < Problem.n; i++) {
            if (this.individual[i] != thatIndividual[i]) {
                dist++;
            }
        }
        return dist;
    }

    public boolean isZero() {
        for (int i = 0; i < Problem.n; i++) {
            if (individual[i] != '0') {
                return false;
            }
        }
        return true;
    }

    public boolean isOne() {
        for (int i = 0; i < Problem.n; i++) {
            if (individual[i] != '1') {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < Problem.n; i++) {
            str += individual[i];
        }
        return str;
    }
}
