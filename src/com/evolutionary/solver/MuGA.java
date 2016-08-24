package com.evolutionary.solver;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.bits.OneMin;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuGA extends EAsolver {

    public static void main(String[] args) {
        try {
            EAsolver solver = new MuGA();
            solver.problem = new OneMin();
            solver.problem.setParameters("200");
            solver.solve(true);
        } catch (Exception ex) {
            Logger.getLogger(MuGA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  

    @Override
    public synchronized void iterate() {
        SimplePopulation offspring = selection.execute(parents);
        offspring = recombination.execute(offspring);
        offspring = mutation.execute(offspring);
        offspring.evaluate();
        parents = replacement.execute(parents, offspring);
        parents = rescaling.execute(parents);
        updateEvolutionStats();
    }
    
     public String getInformation() {
        return "MuGA";
    }
}
