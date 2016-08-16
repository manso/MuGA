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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 27/mar/2016, 6:09:37
 *
 * @author zulu - computer
 */
public class EAexperiment {

    ArrayList<EAsolver> solvers = new ArrayList<>();
    String fileName = "simulation_result.txt"; // filename


    public void load(String simulationFileName) {
        solvers = FileSimulation.loadSimulation(simulationFileName);
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
        for (int i = 0; i < solvers.size(); i++) {
            System.out.println("Solving " + solvers.get(i).report.filename);
            solvers.get(i).solve(verbose);
            System.out.println(solvers.get(i).report.getEvolutionString());
        }
        printFinalReport();
    }

    public void printFinalReport() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(fileName);
            out.println(MyString.toFileString(ReportSimulation.getReportMeans(solvers)));
            out.println(MyString.LINE);
            out.println(MyString.toFileString(ReportSimulation.getReportSTD(solvers)));
            out.println(MyString.LINE);
            out.println(MyString.toFileString(ReportSimulation.getReportData(solvers)));
            out.println(MyString.LINE);            
            out.println(MyString.toFileString(MyString.toComment(ReportSimulation.getSolversInfo(solvers))));
            out.println(MyString.LINE);
            out.print(MyString.toFileString(MyString.toComment(MyString.getCopyright())));
        } catch (Exception ex) {
            Logger.getLogger(EAexperiment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603270609L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        EAexperiment sim = new EAexperiment();
        sim.load("experiment01.txt");
        sim.solve(false);
    }

}
