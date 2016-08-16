/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MUGA.problem;

import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zulu
 */
public class IndividualTest {



    /**
     * Test of getClone method, of class Solution.
     */
    @Test
    public void testGetClone() {
        System.out.println("getClone");
        Solution ind = new OneMax();
        ind.setParameters("1000");
        Solution clone = ind.getClone();
        assertNotSame(clone.getGenome(), ind.getGenome());
    }

    
   
    
}
