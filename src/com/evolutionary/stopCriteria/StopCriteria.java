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

import com.evolutionary.Genetic;
import com.evolutionary.solver.EAsolver;

/**
 * Created on 3/out/2015, 18:43:46
 *
 * @author zulu - computer
 */
public abstract class StopCriteria extends Genetic{

    int maxEvals = (int) 1E5;

    public abstract boolean isDone(EAsolver solver);
    
      /**
     * updats parameters
     *
     * @param params maximum number of evaluations
     * @throws RuntimeException
     */
    @Override
    public void setParameters(String params) throws RuntimeException {
        try {
            maxEvals = Integer.parseInt(params.trim());
            //validate max evaluations
            if (maxEvals < 1) {
                throw new RuntimeException("invalid number of evaluations : " + maxEvals);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * get parameters
     *
     * @return maximum number of evaluations
     */
    public String getParameters() {
        return maxEvals + "";
    }
   
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510031843L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
