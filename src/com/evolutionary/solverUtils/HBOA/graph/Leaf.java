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

package com.evolutionary.solverUtils.HBOA.graph;

//import com.evolutionary.solverUtils.HBOA.Problem;
import java.util.HashSet;

/**
 * Created on 20/abr/2016, 19:46:11 
 * @author zulu - computer
 */
public class Leaf implements IGraph{
	private int depth, 
				side,
				mZero, mOne;
	private Variable parent;
	private int[][]  possibleSplitFrequencies; 	// NOTE: 0 -> m00; 1 -> m01; 2 -> m10; 3 -> m11
	private double[] scoreGains ;
	private int		 bestSplit;
	private double   bestSplitScoreGain;
												 				  	  								 
	public Leaf(){}  // Default constructor
	public Leaf(int depth, int side, int mZero, int mOne, int SIZE){	 
		this.depth = depth;									
		this.side  = side;
		this.mZero = mZero;
		this.mOne  = mOne;
		this.bestSplitScoreGain = Double.NEGATIVE_INFINITY;
                scoreGains = new double[SIZE];
                possibleSplitFrequencies = new int[4][SIZE]; 	// NOTE: 0 -> m00; 1 -> m01; 2 -> m10; 3 -> m11
	}

	public int 									  getDepth(){return depth;}
	public int 									   getSide(){return side;}
	public int 									  getMZero(){return mZero;}
	public int 									   getMOne(){return mOne;}
	public int[][] 			   getPossibleSplitFrequencies(){return possibleSplitFrequencies;}
	public int getPossibleSplitFrequency(int row, int split){return possibleSplitFrequencies[row][split];}
	public Variable 							 getParent(){return parent;}
	public double[] 						 getScoreGains(){return scoreGains;}
	public double 						 getScoreGain(int k){return scoreGains[k];}
	public int								  getBestSplit(){return bestSplit;}
	public double 					 getBestSplitScoreGain(){return bestSplitScoreGain;}

	public void			  addPossibleSplitFrequency(int x, int y){this.possibleSplitFrequencies[x][y]++;}
	public void setPossibleSplitFrequency(int x, int y, int freq){this.possibleSplitFrequencies[x][y] = freq;}
	public void 			setScoreGain(int k, double scoreGain){this.scoreGains[k] = scoreGain;}
	public void 							  setBestSplit(int k){this.bestSplit = k;}
	public void 		  setBestSplitScoreGain(double bestScore){this.bestSplitScoreGain = bestScore;}
	
	public void setParent(Variable parent, int side){
		this.parent = parent;
		this.side = side;
	}
	
	public void updateBestSplit(int k, double scoreGain){				// Responsible for updating the value of the best split score gain in this leaf.
		if(scoreGain > bestSplitScoreGain){		
			this.bestSplit 			= k;
			this.bestSplitScoreGain = scoreGain;
		}
	}
	
	public void resetBestSplit(HashSet<Integer> splitList){
		this.bestSplitScoreGain = Double.NEGATIVE_INFINITY;
		for(int s: splitList){
			double scoreGain = scoreGains[s];
			if(scoreGain > bestSplitScoreGain){
				this.bestSplit 			= s;
				this.bestSplitScoreGain = scoreGain;
			}
		}
	}
			
	public String toString(){
		String str = "";
		for(int i = 0; i < scoreGains.length; i++)
			str += scoreGains[i] + ",";										// Print the scoreOrders array.
		str = str.substring(0, str.length()-1);  							// Remove the last comma.
		return "[d = " + depth + "; s = " + side + "; m0 = " + mZero + 
				"; m1= " + mOne + "]" + "; scoreGains=[" + str + "]";
	}


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604201946L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}