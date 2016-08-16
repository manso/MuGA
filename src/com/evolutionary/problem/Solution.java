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
package com.evolutionary.problem;

import com.evolutionary.Genetic;
import com.evolutionary.solver.EAsolver;
import com.utils.MyString;
import java.lang.reflect.Array;
import java.util.Locale;

/**
 * Created on 29/set/2015, 9:08:08
 *
 * @author zulu - computer
 */
public abstract class Solution extends Genetic implements Comparable<Solution> {

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * calculate the fitness of the invidual this method increase the number of
     * evaluations
     *
     * @return fitness value
     */
    protected abstract double fitness(); //------------------------------------- calculate fitness

    /**
     * verify if the individual is optimum
     *
     * @return
     */
    public abstract boolean isOptimum(); //------------------------------------- calculate fitness

    /**
     * Randomize genome of the individual
     */
    public abstract void randomize(); //--------------------------------------- randomize genome    

    /**
     * calculate the distance between two individuals
     *
     * @param ind distance
     * @return
     */
    public abstract double distance(Solution ind); //------------------------- distance between individuals

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public EAsolver solver = null; //------------------------------------------- solver that evolve this individual
    protected Object genome = null;  //---------------------------------------- genome representation
    protected double value = Double.NaN; //------------------------------------- value of genome
    private int numberOfCopies = 1; //------------------------------------------ number of genome copys (used in multipopulations)
    protected Optimization optimizationType = Optimization.MAXIMIZE; //--------- type of optimization

    /**
     * constructor
     *
     * @param genome genome object
     * @param optimization type of optimization { Optimization.MAXIMIZE ,
     * Optimization.MINIMIZE }
     */
    protected Solution(Object genome, Optimization optimization) {
        this.genome = genome;
        optimizationType = optimization;
    }

    /**
     * @return the numberOfCopies
     */
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * @param numberOfCopies the numberOfCopies to set
     */
    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    /**
     * @param nCopys the numberOfCopies to set
     */
    public void addCopies(int nCopys) {
        this.numberOfCopies += nCopys;
    }

    /**
     * @return the optimizationType
     */
    public Optimization getOptimizationType() {
        return optimizationType;
    }

    public void setSolver(EAsolver solver) {
        this.solver = solver;
        if (solver != null) {
            this.random = solver.random;
        }
    }

    /**
     * Evaluate individual
     */
    public void evaluate() {
        value = fitness(); // calculate fitness
        if (solver != null) {
            solver.numEvaluations++; //--------------------------------------------- FITNESS CALLS
        }
    }

    /**
     * number of allels
     *
     * @return number of allels
     */
    public int getSize() {
        if (genome.getClass().isArray()) {
            return Array.getLength(genome); // array of genes
        }
        return 1; // by default have 1 gene
    }

    public boolean isMaximize() {
        return optimizationType == Optimization.MAXIMIZE;
    }

    /**
     * evaluate the individual and increase fitness calls
     *
     * @return
     */
    public double getFitness() {
        if (!isEvaluated()) {
            evaluate();//------------------------------------------------------- call evaluate and update fitness calls
        }
        return value;
    }

    public boolean isEvaluated() {
        return !Double.isNaN(value); // not defined
    }

    public void setNotEvaluated() {
        value = Double.NaN; // not defined
    }

    public Object getGenome() {
        return genome;
    }

    public void setGenome(Object newGenome) {
        genome = newGenome;
        setNotEvaluated();
    }

    /**
     * gets the name od the problem
     *
     * @return
     */
    public String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public String toString() {
        return toStringIndividual();
    }

    /**
     * textual representation of the individual
     *
     * @return
     */
    public String toStringGenome() { //----------------------------------------  textual representation of genome
        return genome.toString();
    }

    public String toStringPhenotype() {//--------------------------------------- textual representation of phenotype
        return toStringGenome();
    }

    public String toStringIndividual() {
        StringBuilder txt = new StringBuilder();
        if (numberOfCopies == 1) {
            txt.append("    ");
        } else {
            txt.append(String.format("[%2d]", getNumberOfCopies()));
        }
        if (isEvaluated()) {
            txt.append(String.format(Locale.US, "%12.2f", value) + " = ");
        } else {
            txt.append(MyString.setSize("NOT EVAL", 12) + " = ");
        }
        txt.append(toStringGenome());
        txt.append("\t(" + getClass().getSimpleName() + ")");

        if (isOptimum()) {
            txt.append(" OPTIMUM ");
        }

        return txt.toString();
    }

    public String toStringPhenotypeIndividual() {
        StringBuilder txt = new StringBuilder();
        if (numberOfCopies == 1) {
            txt.append("    ");
        } else {
            txt.append(String.format("[%2d]", getNumberOfCopies()));
        }
        if (isEvaluated()) {
            txt.append(String.format(Locale.US, "%12.2f", value) + " = ");
        } else {
            txt.append(MyString.setSize("NOT EVAL", 12) + " = ");
        }
        txt.append(toStringPhenotype());
        txt.append("\t(" + getClass().getSimpleName() + ")");

        if (isOptimum()) {
            txt.append(" OPTIMUM ");
        }

        return txt.toString();
    }

    @Override
    public int compareTo(Solution tmp) { //----------------------------------- compare To

        if (!tmp.isEvaluated()) {
            tmp.evaluate();
        }
        if (!isEvaluated()) {
            evaluate();
        }

        if (getOptimizationType() == Optimization.MAXIMIZE) {//----------------- MAXIMIZE
            if (this.value > tmp.value) {
                return 1;
            } else if (this.value < tmp.value) {
                return -1;
            }
            return 0;

        } else //------------------------------------------------------------- MINIMIZE
        {
            if (this.value > tmp.value) {
                return -1;
            } else if (this.value < tmp.value) {
                return 1;
            }
            return 0;
        }

    }

    /**
     * compare the genomes of the individuals
     *
     * @param other
     * @return
     */
    public boolean equals(Object other) {//------------------------------------- equals
        return genome.equals(((Solution) other).genome);
    }

    /**
     * creates a raw copy of the individual
     *
     * @return raw copy of this
     */
    public Solution getClone() {//-------------------------------------------- get clone
        Solution ind = (Solution) Genetic.getClone(this);
        ind.setSolver(solver);
        ind.value = value;
        ind.numberOfCopies = numberOfCopies;
        ind.optimizationType = optimizationType;
        return ind;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public enum Optimization { //----------------------------------------------- type of optimization
        MAXIMIZE, // maximize optimization
        MINIMIZE //minimize optimization
    };

    public boolean getAllele(int i) {
        if (genome instanceof Boolean[] && ((boolean[]) genome)[i]) {
            return true;
        }
        return false;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290908L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
