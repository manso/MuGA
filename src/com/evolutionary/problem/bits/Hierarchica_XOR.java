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

import com.utils.MyMath;

/**
 * Created on 29/set/2015, 9:30:30
 *
 * @author zulu - computer
 */
public class Hierarchica_XOR extends HIFF {

    @Override
    public String getInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName() + "<" + getSize() + "> : ");
        buf.append(getOptimizationType() == Optimization.MAXIMIZE ? "MAXIMIZE" : "MINIMIZE");
        buf.append("\n\nHierarchical-if-and-only-if (HXOR) ");
        buf.append("\nLevel :" + MyMath.log2(getSize()));
        buf.append("\n\nParameters <LEVEL>");
        buf.append("\n   <LEVEL>  Height of Tree ( bits = 2^LEVEL) ");

        buf.append("\n\n Maping Function:");
        buf.append("\n\t  10 -> 1");
        buf.append("\n\t  01 -> 0");
        buf.append("\n\t  11 -> -");
        buf.append("\n\t  00 -> -");

        buf.append("\n\n Modeling Building-Block Interdependency (1998) ");
        buf.append("\n Richard Watson, Gregory S. Hornby , Jordan B. Pollack ");
        return buf.toString();

    }

    @Override
    public double evaluate(boolean[] genome) {
        float fitness = 0;
        char a, b;
        char[] levelString = toStringGenome().toCharArray();
        int levelSize = genome.length;
        float levelFit;

        while (levelSize > 1) {
            levelFit = 0;
            for (int i = 0; i < levelSize;) {
                a = levelString[i++];
                b = levelString[i++];
                if (a == '1' && b == '0') { // 10 -> 1
                    levelFit += 1;
                    levelString[i / 2 - 1] = '1';
                } else if (a == '0' && b == '1') { // 01 -> 0
                    levelFit += 1;
                    levelString[i / 2 - 1] = '0'; // 00 -> -   &&  11 -> -
                } else {
                    levelString[i / 2 - 1] = '-';
                }
            }
            levelSize /= 2;
            fitness += levelFit * genome.length / levelSize;
        }
        return fitness;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290930L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
