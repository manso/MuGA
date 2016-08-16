/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.population;

import com.evolutionary.operator.GeneticOperator;
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
public class SimplePopulationTest {

    public SimplePopulationTest() {
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
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testSimplePopulation() {
        int SIZE_POP = 32;
        System.out.println("testSimplePopulation");
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

        SimplePopulation pop = new SimplePopulation();
        assertTrue(pop.isEmpty());
        pop.maximumSize = SIZE_POP;
        pop.createRandom(ind);
        pop.evaluate();
        pop.sort();

//        System.out.println(pop);
        assertEquals(pop.getSize(), SIZE_POP);
        for (int i = 0; i < pop.getSize() - 1; i++) {
            assertNotSame(pop.getIndividual(i), pop.getIndividual(i + 1));
            assertTrue(pop.getIndividual(i).compareTo(pop.getIndividual(i + 1)) >= 0);
        }

        SimplePopulation pop2 = pop.getClone();
        for (int i = 0; i < pop.getSize(); i++) {
            assertNotSame(pop.getIndividual(i), pop2.getIndividual(i));
            assertTrue(pop.getIndividual(i).equals(pop2.getIndividual(i)));
        }
        for (int i = pop.getSize() - 1; i >= 0; i--) {
            assertEquals(pop.getSize(), i + 1);
            Solution ind1 = pop.getIndividual(i);
            Solution ind2 = pop.removeIndividual(i);
            assertSame(ind1, ind2);
            assertEquals(pop.getSize(), i);
        }
        assertTrue(pop.isEmpty());
    }
    
     @Test
    public void testPopulationThread() {
        int SIZE = GeneticOperator.NUM_THREADS_TO_TESTS;
        ThreadPopulation[] thr = new ThreadPopulation[SIZE];
        for (int i = 0; i < thr.length; i++) {
            thr[i] = new ThreadPopulation(SIZE*SIZE, new Random(1234 + i));
            thr[i].start();
        }
        for (int i = 0; i < thr.length; i++) {
            try {
                thr[i].join();                
            } catch (InterruptedException ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       ThreadPopulation[] thr2 = new ThreadPopulation[SIZE];
        for (int i = 0; i < thr2.length; i++) {
            thr2[i] = new ThreadPopulation(SIZE*SIZE, new Random(1234 + i));
            thr2[i].start();
        }
        for (int i = 0; i < thr.length; i++) {
            try {
                thr2[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (int i = 0; i < thr2.length; i++) {
            assertTrue(thr[i].individual.equals(thr2[i].individual));
            for (int j = 0; j < thr2[i].pop.getSize(); j++) {
                assertTrue(thr[i].pop.getIndividual(j).equals(thr2[i].pop.getIndividual(j)));                
            }            
        }

    }

    private class ThreadPopulation extends Thread {

        int runs;
        Solution individual;
        SimplePopulation pop;

        public ThreadPopulation(int runs, Random rnd) {
            this.runs = runs;
            individual = new OneMax();
            individual.setRandomGenerator(rnd);
            individual.setParameters("1" + individual.random.nextInt(99)); // 100-199  
            pop = new SimplePopulation();

        }

        public void run() {
            for (int i = 0; i < runs; i++) {
                individual = individual.getClone();
                individual.setParameters("1" + individual.random.nextInt(99)); // 100-199
                individual.randomize();
                individual.evaluate();
                pop.maximumSize = runs;
                pop.createRandom(individual);
            }
//            System.out.println(getName() + " " + pop.getIndividualsSize());
        }

    }

}
