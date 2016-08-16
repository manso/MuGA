/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.problem.bits.maximeZeros;

import com.evolutionary.problem.BinaryString;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class QuadraticTest {
    
    public QuadraticTest() {
    }

   
    /**
     * Test of getInformation method, of class TrapK.
     */
    @Test
    public void testGetInformation() {
        System.out.println("TrapKTest");
        BinaryString ind = new Quadratic();
        ind.evaluate();
        ind.setParameters("4 2");
        ind.setBits("00 00");
        ind.evaluate();
        assertTrue(ind.getFitness() == 2);
        ind.setBits("00 01");
        ind.evaluate();
        assertTrue(ind.getFitness() == 1);
        ind.setBits("10 01");
        ind.evaluate();
        assertTrue(ind.getFitness() == 0);
        // ind.randomize();
//        ind.evaluate();
//        System.out.println(ind.toStringIndividual());
//        System.out.println(ind.toStringPhenotypeIndividual());
//        System.out.println(ind.getParameters());
//        System.out.println(ind.getInformation());

    }
}
