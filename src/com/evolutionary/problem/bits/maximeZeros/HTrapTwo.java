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

/**
 * Created on 31/mar/2016, 12:32:25
 *
 * @author zulu - computer
 */
public class HTrapTwo extends HTrapOne {
  
    @Override
    protected double evaluate(boolean[] genome) {
        double fitness = 0;
        // Bogus value. Just to initialize.
        char[] levelString = BinaryString.booleanArrayToString(genome).toCharArray();
        int levelSize = genome.length;
        int nLevels = (int) (Math.log(levelSize) / Math.log(K));
        float levelFit;

        float  fhigh  = (float) 1 + (float) 0.1 / (float) nLevels, // fitness to '111' at all levels except the top one.
                flow = 1; // fitness to '111' at all levels except the top one.
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
        txt.append("\n\nHierarchical Trap two (" + getSize() + " bits)");
        txt.append("\n\n Maping Function:");
        txt.append("\n\t  '1...1' -> '1'  value = 1.0 + 0.1 * Height of Tree");
        txt.append("\n\t  '0...0' -> '0'  value = 1.0");
        txt.append("\n\t  '?...?' -> '-'  value = 0.0");
        txt.append("\n\tTop level ");
        txt.append("\n\t  '1...1' -> '1'  value = 0.9");
        txt.append("\n\t  '0...0' -> '0'  value = 1");
        txt.append("\n\nEscaping Hierarchical Traps with Competent Genetic Algorithms");
         txt.append("\nMartin Pelikan,David. E. Goldberg :  http://medal-lab.org/hboa/hboa.pdf ");
        return txt.toString();
    }

   
   
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603311232L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        HTrapTwo h = new HTrapTwo();
        h.setParameters("4 2");
        h.randomize();
        h.evaluate();
        System.out.println(h.toString());
        System.out.println(h.getInformation());
    }
}
