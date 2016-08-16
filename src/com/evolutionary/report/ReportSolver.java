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

import com.evolutionary.Genetic;
import com.evolutionary.problem.Solution;
import com.evolutionary.report.statistics.BestFitness;
import com.evolutionary.report.statistics.FunctionCalls;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.report.statistics.SucessRate;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.solverUtils.FileSolver;
import static com.evolutionary.solverUtils.FileSolver.HALL_OF_FAME_KEY;
import com.utils.MyFile;
import com.utils.MyString;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.evolutionary.solverUtils.FileSolver.STATISTIC_KEY;

/**
 * Created on 20/mar/2016, 18:22:20
 *
 * @author zulu - computer
 */
public class ReportSolver {

    public static String DEFAULT_FILE_NAME = "DefaultSolver";
    protected ArrayList<AbstractStatistics> stats = new ArrayList<>(); // template of statistics
    public ArrayList<Double[]> evolution = new ArrayList<>(); // evolution of each statistic    
    public Date startTime = new Date(); // sting time of evolution
    public long runningTime = 0;
    public String path = "./";//+ File.separatorChar + "stats";
    public String filename = DEFAULT_FILE_NAME; // file to save simulation

    public EAsolver solver; // solver to produce report
    public boolean verbose;

    /**
     * get a clone of the object without data
     *
     * @return
     */
    public ReportSolver getCleanClone() {
        ReportSolver clone = new ReportSolver();
        clone.filename = filename;
        clone.path = path;
        clone.verbose = verbose;
        //clone statistics template
        for (AbstractStatistics s : stats) {
            AbstractStatistics sClone = (AbstractStatistics) s.getClone();
            sClone.setSolver(solver);
            clone.stats.add(sClone);
        }
        return clone;
    }

    /**
     * change the filename and the path of the report
     *
     * @param fileName new filename with path ./folder/folder/file.ext
     */
    public void setFileName(String fileName) {
        File file = new File(fileName);
        fileName = file.getAbsolutePath();
        file = new File(fileName);
        this.path = file.getParent();// + File.separatorChar + "stats";
        (new File(path)).mkdirs();
        this.filename = MyFile.getFullFileName(fileName);
        if (filename.isEmpty()) {
            fileName = DEFAULT_FILE_NAME;
        }
        if (!this.filename.endsWith(FileSolver.FILE_EXTENSION)) {
            this.filename += "." + FileSolver.FILE_EXTENSION;
        }
    }

    public String getFullFileName() {
        if (path.endsWith(File.separatorChar + "")) {
            return path + filename;
        }
        return path + File.separatorChar + filename;
    }

    /**
     * update the solver of the report
     *
     * @param solver
     */
    public void setSolver(EAsolver solver) {
        this.solver = solver;
        (new File(path)).mkdirs();
    }

    /**
     * update the solver of the report using an index this method change the
     * fileName of the report
     *
     * @param solver solver of the report
     * @param number index of the solver
     */
    public void setSolver(EAsolver solver, int number) {
        this.solver = solver;
        String fullpath = getReportFileName(solver, number);
        path = MyFile.getPath(fullpath);
        filename = fullpath.substring(path.length());
        if (verbose) {
            (new File(path)).mkdirs();
        }
    }

    public static String getReportFileName(EAsolver solver, int number) {
        String path = solver.report.path;
         if (MyFile.getFileName(solver.report.filename).isEmpty()) {
            solver.report.filename = DEFAULT_FILE_NAME;
        }
        String filename = MyFile.getFileName(solver.report.filename) + "_"
                + String.format("%0" + 4 + "d", number)
                + "." + FileSolver.FILE_EXTENSION;
        return MyFile.getPath(path) + filename;
    }

    /**
     * append one statistic to the report if the statistic not exists yet
     *
     * @param stat string with name of the class
     */
    public void addStatistic(String stat) {
        for (AbstractStatistics stat1 : stats) {
            if (stat1.getClass().getCanonicalName().equals(stat)) {
                return;
            }
        }
        stats.add((AbstractStatistics) Genetic.createNew(stat));
    }

    /**
     * append one statistic to the report if the statistic not exists yet
     *
     * @param stat statistic object
     */
    public void addStatistic(AbstractStatistics stat) {
        for (AbstractStatistics stat1 : stats) {
            if (stat1.getClass().equals(stat.getClass())) {
                return;
            }
        }
        stats.add(stat);
    }

    /**
     * remove statistic of the report
     *
     * @param index index of statistic
     */
    public void removeStatistic(int index) {
        stats.remove(index);
    }

    public ArrayList<AbstractStatistics> getStatistics() {
        return stats;
    }

    /**
     * last values of the statistics
     *
     * @return
     */
    public Double[] getLastValues() {
        return evolution.get(evolution.size() - 1);
    }

    /**
     * return a string with the name of statistics
     *
     * @param title title to header
     * @return
     */
    public String getStatisticsHeader(String title) {
        StringBuilder txt = new StringBuilder();
        txt.append((MyString.setSize(title, FIELD_SIZE))); // name of the line
        for (int i = 0; i < stats.size(); i++) {
            txt.append(" " + MyString.align(stats.get(i).toString(), FIELD_SIZE));
        }
        return txt.toString();
    }

    /**
     * reset stats to begins the evolution
     *
     * @param solver solver to report
     * @param verbose print output ?
     */
    public void startStats(EAsolver solver, boolean verbose) {
        this.solver = solver;
        this.verbose = verbose;
        this.solver.numEvaluations = 0;
        this.solver.numGeneration = 0;
        solver.hallOfFame.clear();

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::by deafult compute sucess rate and best fitness
        if (stats.isEmpty()) {
//            stats.add(new Generation());
            stats.add(new BestFitness());
            stats.add(new SucessRate());
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::add functions call to produce charts
        addStatistic(new FunctionCalls());
        startTime = new Date();
        //initialize array of statistics
        evolution = new ArrayList<>();
        if (verbose) {
            //TITLE OF STATS
            System.out.println("\n" + getStatisticsHeader("Solver"));
        }
    }

    /**
     * gets the statistic in the generation
     *
     * @param generation
     * @return
     */
    public String getStatisticsData(int generation) {
        StringBuilder txt = new StringBuilder();
        txt.append(MyString.setSize(STATISTIC_KEY + " " + generation, FIELD_SIZE));
        Double[] values = evolution.get(generation);
        for (Double value : values) {
            txt.append(" " + MyString.align("" + value, FIELD_SIZE));
        }
        txt.append(" " + solver.parents.getBest());
        return txt.toString();
    }

    /**
     * gets the statistic in the generation
     *
     * @param generation
     * @return
     */
    public double getStatisticsData(int generation, AbstractStatistics stat) {
        Double[] values = evolution.get(generation);
        for (int i = 0; i < stats.size(); i++) {
            if (stats.get(i).getClass().equals(stat.getClass())) {
                return values[i];
            }
        }
        return 0;
    }

    /**
     * update statistics with the last values of the solver
     */
    public void updateStats() {
        Double[] values = new Double[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            values[i] = stats.get(i).execute(solver);
        }
        evolution.add(values);
        //----------------------------------------------------------------------verbose
        if (verbose) {
            System.out.println(getStatisticsData(evolution.size() - 1));
        }
        //----------------------------------------------------------------------verbose
    }

    /**
     * finish stats - calculate running time
     */
    public void finishStats() {
        runningTime = (new Date()).getTime() - startTime.getTime();
        if (verbose) {
            save();
        }
    }

    /**
     * save the report in new File
     *
     * @param path new file of the report
     */
    public void save(String path) {
        setFileName(path);
        save();
    }

    /**
     * save the report in current fileName
     */
    public void save() {
        PrintWriter file = null;
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }

            file = new PrintWriter(path + File.separatorChar + filename);
             file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(MyString.toComment(" Solver Configuration")));
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(solver.toString()));
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(MyString.toComment(" Solver Evolution ")));
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(getEvolutionString()));
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(MyString.toComment(solver.parents.toString())));
            file.println(MyString.toFileString(MyString.toComment(MyString.getCopyright())));
            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportSolver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            file.close();
        }
    }

    protected void save(PrintWriter txtSimulation) {
        try {

            txtSimulation.println("#random seed   = " + solver.randomSeed);
            txtSimulation.println("#START_TIME    = " + MyString.toString(startTime));
            txtSimulation.println("#FINISH_TIME   = " + MyString.toString(new Date(startTime.getTime() + runningTime)));
            txtSimulation.println("#RUNNING_TIME  = " + runningTime);

            //TITLE OF STATS
            txtSimulation.println(getStatisticsHeader("Result"));
            for (int i = 0; i < evolution.size(); i++) {
                //stats data
                txtSimulation.println(getStatisticsData(i));
            }
            txtSimulation.println();
            // txtSimulation.print(NEWLINE + NEWLINE + "Main Population" + NEWLINE + solver.parents.toString().replaceAll("\n", NEWLINE));

        } catch (Exception ex) {
            Logger.getLogger(ReportSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * calculate the string result of the evolution:
     *
     * @return
     */
    public String getEvolutionString() {
        StringBuffer txt = new StringBuffer();
        try {
            txt.append(MyString.toComment(FileSolver.START_TIME + " " + MyString.toString(startTime)) + "\n");
            txt.append(FileSolver.START_TIME + " " + startTime.getTime() + "\n");
            txt.append(FileSolver.RUNNING_TIME + " " + runningTime + "\n");

            //TITLE OF STATS
            txt.append(MyString.setSize("", FIELD_SIZE));
            for (int i = 0; i < stats.size(); i++) {
                txt.append(" " + MyString.align(stats.get(i).toString(), FIELD_SIZE));
            }
            //Stats data 
            for (int i = 0; i < evolution.size(); i++) {
                txt.append("\n");
                Double[] v = evolution.get(i);
                txt.append("" + MyString.setSize(STATISTIC_KEY + " " + i, FIELD_SIZE));
                for (int j = 0; j < v.length; j++) {
                    txt.append(" " + MyString.align(v[j], FIELD_SIZE));
                }
            }
            txt.append("\n\n" + HALL_OF_FAME_KEY + "\n");
            for (Solution ind : solver.hallOfFame) {
                txt.append(ind + "\n");
            }

        } catch (Exception ex) {
            Logger.getLogger(ReportSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt.toString();
    }

    /**
     * add data to statistics
     *
     * @param data string with number separated by spaces
     */
    public void addDataToStatistics(String data) {
        try {
            String[] d = MyString.splitByWhite(data);
            Double[] elem = new Double[d.length];
            for (int i = 0; i < elem.length; i++) {
                elem[i] = Double.parseDouble(d[i]);
            }
            evolution.add(elem);
        } catch (Exception e) {
        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603201822L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    public static int FIELD_SIZE = 20; // number of chars to print filds 
    //public static String NEWLINE = "\r\n"; //new to line to text files

    public static void main(String[] args) {
        EAsolver solver = FileSolver.load("solver.txt");
        solver.solve(true);
    }
}
