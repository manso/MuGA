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
public class TrapKbipolarTest {

    /**
     * Test of getInformation method, of class TrapK.
     */
    @Test
    public void testGetInformation() {
        System.out.println("TEST " + this.getClass().getSimpleName());
        BinaryString ind = new TrapKbipolar();
        ind.randomize();
        ind.evaluate();
//        System.out.println(ind.toStringIndividual());
//        System.out.println(ind.toStringPhenotypeIndividual());
//        System.out.println(ind.getParameters());
//        System.out.println(ind.getInformation());

        ind.setParameters("9 6");
        ind.randomize();
        ind.evaluate();
//        System.out.println(ind.toStringIndividual());
//        System.out.println(ind.toStringPhenotypeIndividual());
//        System.out.println(ind.getParameters());
//        System.out.println(ind.getInformation());
//        
        ind.setParameters("6 6");

        ind.setBits("000000");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 3);
        ind.setBits("111111");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 3);
        ind.setBits("100000");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 0);
        ind.setBits("111110");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 0);
        ind.setBits("101110");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 1);
         ind.setBits("100001");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 1);
        ind.setBits("101010");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 2);
         ind.setBits("101001");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 2);
//        ind.setBits("001");
//        ind.evaluate();
//        assertTrue(ind.getFitness() == 0);
//        ind.setBits("011");
//        ind.evaluate();
//        assertTrue(ind.getFitness() == 1);
//        ind.setBits("111");
//        ind.evaluate();
//        assertTrue(ind.getFitness() == 2);
//        
//        ind.setBits("000 001 110 111 ");
//        ind.evaluate();
//        assertTrue(ind.getFitness() == 6);
    }

}
