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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.evolutionary.stopCriteria;

import com.evolutionary.problem.Solution;
import com.evolutionary.solver.EAsolver;

/**
 * Created on 3/out/2015, 18:46:24
 *
 * @author zulu - computer
 */
public class OptimumFound extends StopCriteria {

    public OptimumFound() {
    }

    @Override
    public boolean isDone(EAsolver solver) {

        for (Solution ind1 : solver.parents.getList()) {
            if (ind1.isOptimum()) {
                return true;
            }
        }
        return solver.numEvaluations >= maxEvals;
    }

    public String getInformation() {
        StringBuilder buf = new StringBuilder(getName() + "\n");
        buf.append("Terminates when finding the optimal value or are performed <MAX> evaluations");
        buf.append("\nParameter <MAX> ");
        buf.append("\nParameter <MAX> : max evaluations");
        return buf.toString();
    }
    //----------------------------------------------------------------------------------------------------------
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510031846L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
