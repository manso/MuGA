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
package com.evolutionary.solverUtils;

import com.evolutionary.Genetic;
import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.problem.Solution;
import com.evolutionary.report.ReportSolver;
import com.evolutionary.report.ReportSolverArray;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.stopCriteria.StopCriteria;
import com.utils.MyString;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Solver array is an solver that contains array of solvers Template solver
 * conatains the atribute template.numberOfRun that produces an individual
 * solver.
 *
 * Report of this solver contains the statistics of the array of solvers
 *
 *
 * Created on 30/mar/2016, 7:55:17
 *
 * @author zulu - computer
 */
public class EAsolverArray extends EAsolver {

    public EAsolver[] arrayOfSolvers = null; // array of solver 
    public EAsolver template = null; // template solver

    public EAsolverArray() {
    }

    public EAsolverArray(EAsolver template) {
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //copy information from template
        this.template = template;
        this.numberOfRun = template.numberOfRun;

        this.randomSeed = template.randomSeed;
        this.random = new Random(randomSeed);

        this.problem = template.problem.getClone();
        this.parents = template.parents.getCleanClone();

        this.selection = (GeneticOperator) Genetic.getClone(template.selection);
        this.recombination = (GeneticOperator) Genetic.getClone(template.recombination);
        this.mutation = (GeneticOperator) Genetic.getClone(template.mutation);
        this.replacement = (GeneticOperator) Genetic.getClone(template.replacement);
        this.rescaling = (GeneticOperator) Genetic.getClone(template.rescaling);
        this.stop = (StopCriteria) Genetic.getClone(template.stop);
        //new type of report 
        this.report = new ReportSolverArray(template);
        this.updateSolverAtributes();
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        arrayOfSolvers = createArrayOfSolvers(template);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::: S O L V E R   A R R A Y     ::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * creates an array of solvers based on a template random seeds of solvers
     * are generate by random object of templeta
     *
     * @param template solver to clonate
     * @return array of smart cloned templates
     */
    public static EAsolver[] createArrayOfSolvers(EAsolver template) {
        EAsolver[] arrayOfSolvers = new EAsolver[template.numberOfRun];
        //initialize solver
        template.random.setSeed(template.randomSeed);
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //build solvers
        for (int i = 0; i < arrayOfSolvers.length; i++) {
            arrayOfSolvers[i] = template.getSolverClone();
            //one run
            arrayOfSolvers[i].numberOfRun = 1;
            // random seed initialized by template.random
            arrayOfSolvers[i].randomSeed = template.random.nextLong();
            arrayOfSolvers[i].updateSolverAtributes();
            //create simple report
            arrayOfSolvers[i].report = new ReportSolver();
            arrayOfSolvers[i].report.setSolver(template, i);
        }
        return arrayOfSolvers;
    }

    /**
     * solve the array of solvers in parallell
     *
     * @param verbose
     */
    public void solve(boolean verbose) {
        startEvolution(verbose); // reset solvers
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //running solvers
        ThreadRunSolver[] thr = new ThreadRunSolver[arrayOfSolvers.length];
        for (int i = 0; i < arrayOfSolvers.length; i++) {
            final int index = i;
            thr[i] = new ThreadRunSolver(arrayOfSolvers[index]);
            thr[i].start();
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //waiting to solvers finish
        for (Thread thread : thr) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(EAsolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //save report
        //report = new ReportSolverArray(this);
        ((ReportSolverArray) report).updateEvolutionStatistics(arrayOfSolvers);
        ((ReportSolverArray) report).save();
        System.out.println(MyString.toString(new Date()) + " : Simulation " + report.filename + " Complete!");
    }

    /**
     * evolve Array of solvers solvers in sequencial (no MultiThread)
     */
    @Override
    public void iterate() {
        this.numEvaluations = 0;
        this.numGeneration = 0;
         parents.clear();
        //evolve all solvers
        for (EAsolver s : arrayOfSolvers) {
            if (!s.stop.isDone(s)) {
                s.iterate(); // iterate solver  
                updateHallOfFame(s.parents);
                
            }
            parents.addAll(s.hallOfFame);
            
            this.numEvaluations += s.numEvaluations;
            this.numGeneration += s.numGeneration;
        }   
         //parents.addAll(hallOfFame);
        this.numEvaluations /= arrayOfSolvers.length;
        this.numGeneration /= arrayOfSolvers.length;
        //update stastistics
        ((ReportSolverArray) report).updateStats(arrayOfSolvers);
    }

    /**
     * verify if the solve is done
     *
     * @return
     */
    public boolean isDone() {
        for (EAsolver s : arrayOfSolvers) {
            if (!s.stop.isDone(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * start sequencial evolution
     *
     * @param verbose
     */
    public void startEvolution(boolean verbose) {
        // init random generator
        if (template.randomSeed == 0) {
            template.random.setSeed(template.random.nextLong());
        } else {
            template.random.setSeed(template.randomSeed);
        }
        if (arrayOfSolvers == null) {
            arrayOfSolvers = createArrayOfSolvers(template);
        }
        //create random population
        parents = parents.getCleanClone();
        parents.createRandom(problem);
        parents.evaluate();

        for (EAsolver s : arrayOfSolvers) {
            s.startEvolution(verbose);
        }
        //start statistics
        report.startStats(this, verbose);

        updateEvolutionStats();
    }

    @Override
    public void updateEvolutionStats() {
        numGeneration++;
        //parents contains haal of fame of solvers
        parents.clear();
        for (EAsolver s : arrayOfSolvers) {
            updateHallOfFame(s.parents);
            parents.addAll(s.hallOfFame);
        }
        report.updateStats();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::: T H R E A D  ::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private class ThreadRunSolver extends Thread {

        EAsolver solver;

        public ThreadRunSolver(EAsolver solver) {
            this.solver = solver;
        }

        public void run() {
            System.out.println("Start " + Thread.currentThread().getName() + " -> " + solver.report.filename);
            solver.random.setSeed(solver.randomSeed);
            solver.solve(solver.report.verbose);
            solver.report.save();
            System.out.println("Stop " + Thread.currentThread().getName() + " -> " + solver.report.filename);
        }
    }

    public EAsolver getSolverClone() {
        EAsolverArray solver = (EAsolverArray) super.getSolverClone();
        solver.template = this.template;
        solver.arrayOfSolvers = null;
        return solver;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603300755L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
