/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.Business.Business;
import model.Business.ConfigureABusiness;

/**
 *
 * @author kal bugrara
 */
public class PricingModel {

  /**
   * @param args the command line arguments
   */
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                System.err.println("Error starting application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }


    private static void createAndShowGUI() {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Create and initialize business
            Business business = ConfigureABusiness.initialize();
            SupplierDirectory supplierDirectory = business.getSupplierDirectory();
            
            // Create the main container frame
            JFrame mainFrame = new JFrame("Pricing Model System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create main work area panel with CardLayout
            JPanel mainWorkArea = new JPanel(new CardLayout());
            
            // Create login panel
            LoginMainJFrame loginFrame = new LoginMainJFrame(mainWorkArea, supplierDirectory);
            mainWorkArea.add(loginFrame.getContentPane(), "login");
            
            // Add main work area to the frame
            mainFrame.add(mainWorkArea);
            
            // Set size and position
            mainFrame.setSize(800, 600);
            mainFrame.setLocationRelativeTo(null);
            
            // Show frame
            mainFrame.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Error in createAndShowGUI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


}
