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
package com.evolutionary.solverUtils;

import com.evolutionary.report.ReportSimulation;
import com.evolutionary.solver.EAsolver;
import com.utils.MyFile;
import com.utils.MyString;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 27/mar/2016, 6:09:37
 *
 * @author zulu - computer
 */
public class EAsimulation {

    ArrayList<EAsolver> solvers = new ArrayList<>();
    String fileName = "simulation_result.txt"; // filename

    public void load(String simulationFileName) {
        solvers = FileSimulation.loadSimulation(simulationFileName);
        if (solvers.isEmpty()) { // no solvers to load
            return;
        }
        String path = MyFile.getPath(simulationFileName)
                + MyFile.getFileName(simulationFileName);

        (new File(path)).mkdirs();
        fileName = MyFile.getPath(path)
                + "result_"
                + MyFile.getFileName(simulationFileName)
                + "."
                + FileSolver.FILE_EXTENSION;
    }

    public void solve(boolean verbose) {
        if (solvers.isEmpty()) { //nothing to solve
            return;
        }
        for (int i = 0; i < solvers.size(); i++) {
            System.out.println("Solving " + solvers.get(i).solverName);
            solvers.get(i).solve(verbose);
            // System.out.println(solvers.get(i).report.getEvolutionString());
        }
        if (solvers.size() > 1) { //multiple solvers
            saveFinalReport();
        }
    }

    public String getFinalReport() {
        StringBuffer txt = new StringBuffer();
        txt.append(ReportSimulation.getReportMeans(solvers) + "\n");
        txt.append(MyString.LINE + "\n");
        txt.append(ReportSimulation.getReportSTD(solvers) + "\n");
        txt.append(MyString.LINE + "\n");
        txt.append(ReportSimulation.getReportData(solvers) + "\n");
        txt.append(MyString.LINE + "\n");
        txt.append(MyString.toComment(ReportSimulation.getSolversInfo(solvers)) + "\n");
        txt.append(MyString.LINE + "\n");
        return txt.toString();
    }

    public void saveFinalReport() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(fileName);
            out.println(MyString.toFileString(getFinalReport()));
            out.print(MyString.toFileString(MyString.toComment(MyString.getCopyright())));

        } catch (Exception ex) {
            Logger.getLogger(EAsimulation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
        ReportSimulation.saveStatisticsCSV(solvers, fileName);
        ReportSimulation.saveStatisticsCSV(solvers, fileName, Locale.getDefault(), ";");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603270609L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        EAsimulation sim = new EAsimulation();
        sim.load("test/teste1.txt");
//        System.out.println(sim.getFinalReport());
        sim.solve(false);
    }

}
