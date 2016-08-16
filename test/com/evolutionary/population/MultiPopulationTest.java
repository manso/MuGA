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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class MultiPopulationTest {

    public MultiPopulationTest() {
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
    public void testMultiPopulationCloneList() {
        System.out.println(this.getClass().getSimpleName() + " testMultiPopulationCloneList");
        int SIZE_POP = 60;
        SimplePopulation pop;
        pop = new MultiPopulation();
        pop.setParameters("" + SIZE_POP);
        Solution ind = new OneMax();
        ind.setParameters("100");

        for (int k = 0; k < 100; k++) {
//            System.out.println(" " + k);

            pop.createRandom(ind);

            for (int i = 0; i < SIZE_POP * 2; i++) {
                pop.addIndividual(pop.getRandom());
            }
            pop.evaluate();

            List<Solution> lst = pop.getIndividualtClonesList();
            assert (lst.size() == pop.getNumberOfIndividuals());
            Collections.sort(lst);
            
            
        }

    }
    
     /**
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testMultiPopulationList() {
        System.out.println(this.getClass().getSimpleName() + " testMultiPopulationList");
        int SIZE_POP = 60;
        SimplePopulation pop;
        pop = new MultiPopulation();
        pop.setParameters("" + SIZE_POP);
        Solution ind = new OneMax();
        ind.setParameters("100");

        for (int k = 0; k < 10; k++) {

            pop.createRandom(ind);

            for (int i = 0; i < SIZE_POP * 2; i++) {
                pop.addIndividual(pop.getRandom());
            }
            pop.evaluate();

            List<Solution> lst = pop.getList();

            for (int i = 1; i < lst.size(); i++) {
//                assertTrue(lst.get(i).getNumberOfCopies() == 1);
                int index = pop.indexOf(lst.get(i));
                assertSame(lst.get(i).getGenome(), pop.pop.get(index).getGenome());
            }
        }

    }
    
      /**
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testMultiPopulationArray() {
        System.out.println(this.getClass().getSimpleName() + " testMultiPopulationArray");
        int SIZE_POP = 60;
        SimplePopulation pop;
        pop = new MultiPopulation();
        pop.setParameters("" + SIZE_POP);
        Solution ind = new OneMax();
        ind.setParameters("100");

        for (int k = 0; k < 10; k++) {
//             System.out.println(" " + k);
            pop.createRandom(ind);

            for (int i = 0; i < SIZE_POP * 2; i++) {
                pop.addIndividual(pop.getRandom());
            }
            pop.evaluate();

            Solution[] lst = pop.getIndividualsReferences();
            assertTrue(lst.length == pop.getNumberOfIndividuals());

            for (int i = 1; i < lst.length; i++) {
                assertTrue(lst[i].getNumberOfCopies() == 1);
                int index = pop.indexOf(lst[i]);
                //same genome
                assertSame(lst[i].getGenome(), pop.pop.get(index).getGenome());
                //not the same individual
                assertNotSame(lst[i], pop.pop.get(index));
                //equals
                assertEquals(lst[i], pop.pop.get(index));
            }
        }

    }

    /**
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testMultiPopulation() {
        int SIZE_POP = 10;
        System.out.println("testMultiPopulation");
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

        SimplePopulation pop = new MultiPopulation();
        assertTrue(pop.isEmpty());
        pop.maximumSize = SIZE_POP;
        pop.createRandom(ind);
        pop.evaluate();
        pop.sort();

//        System.out.println(pop);
        assertEquals(pop.getSize(), SIZE_POP);
        for (int i = 0; i < pop.getSize() - 1; i++) {
            assertNotEquals(pop.getIndividual(i), pop.getIndividual(i + 1));
            assertTrue(pop.getIndividual(i).compareTo(pop.getIndividual(i + 1)) >= 0);
            assertTrue(pop.indexOf(pop.getIndividual(i)) == i);
        }

        SimplePopulation pop2 = pop.getClone();
//        System.out.println(pop2);
        for (int i = 0; i < pop.getSize() - 1; i++) {
            assertNotSame(pop.getIndividual(i), pop2.getIndividual(i));
            assertTrue(pop.getIndividual(i).equals(pop2.getIndividual(i)));
            assertNotEquals(pop2.getIndividual(i), pop2.getIndividual(i + 1));
            assertTrue(pop2.getIndividual(i).compareTo(pop2.getIndividual(i + 1)) >= 0);
            assertTrue(pop2.indexOf(pop2.getIndividual(i)) == i);
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

    /**
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testMultiPopulation2() {
        int SIZE_POP = 10;
        System.out.println("testMultiPopulation");
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

        SimplePopulation pop = new MultiPopulation();
        assertTrue(pop.isEmpty());
        pop.maximumSize = SIZE_POP;
        pop.createRandom(ind);
        pop.evaluate();
        pop.sort();

        ind = pop.getRandom();
        for (int i = 1; i < 10; i++) {
            pop.addIndividual(ind);
            assertEquals(pop.getSize(), SIZE_POP);
            assertEquals(pop.getNumberOfIndividuals(), SIZE_POP + i);
            assertEquals(pop.getIndividualsReferences().length, SIZE_POP + i);
        }
        for (int i = 10; i > 1; i--) {
            pop.removeIndividual(ind);
            assertEquals(pop.getSize(), SIZE_POP);
            assertEquals(pop.getNumberOfIndividuals(), SIZE_POP + i - 2);
        }

        pop.removeIndividual(ind);
        assertEquals(pop.getSize(), SIZE_POP - 1);
        assertEquals(pop.getNumberOfIndividuals(), SIZE_POP - 1);
        assertEquals(pop.indexOf(ind), -1);

    }

    /**
     * Test of createRandom method, of class SimplePopulation.
     */
    @Test
    public void testMultiPopulation3() {
        int SIZE_POP = 10;
        System.out.println("testMultiPopulation");
        Solution ind = new OneMax();
        ind.setParameters("" + SIZE_POP);

        SimplePopulation pop = new MultiPopulation();
        assertTrue(pop.isEmpty());
        pop.maximumSize = SIZE_POP;
        pop.createRandom(ind);
        pop.evaluate();
        pop.sort();

        for (int i = 1; i < 1000; i++) {
            ind = pop.getRandom();
            pop.addIndividual(ind);
            assertEquals(pop.getSize(), SIZE_POP);
            assertEquals(pop.getNumberOfIndividuals(), SIZE_POP + i);
        }
//        System.out.println(pop);
        int size = pop.getNumberOfIndividuals();
        for (int i = size - 1; i > 0; i--) {
            ind = pop.removeRandom();
            assertEquals(pop.getNumberOfIndividuals(), i);
            assertEquals(ind.getNumberOfCopies(), 1);
        }

    }

    @Test
    public void testPopulationThread() {
        int SIZE = GeneticOperator.NUM_THREADS_TO_TESTS;
        ThreadPopulation[] thr = new ThreadPopulation[SIZE];
        for (int i = 0; i < thr.length; i++) {
            thr[i] = new ThreadPopulation(SIZE * SIZE, new Random(1234 + i));
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
            thr2[i] = new ThreadPopulation(SIZE * SIZE, new Random(1234 + i));
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
                assertTrue(thr2[i].pop.getIndividual(j).equals(thr2[i].pop.getIndividual(j)));
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
            pop = new UniquePopulation();

        }

        public void run() {
            try {
                for (int i = 0; i < runs; i++) {
                    individual = individual.getClone();
                    individual.setParameters("1" + individual.random.nextInt(99)); // 100-199
                    individual.randomize();
                    individual.evaluate();
                    pop.maximumSize = runs;
                    pop.createRandom(individual);
                }
//                System.out.println(getName() + " " + pop.getIndividualsSize());
            } catch (Exception ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.getMessage());
            }

        }

    }

}
