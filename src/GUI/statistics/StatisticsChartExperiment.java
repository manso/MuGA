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
import com.evolutionary.solverUtils.EAsolverArray;
import com.evolutionary.solver.MuGA;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.YIntervalSeries;

import org.jfree.data.xy.YIntervalSeriesCollection;

/**
 * Created on 11/abr/2016, 10:33:56
 *
 * @author zulu - computer
 */
public class StatisticsChartExperiment extends StatisticsChartSolver {

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604111033L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public StatisticsChartExperiment(EAsolver solver) {
        super(solver);
    }

    protected JFreeChart createChart(AbstractStatistics stat, String label) {
        DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();

        NumberAxis domain = new NumberAxis("Fitness Evaluations");
        NumberAxis range = new NumberAxis(stat.getSimpleName());
        XYPlot xyplot = new XYPlot();
        xyplot.setDomainAxis(domain);
        xyplot.setRangeAxis(range);
        // xyplot.setBackgroundPaint(Color.black);
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

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

        YIntervalSeries data = new YIntervalSeries(label);
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
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        for (int i = 0; i < series; i++) {
            renderer.setSeriesStroke(i, stroke1);
            float color = (i) / (float) (series);
            renderer.setSeriesPaint(i, Color.getHSBColor(color, 1, 1));
            renderer.setSeriesFillPaint(0, setColorTransparency(Color.getHSBColor(color, 1, 1), 0.5f));
        }
        plot.setRenderer(renderer);
    }

    public static Color setColorTransparency(Color c, float transp) {
        return new Color(
                c.getRed() / 255.0f,
                c.getGreen() / 255.0f,
                c.getBlue() / 255.0f,
                transp);
    }

    public void updateStats(EAsolver solver) {
        Double[] values = solver.report.getLastValues();
        for (int i = 0; i < values.length; i++) {
            ((YIntervalSeriesCollection) statisticDataSet.get(i)).getSeries(0).
                    add(solver.numEvaluations, values[i], values[i] * 0.9, values[i] * 1.2);

        }

    }

    public static void main(String[] args) {
        EAsolver template = new MuGA();
        template.numberOfRun = 10;
        EAsolverArray solver = new EAsolverArray(template);
        solver.InitializeEvolution(true);
        StatisticsChartSolver demo = new StatisticsChartExperiment(solver);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(demo.getTabs(), BorderLayout.CENTER);
        JFrame frm = new JFrame("demo Experiment");
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
