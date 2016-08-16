/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.operator.mutation;


import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.population.UniquePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import com.evolutionary.problem.bits.OneMaxTest;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class FlipBitTest {

    public FlipBitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getParameters method, of class NPointCrossover.
     */
    @Test
    public void MutationTest() {
        System.out.println("Test" + this.getClass().getSimpleName());
        int SIZE_POP = 20;
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

        SimplePopulation pop = new MultiPopulation();
        pop.maximumSize =SIZE_POP;
        pop.createRandom(ind);
        pop.evaluate();

        Mutation x = new FlipBit();
        x.pMutation = 1;
        x.setRandomGenerator(ind.random);
        //count ones
        int ones = 0;
        for (Solution i : pop.getIndividualsReferences()) {
            ones += OneMax.ones(((BinaryString) i).getBitsGenome());
        }
        int numind = pop.getNumberOfIndividuals();
        SimplePopulation pop2 = x.execute(pop);

        //count ones
        int zeros = 0;
        for (Solution i : pop2.getIndividualsReferences()) {
            zeros += i.getSize() - OneMax.ones(((BinaryString) i).getBitsGenome());
        }
        assertEquals(ones, zeros);
        assertEquals(numind, pop2.getNumberOfIndividuals());

    }

    @Test
    public void TestThread() {
        TestThread(new SimplePopulation());
        TestThread(new UniquePopulation());
        TestThread(new MultiPopulation());
    }

    public void TestThread(SimplePopulation p) {
        try {
            int SIZE = GeneticOperator.NUM_THREADS_TO_TESTS;
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
        GeneticOperator op = new FlipBit();

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
                    pop = op.execute(pop);
                    pop.evaluate();
                }
                individual = pop.getBest();                
            } catch (Exception ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.getMessage());
            }

        }

    }
}
