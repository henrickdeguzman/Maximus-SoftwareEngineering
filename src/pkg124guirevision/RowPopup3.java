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
public class RowPopup3 extends JPopupMenu
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
    public RowPopup3(String patId, String appId, String ln, String fn, String mi, String contact, String date, String time)
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
    public RowPopup3(JFrame gui)
    {
        this.gui = gui;
    }
    
    public RowPopup3(JTable table)
    {
        //create popup items
        JMenuItem edit = new JMenuItem("Update This Dental Record");
        JMenuItem delete = new JMenuItem("Archive This Dental Record");

        //when edit is click
        edit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });

        //when delete is click
        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });

        //add items to popup
        add(edit);
        add(delete);
    }
}
