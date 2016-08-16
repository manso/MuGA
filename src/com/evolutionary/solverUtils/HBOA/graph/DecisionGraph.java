package com.evolutionary.solverUtils.HBOA.graph;

import com.evolutionary.Genetic;
import com.evolutionary.solverUtils.HBOA.HBOA;
//import com.evolutionary.solverUtils.HBOA.HBOA;
import java.util.ArrayList;

public class DecisionGraph extends Genetic {

    private IGraph graph;
    private ArrayList<Leaf> leafs = new ArrayList<Leaf>();

    private int bestLeafPos;												// Position of the leaf that contains the best score gain for this decision graph.
    private double bestLeafScoreGain;										// Best score gain among all possible splits for this decision graph.

    public IGraph getGraph() {
        return graph;
    }

    public ArrayList<Leaf> getLeafs() {
        return leafs;
    }

    public Leaf getLeaf(int j) {
        return leafs.get(j);
    }

    public Leaf getBestLeaf() {
        return leafs.get(bestLeafPos);
    }

    public int getBestLeafPos() {
        return bestLeafPos;
    }

    public double getBestLeafScoreGain() {
        return bestLeafScoreGain;
    }

    public void setBestLeafPos(int j) {
        this.bestLeafPos = j;
    }

    public void setBestLeafScoreGain(double bestScoreGain) {
        this.bestLeafScoreGain = bestScoreGain;
    }

    public void updateBestLeaf() {
        bestLeafScoreGain = Double.NEGATIVE_INFINITY;						// Reset the value of best leaf score gain.
        for (int j = 0; j < leafs.size(); j++) {								// Recompute the value of best leaf score gain.
            double scoreGain = leafs.get(j).getBestSplitScoreGain();
            if (scoreGain > bestLeafScoreGain) {
                this.bestLeafPos = j;
                this.bestLeafScoreGain = scoreGain;
            }
        }
    }

    public DecisionGraph() {
    }         										// Empty constructor.

    public DecisionGraph(Leaf leaf) {  										// A DecisionGraph always starts with a single 
        graph = leaf;                 										// leaf and it evolves with each splitLeaf().
        leafs.add(leaf);
//		bestLeafPos = 0;													// Initially each graph as a single leaf, so that's the one with the best split.
//		bestLeafScoreGain = leaf.getBestSplitScoreGain();					// The best score gain was already computed by initializeBN(...)@BayesianNetWork.java.
    }

    public void splitBestLeaf(int j, int split, int SIZE) {
        Leaf oldLeaf = leafs.get(j);
        int depth = oldLeaf.getDepth() + 1;
        int m00 = oldLeaf.getPossibleSplitFrequency(0, split);
        int m01 = oldLeaf.getPossibleSplitFrequency(1, split);
        int m10 = oldLeaf.getPossibleSplitFrequency(2, split);
        int m11 = oldLeaf.getPossibleSplitFrequency(3, split);
        Leaf leaf0 = new Leaf(depth, 0, m00, m10, SIZE);
        Leaf leaf1 = new Leaf(depth, 1, m01, m11, SIZE);
        IGraph newSplit = new Variable(split, leaf0, leaf1);					// NOTE: The Variable constructor is responsible for setting the leaf's parent.
        if (oldLeaf.getParent() == null) {
            graph = newSplit;
        } else if (oldLeaf.getSide() == 0) {
            oldLeaf.getParent().setZero(newSplit);
        } else {
            oldLeaf.getParent().setOne(newSplit);
        }
        leafs.set(j, leaf0);    											// Replace old leaf with leaf zero.
        leafs.add(j + 1, leaf1);   											// Leaf one is added next to leaf zero. 
    }

    public void generateAllele(char[] indiv, int Xi) {
        double prob = getCondProb(indiv);
        indiv[Xi] = (HBOA.random.nextDouble() <= prob) ? '1' : '0';
    }

    private double getCondProb(char[] indiv) {
        IGraph iterator = graph;
        while (iterator instanceof Variable) {
            int x = ((Variable) iterator).getVariable();
            if (indiv[x] == '0') {
                iterator = ((Variable) iterator).getZero();
            } else {
                iterator = ((Variable) iterator).getOne();
            }
        }
        int m0 = ((Leaf) iterator).getMZero();
        int m1 = ((Leaf) iterator).getMOne();
        return ((double) m1) / ((double) (m0 + m1));
    }

    //---------------------------------------------------------------------------
    public void generateAllele(boolean[] indiv, int Xi) {
        double prob = getCondProb(indiv);
        indiv[Xi] = (HBOA.random.nextDouble() <= prob);
    }
    //---------------------------------------------------------------------------

    private double getCondProb(boolean[] indiv) {
        IGraph iterator = graph;
        while (iterator instanceof Variable) {
            int x = ((Variable) iterator).getVariable();
            if (indiv[x] == false) {
                iterator = ((Variable) iterator).getZero();
            } else {
                iterator = ((Variable) iterator).getOne();
            }
        }
        int m0 = ((Leaf) iterator).getMZero();
        int m1 = ((Leaf) iterator).getMOne();
        return ((double) m1) / ((double) (m0 + m1));
    }
//---------------------------------------------------------------------------

    public String toString() {
        return graph + "\n";
    }
}
