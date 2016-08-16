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
public class DecimationTest {
    
     GeneticOperator templateOperator = new Decimation();

    @Test
    public void TestThread() {
        System.out.println(this.getClass().getSimpleName() + " Threads");
        int SIZE_POP = 60;
        SimplePopulation pop;
        //------------------------------------------ test SIMPLE POPULATION
        pop = new SimplePopulation();
        pop.setParameters("" + SIZE_POP);
        Solution ind = new OneMax();
        ind.setParameters("100");
        pop.createRandom(ind);
        pop.evaluate();
        TestThread(pop);
        //------------------------------------------ test UNIQUE POPULATION
        pop = new UniquePopulation();
        pop.setParameters("" + SIZE_POP);
        pop.createRandom(ind);
        pop.evaluate();
        TestThread(pop);
        //------------------------------------------ test MULTISET POPULATION
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
            //number of threads
            int SIZE = GeneticOperator.NUM_THREADS_TO_TESTS;
            //---------------------------------------------------- FIRST SIMULATION 
            ThreadPopulation[] thr = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr.length; i++) {
                Random rnd = new Random(1234 + i); // set new random
                SimplePopulation pop = p.getClone(); // clone population
                pop.setRandomGenerator(rnd);
                thr[i] = new ThreadPopulation(SIZE, pop); // create and start thread
                thr[i].start();
            }
            //------------------------------------------ synchronization point
            for (int i = 0; i < thr.length; i++) {
                thr[i].join();
            }
            //---------------------------------------------------- SECOND SIMULATION 
            ThreadPopulation[] thr2 = new ThreadPopulation[SIZE];
            for (int i = 0; i < thr2.length; i++) {
                Random rnd = new Random(1234 + i); // same random number
                SimplePopulation pop = p.getClone(); // same population
                pop.setRandomGenerator(rnd);
                thr2[i] = new ThreadPopulation(SIZE, pop);// create and start thread
                thr2[i].start();
            }
            //------------------------------------------ synchronization point
            for (int i = 0; i < thr.length; i++) {
                thr2[i].join();
            }
            //------------------------------------------ TEST
            for (int i = 0; i < thr2.length; i++) {
                //individual calculated is equal
                assertTrue(thr[i].individual.equals(thr2[i].individual));
                //all individualas in the population are equal
                for (int j = 0; j < thr2[i].pop.getSize(); j++) {
                    assertTrue(thr[i].pop.getIndividual(j).equals(thr2[i].pop.getIndividual(j)));
                    //are not the same
                    assertNotSame(thr[i].pop.getIndividual(j), thr2[i].pop.getIndividual(j));
                    //compare distance
                    assertTrue(thr[i].pop.getIndividual(j).distance(thr2[i].pop.getIndividual(j)) == 0);
                    //number of copies 
                    assertTrue(thr[i].pop.getIndividual(j).getNumberOfCopies() >= 1);
                    assertTrue(thr2[i].pop.getIndividual(j).getNumberOfCopies() >= 1);
                    //random numbers
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
        GeneticOperator op;

        public ThreadPopulation(int runs, SimplePopulation p) {
            this.runs = runs;
            pop = p;
            op = templateOperator.getClone();
            op.setRandomGenerator(p.getRandomGenerator());
        }

        public void run() {
            try {
                individual = pop.getBest();
                int ind = pop.getSize();
                op.setParameters("0.5");
                for (int i = 0; i < runs; i++) {
                    // restart parents
                    if( i % 4 ==0 ){
                        pop.createRandom(individual);
                        pop.evaluate();
                    }
                    
                    //create offSpring
                    SimplePopulation pop1 = pop.getCleanClone();
                    pop1.createRandom(individual); 
                    pop1.evaluate();
                    //insert clones
                    for (int j = 0; j < pop1.maximumSize*2; j++) {
                        //remove random                        
                        pop1.removeRandom();
                        //add random
                        pop1.addIndividual(pop.getRandom().getClone());
                        
                    }
                    
                    //Replace
                    pop = op.execute(pop,pop1);
                    assertTrue(ind == pop.getSize());
                    individual = pop.getBest();
                }

            } catch (Exception ex) {
                Logger.getLogger(OneMaxTest.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.getMessage());
            }

        }

    }
    
}
