/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.solverUtils;

import com.evolutionary.solver.EAsolver;
import com.evolutionary.solver.MuGA;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class EAsolverArrayTest extends TestCase {

    public EAsolverArrayTest(String testName) {
        super(testName);
    }

    /**
     * Test of createArrayOfSolvers method, of class EAsolverArray.
     */
    public void testCreateArrayOfSolvers() {
        EAsolver s = new MuGA();
        s.numberOfRun = 100;
        
       EAsolverArray sim = new EAsolverArray(s);
       
       sim.solve(false);

        

    }

}
