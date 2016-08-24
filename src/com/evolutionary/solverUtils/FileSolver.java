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

import com.evolutionary.Genetic;
import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.problem.Solution;
import com.evolutionary.report.ReportSolver;
import com.evolutionary.report.ReportSolverArray;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.stopCriteria.StopCriteria;
import com.utils.MyFile;
import com.utils.MyString;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileSolver {

    //  public static final String VERBOSE = "verbose";
    public static final String SOLVER_NAME = "solver name";
    public static final String RANDOM_SEED = "random seed";
    public static final String NUMBER_OF_RUNS = "number of runs";

    public static final String SOLVER_TYPE = "solver type";
    public static final String SOLVER_TYPE_LIB = "com.evolutionary.solver.";

    public static final String STOP_CRITERIA = "stop criteria";
    public static final String STOP_CRITERIA_LIB = "com.evolutionary.stopCriteria.";

    public static final String PROBLEM_NAME = "problem name";
    public static final String PROBLEM_NAME_LIB = "com.evolutionary.problem.";

    public static final String POPULATION_TYPE = "population type";
    public static final String POPULATION_TYPE_LIB = "com.evolutionary.population.";

    public static final String POPULATION_SIZE = "population size";

    public static final String OPERATOR_SELECTION = "selection";
    public static final String OPERATOR_SELECTION_LIB = "com.evolutionary.operator.selection.";

    public static final String OPERATOR_RECOMBINATION = "recombination";
    public static final String OPERATOR_RECOMBINATION_LIB = "com.evolutionary.operator.recombination.";

    public static final String OPERATOR_MUTATION = "mutation";
    public static final String OPERATOR_MUTATION_LIB = "com.evolutionary.operator.mutation.";

    public static final String OPERATOR_REPLACEMENT = "replacement";
    public static final String OPERATOR_REPLACEMENT_LIB = "com.evolutionary.operator.replacement.";

    public static final String OPERATOR_RESCALING = "rescaling";
    public static final String OPERATOR_RESCALING_LIB = "com.evolutionary.operator.rescaling.";

    public static final String STATISTIC = "statistic";
    public static final String STATISTIC_LIB = "com.evolutionary.report.statistics.";

    public static String STATISTIC_KEY = "STATISTIC";
    public static String SOLVER_KEY = "SOLVER";
    public static String HALL_OF_FAME_KEY = "HALL OF FAME";
    public static String START_TIME = "START_TIME";
    public static String RUNNING_TIME = "RUNNING_TIME";

    public static EAsolver load(String path) throws RuntimeException {
        String myPath = MyFile.getPath(path);
        String myname = MyFile.getFullFileName(path);
        String txtFile = MyFile.readFile(myPath + myname);
        if (txtFile == null) {
            // showError("No Simulation loaded!", path);
            return null;
        }
        return loadSolver(txtFile, myPath + myname);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::  L O A D   S O L V E R   F I L E          ::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static EAsolver loadSolver(String txtFile, String simulFileName) {
        //defined solvers
        ArrayList<Genetic> solvers = loadGenetic(txtFile, SOLVER_TYPE, SOLVER_TYPE_LIB);
        //no defined solver
        if (solvers.isEmpty()) {
            showError("No solvers defined !", simulFileName);
            return null;
        }
        EAsolver solver = (EAsolver) solvers.get(0);
        //---------------------------------------------------------------------------
        //read information about solver
        //---------------------------------------------------------------------------
        //solver name
        solver.solverName = loadString(txtFile, SOLVER_NAME);
        if( solver.solverName.isEmpty()){
            solver.solverName = solver.getSimpleName();
        }
        //random seed
        long randomSeed = loadInteger(txtFile, RANDOM_SEED);
        if (randomSeed == -1) {//not defined
            randomSeed = solver.random.nextLong(); //random number
        }
        solver.randomSeed = randomSeed;
        //number of runs
        int numberOfRun = (int) loadInteger(txtFile, NUMBER_OF_RUNS);
        solver.numberOfRun = numberOfRun > 0 ? numberOfRun : 1;
        if (solver.numberOfRun > 1) { //report to array
            solver.report = new ReportSolverArray();
        }
        //stop simulation
        ArrayList<Genetic> stop = loadGenetic(txtFile, STOP_CRITERIA, STOP_CRITERIA_LIB);
        if (!stop.isEmpty()) {
            solver.stop = (StopCriteria) stop.get(0);
        }
        //problem
        ArrayList<Genetic> problems = loadGenetic(txtFile, PROBLEM_NAME, PROBLEM_NAME_LIB);
        if (!problems.isEmpty()) {
            solver.problem = (Solution) problems.get(0);
        }
        //population
        ArrayList<Genetic> populations = loadGenetic(txtFile, POPULATION_TYPE, POPULATION_TYPE_LIB);
        if (!populations.isEmpty()) {
            solver.parents = (SimplePopulation) populations.get(0);
        }
        //Selection
        ArrayList<Genetic> selections = loadGenetic(txtFile, OPERATOR_SELECTION, OPERATOR_SELECTION_LIB);
        if (!selections.isEmpty()) {
            solver.selection = (GeneticOperator) selections.get(0);
        }
        //recombination
        ArrayList<Genetic> recombinations = loadGenetic(txtFile, OPERATOR_RECOMBINATION, OPERATOR_RECOMBINATION_LIB);
        if (!recombinations.isEmpty()) {
            solver.recombination = (GeneticOperator) recombinations.get(0);
        }
        //mutation
        ArrayList<Genetic> mutations = loadGenetic(txtFile, OPERATOR_MUTATION, OPERATOR_MUTATION_LIB);
        if (!mutations.isEmpty()) {
            solver.mutation = (GeneticOperator) mutations.get(0);
        }
        //replacement
        ArrayList<Genetic> replacement = loadGenetic(txtFile, OPERATOR_REPLACEMENT, OPERATOR_REPLACEMENT_LIB);
        if (!replacement.isEmpty()) {
            solver.replacement = (GeneticOperator) replacement.get(0);
        }
        //Rescaling
        ArrayList<Genetic> rescaling = loadGenetic(txtFile, OPERATOR_RESCALING, OPERATOR_RESCALING_LIB);
        if (!rescaling.isEmpty()) {
            solver.rescaling = (GeneticOperator) rescaling.get(0);
        }
        ArrayList<Genetic> statistics = loadGenetic(txtFile, STATISTIC, STATISTIC_LIB);
        //add AbstractStatistics
        for (Genetic stat : statistics) {
            solver.report.addStatistic((AbstractStatistics) stat);
        }
        solver.report.setFileName(simulFileName.isEmpty() ? solver.solverName : simulFileName); // filename or solverName
        solver.updateSolverAtributes();
        solver.report.startStats(solver, false); // reset stats
        return loadEvolution(solver, txtFile);

    }

    private static void showError(String message, String params) {
        System.err.println(message + " \n" + params);
    }

    /**
     * converts solver to String
     *
     * @param solver
     * @return
     */
    public static String toString(EAsolver solver) {
        final int SIZE = 20;
        StringBuffer txt = new StringBuffer();
        txt.append(MyString.setSize(SOLVER_NAME, SIZE) + " = " + solver.solverName + "\n");

        txt.append(MyString.setSize(RANDOM_SEED, SIZE) + " = " + solver.randomSeed + "\n");
        txt.append(MyString.setSize(NUMBER_OF_RUNS, SIZE) + " = " + solver.numberOfRun + "\n");

        txt.append("\n");
        txt.append(MyString.setSize(SOLVER_TYPE, SIZE) + "= " + getSolverType(solver) + "\n");
        txt.append(MyString.setSize(STOP_CRITERIA, SIZE) + "= " + getSimpleGenetic(STOP_CRITERIA_LIB, solver.stop) + "\n");
        txt.append("\n");
        txt.append(MyString.setSize(PROBLEM_NAME, SIZE) + "= " + getSimpleGenetic(PROBLEM_NAME_LIB, solver.problem) + "\n");
        txt.append(MyString.setSize(POPULATION_TYPE, SIZE) + "= " + getSimpleGenetic(POPULATION_TYPE_LIB, solver.parents) + "\n");
        txt.append("\n");
        txt.append(MyString.setSize(OPERATOR_SELECTION, SIZE) + "= " + getSimpleGenetic(OPERATOR_SELECTION_LIB, solver.selection) + "\n");
        txt.append(MyString.setSize(OPERATOR_RECOMBINATION, SIZE) + "= " + getSimpleGenetic(OPERATOR_RECOMBINATION_LIB, solver.recombination) + "\n");
        txt.append(MyString.setSize(OPERATOR_MUTATION, SIZE) + "= " + getSimpleGenetic(OPERATOR_MUTATION_LIB, solver.mutation) + "\n");
        txt.append(MyString.setSize(OPERATOR_REPLACEMENT, SIZE) + "= " + getSimpleGenetic(OPERATOR_REPLACEMENT_LIB, solver.replacement) + "\n");
        txt.append(MyString.setSize(OPERATOR_RESCALING, SIZE) + "= " + getSimpleGenetic(OPERATOR_RESCALING_LIB, solver.rescaling) + "\n");
        txt.append("\n");
        for (AbstractStatistics s : solver.report.getStatistics()) {
            txt.append(MyString.setSize(STATISTIC, SIZE) + "= " + getSimpleGenetic(STATISTIC_LIB, s) + "\n");
        }
        return txt.toString();
    }

    public static String getConfigurationInfo(EAsolver solver) {
        StringBuffer txt = new StringBuffer();
        txt.append(MyString.toComment(MyString.LINE + "\n Solver configuration \n" + MyString.LINE));
        txt.append("\n" + toString(solver) + "\n");
        txt.append(MyString.toComment(MyString.LINE + "\n Solver Information \n" + MyString.LINE + "\n"));
        txt.append(MyString.toComment(getSolverInfo(solver) + "\n"));

        //copyright
        txt.append(MyString.toComment(MyString.getCopyright()));
        return txt.toString();
    }

    public static String getSolverInfo(EAsolver solver) {
        StringBuffer txt = new StringBuffer();
        txt.append(MyString.LINE + "\n S O L V E R \n" + MyString.LINE + "\n" + solver.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n S T O P   C R I T E R I A \n" + MyString.LINE + "\n" + solver.stop.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n P R O B L E M \n" + MyString.LINE + "\n" + solver.problem.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n P O P U L A T I O N \n" + MyString.LINE + "\n" + solver.parents.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n S E L E C T I O N\n" + MyString.LINE + "\n" + solver.selection.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n R E C O M B I N A T I O N\n" + MyString.LINE + "\n" + solver.recombination.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n M U T A T I O N\n" + MyString.LINE + "\n" + solver.mutation.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n R E P L A C E M E N T\n" + MyString.LINE + "\n" + solver.replacement.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n R E S C A L I N G\n" + MyString.LINE + "\n" + solver.rescaling.getInformation());
        txt.append("\n\n" + MyString.LINE + "\n STATISTICS\n" + MyString.LINE + "\n");
        for (AbstractStatistics s : solver.report.getStatistics()) {
            txt.append(MyString.LINE + "\n");
            txt.append(s.getInformation() + "\n");

        }
        return txt.toString();
    }

    public static String getSimpleGenetic(String prefix, Genetic elem) {
        String str = elem.getClass().getCanonicalName().substring(prefix.length());
        str += " " + elem.getParameters();
        return str.trim();

    }

    public static String getSolverType(EAsolver solver) {
        if (solver instanceof EAsolverArray) {
            solver = ((EAsolverArray) solver).template;
        }
        return getSimpleGenetic(SOLVER_TYPE_LIB, solver);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::  L O A D  I N T E G E R S :::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static long loadInteger(String txt, String KEY) {
        try {
            return Long.parseLong(loadString(txt, KEY));
        } catch (Exception e) {
            return -1;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::  L O A D  S T R I N G  :::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String loadString(String txt, String KEY) {
        Scanner file = new Scanner(txt);
        String line;
        while (file.hasNext()) {
            line = file.nextLine().trim();
            if (line.startsWith(KEY)) {
                //remove key
                line = line.substring(KEY.length()).trim();
                // bad definition
                if (line.indexOf("=") != 0) {
                    continue;
                }
                //remove =
                line = line.substring(1).trim();
                try {
                    return line;
                } catch (Exception e) {
                }
            }
        }
        return "";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::  L O A D   E L E M E N T S :::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static ArrayList<Genetic> loadGenetic(String txt, String KEY, String KEY_LIB) {
        ArrayList<Genetic> elements = new ArrayList<>();

        Scanner file = new Scanner(txt);
        String line;
        while (file.hasNext()) {
            line = file.nextLine().trim();
            if (line.startsWith(KEY)) {
                //remove key
                line = line.substring(KEY.length()).trim();
                // bad definition
                if (line.indexOf("=") != 0) {
                    continue;
                }
                //remove =
                line = line.substring(1).trim();
                try {
                    //try to build a genetic alement
                    Genetic g = Genetic.createNew(KEY_LIB + line);
                    elements.add(g);
                } catch (Exception e) {
                    //not a genetic element
                }
            }
        }
        return elements;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::  L O A D   E L E M E N T S :::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static EAsolver loadEvolution(EAsolver solver, String txt) {
        //----------------------------------------------------------------------
        //convert solver to array of solvers
        //----------------------------------------------------------------------
        if (solver.numberOfRun > 1) {
            solver = new EAsolverArray(solver);
        }
        //----------------------------------------------------------------------
        //load simulation data                               
        //----------------------------------------------------------------------
        Scanner file = new Scanner(txt);
        String line;
        while (file.hasNext()) {
            line = file.nextLine().trim();
            if (line.startsWith(START_TIME)) {
                solver.report.startTime = new Date(Long.parseLong(line.substring(START_TIME.length()).trim()));
            } else if (line.startsWith(RUNNING_TIME)) {
                solver.report.runningTime = Long.parseLong(line.substring(RUNNING_TIME.length()).trim());
            } //----------------  Statistic
            else if (line.startsWith(STATISTIC_KEY)) {
                //remove key         
                line = line.substring(STATISTIC_KEY.length()).trim();
                //ignore first number (generation number)
                line = line.substring(line.indexOf(" "));
                solver.report.addDataToStatistics(line);
            } //Solver
            else if (line.startsWith(SOLVER_KEY)) {
                //remove key
                line = line.substring(SOLVER_KEY.length()).trim();
                //ignore first number (generation number)
                line = line.substring(line.indexOf(" "));
                ((ReportSolverArray) solver.report).addDataToResult(line);
            }
        }
        //----------------------------------------------------------------------
        //load Array solvers data                               
        //----------------------------------------------------------------------
        if (solver instanceof EAsolverArray && !solver.report.filename.startsWith(ReportSolver.DEFAULT_FILE_NAME)) {
            //calculate solvers results statistics
            // ((ReportSolverArray) solver.report).calulateEvolutionResult();
            // load individual solvers
            EAsolverArray array = (EAsolverArray) solver;
            for (int i = 0; i < array.arrayOfSolvers.length; i++) {
                //try to load solver
                EAsolver s = load(ReportSolver.getReportFileName(array, i));
                if (s != null) {
                    array.arrayOfSolvers[i] = s;
                }
            }
        }
        //  System.out.println("" + solver.report.getEvolutionString());
        return solver;
    }

    public static final String FILE_EXTENSION = "txt"; // exten
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603260802L;

    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        EAsolver solver = FileSolver.load("_test/solver10.txt");
        System.out.println(solver.toString());
        System.out.println("evolution\n\n" + solver.report.getEvolutionString());
        solver.solve(true);
    }

}
