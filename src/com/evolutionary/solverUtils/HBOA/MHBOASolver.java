package com.evolutionary.solverUtils.HBOA;

//////////////////////////////////////////////
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.solver.EAsolver;
import static com.evolutionary.solverUtils.HBOA.HBOA.nSuccess;
import static com.evolutionary.solverUtils.HBOA.HBOASolver.N;
import static com.evolutionary.solverUtils.HBOA.HBOASolver.fitCalls;
import static com.evolutionary.solverUtils.HBOA.HBOASolver.selectHB;
import com.evolutionary.solverUtils.HBOA.metric.BDMetric;
import com.evolutionary.stopCriteria.OptimumFound;
import static com.evolutionary.solverUtils.HBOA.HBOASolver.replacementHB;

//
// This class implements the HBOA solver.
//
//////////////////////////////////////////////
public class MHBOASolver extends EAsolver {

    public MBayesianNetwork bayesianNetwork;				// NOTE: Use initializeBayesianNetwork() to initialize

    public MHBOASolver() {
        random.setSeed(654321);						 	// Fixing the seed will also fix the random generator.
        this.problem = new com.evolutionary.problem.bits.OneMax();
        this.problem.setParameters("30");
        this.parents.maximumSize = 2000;

        this.selection = new com.evolutionary.operator.selection.TournamentNoReposition();
        this.selection.setParameters("1 4");
        bayesianNetwork = new MBayesianNetwork(new BDMetric(this.parents.maximumSize), (int) (0.6 * this.parents.maximumSize), 10, (BinaryString) this.problem);
        this.replacement = new com.evolutionary.operator.replacement.RestrictedReplacement();
        this.replacement.setParameters("30 " + this.parents.maximumSize);
        this.stop = new OptimumFound();
        updateSolverAtributes();
    }

    @Override
    public void iterate() {
        SimplePopulation pop = selection.execute(parents);
        bayesianNetwork.generateModel(pop);		 							// 2. GENERATE BAYESIAN NETWORK.			
        pop.maximumSize = (int) (parents.maximumSize * 0.6);
        bayesianNetwork.generateModel(pop);	// 3. SAMPLING.		
        parents = replacement.execute(parents, pop);// 4. REPLACEMENT. Responsible for updating information about the best individual.
        updateEvolutionStats();
    }

    public static void main(String[] args) {

        MHBOASolver solver = new MHBOASolver(); 	// Initialize the HBOA solver and Problem 'problem'.
        for (int r = 0; r < 3; r++) {		 	// Perform 'nRuns' for the same problem.
            solver.solve(true);    				// Solve Problem 'problem'.
        }
    }
}
