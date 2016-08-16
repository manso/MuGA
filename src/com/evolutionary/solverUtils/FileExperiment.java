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

import com.evolutionary.solver.EAsolver;
import com.utils.MyFile;
import java.io.File;
import java.util.ArrayList;

/**
 * Created on 26/mar/2016, 10:16:15
 *
 * @author zulu - computer
 */
public class FileExperiment extends FileSimulation {

    public static final String EXPERIMENT_FILE = "experiment file";

    public static ArrayList<EAsolver> loadExperiment(String path) {
        String myPath = MyFile.getPath(path);
        String myFile = MyFile.getFileName(path);
        String simulFileName = myPath + myFile + File.separator;

        String txtFile = MyFile.readFile(path);
        ArrayList<EAsolver> sim = loadtxtSimulation(txtFile, simulFileName);
        ArrayList<EAsolver> files = loadFile(txtFile, simulFileName);
        sim.addAll(files);
        return sim;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603261016L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        loadSimulation("experiment.txt");

    }
}
