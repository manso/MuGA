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
public class TrapKbipolar extends AbstractMaximizeZeros {

    private int K = 4; // size of trap

    @Override
    protected double evaluate(boolean[] genome) {
        float fit = 0;
        int ones;
        for (int i = 0; i < genome.length; i += K) { //Empty increment
            //unitation
            ones = unitation(i, i + K);
            //ones = Math.abs(ones - K / 2);
            if (ones == 0 || ones == K) {
                fit += K / 2;
            } else {
                fit += K / 2 - Math.abs(ones - K / 2) -1;
            }
        }
        return fit;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(super.getInformation());
        txt.append("\n" + getClass().getSimpleName() + " (genome size , trap size)");

        txt.append("\n\nZero Trap Bipolar function\n");
        txt.append(K / 2 + " 0 ... " + (K / 2 - 1) + "...0 " + K / 2);
        return txt.toString();

    }

    @Override
    public String toStringPhenotype() {
        StringBuffer txt = new StringBuffer();
        boolean[] genome = getBitsGenome();
        for (int i = 0; i < genome.length; i++) {
            if (i > 0 && i % K == 0) {
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
                K = Integer.parseInt(p[1]);
            }
            K = K < 4 ? 4 : K + K % 2; // make K pair
            //update size
            int size = Integer.parseInt(p[0]);
            //normalize size
            if (size < K) {
                size = K;
            } else if (size % K != 0) {
                size += K - size % K;
            }
            genome = new boolean[size];
            setNotEvaluated();
        } catch (Exception e) {
            //something wrong happen
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + K;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603311213L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////   
}
