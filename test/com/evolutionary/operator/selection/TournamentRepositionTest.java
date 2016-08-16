/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.operator.selection;

import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.population.UniquePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import com.evolutionary.problem.bits.OneMaxTest;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class TournamentRepositionTest {
    
    
     GeneticOperator templateOperator = new TournamentReposition();

    @Test
    public void TestThread() {
        System.out.println(this.getClass().getSimpleName() + " Threads");
        int SIZE_POP = 60;
        SimplePopulation pop;
        pop = new SimplePopulation();
        pop.setParameters("" + SIZE_POP);
        Solution ind = new OneMax();
        ind.setParameters("100");
        pop.createRandom(ind);
        pop.evaluate();
        TestThread(pop);
        
        
        
//        pop = new UniquePopulation();
//        pop.setParameters("" + SIZE_POP);
//        pop.createRandom(ind);
//        pop.evaluate();
//        TestThread(pop);
        
        pop = new MultiPopulation();
        pop.setParameters("" + SIZE_POP);
        pop.createRandom(ind);

        for (int i = 0; i < SIZE_POP * 2; i++) {
            pop.addIndividual(pop.getRandom());
        }
        pop.evaluate();
        TestThread(pop);
    }

    public void TestThread(SimplePopulation p) {
        try {
            int SIZE = GeneticOperator.NUM_THREADS_TO_TESTS;

            ThreadPopulation[] thr = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr.length; i++) {
                Random rnd = new Random(1234 + i);
                SimplePopulation pop = p.getClone();
                pop.setRandomGenerator(rnd);
                thr[i] = new ThreadPopulation(SIZE, pop);
                thr[i].start();
            }
            for (int i = 0; i < thr.length; i++) {
                thr[i].join();
            }

            ThreadPopulation[] thr2 = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr2.length; i++) {
                Random rnd = new Random(1234 + i);
                SimplePopulation pop = p.getClone();
                pop.setRandomGenerator(rnd);
                thr2[i] = new ThreadPopulation(SIZE, pop);
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
        } catch (InterruptedException ex) {
            Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }

    }

    private class ThreadPopulation extends Thread {

        int runs;
        Solution individual;
        SimplePopulation pop;
        GeneticOperator op ;

        public ThreadPopulation(int runs, SimplePopulation p) {
            this.runs = runs;
            pop = p;
            op = templateOperator.getClone();
            op.setRandomGenerator(p.getRandomGenerator());
        }

        public void run() {
            try {
                individual = pop.getBest();
                int ind = pop.getNumberOfIndividuals();
                op.setParameters("0.5");
                for (int i = 0; i < runs; i++) {
                    //50% 
                    SimplePopulation pop1 = op.execute(pop);
                    //50%
                    pop = op.execute(pop);
                    //join
                    pop.addPopulation(pop1);
//                    System.out.println( ind + " - " + pop.getNumberOfIndividuals());
                    assertTrue(ind == pop.getNumberOfIndividuals());
                    individual = pop.getBest();
                }

            } catch (Exception ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.getMessage());
            }

        }

    }
}
