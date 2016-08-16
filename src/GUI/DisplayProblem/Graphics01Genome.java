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
package GUI.DisplayProblem;

import com.evolutionary.problem.BinaryString;
import com.evolutionary.problem.Solution;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Created on 7/mar/2016, 16:38:37
 *
 * @author zulu - computer
 */
public class Graphics01Genome extends DisplayPopulation {
    public  boolean isValid(Solution ind){
        return ind instanceof BinaryString;
    }

    @Override
    protected void displayIndividual(Graphics gr, Solution ind, int px, int py, int sizex, int sizey) {
        displayIndividual(gr, ind.toStringGenome(), ind.isOptimum(), px, py, sizex, sizey);
    }
    
    protected void displayIndividual(Graphics gr, String genome, boolean isOptimum, int px, int py, int sizex, int sizey) {
        double dx = sizex / (genome.length());
        //gap between bits
        int gap = 2;
        //height of allel
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }
        //position of the allel
        double posx = px;
        for (int i = 0; i < genome.length(); i++) {
            if (genome.charAt(i) == '0' || genome.charAt(i) == '1') {
                //draw optimum alell
                if (isOptimum) {
                    gr.setColor(getBestColor(genome.charAt(i) == '1'));
                    gr.fillRoundRect((int) posx, py, (int) (dx - gap), sizey, sizey, sizey);

                } else { // draw normal allel
                    gr.setColor(getColor(genome.charAt(i) == '1'));
                    gr.fillRoundRect((int) posx, py, (int) (dx - gap), sizey, sizey, sizey);
                }
                gr.setColor(Color.BLACK);
                gr.drawRoundRect((int) posx, py, (int) (dx - gap), sizey, sizey, sizey);
                //increase px
            }
            posx += dx;
        }
    }
   

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603071638L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
