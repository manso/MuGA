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
public class TrapKoverlap extends AbstractMaximizeZeros {

    private int K = 3; // size of trap
    private int OVERLAP = 2; // size of trap

    @Override
    protected double evaluate(boolean[] genome) {
        float fit = 0;
        int ones;
        //build block
        for (int i = 0; i < genome.length - OVERLAP; i += K - OVERLAP) {
            //unitation of the block
            ones = unitation(i, i + K);
            if (ones == 0) {
                fit += K;
            } else {
                fit += ones - 1;
            }
        }
        return fit;
    }

    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder(super.getInformation());
        txt.append("\n" + getClass().getSimpleName() + " (genome size , trap size , overlapped bits)");

        txt.append("\n\nZero Trap function with overlapped bits\n");
        txt.append(K + " 0 ... " + (K - 1));
        return txt.toString();

    }

    @Override
    public String toStringPhenotype() {
        StringBuffer txt = new StringBuffer();
        boolean[] genome = getBitsGenome();
       //build block
        for (int i = 0; i < genome.length - OVERLAP; i += K - OVERLAP) {
            // block
            for (int j = i; j < i+K; j++) {
                txt.append(genome[j] ? "1" : "0");                
            }
            txt.append(" ");
        }
        return txt.toString().trim();
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
            try {
                K = Integer.parseInt(p[1]);
                //normalize K
                K = K < 3 ? 3 : K;
            } catch (Exception e) {
            }
            try {
                OVERLAP = Integer.parseInt(p[2]);
                //Normalize OVERLAP
                OVERLAP = OVERLAP > 0 ? OVERLAP >= K ? K - 1 : OVERLAP : 1;
            } catch (Exception e) {
            }

            //update size
            int size = Integer.parseInt(p[0]);
            //normalize size
            if (size < K) {
                size = K;
            } else if (K > 0) {
                int nextSize = K;
                //until complete the genome is covered
                while (nextSize < size) {
                    nextSize += K - OVERLAP;
                }
                size = nextSize;
            }
            genome = new boolean[size];
            setNotEvaluated();
        } catch (Exception e) {
            //something wrong happen
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + K + " " + OVERLAP;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603311213L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////   
}
