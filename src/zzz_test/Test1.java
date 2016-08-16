//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////

package zzz_test;

import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.solver.EAsolver;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 26/mar/2016, 17:24:40 
 * @author zulu - computer
 */
public class Test1 {
    
    private class ThreadSolver extends Thread {

        Solution individual;
        SimplePopulation pop;
        EAsolver solver;

        public ThreadSolver(EAsolver solver) {
            this.solver = solver;
        }

        @Override
        public void run() {
            solver.solve(false);
            pop = solver.parents;
//            System.out.println(solver.hallOfFame);
            individual = solver.parents.getBest();
            individual = solver.hallOfFame.iterator().next();

            System.out.println(solver.numEvaluations + " " + getName() + " " + individual);
        }

    }
    
    
    public static void main(String[] args) {
        
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603261724L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}