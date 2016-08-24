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
package com.evolutionary.report;

import com.evolutionary.report.compareMeans.Tstudent;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.solver.EAsolver;
import com.utils.MyFile;
import com.utils.MyStatistics;
import com.utils.MyString;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 28/mar/2016, 13:06:37
 *
 * @author zulu - computer
 */
public class ReportSimulation {

    public static String getReportMeans(ArrayList<EAsolver> solvers) {
        StringBuffer txt = new StringBuffer();
        txt.append(solvers.get(0).report.getStatisticsHeader("AVG"));
        for (int index = 0; index < solvers.size(); index++) {
            // ReportSolverArray report = (ReportSolverArray) solvers.get(index).report;
            double[] v = ((ReportSolverArray) solvers.get(index).report).getMeans();
            txt.append("\n" + MyString.setSize(solvers.get(index).solverName, ReportSolver.FIELD_SIZE));
            for (int i = 0; i < v.length; i++) {
                txt.append(" " + MyString.align(v[i], ReportSolver.FIELD_SIZE));
            }
        }
        return txt.toString() + "\n";
    }

    public static String getReportSTD(ArrayList<EAsolver> solvers) {
        StringBuffer txt = new StringBuffer();
        txt.append(solvers.get(0).report.getStatisticsHeader("STD"));
        for (int index = 0; index < solvers.size(); index++) {
            ReportSolverArray report = (ReportSolverArray) solvers.get(index).report;
            double[] v = ((ReportSolverArray) solvers.get(index).report).getSTDs();
            txt.append("\n" + MyString.setSize(solvers.get(index).solverName, ReportSolver.FIELD_SIZE));
            for (int i = 0; i < v.length; i++) {
                txt.append(" " + MyString.align(v[i], ReportSolver.FIELD_SIZE));
            }
        }
        return txt.toString() + "\n";
    }

    public static String getReportData(ArrayList<EAsolver> solvers) {
        StringBuffer txt = new StringBuffer();
        ArrayList<AbstractStatistics> stats = solvers.get(0).report.stats;
        //for all statistics
        for (int indexStat = 0; indexStat < stats.size(); indexStat++) {
            txt.append(MyString.LINE + "\n");
            txt.append(MyString.center(stats.get(indexStat).toString().toUpperCase(), MyString.LINE.length()) + "\n");
            txt.append(MyString.LINE + "\n");
            txt.append(getReportDataVertical(solvers, indexStat) + "\n");
            txt.append(getReportData(solvers, indexStat) + "\n");
            txt.append(stats.get(indexStat) + " : Compare Means  using Student's t-test\n");
            txt.append(Tstudent.getReportComapareMeans(solvers, stats.get(indexStat)) + "\n");
        }//all stats    
        return txt.toString();
    }

    public static String getReportData(ArrayList<EAsolver> solvers, int indexStat) {
        StringBuffer txt = new StringBuffer();
        ArrayList<AbstractStatistics> stats = solvers.get(0).report.stats;
        //::::::: HEADER :::::::::::
        txt.append(MyString.setSize(stats.get(indexStat).toString(), ReportSolver.FIELD_SIZE));
        txt.append(" " + MyString.align("Mean", ReportSolver.FIELD_SIZE));
        txt.append(" " + MyString.align("STD", ReportSolver.FIELD_SIZE));
        for (int j = 0; j < solvers.get(0).numberOfRun; j++) {
            txt.append(" " + MyString.align("Solver " + j, ReportSolver.FIELD_SIZE));
        }
        txt.append("\n");
        //::::::::::::::::::::::: DATA ::::::::::::::::::::::::::
        for (int j = 0; j < solvers.size(); j++) {
            //name of simulation
            txt.append(MyString.align(solvers.get(j).solverName, ReportSolver.FIELD_SIZE));
            //data
            double[] v = ((ReportSolverArray) solvers.get(j).report).getStatisticResult(indexStat);
            txt.append(" " + MyString.align(MyStatistics.mean(v), ReportSolver.FIELD_SIZE));
            txt.append(" " + MyString.align(MyStatistics.std(v), ReportSolver.FIELD_SIZE));
            for (int s = 0; s < v.length; s++) {
                txt.append(" " + MyString.align(v[s], ReportSolver.FIELD_SIZE));
            }
            txt.append("\n");
        }
        txt.append("\n");
        return txt.toString();
    }

    public static void saveStatisticsCSV(ArrayList<EAsolver> solvers, String fileName) {
         String SEPARATOR = ";";
        ArrayList<AbstractStatistics> stats = solvers.get(0).report.stats;
        //for all statistics
        for (int indexStat = 0; indexStat < stats.size(); indexStat++) {
            try {
                PrintWriter out = new PrintWriter(fileName + "_" + stats.get(indexStat).toString() + ".csv");
                StringBuilder txt = new StringBuilder("sep=" + SEPARATOR + "\n"); // excel 2010
                //::::::: HEADER :::::::::::
                txt.append(MyString.setSize(stats.get(indexStat).toString(), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" "); // stat
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(solvers.get(j).solverName, ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: MEAN :::::::
                txt.append("\n" + MyString.align("Mean", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(
                            ((ReportSolverArray) solvers.get(j).report).getMean(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: STD :::::::
                txt.append("\n" + MyString.align("Std", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(
                            ((ReportSolverArray) solvers.get(j).report).getSTD(indexStat), ReportSolver.FIELD_SIZE) +" " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: MAX :::::::
                txt.append("\n" + MyString.align("Minimum", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(
                            ((ReportSolverArray) solvers.get(j).report).getMin(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: Median :::::::
                txt.append("\n" + MyString.align("Median", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(
                            ((ReportSolverArray) solvers.get(j).report).getMedian(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: STD :::::::
                txt.append("\n" + MyString.align("Maximum", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(
                            ((ReportSolverArray) solvers.get(j).report).getMax(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                txt.append("\n");
                //:::::::::::::::::: GET DATA :::::::::::::::::::::::
                double[][] data = new double[solvers.size()][];
                for (int j = 0; j < solvers.size(); j++) {
                    data[j] = ((ReportSolverArray) solvers.get(j).report).getStatisticResult(indexStat);
                }
                //::::::::::::::::::::::: DATA ::::::::::::::::::::::::::
                for (int numSolver = 0; numSolver < data[0].length; numSolver++) {
                    txt.append("\n" + MyString.align("Solver " + numSolver, ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                    for (int numExperiment = 0; numExperiment < solvers.size(); numExperiment++) {
                        txt.append(MyString.align(data[numExperiment][numSolver], ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                    }
                }               
                //write string to file
                out.println(MyString.toFileString(txt.toString()));
                //write copyright
                out.print(MyString.toFileString(MyString.toComment(MyString.getCopyright())));
                //close file
                out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReportSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//all stats    
    }
    
    public static void saveStatisticsCSV(ArrayList<EAsolver> solvers, String fileName , Locale local , String SEPARATOR) {
        String path = MyFile.getPath(fileName);
        String name  = MyFile.getFileName(fileName); // remove extension
        fileName = path + name +"_" +local.getCountry()+"_";
        ArrayList<AbstractStatistics> stats = solvers.get(0).report.stats;
        //for all statistics
        for (int indexStat = 0; indexStat < stats.size(); indexStat++) {
            try {
                PrintWriter out = new PrintWriter(fileName +  stats.get(indexStat).toString() + ".csv");
                StringBuilder txt = new StringBuilder("sep=" + SEPARATOR + "\n"); // excel 2010
                //::::::: HEADER :::::::::::
                txt.append(MyString.setSize(stats.get(indexStat).toString(), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" "); // stat
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(MyString.align(solvers.get(j).solverName, ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: MEAN :::::::
                txt.append("\n" + MyString.align("Mean", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(String.format(local, "%f",
                            ((ReportSolverArray) solvers.get(j).report).getMean(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: STD :::::::
                txt.append("\n" + MyString.align("Std", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(String.format(local, "%f",
                            ((ReportSolverArray) solvers.get(j).report).getSTD(indexStat), ReportSolver.FIELD_SIZE) +" " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: MAX :::::::
                txt.append("\n" + MyString.align("Minimum", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(String.format(local, "%f",
                            ((ReportSolverArray) solvers.get(j).report).getMin(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: Median :::::::
                txt.append("\n" + MyString.align("Median", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(String.format(local, "%f",
                            ((ReportSolverArray) solvers.get(j).report).getMedian(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                //::::: STATISTICS ::::::: STD :::::::
                txt.append("\n" + MyString.align("Maximum", ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                for (int j = 0; j < solvers.size(); j++) { // name of solvers
                    txt.append(String.format(local, "%f",
                            ((ReportSolverArray) solvers.get(j).report).getMax(indexStat), ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                }
                txt.append("\n");
                //:::::::::::::::::: GET DATA :::::::::::::::::::::::
                double[][] data = new double[solvers.size()][];
                for (int j = 0; j < solvers.size(); j++) {
                    data[j] = ((ReportSolverArray) solvers.get(j).report).getStatisticResult(indexStat);
                }
                //::::::::::::::::::::::: DATA ::::::::::::::::::::::::::
                for (int numSolver = 0; numSolver < data[0].length; numSolver++) {
                    txt.append("\n" + MyString.align("Solver " + numSolver, ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                    for (int numExperiment = 0; numExperiment < solvers.size(); numExperiment++) {
                        txt.append(String.format(local, "%f",
                                data[numExperiment][numSolver], ReportSolver.FIELD_SIZE) + " " + SEPARATOR+" ");
                    }
                }               
                //write string to file
                out.println(MyString.toFileString(txt.toString()));
                //write copyright
                out.print(MyString.toFileString(MyString.toComment(MyString.getCopyright())));
                //close file
                out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReportSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//all stats    
    }
    
    
    

    public static String getReportDataVertical(ArrayList<EAsolver> solvers, int indexStat) {
        StringBuffer txt = new StringBuffer();
        ArrayList<AbstractStatistics> stats = solvers.get(0).report.stats;
        //::::::: HEADER :::::::::::
        txt.append(MyString.setSize(stats.get(indexStat).toString(), ReportSolver.FIELD_SIZE) + " "); // stat
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(solvers.get(j).solverName, ReportSolver.FIELD_SIZE) + " ");
        }
        //::::: STATISTICS ::::::: MEAN :::::::
        txt.append("\n" + MyString.align("Mean", ReportSolver.FIELD_SIZE) + " ");
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(
                    ((ReportSolverArray) solvers.get(j).report).getMean(indexStat), ReportSolver.FIELD_SIZE) + " ");
        }
        //::::: STATISTICS ::::::: STD :::::::
        txt.append("\n" + MyString.align("Std", ReportSolver.FIELD_SIZE) + " ");
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(
                    ((ReportSolverArray) solvers.get(j).report).getSTD(indexStat), ReportSolver.FIELD_SIZE) + " ");
        }
        //::::: STATISTICS ::::::: MAX :::::::
        txt.append("\n" + MyString.align("Minimum", ReportSolver.FIELD_SIZE) + " ");
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(
                    ((ReportSolverArray) solvers.get(j).report).getMin(indexStat), ReportSolver.FIELD_SIZE) + " ");
        }
        //::::: STATISTICS ::::::: Median :::::::
        txt.append("\n" + MyString.align("Median", ReportSolver.FIELD_SIZE) + " ");
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(
                    ((ReportSolverArray) solvers.get(j).report).getMedian(indexStat), ReportSolver.FIELD_SIZE) + " ");
        }
        //::::: STATISTICS ::::::: STD :::::::
        txt.append("\n" + MyString.align("Maximum", ReportSolver.FIELD_SIZE) + " ");
        for (int j = 0; j < solvers.size(); j++) { // name of solvers
            txt.append(MyString.align(
                    ((ReportSolverArray) solvers.get(j).report).getMax(indexStat), ReportSolver.FIELD_SIZE) + " ");
        }
        txt.append("\n");
        //:::::::::::::::::: GET DATA :::::::::::::::::::::::
        double[][] data = new double[solvers.size()][];
        for (int j = 0; j < solvers.size(); j++) {
            data[j] = ((ReportSolverArray) solvers.get(j).report).getStatisticResult(indexStat);
        }
        //::::::::::::::::::::::: DATA ::::::::::::::::::::::::::
        for (int numSolver = 0; numSolver < data[0].length; numSolver++) {
            txt.append("\n" + MyString.align("Solver " + numSolver, ReportSolver.FIELD_SIZE) + " ");
            for (int numExperiment = 0; numExperiment < solvers.size(); numExperiment++) {
                txt.append(MyString.align(data[numExperiment][numSolver], ReportSolver.FIELD_SIZE) + " ");
            }
        }

        txt.append("\n");
        return txt.toString();
    }

    public static String getSolversInfo(ArrayList<EAsolver> solvers) {
        StringBuffer txt = new StringBuffer();
        for (EAsolver solver : solvers) {
            txt.append(MyString.LINE + "\n");
            txt.append(solver.report.filename + "\n");
            txt.append(MyString.LINE + "\n");
            txt.append(solver.toString() + "\n");
        }
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603270609L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
