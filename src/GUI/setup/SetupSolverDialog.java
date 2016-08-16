/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.setup;

import GUI.utils.MuGASystem;
import com.evolutionary.Genetic;
import com.evolutionary.operator.GeneticOperator;
import com.evolutionary.population.SimplePopulation;
import com.evolutionary.report.statistics.AbstractStatistics;
import com.evolutionary.solver.EAsolver;
import com.evolutionary.solverUtils.FileSolver;
import com.evolutionary.stopCriteria.StopCriteria;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author zulu
 */
public class SetupSolverDialog extends javax.swing.JDialog {

    public EAsolver solver;
    private EAsolver solverOriginal;

    /**
     * Creates new form SetupSolverFrm
     */
    public SetupSolverDialog(EAsolver s) {
        initComponents();
        initMyComponents(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initMyComponents(EAsolver s) {
        solver = s.getSolverClone();
        solverOriginal = s;
//        solver.InitializeEvolution(false);
        loadSolver(solver);
        setupPop.setPopulation(solver.parents, solver.problem);

//----------------------------------------------------------------------------        
//Listener to solver
        setupSolver.load(MuGASystem.SOLVER, solver.getClass().getSimpleName());
        setupSolver.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver = (EAsolver) source;
                updateSolverInfo();
            }
        });
        //----------------------------------------------------------------------------        
//Listener to StopCriteria
        setupStopcriteria.load(MuGASystem.STOP_CRITERIA, solver.stop.getClass().getSimpleName());
        setupStopcriteria.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.stop = (StopCriteria) source;
                updateSolverInfo();
            }
        });

//----------------------------------------------------------------------------        
//Listener to population
        setupPop.AddEventListener(new PopulationEventListener() {
            @Override
            public void onPopulationChange(SimplePopulation source) {
                solver.setParents(source);
                updateSolverInfo();
            }
        });

//----------------------------------------------------------------------------        
//Listener to Selection
        setupSelection.load(MuGASystem.SELECTION, solver.selection.getClass().getSimpleName());
        setupSelection.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.selection = (GeneticOperator) source;
                updateSolverInfo();
            }
        });
//----------------------------------------------------------------------------        

//----------------------------------------------------------------------------        
//Listener to Recombination
        setupRecombination.load(MuGASystem.RECOMBINATION, solver.recombination.getClass().getSimpleName());
        setupRecombination.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.recombination = (GeneticOperator) source;
                updateSolverInfo();
            }
        });
//----------------------------------------------------------------------------        
//Listener to Mutation
        setupMutation.load(MuGASystem.MUTATION, solver.mutation.getClass().getSimpleName());
        setupMutation.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.mutation = (GeneticOperator) source;
                updateSolverInfo();
            }
        });
        //----------------------------------------------------------------------------        
//Listener to Replacement
        setupReplacement.load(MuGASystem.REPLACEMENT, solver.replacement.getClass().getSimpleName());
        setupReplacement.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.replacement = (GeneticOperator) source;
                updateSolverInfo();
            }
        });
        //----------------------------------------------------------------------------        
//Listener to Rescaling
        setupRescaling.load(MuGASystem.RESCALING, solver.rescaling.getClass().getSimpleName());
        setupRescaling.AddEventListener(new GeneticEventListener() {
            @Override
            public void onGeneticChange(Genetic source) {
                solver.rescaling = (GeneticOperator) source;
                updateSolverInfo();
            }
        });
        //----------------------------------------------------------------------------        
        setupStatistics.load(MuGASystem.STATISTICS, "");
    }

    public void updateSolverInfo() {
        txtEAsolver.setText(FileSolver.getConfigurationInfo(solver));
        txtEAsolver.setCaretPosition(0);
    }

    private void loadSolver(EAsolver s) {
        solver = s;
        txtEAsolver.setText(FileSolver.getConfigurationInfo(solver));
        txtEAsolver.setCaretPosition(0);
        setupSolver.setSelected(solver.getClass().getSimpleName());
        setupStopcriteria.setSelected(solver.stop.getClass().getSimpleName());
        setupSelection.setSelected(solver.selection.getClass().getSimpleName());
        setupRecombination.setSelected(solver.recombination.getClass().getSimpleName());
        setupMutation.setSelected(solver.mutation.getClass().getSimpleName());
        setupReplacement.setSelected(solver.replacement.getClass().getSimpleName());
        setupRescaling.setSelected(solver.rescaling.getClass().getSimpleName());
        setupPop.setPopulation(solver.parents, solver.problem);
        loadStatistics();
    }

    public void loadStatistics() {
        DefaultListModel model = new DefaultListModel();
        ArrayList<AbstractStatistics> stats = solver.report.getStatistics();
        for (AbstractStatistics stat : stats) {
            model.addElement(stat);
        }
        lstStatiscs.setModel(model);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        tbSimulation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEAsolver = new javax.swing.JTextArea();
        pnSolver = new javax.swing.JPanel();
        setupSolver = new GUI.setup.GeneticParameter();
        setupStopcriteria = new GUI.setup.GeneticParameter();
        setupPop = new GUI.setup.PopulationParameters();
        setupSelection = new GUI.setup.GeneticParameter();
        setupRecombination = new GUI.setup.GeneticParameter();
        setupMutation = new GUI.setup.GeneticParameter();
        setupReplacement = new GUI.setup.GeneticParameter();
        setupRescaling = new GUI.setup.GeneticParameter();
        pnStatistics = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstStatiscs = new javax.swing.JList<>();
        setupStatistics = new GUI.setup.GeneticParameter();
        btAddStatistic = new javax.swing.JButton();
        btRemoveStats = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btAcceptSolver = new javax.swing.JButton();
        btCancelSolver = new javax.swing.JButton();

        setTitle("SetupSolver");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        tbSimulation.setLayout(new java.awt.BorderLayout());

        txtEAsolver.setColumns(20);
        txtEAsolver.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtEAsolver.setRows(5);
        jScrollPane1.setViewportView(txtEAsolver);

        tbSimulation.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Simulation", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Setup_Solver-16.png")), tbSimulation); // NOI18N

        setupSolver.setBorder(javax.swing.BorderFactory.createTitledBorder("Solver Type"));

        setupStopcriteria.setBorder(javax.swing.BorderFactory.createTitledBorder("Stop Criteria"));

        javax.swing.GroupLayout pnSolverLayout = new javax.swing.GroupLayout(pnSolver);
        pnSolver.setLayout(pnSolverLayout);
        pnSolverLayout.setHorizontalGroup(
            pnSolverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(setupSolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(setupStopcriteria, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        pnSolverLayout.setVerticalGroup(
            pnSolverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSolverLayout.createSequentialGroup()
                .addComponent(setupStopcriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setupSolver, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Solver", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Setup-16.png")), pnSolver); // NOI18N
        jTabbedPane2.addTab("Problem", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Population-16.png")), setupPop); // NOI18N
        jTabbedPane2.addTab("Selection", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/DNA-16.png")), setupSelection); // NOI18N
        jTabbedPane2.addTab("Recombination", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/DNA-16.png")), setupRecombination); // NOI18N
        jTabbedPane2.addTab("Mutation", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/DNA-16.png")), setupMutation); // NOI18N
        jTabbedPane2.addTab("Replacement", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/DNA-16.png")), setupReplacement); // NOI18N
        jTabbedPane2.addTab("Rescaling", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/DNA-16.png")), setupRescaling); // NOI18N

        lstStatiscs.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Statistics"));
        lstStatiscs.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstStatiscs);

        btAddStatistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/ArrowLeft-32.png"))); // NOI18N
        btAddStatistic.setText("Select");
        btAddStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddStatisticActionPerformed(evt);
            }
        });

        btRemoveStats.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Delete-32.png"))); // NOI18N
        btRemoveStats.setText("Remove");
        btRemoveStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveStatsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnStatisticsLayout = new javax.swing.GroupLayout(pnStatistics);
        pnStatistics.setLayout(pnStatisticsLayout);
        pnStatisticsLayout.setHorizontalGroup(
            pnStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatisticsLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAddStatistic)
                    .addComponent(btRemoveStats))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setupStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
        );
        pnStatisticsLayout.setVerticalGroup(
            pnStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatisticsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnStatisticsLayout.createSequentialGroup()
                        .addComponent(btAddStatistic)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btRemoveStats)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(setupStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Statistics", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Charts-16.png")), pnStatistics); // NOI18N

        getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btAcceptSolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Accept-64.png"))); // NOI18N
        btAcceptSolver.setText("Accept");
        btAcceptSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAcceptSolverActionPerformed(evt);
            }
        });
        jPanel1.add(btAcceptSolver);

        btCancelSolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Discard-64.png"))); // NOI18N
        btCancelSolver.setText("Cancel");
        btCancelSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelSolverActionPerformed(evt);
            }
        });
        jPanel1.add(btCancelSolver);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddStatisticActionPerformed
        solver.report.addStatistic(setupStatistics.genetic.getClass().getCanonicalName());
        loadStatistics();
        updateSolverInfo();
    }//GEN-LAST:event_btAddStatisticActionPerformed

    private void btRemoveStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveStatsActionPerformed
        if (lstStatiscs.getSelectedIndex() >= 0) {
            solver.report.removeStatistic(lstStatiscs.getSelectedIndex());
        }
        loadStatistics();
        updateSolverInfo();
    }//GEN-LAST:event_btRemoveStatsActionPerformed

    private void btAcceptSolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAcceptSolverActionPerformed
        try {
            solver = FileSolver.loadSolver(txtEAsolver.getText(), solver.report.getFullFileName());
            setupPop.createPopulation();
            this.setVisible(false);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btAcceptSolverActionPerformed

    private void btCancelSolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelSolverActionPerformed
        solver = solverOriginal;
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btCancelSolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAcceptSolver;
    private javax.swing.JButton btAddStatistic;
    private javax.swing.JButton btCancelSolver;
    private javax.swing.JButton btRemoveStats;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JList<String> lstStatiscs;
    private javax.swing.JPanel pnSolver;
    private javax.swing.JPanel pnStatistics;
    private GUI.setup.GeneticParameter setupMutation;
    private GUI.setup.PopulationParameters setupPop;
    private GUI.setup.GeneticParameter setupRecombination;
    private GUI.setup.GeneticParameter setupReplacement;
    private GUI.setup.GeneticParameter setupRescaling;
    private GUI.setup.GeneticParameter setupSelection;
    private GUI.setup.GeneticParameter setupSolver;
    private GUI.setup.GeneticParameter setupStatistics;
    private GUI.setup.GeneticParameter setupStopcriteria;
    private javax.swing.JPanel tbSimulation;
    private javax.swing.JTextArea txtEAsolver;
    // End of variables declaration//GEN-END:variables
}
