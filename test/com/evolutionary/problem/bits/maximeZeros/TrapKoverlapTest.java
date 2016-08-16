/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.problem.bits.maximeZeros;

import com.evolutionary.problem.BinaryString;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author zulu
 */
public class TrapKoverlapTest {

    /**
     * Test of getInformation method, of class TrapK.
     */
    @Test
    public void testClass() {
        System.out.println("TEST " + this.getClass().getSimpleName());
        BinaryString ind = new TrapKoverlap();
        ind.evaluate();
        ind.setParameters("5 3 1");
        ind.randomize();
//        System.out.println(ind.toStringIndividual());
//        System.out.println(ind.toStringPhenotypeIndividual());
//        System.out.println(ind.getParameters());
//        System.out.println(ind.getInformation());
        ind.setBits("000");
        ind.evaluate();
        assertTrue(ind.getFitness() == 3);
        ind.setBits("00001");
        ind.evaluate();
        assertTrue(ind.getFitness() == 3);
        ind.setBits("00011");
        ind.evaluate();
        assertTrue(ind.getFitness() == 4);
        ind.setBits("00111");
        ind.evaluate();
        assertTrue(ind.getFitness() == 2);
        ind.setBits("01111");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 3);
        ind.setBits("11111");
        ind.evaluate();
//        System.out.println(ind.toStringPhenotypeIndividual());
        assertTrue(ind.getFitness() == 4);

        // ind.randomize();
//        ind.evaluate();
    }

}
