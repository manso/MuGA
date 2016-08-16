//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manso  &  Luis Correia                                      ::
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
package com.evolutionary.problem.bits;

import com.evolutionary.problem.BinaryString;

/**
 * Created on 29/set/2015, 9:30:30
 *
 * @author zulu - computer
 */
public class OneMax extends BinaryString {

    public OneMax() {
        super(Optimization.MAXIMIZE);
    }

    @Override
    public boolean isOptimum() {
        return ones(getBitsGenome()) == getSize();
    }

    @Override
    protected double evaluate(boolean[] genome) {        
        return (double) ones(genome);
    }
    
    public static int ones(boolean[] genome){
         int ones = 0;
        for (boolean gene : genome) {
            if (gene == true) {
                ones++;
            }
        }
        return  ones;
    }

    @Override
    public String getInformation() {
        // public String getInfo() {
        StringBuilder txt = new StringBuilder(super.getInformation());
        txt.append("\n");
        txt.append("\nParameters <SIZE>");
        txt.append("\n           <SIZE>  size of genome");
        txt.append("\n\nCount the number of ones in the genome");
        return txt.toString();

    }
    
   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290930L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
