/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.solver.muga;

import com.evolutionary.solver.MuGA;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.population.UniquePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import com.evolutionary.problem.bits.OneMaxTest;
import com.evolutionary.problem.bits.OneMin;
import com.evolutionary.solver.EAsolver;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class MuGATestMin {

    @Test
    public void TestThread1() {
        System.out.println("MUGA Test ones MIN");
        EAsolver solver = new MuGA();
        solver.randomSeed = 14;
        solver.problem = new OneMin();
        solver.problem.setParameters("500");
        solver.numberOfRun=10;
        solver.solve(false);
//        System.out.println(solver.hallOfFame);
        assertSame(solver.random, solver.mutation.random);
        assertSame(solver.random, solver.recombination.random);
        assertSame(solver.random, solver.replacement.random);
        assertSame(solver.random, solver.rescaling.random);
        assertSame(solver.random, solver.selection.random);
        assertSame(solver.random, solver.problem.random);
        assertSame(solver.random, solver.parents.random);

    }

    @Test
    public void TestThread() {
        EAsolver solver = new MuGA();
        solver.problem = new OneMax();
        solver.problem.setParameters("50");
        solver.parents = new MultiPopulation();
        TestThread(solver);
        solver.parents = new UniquePopulation();
        TestThread(solver);
        solver.parents = new SimplePopulation();
        TestThread(solver);
        
        
        

    }

    public void TestThread(EAsolver solver) {
        try {
            int SIZE = 6;
            solver.random = new Random(solver.randomSeed);
            ThreadSolver[] thr = new ThreadSolver[SIZE];
            for (int i = 0; i < thr.length; i++) {
                EAsolver s = solver.getSolverClone();
                s.randomSeed = solver.random.nextInt();
                thr[i] = new ThreadSolver(s);
                thr[i].run();
            }
            for (int i = 0; i < thr.length; i++) {                
                thr[i].join();
            }

            ThreadSolver[] thr2 = new ThreadSolver[SIZE];
            solver.random = new Random(solver.randomSeed);
            for (int i = 0; i < thr2.length; i++) {
                EAsolver s = solver.getSolverClone();
                s.randomSeed = solver.random.nextInt();
                thr2[i] = new ThreadSolver(s);
                thr2[i].start();
            }
            for (int i = 0; i < thr.length; i++) {                
                thr2[i].join();
            }

            for (int i = 0; i < thr2.length; i++) {
                assertTrue(thr[i].individual.equals(thr2[i].individual));
                for (int j = 0; j < thr2[i].pop.getSize(); j++) {
                    assertTrue(thr[i].pop.getIndividual(j).equals(thr2[i].pop.getIndividual(j)));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }

    }

    private class ThreadSolver extends Thread {

        Solution individual;
        SimplePopulation pop;
        EAsolver solver;

        public ThreadSolver(EAsolver solver) {
            this.solver = solver;
        }

        @Override
        public void run() {
            solver.solve(false);
            pop = solver.parents;
//            System.out.println(solver.hallOfFame);
            individual = solver.parents.getBest();
            individual = solver.hallOfFame.iterator().next();

//            System.out.println(solver.numEvaluations + " " + getName() + " " + individual);
        }

    }
}
