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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.evolutionary.solver;

import com.evolutionary.solverUtils.FileSolver;
import com.evolutionary.Genetic;
import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.operator.mutation.M_WaveMutation;
import com.evolutionary.operator.recombination.M_Crossover;
import com.evolutionary.operator.replacement.TournamentReplace;
import com.evolutionary.operator.rescaling.AdtaptiveRescaling;
import com.evolutionary.operator.selection.TournamentReposition;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.HIFF;
import com.evolutionary.report.ReportSolver;
import com.evolutionary.stopCriteria.OptimumFound;
import com.evolutionary.stopCriteria.StopCriteria;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created on 3/out/2015, 12:39:44
 *
 * @author zulu - computer
 */
public abstract class EAsolver extends Genetic {

    public abstract void iterate();

    public String solverName = this.getClass().getSimpleName(); // name of the solver
    public long randomSeed = 1234;                   // seed to random  
    public int numberOfRun = 1; // number of run (
    public int numEvaluations = 0; // number of evaluations (updated by genetic operators)
    public int numGeneration = 0;  // number of generations

    public Solution problem = new HIFF(); // problem to solve
    // Genetic operators
    public GeneticOperator selection = new TournamentReposition();
    public GeneticOperator recombination = new M_Crossover();
    public GeneticOperator mutation = new M_WaveMutation();
    public GeneticOperator replacement = new TournamentReplace();
    public GeneticOperator rescaling = new AdtaptiveRescaling();

    public StopCriteria stop = new OptimumFound(); // stop criteria

    public SimplePopulation parents = new MultiPopulation(); // population type

    public Collection<Solution> hallOfFame = new ArrayList<>(); // hall of fame
    public ReportSolver report = new ReportSolver();  // report of evolution

    /**
     * initialize all the components of the evolution
     *
     * @param verbose display messages in console ?
     */
    public void InitializeEvolution(boolean verbose) {
        // init random generator
        if (randomSeed == 0) {
            random.setSeed(random.nextLong());
        } else {
            random.setSeed(randomSeed);
        }
        //update atribute solver of operators to this
        updateSolverAtributes();
        //create random population
        parents = parents.getCleanClone();
        parents.createRandom(problem);
        parents.evaluate();
        //start statistics
        report.startStats(this, verbose);
        updateEvolutionStats();
    }

    public void finishEvolution() {
        //finish report
        report.finishStats();
    }

    public void solve(boolean verbose) {
        InitializeEvolution(verbose);
        while (!stop.isDone(this)) {
            iterate();
            updateEvolutionStats();
        }
        finishEvolution();
    }

    public void setParents(SimplePopulation pop) {
        this.parents = pop;
        if (!pop.isEmpty()) {
            this.problem = pop.getIndividual(0);
        }
    }

    public final void updateSolverAtributes() {
        problem.setSolver(this);
        parents.setRandomGenerator(this.random);
        selection.setSolver(this);
        recombination.setSolver(this);
        mutation.setSolver(this);
        replacement.setSolver(this);
        rescaling.setSolver(this);
        selection.setSolver(this);
        report.setSolver(this);

    }

    public void updateEvolutionStats() {
        numGeneration++;
        parents.sort(); // sort population
        updateHallOfFame(parents);
        report.updateStats();
    }

    public void updateHallOfFame(SimplePopulation pop) {
        if (hallOfFame.isEmpty()) {
            Collection<Solution> best = pop.getAllBest();
            for (Solution individual : best) {
                if (!hallOfFame.contains(individual)) {
                    hallOfFame.add(individual.getClone());
                }
            }
        } else {
            Solution hBEst = hallOfFame.iterator().next();
            Solution pBest = pop.getBest();
            if (pBest.compareTo(hBEst) > 0) {
                hallOfFame.clear();
            }
            if (pBest.compareTo(hBEst) >= 0 && hallOfFame.size() < pop.maximumSize) {
                Collection<Solution> best = pop.getAllBest();
                for (Solution individual : best) {
                    if (!hallOfFame.contains(individual)) {
                        hallOfFame.add(individual.getClone());
                    }
                }

            }
        }

    }

    public String toString() {
        return FileSolver.toString(this);
    }

    public String getInformation() {
        return "Solver Info";
    }

    public EAsolver getSolverClone() {
        EAsolver solver = (EAsolver) getClone(this);
        solver.solverName = solverName;
        
        solver.numberOfRun = this.numberOfRun;

        solver.randomSeed = this.randomSeed;
        solver.random = new Random(randomSeed);

        solver.problem = problem.getClone();
        solver.parents = this.parents.getCleanClone();

        solver.selection = (GeneticOperator) Genetic.getClone(this.selection);
        solver.recombination = (GeneticOperator) Genetic.getClone(this.recombination);
        solver.mutation = (GeneticOperator) Genetic.getClone(this.mutation);
        solver.replacement = (GeneticOperator) Genetic.getClone(this.replacement);
        solver.rescaling = (GeneticOperator) Genetic.getClone(this.rescaling);
        solver.stop = (StopCriteria) Genetic.getClone(this.stop);

        solver.report = report.getCleanClone();

        solver.updateSolverAtributes();
        return solver;
    }

    public void setSolver(EAsolver other) {
        this.numberOfRun = other.numberOfRun;
        this.randomSeed = other.randomSeed;

        this.problem = other.problem.getClone();
        this.parents = other.parents.getCleanClone();

        this.selection = (GeneticOperator) Genetic.getClone(other.selection);
        this.recombination = (GeneticOperator) Genetic.getClone(other.recombination);
        this.mutation = (GeneticOperator) Genetic.getClone(other.mutation);
        this.replacement = (GeneticOperator) Genetic.getClone(other.replacement);
        this.rescaling = (GeneticOperator) Genetic.getClone(other.rescaling);
        this.stop = (StopCriteria) Genetic.getClone(other.stop);

        this.report = other.report.getCleanClone();

        this.updateSolverAtributes();

    }

    /**
     * verify if the solve is done
     *
     * @return
     */
    public boolean isDone() {
        return stop.isDone(this);
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510031239L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
