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
public class HTrapOneTest {
    
    public HTrapOneTest() {
    }

    /**
     * Test of getInformation method, of class HTrapOne.
     */
    @Test
    public void testGetInformation() {
        System.out.println("getInformation");
        HTrapOne instance = new HTrapOne();
        String expResult = "";
        String result = instance.getInformation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParameters method, of class HTrapOne.
     */
    @Test
    public void testGetParameters() {
        System.out.println("getParameters");
        HTrapOne instance = new HTrapOne();
        String expResult = "";
        String result = instance.getParameters();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setParameters method, of class HTrapOne.
     */
    @Test
    public void testSetParameters() {
        System.out.println("setParameters");
        String params = "";
        HTrapOne instance = new HTrapOne();
        instance.setParameters(params);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toStringPhenotype method, of class HTrapOne.
     */
    @Test
    public void testToStringPhenotype() {
        System.out.println("toStringPhenotype");
        HTrapOne instance = new HTrapOne();
        String expResult = "";
        String result = instance.toStringPhenotype();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class HTrapOne.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        HTrapOne.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
