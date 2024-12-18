/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import model.Supplier.Supplier;

/**
 *
 * @author vinaypawar
 */
public class SupplierWorkAreaJPanel extends javax.swing.JPanel {

    JPanel mainWorkArea;
    Supplier supplier;

    /**
     * Creates new form ProductManagerWorkAreaJPanel
     */
    public SupplierWorkAreaJPanel(JPanel mainWorkArea, Supplier supplier) {

        initComponents();
        this.mainWorkArea = mainWorkArea;
        this.supplier = supplier;

        setPreferredSize(new Dimension(900, 800));

        // Make the split pane maintain its divider location
        splitPane.setResizeWeight(0.1);
        if (supplier != null) {
            lblWelcome.setText("Welcome, " + supplier.getName());
        }
        refreshUI();

    }

    public void refreshUI() {
        if (supplier != null) {
            lblWelcome.setText("Welcome, " + supplier.getName());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        workArea = new javax.swing.JPanel();
        menuBar = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        btnPricePerformance = new javax.swing.JButton();
        btnAdjustPrice = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        btnFinalReport = new javax.swing.JButton();

        splitPane.setDividerLocation(90);
        splitPane.setDividerSize(1);
        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.1);
        splitPane.setToolTipText("");

        workArea.setLayout(new java.awt.CardLayout());
        splitPane.setBottomComponent(workArea);

        menuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblWelcome.setText("<WelcomeMsg>");
        menuBar.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        btnPricePerformance.setText("Browse Price Performance");
        btnPricePerformance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPricePerformanceActionPerformed(evt);
            }
        });
        menuBar.add(btnPricePerformance, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        btnAdjustPrice.setText("Price Adjustment");
        btnAdjustPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjustPriceActionPerformed(evt);
            }
        });
        menuBar.add(btnAdjustPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        menuBar.add(btnLogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, -1));

        btnFinalReport.setText("Final Report");
        btnFinalReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalReportActionPerformed(evt);
            }
        });
        menuBar.add(btnFinalReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, -1));

        splitPane.setLeftComponent(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPricePerformanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPricePerformanceActionPerformed
        // TODO add your handling code here:

        PricePerformanceJPanel ppjp = new PricePerformanceJPanel(workArea, supplier);
        workArea.add(ppjp, "PricePerformancePanel");
        CardLayout layout = (CardLayout) workArea.getLayout();
        layout.show(workArea, "PricePerformancePanel");
    }//GEN-LAST:event_btnPricePerformanceActionPerformed

    private void btnAdjustPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdjustPriceActionPerformed
        // TODO add your handling code here:
        PriceAdjustmentJPanel atpjp = new PriceAdjustmentJPanel(workArea, supplier);
        workArea.add("AdjustTargetPriceJPanel", atpjp);
        CardLayout layout = (CardLayout) workArea.getLayout();
        layout.next(workArea);
    }//GEN-LAST:event_btnAdjustPriceActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        mainWorkArea.remove(this);

        // Use CardLayout to switch back to login panel
        CardLayout layout = (CardLayout) mainWorkArea.getLayout();
        layout.first(mainWorkArea);

        // Optional: refresh the login panel if needed
        for (Component comp : mainWorkArea.getComponents()) {
            if (comp instanceof LoginMainJFrame) {
                ((LoginMainJFrame) comp).populateSupplierCombo();
            }
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnFinalReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalReportActionPerformed
        // TODO add your handling code here:
        System.out.println("Final Report button clicked"); // Debug message

        try {
            FinalReportJPanel finalReport = new FinalReportJPanel(mainWorkArea, supplier);  // Changed workArea to mainWorkArea
            mainWorkArea.add(finalReport, "FinalReportPanel");  // Changed workArea to mainWorkArea
            CardLayout layout = (CardLayout) mainWorkArea.getLayout();  // Changed workArea to mainWorkArea
            layout.show(mainWorkArea, "FinalReportPanel");
            System.out.println("Final Report panel added and shown");
        } catch (Exception e) {
            System.out.println("Error showing Final Report panel: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnFinalReportActionPerformed
    public String toString() {
        return "Supplier";
    }

    @Override
    public void addNotify() {
        super.addNotify();
        refreshUI();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdjustPrice;
    private javax.swing.JButton btnFinalReport;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnPricePerformance;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel menuBar;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JPanel workArea;
    // End of variables declaration//GEN-END:variables
}
