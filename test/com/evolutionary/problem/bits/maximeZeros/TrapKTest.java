/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.problem.bits.maximeZeros;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class TrapKTest {

    public TrapKTest() {
    }

    /**
     * Test of getInformation method, of class TrapK.
     */
    @Test
    public void testGetInformation() {
        System.out.println("TrapKTest");
        TrapK ind = new TrapK();
        ind.evaluate();
        ind.setParameters("9 3");
        ind.setBits("000");
        ind.evaluate();
        assertTrue(ind.getFitness() == 3);
        ind.setBits("001");
        ind.evaluate();
        assertTrue(ind.getFitness() == 0);
        ind.setBits("011");
        ind.evaluate();
        assertTrue(ind.getFitness() == 1);
        ind.setBits("111");
        ind.evaluate();
        assertTrue(ind.getFitness() == 2);
        
        ind.setBits("000 001 110 111 ");
        ind.evaluate();
        assertTrue(ind.getFitness() == 6);
       // ind.randomize();
//        ind.evaluate();
//        System.out.println(ind.toStringIndividual());
//        System.out.println(ind.toStringPhenotypeIndividual());
//        System.out.println(ind.getParameters());
//        System.out.println(ind.getInformation());

    }

}
