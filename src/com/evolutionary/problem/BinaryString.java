//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manso  &  Luis Correia                                      ::
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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.evolutionary.problem;

import com.utils.MyString;

/**
 * Created on 29/set/2015, 9:27:32
 *
 * @author zulu - computer
 */
public abstract class BinaryString extends Solution {

    /**
     * evaluate binary string
     *
     * @param genome array of booleans
     * @return fitness of booleans
     */
    protected abstract double evaluate(boolean[] genome);//--------------------- calculate fitness

    public static int DEFAULT_SIZE = 8; //-------------------------------------- default size

    /**
     * contructor of maximize problems
     *
     * @param size size of individual
     */
    protected BinaryString(Optimization type) {
        super(new boolean[DEFAULT_SIZE], type);
        setParameters(DEFAULT_SIZE + "");
    }
    
     /**
     * contructor of maximize problems
     *
     * @param size size of individual
     */
    protected BinaryString(int size , Optimization type) {
        super(new boolean[size], type);
        setParameters(size + "");
    }

    /**
     * convert to array of booleans from genome Object
     *
     * @return array of booleans
     */
    public boolean[] getBitsGenome() {
        return (boolean[]) genome;
    }

    /**
     * gets the number of allels
     *
     * @return
     */
    @Override
    public int getSize() {
        return ((boolean[]) genome).length;
    }

    /**
     * calculate the fitness of the invidual this method increase the number of
     * evaluations
     *
     * @return fitness value
     */
    @Override
    public double fitness() {
        return evaluate(getBitsGenome());
    }

    /**
     * converte genome to string
     * @return string genome
     */
    @Override
    public String toStringGenome() {
        return booleanArrayToString(getBitsGenome());
    }

    /**
     * update genome with bit string with '0' and '1' 
     * characters there are not '0' or '1' are ignored.
     * this method change the lenght of the genome
     * @param bits binary string
     */
    public void setBits(String bits) {//---------------------------------------- setBits
        bits = MyString.removeNot01(bits); // remove characters that are not 0 or 1
        boolean genes[] = new boolean[bits.length()];
        for (int i =  genes.length-1; i >=0; i--) {
            genes[i] = bits.charAt(i) == '1' ? true : false;

        }
        setGenome(genes);
    }

    /**
     * randomize genome
     */
    public void randomize() {//-------------------------------------------------randomize
        //create random bits
        boolean[] genes = getBitsGenome();
        for (int j = genes.length - 1; j >= 0; j--) {
            genes[j] = random.nextBoolean(); // Genetic random
        }
        //clear evaluation
        setNotEvaluated();
    }

    /**
     * hamming distance
     *
     * @param ind other individual
     * @return hamming distance
     */
    @Override
    public double distance(Solution ind) {//---------------------------------- Hamming Distance
        boolean[] thisGenome = getBitsGenome();
        boolean[] otherGenome = ((BinaryString) ind).getBitsGenome();
        double dist = 0;
        for (int i = 0; i < thisGenome.length; i++) {
            if (thisGenome[i] != otherGenome[i]) {
                dist++;
            }
        }
        return dist;
    }

    /**
     * deep copy
     *
     * @return
     */
    @Override
    public Solution getClone() {//-------------------------------------------- get clone
        BinaryString clone = (BinaryString) super.getClone();
        //clone genome
        boolean[] bits = getBitsGenome();
        boolean[] newBits = new boolean[bits.length];
        System.arraycopy(bits, 0, newBits, 0, bits.length);
        clone.genome = newBits;        
        return clone;
    }

    /**
     * verify the equality of the genomes
     *
     * @param ind ind to test
     * @return
     */
    public boolean equals(Object ind) {//--------------------------------------- equals
        boolean[] thisGenome = getBitsGenome();
        boolean[] otherGenome = ((BinaryString) ind).getBitsGenome();
        for (int i = 0; i < otherGenome.length; i++) {
            if (thisGenome[i] != otherGenome[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * update the size of the genome
     *
     * @param params size of the genome
     */
    @Override
    public void setParameters(String params) {//-------------------------------- set parameters
        try {
            //update size
            int size = Integer.parseInt(params);
            genome = new boolean[size];
            setNotEvaluated();
        } catch (Exception e) {
        }
    }

    @Override
    public String getParameters() {//------------------------------------------- get parameters
        return getSize() + "";
    }

    @Override
    public String getInformation() {//------------------------------------------ get information
        // public String getInfo() {
        StringBuilder txt = new StringBuilder();
        String p = getParameters();
        p = p.replaceAll(" ", " , ");
        txt.append(getClass().getSimpleName() + "(" + p + ") : ");
        txt.append(getOptimizationType() == Optimization.MAXIMIZE ? "MAXIMIZE" : "MINIMIZE");
        return txt.toString();

    }
    
    /**
     * compute the number of ones int he genome
     *
     * @param ini start position (inclusive)
     * @param fin end position (exclusive)
     * @return number of ones between ini and fin
     */
    protected int unitation(int ini, int fin) {//------------------------------- unitation
        boolean[] genome = getBitsGenome();
        int ones = 0;
        for (int i = ini; i < fin; i++) {
            ones += genome[i] ? 1 : 0;
        }
        return ones;
    }
    /**
     * Conevert a boolean array to String
     * @param bits boolean array
     * @return String with '0' and '1'
     */
    public static String booleanArrayToString(boolean[] bits) {//--------------- boolean array to string
        StringBuilder txt = new StringBuilder();
        for (boolean bit : bits) {
            txt.append(bit ? "1" : "0");
        }
        return txt.toString();
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509290927L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
