/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.problem.bits;

import com.evolutionary.problem.Solution;
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
public class OneMaxTest {

    public OneMaxTest() {
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
     * Test of isOptimum method, of class OneMax.
     */
    @Test
    public void testOneMax() {
        System.out.println("OneMax");
        String genes = "11111111";
        OneMax ind = new OneMax();
        ind.setBits(genes);
//        System.out.println(ind.toString());
        assertTrue(ind.isOptimum());
        assertEquals(ind.getSize(), genes.length());
        ind.evaluate();
        assertTrue(ind.isEvaluated());
        assertEquals(ind.getFitness(), genes.length(), 0.001);
//        System.out.println(ind);
        ind.setNumberOfCopies(10);
//        System.out.println(ind);
        ind.randomize();
        assertFalse(ind.isEvaluated());
//        System.out.println(ind);

        ind.evaluate();
        OneMax ind2 = (OneMax) ind.getClone();
//        System.out.println(ind2);
        assertNotSame(ind, ind2);
        assertTrue(ind2.equals(ind));
        assertTrue(ind2.isEvaluated());
        assertEquals(ind2.getFitness(), ind.getFitness(), 0.001);
        assertSame(ind2.solver, ind.solver);
        assertSame(ind2.random, ind.random);
    }

    @Test
    public void testOneMaxThread() {
        int SIZE = 10;
        ThreadOneMax[] thr = new ThreadOneMax[SIZE];
        for (int i = 0; i < thr.length; i++) {
            thr[i] = new ThreadOneMax(SIZE*SIZE, new Random(1234 + i));
            thr[i].start();
        }
        for (int i = 0; i < thr.length; i++) {
            try {
                thr[i].join();                
            } catch (InterruptedException ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       ThreadOneMax[] thr2 = new ThreadOneMax[SIZE];
        for (int i = 0; i < thr2.length; i++) {
            thr2[i] = new ThreadOneMax(SIZE*SIZE, new Random(1234 + i));
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
            
        }

    }

    private class ThreadOneMax extends Thread {

        int runs;
        Solution individual;

        public ThreadOneMax(int runs, Random rnd) {
            this.runs = runs;
            individual = new OneMax();
            individual.setRandomGenerator(rnd);
            individual.setParameters("1" + individual.random.nextInt(99)); // 100-199

        }

        public void run() {
            for (int i = 0; i < runs; i++) {
                individual = individual.getClone();
                individual.setParameters("1" + individual.random.nextInt(99)); // 100-199
                individual.randomize();
                individual.evaluate();
            }
//            System.out.println(getName() + " " + individual);
        }

    }

}
