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
import com.evolutionary.report.statistics.FunctionCalls;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.solver.MuGA;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created on 10/abr/2016, 20:05:04
 *
 * @author zulu - computer
 */
public class StatisticsChartSolver {

    /**
     * TabedPane to all statistics
     */
    JTabbedPane tabs = new JTabbedPane();
    ArrayList statisticDataSet; // render to dataSet

    public StatisticsChartSolver(EAsolver solver) {
        setSolver(solver);
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public void setSolver(EAsolver solver) {
        //tabed pane
        tabs.removeAll();
        statisticDataSet = new ArrayList<>();
        //----------------------------------------------------------------------
        //Conver statistics to GUI
        ArrayList<AbstractStatistics> stats = solver.report.getStatistics();

        for (int i = 0; i < stats.size(); i++) {
            JFreeChart chart = createChart(stats.get(i), solver.getSimpleName());
            formatChart(chart, 1);
            tabs.add(new ChartPanel(chart), chart.getXYPlot().getRangeAxis().getLabel());
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: insert data to chart
        //update charts with values
        ArrayList<Double[]> evolution = solver.report.evolution;
        AbstractStatistics evals = new FunctionCalls();
        for (int i = 0; i < evolution.size(); i++) {
            updateStats(solver.report.getStatisticsData(i, evals), evolution.get(i));
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        tabs.revalidate();
    }

    public void updateStats(EAsolver solver) {
        updateStats(solver.numEvaluations, solver.report.getLastValues());
    }

    public void updateStats(double numEvals, Double[] values) {
        for (int i = 0; i < values.length; i++) {
            ((XYSeriesCollection) statisticDataSet.get(i)).getSeries(0).
                    add(numEvals, values[i]);
        }

    }

    protected JFreeChart createChart(AbstractStatistics stat, String label) {
        DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();

        NumberAxis domain = new NumberAxis("Fitness Evaluations");
        NumberAxis range = new NumberAxis(stat.getSimpleName());
        XYPlot xyplot = new XYPlot();
        xyplot.setDomainAxis(domain);
        xyplot.setRangeAxis(range);
        // xyplot.setBackgroundPaint(Color.black);
        XYSeriesCollection dataset = new XYSeriesCollection();

        xyplot.setDataset(dataset);

        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesShapesVisible(0, false);
        xyplot.setRenderer(renderer);

        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);

        domain.setTickLabelsVisible(true);
        JFreeChart chart = new JFreeChart(stat.getSimpleName(),
                JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);

        chart.setNotify(true);

        XYSeries data = new XYSeries(label);
        dataset.addSeries(data);
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        statisticDataSet.add(dataset); // save render of statistic
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        return chart;
    }

    protected void formatChart(JFreeChart chart, int series) {
        XYPlot plot = (XYPlot) chart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);
        plot.getRangeAxis().setAutoRange(true);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRange(true);
        yAxis.setAutoRangeIncludesZero(false);
//         set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(ticks);
//         set the renderer's stroke
        Stroke stroke1 = new BasicStroke(
                3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        for (int i = 0; i < series; i++) {
            renderer.setSeriesStroke(i, stroke1);
            float color = (i) / (float) (series);
            renderer.setSeriesPaint(i, Color.getHSBColor(color, 1, 1));
        }
        plot.setRenderer(renderer);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604102005L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        EAsolver solver = new MuGA();
        solver.startEvolution(true);
        StatisticsChartSolver demo = new StatisticsChartSolver(solver);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(demo.getTabs(), BorderLayout.CENTER);
        JFrame frm = new JFrame("demo");
        frm.getContentPane().add(panel);
        frm.pack();
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        solver.iterate();
                        demo.updateStats(solver);
                        frm.repaint();
//                        Thread.sleep(200);

                        //  JOptionPane.showMessageDialog(null, i+"");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(CharStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }
}
