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
package com.evolutionary.problem.bits.maximeZeros;

import com.evolutionary.problem.BinaryString;
import com.utils.MyMath;
import com.utils.MyString;

/**
 * Created on 31/mar/2016, 12:32:25
 *
 * @author zulu - computer
 */
public class HTrapOne extends AbstractMaximizeZeros {

    static int default_K = 3;
    int K = default_K;

    @Override
    protected double evaluate(boolean[] genome) {
        double fitness = 0;
        // Bogus value. Just to initialize.
        char[] levelString = BinaryString.booleanArrayToString(genome).toCharArray();
        int levelSize = genome.length;
        float levelFit;

        float flow = 1, // Parameters for trap at all levels except the top one.
                fhigh = 1;
        float topFlow = (float) 0.9, // Parameters for topTrap
                topFhigh = 1;
        int sumBits;

        while (levelSize > 3) {							// All levels except the top one.
            levelFit = 0;							// Fitness contribution of each level.
            for (int i = 0; i < levelSize;) {// Empty increment
                //calculate sum of zeros and ones
                sumBits = 0;
                for (int j = 0; j < K; j++) {
                    sumBits += Integer.parseInt(String.valueOf(levelString[i++]));
                }
                if (sumBits == K) {//------------------all ones
                    levelFit += fhigh;						// fhigh = 1.
                    levelString[i / K - 1] = '1';				// '111' -> 1. NOTE: rewriting over the leftmost part of levelString. 
                } else if (sumBits == 0) {//---------- all zeros
                    levelFit += flow;						// flow = 1.
                    levelString[i / K - 1] = '0';				// '000' -> 0. NOTE: rewriting over the leftmost part of levelString.
                } else if (sumBits < K) {//----------- zeros and ones
                    levelFit += flow - ((float) sumBits) * flow / ((float) 2);	// flow - u*flow/(k-1).
                    levelString[i / K - 1] = '8';				// anything else -> 8 (NULL symbol). NOTE: rewriting over the leftmost part of levelString.
                } else if (sumBits > K) { //--------- null present
                    // levelFit += 0						// NULL symbol present.
                    levelString[i / K - 1] = '8';				// anything else -> 8 (NULL symbol). NOTE: rewriting over the leftmost part of levelString.
                }
            }
            levelSize /= K;							 // Next levelSize. Each 3-string is collapsed in to a single symbol.
            fitness += levelFit * genome.length / levelSize; 			// Each level contribution is multiplied by the factor: 3^level = stringSize/(next levelSize). 
        }

        levelFit = 0;								// We are at the top level. No need for mapping. Use topTrap as the contribution function.

        //calculate sum of zeros and ones
        sumBits = 0;
        for (int i = 0; i < K;) {
            for (int j = 0; j < K; j++) {
                sumBits += Integer.parseInt(String.valueOf(levelString[i++]));
            }
        }
        if (sumBits == K) {
            levelFit += topFhigh;						// fhigh = 1.
        } else if (sumBits == 0) {
            levelFit += topFlow;						// flow = .9.
        } else if (sumBits < 3) {
            levelFit += topFlow - ((float) sumBits) * topFlow / ((float) 2);	// flow - u*flow/(k-1).
        }									// else NULL symbol present, do nothing.
        fitness += levelFit * genome.length;					// At the top level the contribution factor is
        return fitness;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(super.getInformation());
        txt.append("\n" + getClass().getSimpleName() + " (Height of Tree , block size)");
        txt.append("\n\nHierarchical Trap one (" + getSize() + " bits)");
        txt.append("\n\n Maping Function:");
        txt.append("\n\t  '1...1' -> '1'  value = 1.0");
        txt.append("\n\t  '0...0' -> '0'  value = 1.0");
        txt.append("\n\t  '?...?' -> '-'  value = 0.0");
        txt.append("\n\tTop level ");
        txt.append("\n\t  '1...1' -> '1'  value = 0.9");
        txt.append("\n\t  '0...0' -> '0'  value = 1");
        return txt.toString();
    }

    /**
     * gets the size of the three Log K
     *
     * @return height of the tree ( power of 3 )
     */
    @Override
    public String getParameters() {
        int height = MyMath.log(getSize(), K);
        if (height < 1) {
            return "1";
        }
        if (height > 14) {
            return "14"; // for safety
        }
        return height + "";
    }

    /**
     * update the size of the genome
     *
     * @param params height of the tree
     */
    @Override
    public void setParameters(String params) {
        String[] p = MyString.splitByWhite(params);
        int level = MyMath.log(getSize(), K);
        try {
            level = (int) Integer.parseInt(p[0]);
            //normalize level [ 1..14 ]
            level = level < 1 ? 1 : level > 14 ? 14 : level;
        } catch (Exception e) {
        }
        try {
            K = Integer.parseInt(p[1]);
            //normalize newK [ 2 ...6 ]
            K = K < 2 ? 2 : K > 6 ? 6 : K;
        } catch (Exception e) {
        }
        super.setParameters(((int) Math.pow(K, level)) + "");
    }

    @Override
    public String toStringPhenotype() {
        boolean[] genome = getBitsGenome();
        StringBuilder hiffStr = new StringBuilder(
                "[" + MyString.insertRegularSpaces(super.toStringGenome(), K) + "]");

        char[] genes = hiffStr.toString().toCharArray();
        int level = genome.length;
        int zeros = 0, ones = 0;
        while (level > 1) {
            StringBuilder strLevel = new StringBuilder();

            for (int i = 0; i < level;) {
                //calculate sum of zeros and ones
                zeros = 0;
                ones = 0;
                for (int j = 0; j < K; j++) {
                    char g = genes[i++];
                    zeros += g == '0' ? 1 : 0;
                    ones += g == '1' ? 1 : 0;
                }
                //replace String
                if (zeros == K) { // 0..0 -> 0   
                    genes[i / K - 1] = '0';
                    strLevel.append('0');
                }
                if (ones == K) { //  1..1 -> 1
                    genes[i / K - 1] = '1';
                    strLevel.append('1');
                } else {
                    genes[i / K - 1] = '-';
                    strLevel.append("-");
                }
            }
            hiffStr.append(" [" + MyString.insertRegularSpaces(strLevel.toString(), K) + "]");
            level /= K;
        }
        return hiffStr.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603311232L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        HTrapOne h = new HTrapOne();
        h.setParameters("4 2");
        h.randomize();
        h.evaluate();
        System.out.println(h.toString());
        System.out.println(h.getInformation());
    }
}
