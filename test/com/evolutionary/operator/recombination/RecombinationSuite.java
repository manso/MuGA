/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary.operator.recombination;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author zulu
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    NPointCrossoverTest.class, 
    M_CrossoverTest.class, 
    UniformCrossoverTest.class,    
    M_UniformeTest.class
})
public class RecombinationSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
   
}
