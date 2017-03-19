package pkg124guirevision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//popup class
public class RowPopup extends JPopupMenu
{   
    
    Connection conn;
    static String patId;
    static String ln;
    static String fn;
    static String mi;
    static String contact;
    static JFrame gui;
    static String address;
    static String birth;
    static String age;

    //constructor
    public RowPopup(String patId, String ln, String fn, String mi, String address, String contact, String birth)
    {
        this.patId = patId;
        this.ln = ln;
        this.fn = fn;
        this.mi = mi;
        this.address = address;
        this.contact = contact;
        this.birth = birth;
    }
    //for closing of DentalRecords
    public RowPopup(JFrame gui)
    {
        this.gui = gui;
    }
    
    public RowPopup(JTable table)
    {
        //create popup items
        JMenuItem view = new JMenuItem("View This Patient's Record");
        JMenuItem edit = new JMenuItem("Update This Patient's Record");
        JMenuItem delete = new JMenuItem("Archive This Patient's Record");
        JMenuItem addApp = new JMenuItem("Add Appointment to This Patient");
        
        
        //when addApp is click
        addApp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new AddAppointment(patId, ln, fn, mi, contact).setVisible(true);
                gui.dispose();
            }
        });

        //when update/edit is click
        edit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               UpdatePatientRecord upr=  new UpdatePatientRecord(patId, ln, fn);
             // upr.updatePatientID.setText(patId);
               upr.setVisible(true);
                
                gui.dispose();
            }
        });
        
        //when delete is click
        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to archive " + ln + ", " + fn + "?", "", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    
                    try
                    {
                        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Dental Clinic", "maximus", "maximus123");
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                    try
                    {   
                        String ts = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
                        Object b = birth;
                        String sqlPR = "SELECT * FROM PATIENT_RECORD  WHERE PATIENT_ID =? "; 
                        PreparedStatement p = conn.prepareStatement(sqlPR);
                        p.setInt(1, Integer.parseInt(patId));
                        ResultSet rs = p.executeQuery();
                        while(rs.next())
                        {
                            String qry = "INSERT INTO PATIENT_RECORD_ARCHIVED(PATIENT_ID, LASTNAME, FIRSTNAME, MIDDLE_INIT, ADDRESS , CONTACT_NUMBER, BIRTHDAY, DATE_ISSUED, DATE_ARCHIVED) VALUES(?, ?, ?, ?, ?, ?, ?, ?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(qry);
                            pstmt.setInt(1, Integer.parseInt(patId));
                            pstmt.setString(2, ln);
                            pstmt.setString(3,fn);
                            pstmt.setString(4, mi);
                            pstmt.setString(5, address);
                            pstmt.setString(6, contact);
                            pstmt.setObject(7, b);
                            pstmt.setString(8, rs.getString("DATE_ISSUED"));
                            pstmt.setString(9, ts);
                            pstmt.executeUpdate();
                            
                            String qry2 = "INSERT INTO HISTORY(DATE, EVENT) VALUES(?,?)";
            
                            PreparedStatement pstmt2 = conn.prepareStatement(qry2);
                            pstmt2.setString(1, ts);
                            pstmt2.setString(2,"Deleted Patient Record for  Patient # "+patId+" Name: " +ln +", "+fn+".");
                            pstmt2.executeUpdate();    
                        }
                        rs.close();
                        
                        String sqld = "SELECT * FROM DENTAL_RECORD WHERE PATIENT_ID =? "; 
                        PreparedStatement pd = conn.prepareStatement(sqld);
                        pd.setInt(1, Integer.parseInt(patId));
                        ResultSet rsd = pd.executeQuery();
                        while(rsd.next())
                        {
                            String qry = "INSERT INTO DENTAL_RECORD_ARCHIVED(PATIENT_ID, TOOTH_NO, PATIENT_DATE, DESCRIPTION, TOTAL_AMOUNT , AMOUNT_PAID,BALANCE, DATE_ARCHIVED) VALUES( ?, ?, ?, ?, ?, ?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(qry);
                            pstmt.setInt(1, rsd.getInt("PATIENT_ID"));
                            pstmt.setString(2, rsd.getString("TOOTH_NO"));
                            pstmt.setObject(3, rsd.getObject("PATIENT_DATE"));
                            pstmt.setString(4, rsd.getString("DESCRIPTION"));
                            pstmt.setDouble(5, rsd.getDouble("TOTAL_AMOUNT"));
                            pstmt.setDouble(6, rsd.getDouble("AMOUNT_PAID"));
                            pstmt.setDouble(7, rsd.getDouble("BALANCE"));
                            pstmt.setString(8, ts);
                            pstmt.executeUpdate();
                            
                            String qry2 = "INSERT INTO HISTORY(DATE, EVENT) VALUES(?,?)";
            
                            PreparedStatement pstmt2 = conn.prepareStatement(qry2);
                            pstmt2.setString(1, ts);
                            pstmt2.setString(2,"Deleted Dental Record for  Patient # "+patId+" Name: " +ln +", "+fn+".");
                            pstmt2.executeUpdate();   
                        }
                        rsd.close();
                        
                        String sqla = "SELECT * FROM APPOINTMENT WHERE PATIENT_ID =? "; 
                        PreparedStatement pa = conn.prepareStatement(sqla);
                        pa.setInt(1, Integer.parseInt(patId));
                        ResultSet rsa = pa.executeQuery();
                        while(rsa.next())
                        {
                            String qry = "INSERT INTO APPOINTMENT_ARCHIVED(PATIENT_ID, LASTNAME, FIRSTNAME, MIDDLEINITIAL, CONTACT_NUMBER , VISIT_DATE,VISIT_TIME,DATE_ARCHIVED) VALUES(?, ?, ?, ?, ?, ?, ?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(qry);
                            pstmt.setInt(1, rsa.getInt("PATIENT_ID"));
                            pstmt.setString(2, rsa.getString("LASTNAME"));
                            pstmt.setString(3, rsa.getString("FIRSTNAME"));
                            pstmt.setString(4, rsa.getString("MIDDLEINITIAL"));
                            pstmt.setString(5, rsa.getString("CONTACT_NUMBER"));
                            pstmt.setObject(6, rsa.getObject("VISIT_DATE"));
                            pstmt.setObject(7, rsa.getObject("VISIT_TIME"));
                            pstmt.setString(8, ts);
                            pstmt.executeUpdate();
                            
                            String qry2 = "INSERT INTO HISTORY(DATE, EVENT) VALUES(?,?)";
            
                            PreparedStatement pstmt2 = conn.prepareStatement(qry2);
                            pstmt2.setString(1, ts);
                            pstmt2.setString(2,"Canceled Appointment Record for  Patient # "+patId+" Name: " +ln +", "+fn+".");
                            pstmt2.executeUpdate();   
                        }
                        rsa.close();
                        
                        
                        String qrydd = "DELETE FROM DENTAL_RECORD WHERE PATIENT_ID = ?";
                        PreparedStatement pstmtdd = conn.prepareStatement(qrydd);
                        pstmtdd.setInt(1, Integer.parseInt(patId));
                        pstmtdd.executeUpdate();
                        
                        String qrya = "DELETE FROM APPOINTMENT WHERE PATIENT_ID = ?";
                        PreparedStatement pstmta = conn.prepareStatement(qrya);
                        pstmta.setInt(1, Integer.parseInt(patId));
                        pstmta.executeUpdate();
                        
                        String qry1 = "DELETE FROM PATIENT_RECORD WHERE PATIENT_ID = ?";
                        PreparedStatement pstmt1 = conn.prepareStatement(qry1);
                        pstmt1.setInt(1, Integer.parseInt(patId));
                        pstmt1.executeUpdate();
                        
                        
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        JOptionPane.showMessageDialog(null, "The data has successfully archived!");

                    new PatientRecords().setVisible(true);
                    gui.dispose();
                }
            }
        });

        //when view is click
        DentalRecords dr = new DentalRecords();
        view.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                   
                try
                {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");
                    String url = "jdbc:derby://localhost:1527/Dental Clinic";
                    String user = "maximus";
                    String pass = "maximus123";

                    Connection con = DriverManager.getConnection(url, user, pass);

                    String sqlPR = "SELECT * FROM DENTAL_RECORD  WHERE PATIENT_ID =? "; 
                    PreparedStatement pstmt = con.prepareStatement(sqlPR);
                    pstmt.setInt(1, Integer.parseInt(patId));
                    ResultSet rs = pstmt.executeQuery();
                    Statement stmt = con.createStatement();



                    DefaultTableModel model2 = (DefaultTableModel) dr.dentalTable.getModel();
                    Object rowData[] = new Object[7];
                    while(rs.next())
                    {
                        rowData[0] = rs.getString("TRANSACTION_ID");
                        rowData[1] = rs.getString("TOOTH_NO");
                        rowData[2] = rs.getString("PATIENT_DATE");
                        rowData[3] = rs.getString("DESCRIPTION");
                        rowData[4] = rs.getDouble("TOTAL_AMOUNT");
                        rowData[5] = rs.getDouble("AMOUNT_PAID");
                        rowData[6] = rs.getDouble("BALANCE"); 

                        model2.addRow(rowData);

                    }

                    dr.patientID.setText(patId.toString());
                    dr.patientLName.setText(ln);
                    dr.patientFName.setText(fn);


                    rs.close();
                    stmt.close();
                }
                catch(Exception s){

                }


                dr.setVisible(true); 
                gui.dispose();
                dr.pack();



                
                
                
            }
        });

        //add items to popup
        add(edit);
        add(delete);
        add(new JSeparator());
        add(view);
        add(new JSeparator());
        add(addApp);
    }
}
