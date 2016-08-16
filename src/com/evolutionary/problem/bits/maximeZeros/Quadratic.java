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

import com.utils.MyString;

/**
 * Created on 31/mar/2016, 12:13:06
 *
 * @author zulu - computer
 */
public class Quadratic extends AbstractMaximizeZeros {

    private int BLOCK = 2; // size of the block

    @Override
    protected double evaluate(boolean[] genome) {
        double fit = 0;
        int ones;
        for (int i = 0; i < genome.length;i+=BLOCK) { 
            ones= unitation(i, i+BLOCK); //unitation of block
            if (ones == BLOCK) { // all ones
                fit += 0.9;
            } else if (ones == 0){ // all zeros
                fit += 1.0;
            }
        }
        return fit;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(super.getInformation());
        txt.append("\n"+getClass().getSimpleName() +" (genome size , block size)\n");
  
        txt.append("\nQuadratic Function - Sum block of bits");
        txt.append("\n '1..1' = 0.9  - all ones");
        txt.append("\n '0..0' = 1.0  - all zeros");
        txt.append("\n '?..?' = 0.0  - mix of ones and zeros");
        return txt.toString();

    }

    @Override
    public String toStringPhenotype() {
        StringBuffer txt = new StringBuffer();
        boolean[] genome = getBitsGenome();
        for (int i = 0; i < genome.length; i++) {
            if (i > 0 && i % BLOCK == 0) {
                txt.append(" ");
            }
            txt.append(genome[i] ? "1" : "0");
        }
        return txt.toString();
    }

    /**
     * update the size of the genome
     *
     * @param params size of the genome
     */
    @Override
    public void setParameters(String params) {
        try {
            String[] p = MyString.splitByWhite(params);
            //update key
            if (p.length > 1) {
                BLOCK = Integer.parseInt(p[1]);
            }
            BLOCK = BLOCK < 2 ? 2 : BLOCK;
            //update size
            int size = Integer.parseInt(p[0]);
            //normalize size
            if (size < BLOCK) {
                size = BLOCK;
            } else if (size % BLOCK != 0) {
                size += BLOCK - size % BLOCK;
            }
            genome = new boolean[size];
            setNotEvaluated();
        } catch (Exception e) {
            //something wrong happen
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + BLOCK;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603311213L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////   
}
