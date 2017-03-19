package pkg124guirevision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import static pkg124guirevision.RowPopup.birth;

//popup class
public class RowPopup2 extends JPopupMenu
{
    Connection conn;
    static String patId;
    static String appId;
    static String ln;
    static String fn;
    static String mi;
    static String contact;
    static String date;
    static String time;
    static JFrame gui;

    //constructor
    public RowPopup2(String patId, String appId, String ln, String fn, String mi, String contact, String date, String time)
    {
        this.patId = patId;
        this.appId = appId;
        this.ln = ln;
        this.fn = fn;
        this.mi = mi;
        this.contact = contact;
        this.date = date;
        this.time = time;
    }
    //for closing of DentalRecords
    public RowPopup2(JFrame gui)
    {
        this.gui = gui;
    }
    
    public RowPopup2(JTable table)
    {
        //create popup items
        JMenuItem edit = new JMenuItem("Reschedule This Appointment");
        JMenuItem delete = new JMenuItem("Cancel This Appointment");

        //when edit is click
        edit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new UpdateAppointment(patId,appId, ln, fn, mi, contact).setVisible(true);
                gui.dispose();
            }
        });

        //when delete is click
        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel appointment with " + ln + ", " + fn + " in " + time + "??" ,"",JOptionPane.YES_NO_OPTION);
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
                        String qry = "INSERT INTO APPOINTMENT_ARCHIVED(PATIENT_ID, LASTNAME, FIRSTNAME, MIDDLEINITIAL, CONTACT_NUMBER , VISIT_DATE,VISIT_TIME,DATE_ARCHIVED) VALUES(?, ?, ?, ?, ?, ?, ?,?)";
                        PreparedStatement pstmt = conn.prepareStatement(qry);
                        pstmt.setInt(1, Integer.parseInt(patId));
                        pstmt.setString(2, ln);
                        pstmt.setString(3, fn);
                        pstmt.setString(4, mi);
                        pstmt.setString(5, contact);
                        pstmt.setObject(6, date);
                        pstmt.setObject(7, time);
                        pstmt.setString(8, ts);
                        pstmt.executeUpdate();
                        
                        String qryh = "INSERT INTO HISTORY(DATE, EVENT) VALUES(?,?)";
                        PreparedStatement pstmth = conn.prepareStatement(qryh);
                        pstmth.setString(1, ts);
                        pstmth.setString(2,"Canceled Appointment Record for  Patient # "+patId+" Name: " +ln+ ", "+fn+".");
                        pstmth.executeUpdate(); 
                        
                        String qrya = "DELETE FROM APPOINTMENT WHERE APP_ID = ?";
                        PreparedStatement pstmta = conn.prepareStatement(qrya);
                        pstmta.setInt(1, Integer.parseInt(appId));
                        pstmta.executeUpdate();
                        
                        new PatientRecords().setVisible(true);
                        gui.dispose();
                    }
                    catch(Exception ex)
                    {
                        System.out.println(e);
                    }
                }
            }
        });

        //add items to popup
        add(edit);
        add(delete);
    }
}
