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
package com.evolutionary.operator.mutation;

import com.evolutionary.Genetic;
import com.evolutionary.population.MultiPopulation;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import com.evolutionary.problem.bits.OneMax;
import com.utils.MyString;
import java.util.Locale;

/**
 * Created on 4/abr/2016, 0:12:12
 *
 * @author zulu - computer
 */
public class M_WaveMutation extends FlipBit {

    public double exponent = 3;
    double wave = 1;

    @Override
    public SimplePopulation execute(SimplePopulation offspring) {
        SimplePopulation mutants = offspring.getCleanClone();
        //for all the offsping individuals
        for (Solution ind : offspring.getList()) {
            Solution[] muts = doMutation(ind);
            for (Solution mut : muts) {
                mutants.addIndividual(mut);
            }
        }
        return mutants;
    }

    /**
     * mutate one individual ind must contains the copy number in .numOfCopys
     *
     * @param ind individual mutateded with the copy number
     */
    public Solution[] doMutation(Solution ind) {
        //------------------------------------------
        Solution[] mutant = new Solution[ind.getNumberOfCopies()];
        //-------------------------------------------
        for (int i = 0; i < mutant.length; i++) {
            //--------------------------------------------------
            mutant[i] = ind.getClone();
            mutant[i].setNumberOfCopies(1);
            boolean bits[] = ((BinaryString) mutant[i]).getBitsGenome();
            //--------------------------------------------------
            //include minimal probability
            double prob = getProbability(bits.length) + waveFunctionSin(i + 1, wave, exponent);
            //for all bits
            for (int j = 0; j < bits.length; j++) {
                //verify probability
                if (random.nextDouble() < prob) {
                    bits[j] = !bits[j];
                    mutant[i].setNotEvaluated();
                }
            }
        }
        return mutant;
    }

    private static double waveFunctionSin(int copies, double wave, double exp) {
        double val1 = (-Math.sin(Math.PI / 2 + (copies - 1) / wave) + 1) / 2.0;
        double val2 = (-Math.sin(Math.PI / 2 + (copies - 1.33) / wave) + 1) / 2.0;
        double val3 = (-Math.sin(Math.PI / 2 + (copies - 0.66) / wave) + 1) / 2.0;
        //to explore the peak of the sin function
        return Math.max(
                Math.max(
                        Math.pow(val1, exp), Math.pow(val2, exp)),
                Math.pow(val3, exp));
    }

    @Override
    public String getInformation() {
         StringBuilder txt = new StringBuilder(Genetic.getName(this));
         txt.append("\n"+getClass().getSimpleName() +" (probability , wave , exponent)\n");

        txt.append("\n\nMultiset Flip Bit Mutation");
        txt.append("\nMulti individuals are splited in many single individuals");
        txt.append("\nEach individuals receives a copy number");
        txt.append("\nProbability of mutation is a wave function (SIN)");

        txt.append("\n\n# of Copy  ");
        for (int i = 1; i < 64; i++) {
            txt.append(MyString.center(i + "", 6));
        }
        txt.append("\nProbability  ");
        for (int i = 1; i < 64; i++) {
            txt.append(MyString.setSize(String.format(Locale.US,"%5.3f", waveFunctionSin(i, wave, exponent)) + "", 6));
        }
        return txt.toString();
    }

    @Override
    public String getParameters() {
        return pMutation + " " + wave + " " + exponent;
    }

    //----------------------------------------------------------------------------------------------------------
    /**
     * update parameters od the operator
     *
     * @param params parameters separated by spaces
     * @throws RuntimeException not good values to parameters
     */
    @Override
    public void setParameters(String params) throws RuntimeException { //update parameters by string values
        //split string by withe chars
        String[] aParams = params.split("\\s+");
        try {
            pMutation = Double.parseDouble(aParams[0]);
        } catch (Exception e) {
        }
        try {
            wave = Double.parseDouble(aParams[1]);
        } catch (Exception e) {
        }
        try {
            exponent = Double.parseDouble(aParams[2]);
        } catch (Exception e) {
        }
    }//----------------------------------------------------------------------------------------------------------
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604040012L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        M_WaveMutation m = new M_WaveMutation();
        m.setParameters("-1 2 4");
        System.out.println(m.getInformation());
        Solution i = new OneMax();
        i.setParameters("1000");      
//        SimplePopulation pop = new SimplePopulation();
        SimplePopulation pop = new MultiPopulation();
        for (int j = 0; j < 50; j++) {
            pop.addIndividual(i.getClone());
            
        }
        pop.addIndividual(i);
        System.out.println(pop);
        pop = m.execute(pop);
        pop.evaluate();
        System.out.println(pop);
    }

}
