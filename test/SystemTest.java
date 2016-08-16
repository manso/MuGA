/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author zulu
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
            com.evolutionary.problem.ProblemSuite.class, 
            com.evolutionary.population.PopulationSuite.class, 
            com.evolutionary.operator.selection.SelectionSuite.class, 
            com.evolutionary.operator.recombination.RecombinationSuite.class, 
            com.evolutionary.operator.mutation.MutationSuite.class, 
            com.evolutionary.operator.replacement.ReplacementSuite.class, 
            com.evolutionary.solver.SolverSuite.class, 
        })
public class SystemTest {
    
     public static int THREADS = 10;

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
