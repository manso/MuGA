/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.DisplayProblem;

import GUI.utils.MuGASystem;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author arm
 */
public abstract class DisplayPopulation extends JPanel {

    SimplePopulation pop = null; //population of display

    public abstract boolean isValid(Solution ind);

    protected abstract void displayIndividual(Graphics gr, Solution ind, int px, int py, int sizex, int sizey);

    public void setPopulation(SimplePopulation pop) {
        setDoubleBuffered(true);
        this.pop = pop;
        //revalidate();
        //repaint();

    }

    /**
     * display population of the solver
     *
     * @param pop
     * @param gr
     * @param bounds
     */
    public void showPopulation(SimplePopulation pop, Graphics gr, Rectangle bounds) {
        try {
            this.pop = pop;
            //----------------------------------------------------------------
            //clean area
            gr.setColor(Color.LIGHT_GRAY);
            gr.fill3DRect(bounds.x, bounds.y, bounds.width, bounds.height, true);
            //----------------------------------------------------------------
            //dimensions of Line of bits
            double dimY = (double) bounds.height / (pop.getSize() + 0);
            bounds.y += dimY / 8;
            int i = 0;
            for (Solution ind : pop.getList()) {
                displayIndividual(gr, ind,
                        bounds.x,
                        bounds.y + (int) (dimY * i++),
                        bounds.width,
                        (int) dimY);
            }
        } catch (Exception e) {
        }
    }

    public void showEmpty(Graphics gr, Rectangle bounds) {
        gr.setColor(new Color(120, 120, 120));
        //gr.clearRect(0, 0, this.getWidth(), this.getHeight());
        gr.fill3DRect(bounds.x, bounds.y, bounds.width, bounds.height, true);
        gr.setFont(new java.awt.Font("Courier", 0, 18));
        gr.setColor(Color.WHITE);
        gr.drawString("Empty Population", bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
        return;
    }

    protected void show(Graphics gr, Rectangle bounds) {
        //empty population
        if (pop == null || pop.isEmpty()) {
            showEmpty(gr, bounds);
            return;
        }
        //genotype too long
        if (pop.getIndividual(0).getSize() > bounds.width) {
            gr.setFont(new java.awt.Font("Courier", 0, 18));
            gr.setColor(Color.WHITE);
            gr.drawString("Too long genotype!",
                    bounds.x + bounds.width / 2,
                    bounds.y + bounds.height / 2);
            gr.setColor(Color.WHITE);
            return;
        }
        bounds.y = 0;
        showPopulation(pop, gr, bounds);
        //show copyright
        drawInformation(gr, bounds.width - 100, bounds.height - 20);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g != null && pop != null) {
            show(g, getBounds());
        }
    }

    protected void drawInformation(Graphics gr, int px, int py) {
        gr.setFont(new java.awt.Font("Courier", 0, 12));
        gr.setColor(Color.DARK_GRAY);
        gr.drawString(MuGASystem.copyright, px + 1, py + 1);
        gr.setColor(Color.LIGHT_GRAY);
        gr.drawString(MuGASystem.copyright, px, py);
    }

    ///////////////////////////////////////////////////////////////////////////
    //------------------    C O L O R S  ------------------------------------//
    ///////////////////////////////////////////////////////////////////////////
    protected static Color getColor(boolean cValue) {
        if (cValue) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    protected static Color getDeceptive(boolean cValue) {
        if (cValue) {
            return new Color(1f, 1f, 1f, .75f);
        }
        return new Color(.1f, 1f, 1f, .35f);
    }

    protected static Color getBestColor(boolean cValue) {
        if (cValue) {
            return new Color(0, 255, 0);
        }
        return new Color(200, 255, 200);
    }

    protected static Color getHSBColor(boolean bit, float hue) {
        if (bit) {
            return Color.getHSBColor(hue, 1.0f, 1.0f);
        }
        return Color.getHSBColor(hue, 0.2f, 1.0f);
    }

    public void drawbit(Graphics gr, int x1, int y1, int sizex, int sizey) {
        int round = 6;
        gr.fillRoundRect(x1, y1, sizex, sizey, sizex / round, sizex / round);
    }

    public void drawBestBit(boolean bit, Graphics gr, int x1, int y1, int sizex, int sizey) {
        gr.setColor(getBestColor(bit));
        gr.fillOval(x1, y1, sizex, sizey);
        gr.setColor(Color.BLACK);
        gr.drawOval(x1, y1, sizex, sizey);
    }

    public void drawBit(boolean bit, Graphics gr, int x1, int y1, int sizex, int sizey) {
        gr.setColor(getColor(bit));
        gr.fillRect(x1, y1, sizex, sizey);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
