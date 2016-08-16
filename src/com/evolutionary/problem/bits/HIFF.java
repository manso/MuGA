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
import com.utils.MyMath;

/*
     * The Hierarchical-if-and-only-if (H-IFF)
     *
     * defined in: Modeling Building-Block Interdependency (1998) by Richard Watson
     * , Gregory S. Hornby , Jordan B. Pollack Parallel Problem Solving from Nature
     * - PPSN V
     * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.48.6392&rep=rep1&type=pdf
     *
     *
     * Solved By: algoritmo - A RECOMBINATIVE HILL-CLIMBER Richard A. Watson -
     * Analysis of recombinative algorithms on a non-separable building-block
     * problem 2000
     *
     * Escaping Hierarchical Traps with Competent Genetic Algorithms bDavid E.
     * Goldberg , David E. Goldberg 2001
     *
     * Natural Intelligence for Scheduling, Planning and Packing Problems Studies in
     * Computational Intelligence Volume 250, 2009, pp 111-143 Solving
     * Hierarchically Decomposable Problems with the Evolutionary Transition
     * Algorithm
     *
 */
/**
 * Created on 29/set/2015, 9:30:30
 *
 * @author zulu - computer
 */
public class HIFF extends BinaryString {

    public HIFF() {
        //default size = 64 bits
        super(6, Optimization.MAXIMIZE);
    }

    /**
     * update the size of the genome
     *
     * @param params height of the tree ( power of 2 )
     */
    @Override
    public void setParameters(String params) {
        try {
            int size = Integer.parseInt(params);
            super.setParameters(MyMath.pow2(size) + "");
        } catch (Exception e) {
        }
    }

    @Override
    public String getParameters() {
        return MyMath.log2(getSize()) + "";
    }

    @Override
    public boolean isOptimum() {
        int ones = unitation(0, getSize());
        return ones == getSize() || ones == 0;
    }

    @Override
    protected double evaluate(boolean[] genome) {
//        double fitness = genome.length;
//        char a, b;
//        char[] genes = toStringGenome().toCharArray();
//        int level = genome.length;
//        float levelFit;
//        int power = 1;
//
//        while (level > 1) {
//            levelFit = 0;
//            for (int i = 0; i < level;) {
//                a = genes[i++];
//                b = genes[i++];
//                if (a == b) { // 00 -> 0   && 11 -> 1
//                    levelFit += 1;
//                    genes[i / 2 - 1] = a;
//                } else {// 10 -> -   && 01 -> -
//                    genes[i / 2 - 1] = '-';
//                }
//            }           
//            fitness += levelFit * Math.pow(2, power);//levelFit * genome.length / level;
//            power++;
//            level /= 2;
//
//        }
//        return fitness;
        return genome.length + HIFF_Value(BinaryString.booleanArrayToString(genome), 1);

    }

    public static double HIFF_Value(String bits, int level) {
        //top of the tree
        if (bits.length() == 1) {
            if (bits.charAt(0) == '-') {
                return 0;
            }
            return Math.pow(2, level);
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / 2);
        for (int i = 0; i < bits.length(); i += 2) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '0') {
                newLevel.append("0");
                sum += Math.pow(2, level);
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '1') {
                newLevel.append("1");
                sum += Math.pow(2, level);
            } else //error
            {
                newLevel.append("-");
            }
        }
        return sum + HIFF_Value(newLevel.toString(), level + 1);
    }

    @Override
    public String getInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder(super.getInformation());
        buf.append("\n\nHierarchical-if-and-only-if (HIFF) ");
        buf.append("\nLevel :" + MyMath.log2(getSize()));
        buf.append("\n\nParameters <LEVEL>");
        buf.append("\n   <LEVEL>  Height of Tree ( bits = 2^LEVEL) ");

        buf.append("\n\n Maping Function:");
        buf.append("\n\t  00 -> 0");
        buf.append("\n\t  11 -> 1");
        buf.append("\n\t  10 -> -");
        buf.append("\n\t  01 -> -");

        buf.append("\n\n Modeling Building-Block Interdependency (1998) ");
        buf.append("\n Richard Watson, Gregory S. Hornby , Jordan B. Pollack ");
        return buf.toString();

    }

    @Override
    public String toStringPhenotype() {
        boolean[] genome = getBitsGenome();
        StringBuilder hiffStr = new StringBuilder(super.toStringGenome());
        char[] genes = hiffStr.toString().toCharArray();
        char a, b;
        int level = genome.length;
        while (level > 1) {
            hiffStr.append(" ");

            for (int i = 0; i < level;) {
                a = genes[i++];
                b = genes[i++];
                if (a == b) { // 00 -> 0   && 11 -> 1
                    genes[i / 2 - 1] = a;
                    hiffStr.append(a);
                } else {// 10 -> -   && 01 -> -
                    genes[i / 2 - 1] = '-';
                    hiffStr.append("-");
                }
            }
            level /= 2;
        }
        //remove genome bits
        return hiffStr.toString().substring(hiffStr.indexOf(" "));
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290930L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
