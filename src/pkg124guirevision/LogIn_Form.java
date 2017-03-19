/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg124guirevision;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author renziverdb
 */
public class LogIn_Form extends javax.swing.JFrame {

    /**
     * Creates new form LogIn_Form
     */
    public LogIn_Form() {
        initComponents();
    }

    public void login()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/Dental Clinic";
            String user = "maximus";
            String pass = "maximus123";
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LOGIN");
            boolean correct = false;
            while(rs.next())
            {
                if(userNameField.getText().equals(rs.getString("USERNAME")) && passWordField.getText().equals(rs.getString("PASSWORD")))
                {
                    correct = true;
                }
            }
            if(correct)
            {
                PatientRecords dr = new PatientRecords();
                new RowPopup(dr);
                new RowPopup2(dr);
                dr.setVisible(true);
                super.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Incorrect username and password");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        userNameField = new javax.swing.JTextField();
        passWordField = new javax.swing.JPasswordField();
        logInButton = new javax.swing.JButton();
        forgotPass = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log In | Diamese-Montero Dental Clinic");
        setMinimumSize(new java.awt.Dimension(900, 588));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userNameField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        userNameField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        getContentPane().add(userNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 270, 30));

        passWordField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passWordField.setToolTipText("");
        passWordField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        passWordField.setMinimumSize(new java.awt.Dimension(2, 22));
        passWordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passWordFieldKeyPressed(evt);
            }
        });
        getContentPane().add(passWordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 270, 20));

        logInButton.setBackground(new java.awt.Color(189, 195, 199));
        logInButton.setFont(new java.awt.Font("Avenir", 0, 13)); // NOI18N
        logInButton.setText("LOG IN");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 90, -1));

        forgotPass.setBackground(new java.awt.Color(189, 195, 199));
        forgotPass.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        forgotPass.setText("Forgot Pass");
        forgotPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotPassActionPerformed(evt);
            }
        });
        getContentPane().add(forgotPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, 110, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/resizedbg.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 590));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
       login();
    }                                           

    private void passWordFieldKeyPressed(java.awt.event.KeyEvent evt)                                         
    {                                             
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            login();
        }
    }                                        

    private void forgotPassActionPerformed(java.awt.event.ActionEvent evt) {                                           
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/Dental Clinic";
            String user = "maximus";
            String pass = "maximus123";
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LOGIN");
            boolean correct = false;
            if(userNameField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Provide a username in the username field");
            }
            else{
                while(rs.next()){
                    if(userNameField.getText().equals(rs.getString("USERNAME"))){
                        JOptionPane.showMessageDialog(null, "Your password is '" + rs.getString("PASSWORD") + "'");
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }                                          

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LogIn_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel background;
    private javax.swing.JButton forgotPass;
    private javax.swing.JButton logInButton;
    private javax.swing.JPasswordField passWordField;
    private javax.swing.JTextField userNameField;
    // End of variables declaration                   
}
