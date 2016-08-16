/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.problem.bits;

import com.evolutionary.problem.Solution;
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
public class OneMinTest {

    public OneMinTest() {
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
        System.out.println("OneMin");
        String genes = "00000000";
        OneMin ind = new OneMin();
        ind.setBits(genes);
//        System.out.println(ind.toString());
        assertTrue(ind.isOptimum());
        assertEquals(ind.getSize(), genes.length());
        ind.evaluate();
        assertTrue(ind.isEvaluated());
//        System.out.println("ind " + ind);
        assertEquals(ind.getFitness(), 0, 0.001);
//        System.out.println(ind);
        ind.setNumberOfCopies(10);
//        System.out.println(ind);
        ind.randomize();
        assertFalse(ind.isEvaluated());
//        System.out.println(ind);

        ind.evaluate();
        Solution ind2 = ind.getClone();
//        System.out.println(ind2);
        assertNotSame(ind, ind2);
        assertTrue(ind2.equals(ind));
        assertTrue(ind2.isEvaluated());
        assertEquals(ind2.getFitness(), ind.getFitness(), 0.001);
        assertSame(ind2.solver, ind.solver);
        assertSame(ind2.random, ind.random);
    }

}
