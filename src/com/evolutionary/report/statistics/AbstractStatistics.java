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
package com.evolutionary.report.statistics;

import com.evolutionary.Genetic;
import com.evolutionary.solver.EAsolver;
import com.utils.MyNumber;

/**
 * Created on 19/jan/2016, 12:07:58
 *
 * @author zulu - computer
 */
public abstract class AbstractStatistics extends Genetic {

    String title;
    public boolean higherIsBetter = true;

    public AbstractStatistics(String title) {
        this.title = title;
    }
    
    public void setSolver(EAsolver s){
        //to update higherIsBetter in statistics that depends  of optimization type (maximize or minimize)
    }

    /**
     * calculate the statistic
     *
     * @param s solver
     * @return value
     */
    public abstract double execute(EAsolver s);

    /**
     * title of the statistic
     *
     * @return title
     */
    public String title() {
        return title;
    }

    /**
     * textual representation of statistic
     *
     * @param s solver
     * @return textual value
     */
    public String getText(EAsolver s) {
        return MyNumber.numberToString(execute(s), title.length());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    public AbstractStatistics getClone(){
        return (AbstractStatistics) Genetic.getClone(this);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201601191207L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
