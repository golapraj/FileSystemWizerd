package Manager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Rima
 * @version 12.05.48 
 */

public class Homepage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
     
        try {
           javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
       } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
           JOptionPane.showMessageDialog(null, "Could not set NimbusLookAndFeel\nClass not found Exception\nSee Logs for details", "Fatal Error!", JOptionPane.ERROR_MESSAGE);
        }
        MainWindows app = new MainWindows();
        app.setResizable(false);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLocationRelativeTo(null);
        app.setFont(new java.awt.Font("Siyam Rupali", 0, 11));
        app.setVisible(true);
    }
}
