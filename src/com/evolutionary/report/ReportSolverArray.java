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

import com.evolutionary.problem.Solution;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.report.statistics.FunctionCalls;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.solverUtils.EAsolverArray;
import com.evolutionary.solverUtils.FileSolver;
import static com.evolutionary.solverUtils.FileSolver.SOLVER_KEY;
import com.utils.MyStatistics;
import com.utils.MyString;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.utils.MyFile;
import java.util.ArrayList;

/**
 * Created on 23/mar/2016, 14:21:34
 *
 * @author zulu - computer
 */
public class ReportSolverArray extends ReportSolver {

    /**
     * Result of statistics in each generation of the solvers if the solvers
     * dont have the generation, last generation of the solver is used
     *
     */
    public ArrayList<StatisticElement[]> evolutionGeneration = new ArrayList<>(); // Result of statistics in each generation
    public ArrayList<Double[]> solversResult = new ArrayList<>(); // Result of statistics in last generation of each solver

    public ReportSolverArray() {
    }

    /**
     * build a report array to solver
     *
     * @param solver
     */
    public ReportSolverArray(EAsolver solver) {
        this.filename = solver.report.filename;
        this.path = solver.report.path;
        this.solver = solver;
        this.verbose = solver.report.verbose;
        //clone statistics template
        for (AbstractStatistics s : solver.report.stats) {
            this.stats.add(s.getClone());
        }
    }

    /**
     * Start the report statistics of to the solver
     *
     * @param solver solver of the statistics
     * @param verbose verbose ?
     */
    @Override
    public void startStats(EAsolver solver, boolean verbose) {
        super.startStats(solver, verbose);
        evolutionGeneration.clear();
        solversResult.clear();
    }

    /**
     * gets the last statistic of the evolution
     *
     * @return StatisticElement of last generation evolution
     */
    public StatisticElement[] getLastValuesStatistcs() {
        return evolutionGeneration.get(evolutionGeneration.size() - 1);
    }

    /**
     * save report using report fileanme
     */
    public void save() {
        save(((EAsolverArray) solver).arrayOfSolvers);
    }

    /**
     * save array of solvers
     *
     * @param arraySolvers array of solvers
     */
    private void save(EAsolver[] arraySolvers) {
        try {
            //if the name is empty sets the name of the solver
            if (MyFile.getFileName(filename).isEmpty()) {
                filename = solver.getClass().getSimpleName() + "_"
                        + solver.problem.getClass().getSimpleName() + "_"
                        + solver.problem.getParameters()
                        + "." + FileSolver.FILE_EXTENSION;
            }

            PrintWriter file = new PrintWriter(path + File.separatorChar + filename);
            //solver information
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(MyString.toComment(" Solver Array Configuration")));
            file.println(MyString.toFileString(MyString.LINE));
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: solver setup
            file.println(MyString.toFileString(solver.toString()));
            file.println(MyString.toFileString(MyString.LINE));
            file.println(MyString.toFileString(MyString.toComment(" Simulation Results")));
            file.println(MyString.toFileString(MyString.LINE));
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: result of simulation            
            file.println(MyString.toFileString(getEvolutionString()));
            file.println("\n" + MyString.toFileString(MyString.getCopyright()));

            file.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < arraySolvers.length; i++) {
            arraySolvers[i].report.save(getReportFileName(solver, i));
        }
    }

    public void updateEvolutionStatistics(EAsolver[] arraySolvers) {
        //reset stats information
        solver.report.startStats(solver, false);
        //  solversResult.clear();
        startTime = arraySolvers[0].report.startTime;
        //----------------------------------------------------------------------        
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: mean time of running solvers
        runningTime = 0;
        for (int i = 0; i < arraySolvers.length; i++) {
            //update hall of fame
            solver.updateHallOfFame(arraySolvers[i].parents);
            //last stat
            solversResult.add(arraySolvers[i].report.evolution.get(arraySolvers[i].report.evolution.size() - 1));
            runningTime += arraySolvers[i].report.runningTime;
        }
        //mean of running time
        runningTime /= arraySolvers.length;
        //----------------------------------------------------------------------
        //calculate statistic of each statistic - LAST VALUE OF SIMULATION
        solversResult.clear();
        for (EAsolver solver : arraySolvers) {
            solversResult.add(solver.report.getLastValues());
        }

        //calcule evolution of the mean
        evolution.clear();
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: calulate maxGeneration
        int maxGen = -1;
        for (EAsolver solver : arraySolvers) {
            if (solver.report.evolution.size() > maxGen) {
                maxGen = solver.report.evolution.size();
            }
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: calculate means of each generation
        for (int i = 0; i < maxGen; i++) {
            //mean ( seted to inherited evolution )
            evolution.add(getMeanAtGeneration(arraySolvers, i));
            //complete statistic
            evolutionGeneration.add(getValuesAtGeneration(arraySolvers, i));
        }
    }

    /**
     * gets the statistic of the statistics in one generation
     *
     * @param arraySolvers array of solvers
     * @param gen generation number
     * @return mean statistics
     */
    private StatisticElement[] getValuesAtGeneration(EAsolver[] arraySolvers, int gen) {
        StatisticElement[] elem = new StatisticElement[arraySolvers[0].report.stats.size()];
        //for each startistic
        for (int stat = 0; stat < arraySolvers[0].report.stats.size(); stat++) {
            //value of each solver
            double[] values = new double[arraySolvers.length];
            for (int i = 0; i < arraySolvers.length; i++) {
                if (gen < arraySolvers[i].report.evolution.size()) // generation defined
                {
                    values[i] = arraySolvers[i].report.evolution.get(gen)[stat];
                } else //last generation 
                {
                    values[i] = arraySolvers[i].report.evolution.get(arraySolvers[i].report.evolution.size() - 1)[stat]; // las generation
                }            //acumulate values         
            }
            //build statistic
            elem[stat] = new StatisticElement(values);
        }
        return elem;
    }

    /**
     * gets the mean of the statistics in one generation
     *
     * @param arraySolvers array of solvers
     * @param gen generation number
     * @return mean statistics
     */
    private Double[] getMeanAtGeneration(EAsolver[] arraySolvers, int gen) {
        //sum of each statistic
        double[] sum = new double[arraySolvers[0].report.stats.size()];
        //for each solver
        for (int i = 0; i < arraySolvers.length; i++) {
            //get statistic
            Double[] stat = null;
            if (gen < arraySolvers[i].report.evolution.size()) // generation defined
            {
                stat = arraySolvers[i].report.evolution.get(gen);
            } else //last generation 
            {
                stat = arraySolvers[i].report.evolution.get(arraySolvers[i].report.evolution.size() - 1); // last generation
            }            //acumulate values
            for (int j = 0; j < stat.length; j++) {
                sum[j] += stat[j];

            }
        }
        Double[] mean = new Double[sum.length];
        //calculate mean
        for (int j = 0; j < sum.length; j++) {
            mean[j] = new Double(sum[j] / arraySolvers.length);

        }
        return mean;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @Override
    public String getEvolutionString() {
        // updateEvolutionStatistics(((EAsolverArray) solver).arrayOfSolvers);

        StringBuffer txt = new StringBuffer(getStatisticsHeader("Result") + "\n");

        StatisticElement[] lasStat = evolutionGeneration.get(evolutionGeneration.size() - 1);

        //print stats      
        txt.append(MyString.setSize(StatisticElement.getMeanLabel() + " ", ReportSolver.FIELD_SIZE));
        for (int j = 0; j < stats.size(); j++) {
            txt.append(" " + MyString.align(lasStat[j].getMean(), ReportSolver.FIELD_SIZE));
        }
        txt.append(MyString.setSize("\n" + StatisticElement.getStdLabel() + " ", ReportSolver.FIELD_SIZE + 1)); // +1 because \n
        for (int j = 0; j < stats.size(); j++) {
            txt.append(" " + MyString.align(lasStat[j].getStd(), ReportSolver.FIELD_SIZE));
        }
        txt.append(MyString.setSize("\n" + StatisticElement.getMinLabel() + " ", ReportSolver.FIELD_SIZE + 1));
        for (int j = 0; j < stats.size(); j++) {
            txt.append(" " + MyString.align(lasStat[j].getMin(), ReportSolver.FIELD_SIZE));
        }
        txt.append(MyString.setSize("\n" + StatisticElement.getMedianLabel() + " ", ReportSolver.FIELD_SIZE + 1));
        for (int j = 0; j < stats.size(); j++) {
            txt.append(" " + MyString.align(lasStat[j].getMedian(), ReportSolver.FIELD_SIZE));
        }
        txt.append(MyString.setSize("\n" + StatisticElement.getMaxLabel() + " ", ReportSolver.FIELD_SIZE + 1));
        for (int j = 0; j < stats.size(); j++) {
            txt.append(" " + MyString.align(lasStat[j].getMax(), ReportSolver.FIELD_SIZE));
        }
        //print solvers info             
        txt.append("\n\n");
        //update information about solvers
        if (solversResult.isEmpty() && ((EAsolverArray) solver).arrayOfSolvers != null) {
            for (EAsolver solver : ((EAsolverArray) solver).arrayOfSolvers) {
                solversResult.add(solver.report.getLastValues());
            }
        }
        for (int i = 0; i < solversResult.size(); i++) {
            //last stat
            Double[] v = solversResult.get(i);
            //solver number
            txt.append(MyString.setSize(SOLVER_KEY + " " + i, ReportSolver.FIELD_SIZE));
            //other stats
            for (int j = 0; j < v.length; j++) {
                txt.append(" " + MyString.align("" + v[j], ReportSolver.FIELD_SIZE));
            }
            txt.append("\n");
        }

        txt.append("\n\n" + FileSolver.HALL_OF_FAME_KEY + "\n");
        for (Solution ind : solver.hallOfFame) {
            txt.append(ind + "\n");
        }

        txt.append(MyString.toFileString(MyString.LINE) + "\n");
        txt.append(MyString.toFileString(MyString.toComment(" Evolution Statistics in generations")) + "\n");
        txt.append(MyString.toFileString(MyString.LINE) + "\n");
//        //means
//        txt.append(super.getEvolutionString());
//        
        txt.append("\n\n" + getArrayEvolutionString());
        return txt.toString();
    }

    public String getArrayEvolutionString() {
        StringBuilder txt = new StringBuilder();

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        // HEADER
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        String header = StatisticElement.getHeaderString(); // mean min , max , etc
        txt.append(MyString.setSize("", ReportSolver.FIELD_SIZE));//empty
        for (int i = 0; i < stats.size(); i++) {
            txt.append(MyString.center(stats.get(i).getSimpleName(), header.length(), '_') + " \t\t");//statistics name            
        }
        txt.append("\n" + MyString.setSize("", ReportSolver.FIELD_SIZE));//empty
        for (int i = 0; i < stats.size(); i++) {// mean min , max , etc
            txt.append(header + " \t\t");
        }
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        // DATA
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        for (int i = 0; i < evolutionGeneration.size(); i++) {
            txt.append("\n" + MyString.setSize(FileSolver.STATISTIC_KEY + " " + i, ReportSolver.FIELD_SIZE));//statistic number
            StatisticElement[] stat = evolutionGeneration.get(i);
            for (StatisticElement statisticElement : stat) {
                txt.append(statisticElement.getDataString() + " \t\t");
            }
        }

        return txt.toString();

    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::      S T A T I S T I C S    R E S U L T S      :::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private StatisticElement getLastStat(int index) {
        return evolutionGeneration.get(evolutionGeneration.size() - 1)[index];
    }

    public double getNumberOfEvalutions(int generation) {
        if (generation >= evolutionGeneration.size()) {
            generation = evolutionGeneration.size() - 1;
        }
        StatisticElement[] values = evolutionGeneration.get(generation);
        for (int i = 0; i < stats.size(); i++) {
            if (stats.get(i).getClass().equals(FunctionCalls.class)) {
                return values[i].getMean();
            }
        }
        return 0;
    }

    //--------------------------------------------------------------------------mean
    public double getMean(int index) {
        return getLastStat(index).getMean();
    }

    public double[] getMeans() {
        double[] v = new double[stats.size()];
        for (int index = 0; index < v.length; index++) {
            v[index] = MyStatistics.mean(getStatisticResult(index));
        }
        return v;
    }

    //--------------------------------------------------------------------------std
    public double getSTD(int index) {
        return getLastStat(index).getStd();
        //return MyStatistics.std(getStatisticResult(index));
    }

    public double[] getSTDs() {
        double[] v = new double[stats.size()];
        for (int index = 0; index < v.length; index++) {
            v[index] = MyStatistics.std(getStatisticResult(index));
        }
        return v;
    }

    //--------------------------------------------------------------------------min
    public double getMin(int index) {
        return getLastStat(index).getMin();
        //return MyStatistics.min(getStatisticResult(index));
    }

    //--------------------------------------------------------------------------max
    public double getMax(int index) {
        return getLastStat(index).getMax();
        //return MyStatistics.max(getStatisticResult(index));
    }

    //--------------------------------------------------------------------------max
    public double getMedian(int index) {
        return getLastStat(index).getMedian();
    }

    //--------------------------------------------------------------------------std
    /**
     * gets the values of the statistics in one evolution simulation
     *
     * @param index index of statistic
     * @return
     */
    public double[] getStatisticResult(int index) {
        double[] values = new double[solversResult.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = solversResult.get(i)[index];
        }
        return values;
    }

    /**
     * gets the values of the statistics in the evolution
     *
     * @param stat statistic
     * @return
     */
    public double[] getStatisticResult(AbstractStatistics stat) {
        for (int i = 0; i < stats.size(); i++) {
            if (stat.getClass().getCanonicalName().equals(stats.get(i).getClass().getCanonicalName())) {
                return getStatisticResult(i);
            }
        }
        return null;
    }

    /**
     * add data to statistics
     *
     * @param data string with number separated by spaces
     */
    public void addDataToStatistics(String data) {
        try {
            String[] d = MyString.splitByWhite(data);
            Double[] evol = new Double[stats.size()];
            int indexValue = 0;

            //all statistics ( evaluations, sucess, . . .)
            StatisticElement[] elem = new StatisticElement[stats.size()];
            for (int i = 0; i < stats.size(); i++) {
                elem[i] = new StatisticElement();

                elem[i].setMean(Double.parseDouble(d[indexValue++]));
                elem[i].setStd(Double.parseDouble(d[indexValue++]));
                elem[i].setMin(Double.parseDouble(d[indexValue++]));
                elem[i].setMedian(Double.parseDouble(d[indexValue++]));
                elem[i].setMax(Double.parseDouble(d[indexValue++]));
                elem[i].setQ1(Double.parseDouble(d[indexValue++]));
                elem[i].setQ3(Double.parseDouble(d[indexValue++]));
                evol[i] = elem[i].getMean();

            }
            this.evolutionGeneration.add(elem);
            evolution.add(evol);
        } catch (Exception e) {
        }

    }

    /**
     * add data to statistics
     *
     * @param data string with number separated by spaces
     */
    public void addDataToResult(String line) {
        //getNumbers
        String[] nTxt = MyString.splitByWhite(line);
        //ignore first number (generation number)
        Double[] v = new Double[nTxt.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = Double.parseDouble(nTxt[i]);
        }
        solversResult.add(v);
    }

    public void updateStats(EAsolver[] arraySolvers) {
        //mean ( seted to inherited evolution )
        evolution.add(getMeanAtGeneration(arraySolvers, Integer.MAX_VALUE)); // last value
        //complete statistic
        evolutionGeneration.add(getValuesAtGeneration(arraySolvers, Integer.MAX_VALUE));// last value
        //----------------------------------------------------------------------verbose
        if (verbose) {
            System.out.println(getStatisticsData(evolution.size() - 1));
        }
        //----------------------------------------------------------------------verbose
    }

    /**
     * get a clone of the object without data
     *
     * @return
     */
    public ReportSolver getCleanClone() {
        ReportSolverArray clone = new ReportSolverArray();
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

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603231421L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        EAsolver solver = FileSolver.load("array.txt");
//        System.out.println(solver.report.getEvolutionString());
        solver.solve(true);
        solver.report.save();
    }
}
