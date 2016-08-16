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
package GUI.statistics;

import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.solver.MuGA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created on 10/abr/2016, 16:53:52
 *
 * @author zulu - computer
 */
public class CharStatistics {

    private JFreeChart chart;
    XYSeries data;
    int indexOfStatistic;
    DefaultXYItemRenderer renderer;
    
    public void add(double x, double y){
        data.add(x, y, true);
        //chart.fireChartChanged();
    }

    public void createChart(EAsolver solver, int indexOfStatistic) {

        NumberAxis domain = new NumberAxis("Fitness Evaluations");
        NumberAxis range = new NumberAxis("Value");
        XYPlot xyplot = new XYPlot();
        xyplot.setDomainAxis(domain);
        xyplot.setRangeAxis(range);
        // xyplot.setBackgroundPaint(Color.black);
        XYSeriesCollection dataset = new XYSeriesCollection();

        xyplot.setDataset(dataset);

         renderer = new DefaultXYItemRenderer();
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesShapesVisible(0, false);
        xyplot.setRenderer(renderer);

        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);

        domain.setTickLabelsVisible(true);
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart = new JFreeChart(solver.getSimpleName(),
                JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
       
        chart.setNotify(true);
       
        data = new XYSeries(solver.report.getStatistics().get(indexOfStatistic).getSimpleName());
        dataset.addSeries(data);
    }

    public static void main(String[] args) {
        EAsolver solver = new MuGA();
        solver.InitializeEvolution(true);
        CharStatistics demo = new CharStatistics();
        demo.createChart(solver, 0);

        ChartPanel chartPanel = new ChartPanel(demo.chart);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(chartPanel, BorderLayout.CENTER);
        JFrame frm = new JFrame("demo");
        frm.getContentPane().add(chartPanel);
        frm.pack();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        
         EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        demo.add(i, Math.random());                        
                        System.out.println(i+"" + demo.chart.isNotify());
                        Thread.sleep(20);
                        JOptionPane.showMessageDialog(null, i+"");
                    }
                                        
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(CharStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
         });

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604101653L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
