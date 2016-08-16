/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.operator.replacement;

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
public class TournamentTest {

    /**
     * Test of getParameters method, of class NPointCrossover.
     */
    @Test
    public void ReplacementTest() {
        System.out.println("Test" + this.getClass().getSimpleName());
        int SIZE_POP = 5;
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

            SimplePopulation pop = new MultiPopulation();
            pop.maximumSize = SIZE_POP;
            pop.createRandom(ind);
            pop.evaluate();

            SimplePopulation pop2 = new MultiPopulation();
            pop.maximumSize = SIZE_POP;
            pop2.createRandom(ind);
            pop2.evaluate();

            Replacement op = new TournamentReplace();
            op.setRandomGenerator(ind.random);
            
            
//        System.out.println("POP1 " + pop);
//        System.out.println("POP2 " + pop2);
            SimplePopulation sel = op.execute(pop, pop2);
            assertEquals(sel.getSize(), SIZE_POP);

        

//        System.out.println("SEL  " + sel);
    }

    @Test
    public void TestThread() {
        TestThread(new SimplePopulation());
        TestThread(new UniquePopulation());
        TestThread(new MultiPopulation());
    }

    public void TestThread(SimplePopulation p) {
        try {
            int SIZE = 10;
            ThreadPopulation[] thr = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr.length; i++) {
                thr[i] = new ThreadPopulation(SIZE, new Random(1234 + i), p);
                thr[i].start();
            }
            for (int i = 0; i < thr.length; i++) {
                thr[i].join();
            }

            ThreadPopulation[] thr2 = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr2.length; i++) {
                thr2[i] = new ThreadPopulation(SIZE, new Random(1234 + i), p);
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
        GeneticOperator op = new TournamentReplace();

        public ThreadPopulation(int runs, Random rnd, SimplePopulation p) {
            this.runs = runs;
            individual = new OneMax();
            individual.setRandomGenerator(rnd);
            individual.setParameters("" + runs);
            pop = p.getCleanClone();
            pop.createRandom(individual);
            pop.evaluate();
            op.setRandomGenerator(rnd);
//            System.out.println(getName() + " ORIGINAL " + pop.getBest());
        }

        public void run() {
            try {
                for (int i = 0; i < runs; i++) {
                    SimplePopulation off = pop.getCleanClone();
                    off.maximumSize = (int) (pop.getSize() * op.random.nextDouble() * 4) + 1;
                    off.createRandom(individual);
                    off.evaluate();
                    pop = op.execute(pop, off);
                    pop.evaluate();
                }
                individual = pop.getBest();
//                System.out.println(getName() + " BEST " + individual);
            } catch (Exception ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.getMessage());
            }

        }

    }
}
