/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg124guirevision;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author renziverdb
 */
public class PatientRecords extends javax.swing.JFrame
{

    /**
     * Creates new form DentalRecords
     */
    Connection con;
    Statement stmt;
    ResultSet rs;
    PreparedStatement pstmt;
    String patId = "";
    String appId = "";
    String ln = "";
    String fn = "";
    String mi = "";
    String contact = "";
    String date = "";
    String time = "";
    String address = "";
    String birth = "";
    String age = "";
    int[] y = {0};
    int[] m = {0};
    int totalPat = 0;
    
    public PatientRecords()
    {
        initComponents();
        
        
        
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/Dental Clinic";
            String user = "maximus";
            String pass = "maximus123";
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            //Dental
            rs = stmt.executeQuery("SELECT * FROM PATIENT_RECORD ORDER BY PATIENT_ID");
            DefaultTableModel model = (DefaultTableModel) dentalResultTable.getModel();
            String[] newPat;
            String[] datePat;
            String rowData[] = new String[8];
            while (rs.next())
            {
                rowData[0] = rs.getString("PATIENT_ID");
                rowData[1] = rs.getString("LAST_NAME");
                rowData[2] = rs.getString("FIRST_NAME");
                rowData[3] = rs.getString("MIDDLE_INIT");
                rowData[4] = rs.getString("ADDRESS");
                rowData[5] = rs.getString("CONTACT_NUM");
                rowData[6] = rs.getString("BIRTHDAY");
                String[] date = rs.getString("BIRTHDAY").split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate bday = LocalDate.of(year, month, day);
                LocalDate today = LocalDate.now();
                Period p = Period.between(bday, today);
                int age = Integer.parseInt("" + p.getYears());
                if (age < 0)
                {
                    age = 0;
                }
                rowData[7] = "" + age;
                model.addRow(rowData);
                

                totalPat++;
            }
            clck();
            
            Calendar current = Calendar.getInstance();
            System.out.println((current.getTime()).getTime());
            TP_Count1.setText(""+totalPat);
            //Appointment
            rs = stmt.executeQuery("SELECT * FROM APPOINTMENT ORDER BY APP_ID");
            DefaultTableModel model2 = (DefaultTableModel) appointmentTable17.getModel();
            String rowData2[] = new String[8];
            while(rs.next())
            {
                rowData2[0] = rs.getString("PATIENT_ID");
                rowData2[1] = rs.getString("APP_ID");
                rowData2[2] = rs.getString("LASTNAME");
                rowData2[3] = rs.getString("FIRSTNAME");
                rowData2[4] = rs.getString("MIDDLEINITIAL");
                rowData2[5] = rs.getString("CONTACT_NUMBER");
                rowData2[6] = rs.getString("VISIT_DATE");
                rowData2[7] = rs.getString("VISIT_TIME");
                model2.addRow(rowData2);
            }
            clckApp();
            sort();
            
            //Dental Record Archive
            rs = stmt.executeQuery("SELECT * FROM DENTAL_RECORD_ARCHIVED ORDER BY PATIENT_ID");
            DefaultTableModel model3 = (DefaultTableModel) archiveDR.getModel();
            String rowData3[] = new String[10];
            while(rs.next())
            {
                rowData3[0] = rs.getString("PATIENT_ID");
                rowData3[1] = rs.getString("TRANSACTION_ID_ARCHIVE");
                rowData3[2] = rs.getString("TOOTH_NO");
                rowData3[3] = rs.getString("PATIENT_DATE");
                rowData3[4] = rs.getString("DESCRIPTION");
                rowData3[5] = rs.getString("TOTAL_AMOUNT");
                rowData3[6] = rs.getString("AMOUNT_PAID");
                rowData3[7] = rs.getString("BALANCE");
                rowData3[8] = rs.getString("DATE_ISSUED");
                rowData3[9] = rs.getString("DATE_ARCHIVED");
                model3.addRow(rowData3);
            }
            
            //Appointment Archive
            rs = stmt.executeQuery("SELECT * FROM APPOINTMENT_ARCHIVED ORDER BY PATIENT_ID");
            DefaultTableModel model4 = (DefaultTableModel) archiveA.getModel();
            String rowData4[] = new String[10];
            while(rs.next())
            {
                rowData4[0] = rs.getString("PATIENT_ID");
                rowData4[1] = rs.getString("APP_ID_ARCHIVE");
                rowData4[2] = rs.getString("LASTNAME");
                rowData4[3] = rs.getString("FIRSTNAME");
                rowData4[4] = rs.getString("MIDDLEINITIAL");
                rowData4[5] = rs.getString("CONTACT_NUMBER");
                rowData4[6] = rs.getString("VISIT_DATE");
                rowData4[7] = rs.getString("VISIT_TIME");
                rowData4[8] = rs.getString("DATE_ARCHIVED");
                model4.addRow(rowData4);
            }
            
            
            //Patient Record Archive
            rs = stmt.executeQuery("SELECT * FROM PATIENT_RECORD_ARCHIVED ORDER BY PATIENT_ID");
            DefaultTableModel model5 = (DefaultTableModel) archivePR.getModel();
            String rowData5[] = new String[11];
            while(rs.next())
            {
                rowData5[0] = rs.getString("PATIENT_ID");
                rowData5[1] = rs.getString("LASTNAME");
                rowData5[2] = rs.getString("FIRSTNAME");
                rowData5[3] = rs.getString("MIDDLE_INIT");
                rowData5[4] = rs.getString("ADDRESS");
                rowData5[5] = rs.getString("CONTACT_NUMBER");
                rowData5[6] = rs.getString("BIRTHDAY");
                String[] date = rs.getString("BIRTHDAY").split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate bday = LocalDate.of(year, month, day);
                LocalDate today = LocalDate.now();
                Period p = Period.between(bday, today);
                int age = Integer.parseInt("" + p.getYears());
                if (age < 0)
                {
                    age = 0;
                }
                rowData5[7] =  "" + age;
                rowData5[8] = rs.getString("DATE_ISSUED");
                rowData5[9] = rs.getString("DATE_ARCHIVED");
                model5.addRow(rowData5);
            }
            
            
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
         try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/Dental Clinic";
            String user = "maximus";
            String pass = "maximus123";
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            //Dental
            
            
            
            
            
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        
         
        try{
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url2 = "jdbc:derby://localhost:1527/Dental Clinic";
            String user2 = "maximus";
            String pass2 = "maximus123";
            con = DriverManager.getConnection(url2, user2, pass2);
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM HISTORY");
            DefaultTableModel historyModel = (DefaultTableModel) historyTable.getModel();
            String rowData2[] = new String[2];
            while(rs2.next())
            {
                rowData2[0] = rs2.getString("DATE");
                rowData2[1] = rs2.getString("EVENT");
                
          
            
                historyModel.addRow(rowData2);

            }
          //  sort();
            rs2.close();
            stmt2.close();
        }catch (Exception e){
            
        }
        
        
    }
    //Dental Click
    private void clck()
    {
        //create popup
        final RowPopup pop = new RowPopup(dentalResultTable);

        //event
        dentalResultTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                //get data
                int index = dentalResultTable.rowAtPoint(me.getPoint());
                TableModel model = dentalResultTable.getModel();
                patId = model.getValueAt(index, 0).toString();
                ln = model.getValueAt(index, 1).toString();
                fn = model.getValueAt(index, 2).toString();
                mi = model.getValueAt(index, 3).toString();
                address = model.getValueAt(index, 4).toString();
                contact = model.getValueAt(index, 5).toString();
                birth = model.getValueAt(index, 6).toString();
                new RowPopup(patId, ln, fn, mi, address, contact, birth); //populate row popup
                new AddDentalRecord(Integer.parseInt(patId),ln,fn);
                new UpdateDentalRecord(Integer.parseInt(patId),ln,fn);
                new DentalRecords(Integer.parseInt(patId));
                new UpdatePatientRecord(patId,ln,fn);
                //determine right or left
                if (SwingUtilities.isLeftMouseButton(me))
                {
                    pop.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
    }
    //Appoint Click
    private void clckApp()
    {
        //create popup
        final RowPopup2 pop = new RowPopup2(appointmentTable17);

        //event
        appointmentTable17.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                //get data
                int index = appointmentTable17.rowAtPoint(me.getPoint());
                TableModel model = appointmentTable17.getModel();
                patId = model.getValueAt(index, 0).toString();
                appId = model.getValueAt(index, 1).toString();               
                ln = model.getValueAt(index, 2).toString();
                fn = model.getValueAt(index, 3).toString();
                mi = model.getValueAt(index, 4).toString();
                contact = model.getValueAt(index, 5).toString();
                date = model.getValueAt(index, 6).toString();
                time = model.getValueAt(index, 7).toString();
                new RowPopup2(patId, appId, ln, fn, mi, contact, date, time);
                //determine right or left
                if (SwingUtilities.isLeftMouseButton(me))
                {
                    pop.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
    }

    private void sort()
    {
        //Dental
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter((DefaultTableModel) dentalResultTable.getModel());
        dentalResultTable.setRowSorter(sorter);
        
        //Appoint
        TableRowSorter<DefaultTableModel> sorter2 = new TableRowSorter((DefaultTableModel) appointmentTable17.getModel());
        appointmentTable17.setRowSorter(sorter2);
    }

    private boolean isAlpha(String text)
    {
        char[] c = text.toCharArray();
        for (char ch : c)
        {
            if (!Character.isLetter(ch))
            {
                return false;
            }
        }
        return true;
    }

    private void search(ResultSet rs, DefaultTableModel model, String attr, String searchText)
    {
        try
        {
            String rowData[] = new String[8];
            model.setRowCount(0);
            while (rs.next())
            {
                if (rs.getString(attr).toLowerCase().contains(searchText.toLowerCase()))
                {
                    rowData[0] = rs.getString("PATIENT_ID");
                    rowData[1] = rs.getString("LASTNAME");
                    rowData[2] = rs.getString("FIRSTNAME");
                    rowData[3] = rs.getString("MIDDLEINITIAL");
                    rowData[4] = rs.getString("ADDRESS");
                    rowData[5] = rs.getString("CONTACT_NUMBER");
                    rowData[6] = rs.getString("BIRTHDAY");
                    String[] date = rs.getString("BIRTHDAY").split("-");
                    int year = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int day = Integer.parseInt(date[2]);
                    LocalDate bday = LocalDate.of(year, month, day);
                    LocalDate today = LocalDate.now();
                    Period p = Period.between(bday, today);
                    int age = Integer.parseInt("" + p.getYears());
                    if (age < 0)
                    {
                        age = 0;
                    }
                    rowData[7] = "" + age;
                    model.addRow(rowData);
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    public void searchingItems()
    {
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PATIENT_INFO ORDER BY PATIENT_ID");
            String searchBy = sortDental.getSelectedItem().toString();
            String searchText = searchDental.getText();
            String attr;
            DefaultTableModel model = (DefaultTableModel) dentalResultTable.getModel();
            if (searchText.equals("") || searchText.equals(" "))
            {
                JOptionPane.showMessageDialog(null, "Search Textbox Cannot Be Blank!");
            }
            else if (searchBy.equals("ID"))
            {
                if (!isAlpha(searchText))
                {
                    attr = "PATIENT_ID";
                    search(rs, model, attr, searchText);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else if (searchBy.equals("Last Name"))
            {
                attr = "LASTNAME";
                search(rs, model, attr, searchText);
            }
            else if (searchBy.equals("First Name"))
            {
                attr = "FIRSTNAME";
                search(rs, model, attr, searchText);
            }
            else if (searchBy.equals("Middle Initial"))
            {
                attr = "MIDDLEINITIAL";
                search(rs, model, attr, searchText);
            }
            else if (searchBy.equals("Address"))
            {
                attr = "ADDRESS";
                search(rs, model, attr, searchText);
            }
            else if (searchBy.equals("Contact Number"))
            {
                if (!isAlpha(searchText))
                {
                    attr = "CONTACT_NUMBER";
                    search(rs, model, attr, searchText);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else if (searchBy.equals("Birthday"))
            {
                if (!isAlpha(searchText))
                {
                    attr = "BIRTHDAY";
                    search(rs, model, attr, searchText);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else
            {
                model.setRowCount(0);
                String rowData[] = new String[8];
                if (!isAlpha(searchText))
                {
                    while (rs.next())
                    {
                        String[] date = rs.getString("BIRTHDAY").split("-");
                        int year = Integer.parseInt(date[0]);
                        int month = Integer.parseInt(date[1]);
                        int day = Integer.parseInt(date[2]);
                        LocalDate bday = LocalDate.of(year, month, day);
                        LocalDate today = LocalDate.now();
                        Period p = Period.between(bday, today);
                        String age;
                        if (Integer.parseInt("" + p.getYears()) < 0)
                        {
                            age = "0";
                        }
                        else
                        {
                            age = "" + p.getYears();
                        }
                        if (age.contains(searchText))
                        {
                            rowData[0] = rs.getString("PATIENT_ID");
                            rowData[1] = rs.getString("LASTNAME");
                            rowData[2] = rs.getString("FIRSTNAME");
                            rowData[3] = rs.getString("MIDDLEINITIAL");
                            rowData[4] = rs.getString("ADDRESS");
                            rowData[5] = rs.getString("CONTACT_NUMBER");
                            rowData[6] = rs.getString("BIRTHDAY");
                            rowData[7] = age;
                            model.addRow(rowData);
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            clck();
            sort();
        }
        catch (Exception e)
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logOutBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        MainTab = new javax.swing.JTabbedPane();
        DentalRecordsPanel = new javax.swing.JPanel();
        sortDental = new javax.swing.JComboBox<>();
        searchDental = new javax.swing.JTextField();
        searchBtnDental = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dentalResultTable = new javax.swing.JTable();
        addDentalRecord = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        AppointmentPanel = new javax.swing.JPanel();
        sortAppointment = new javax.swing.JComboBox<>();
        searchAppointment = new javax.swing.JTextField();
        searchBtnAppointment = new javax.swing.JButton();
        TABLE = new javax.swing.JScrollPane();
        appointmentTable17 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        ArchivePanel = new javax.swing.JPanel();
        DentalArchives = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        archivePR = new javax.swing.JTable();
        TABLE2 = new javax.swing.JScrollPane();
        archiveA = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        archiveDR = new javax.swing.JTable();
        ReportPanel = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TABLE4 = new javax.swing.JTable();
        NewPatients1 = new javax.swing.JLayeredPane();
        NP1 = new javax.swing.JLabel();
        NP_Count1 = new javax.swing.JLabel();
        PatientsVisited1 = new javax.swing.JLayeredPane();
        PV1 = new javax.swing.JLabel();
        PV_Count1 = new javax.swing.JLabel();
        TotalPatients1 = new javax.swing.JLayeredPane();
        TP1 = new javax.swing.JLabel();
        TP_Count1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TABLE5 = new javax.swing.JTable();
        NewPatients = new javax.swing.JLayeredPane();
        NP = new javax.swing.JLabel();
        NP_Count2 = new javax.swing.JLabel();
        PatientsVisited = new javax.swing.JLayeredPane();
        PV = new javax.swing.JLabel();
        PV_Count2 = new javax.swing.JLabel();
        TotalPatients = new javax.swing.JLayeredPane();
        TP = new javax.swing.JLabel();
        TP_Count2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        today = new javax.swing.JLabel();
        historyDateToday = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Diamse-Montero Dental Clinic");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(52, 152, 219));

        jLabel1.setBackground(new java.awt.Color(239, 249, 247));
        jLabel1.setFont(new java.awt.Font("Avenir", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(239, 249, 247));
        jLabel1.setText("DIAMSE - MONTERO ");

        logOutBtn.setText("LOG OUT");
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel2.setText("D  E  N  T  A  L    C  L  I  N  I  C  ");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186))
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jLabel1)
                        .addGap(40, 194, Short.MAX_VALUE)))
                .addComponent(logOutBtn)
                .addContainerGap())
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(logOutBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(27, 27, 27))
        );

        getContentPane().add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 90));

        MainPanel.setBackground(new java.awt.Color(41, 128, 185));

        MainTab.setToolTipText("");

        DentalRecordsPanel.setBackground(new java.awt.Color(239, 249, 247));

        sortDental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Last Name", "First Name", "Middle Initial", "Address", "Contact Number", "Birthday", "Age" }));

        searchDental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchDentalKeyPressed(evt);
            }
        });

        searchBtnDental.setText("Search");
        searchBtnDental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnDentalActionPerformed(evt);
            }
        });

        dentalResultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Last Name", "First Name", "MI", "Address", "Contact #", "Birthday", "Age"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(dentalResultTable);

        addDentalRecord.setText("Add New Record");
        addDentalRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDentalRecordActionPerformed(evt);
            }
        });

        jButton2.setText("View All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DentalRecordsPanelLayout = new javax.swing.GroupLayout(DentalRecordsPanel);
        DentalRecordsPanel.setLayout(DentalRecordsPanelLayout);
        DentalRecordsPanelLayout.setHorizontalGroup(
            DentalRecordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DentalRecordsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DentalRecordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DentalRecordsPanelLayout.createSequentialGroup()
                        .addComponent(sortDental, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchDental)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtnDental)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DentalRecordsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(DentalRecordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DentalRecordsPanelLayout.createSequentialGroup()
                                .addComponent(addDentalRecord)
                                .addGap(12, 12, 12)))))
                .addContainerGap())
        );
        DentalRecordsPanelLayout.setVerticalGroup(
            DentalRecordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DentalRecordsPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(DentalRecordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sortDental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchDental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtnDental)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDentalRecord)
                .addGap(11, 11, 11))
        );

        MainTab.addTab("Patient Records", DentalRecordsPanel);

        AppointmentPanel.setBackground(new java.awt.Color(239, 249, 247));

        sortAppointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Last Name", "First Name", "Middle Initial", "Address", "Contact Number", "Birthday", "Age" }));

        searchBtnAppointment.setText("Search");
        searchBtnAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnAppointmentActionPerformed(evt);
            }
        });

        appointmentTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Transaction ID", "Last Name", "First Name", "MI", "Contact #", "Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABLE.setViewportView(appointmentTable17);

        jButton3.setText("View All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AppointmentPanelLayout = new javax.swing.GroupLayout(AppointmentPanel);
        AppointmentPanel.setLayout(AppointmentPanelLayout);
        AppointmentPanelLayout.setHorizontalGroup(
            AppointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AppointmentPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(AppointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AppointmentPanelLayout.createSequentialGroup()
                        .addComponent(sortAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtnAppointment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(TABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AppointmentPanelLayout.setVerticalGroup(
            AppointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AppointmentPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(AppointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sortAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtnAppointment)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TABLE, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainTab.addTab("Appointment ", AppointmentPanel);

        ArchivePanel.setBackground(new java.awt.Color(239, 249, 247));

        DentalArchives.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        archivePR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Last Name", "First Name", "MI", "Address", "Contact #", "Birthday", "Age", "Date Issued", "Date Archived"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(archivePR);
        if (archivePR.getColumnModel().getColumnCount() > 0) {
            archivePR.getColumnModel().getColumn(0).setResizable(false);
            archivePR.getColumnModel().getColumn(1).setResizable(false);
            archivePR.getColumnModel().getColumn(2).setResizable(false);
            archivePR.getColumnModel().getColumn(3).setResizable(false);
            archivePR.getColumnModel().getColumn(4).setResizable(false);
            archivePR.getColumnModel().getColumn(5).setResizable(false);
            archivePR.getColumnModel().getColumn(6).setResizable(false);
            archivePR.getColumnModel().getColumn(7).setResizable(false);
            archivePR.getColumnModel().getColumn(8).setResizable(false);
            archivePR.getColumnModel().getColumn(9).setResizable(false);
        }

        DentalArchives.addTab("Patient Records Archive", jScrollPane3);

        archiveA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Transaction ID", "Last Name", "First Name", "MI", "Contact #", "Date", "Time", "Date Archived"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABLE2.setViewportView(archiveA);
        if (archiveA.getColumnModel().getColumnCount() > 0) {
            archiveA.getColumnModel().getColumn(0).setResizable(false);
            archiveA.getColumnModel().getColumn(0).setPreferredWidth(15);
            archiveA.getColumnModel().getColumn(1).setResizable(false);
            archiveA.getColumnModel().getColumn(2).setResizable(false);
            archiveA.getColumnModel().getColumn(3).setResizable(false);
            archiveA.getColumnModel().getColumn(4).setResizable(false);
            archiveA.getColumnModel().getColumn(4).setPreferredWidth(15);
            archiveA.getColumnModel().getColumn(5).setResizable(false);
            archiveA.getColumnModel().getColumn(6).setResizable(false);
            archiveA.getColumnModel().getColumn(7).setResizable(false);
            archiveA.getColumnModel().getColumn(8).setResizable(false);
        }

        DentalArchives.addTab("Appointment Archives", TABLE2);

        jPanel2.setBackground(new java.awt.Color(232, 242, 240));

        archiveDR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Transaction ID", "Tooth No.", "Date", "Description", "Total Amount", "Amount Paid", "Balance Due", "Date Issued", "Date Archived"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        archiveDR.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(archiveDR);
        if (archiveDR.getColumnModel().getColumnCount() > 0) {
            archiveDR.getColumnModel().getColumn(0).setResizable(false);
            archiveDR.getColumnModel().getColumn(0).setPreferredWidth(15);
            archiveDR.getColumnModel().getColumn(1).setResizable(false);
            archiveDR.getColumnModel().getColumn(2).setResizable(false);
            archiveDR.getColumnModel().getColumn(3).setResizable(false);
            archiveDR.getColumnModel().getColumn(4).setResizable(false);
            archiveDR.getColumnModel().getColumn(5).setResizable(false);
            archiveDR.getColumnModel().getColumn(6).setResizable(false);
            archiveDR.getColumnModel().getColumn(7).setResizable(false);
            archiveDR.getColumnModel().getColumn(8).setResizable(false);
            archiveDR.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        DentalArchives.addTab("Dental Records Archive", jPanel2);

        javax.swing.GroupLayout ArchivePanelLayout = new javax.swing.GroupLayout(ArchivePanel);
        ArchivePanel.setLayout(ArchivePanelLayout);
        ArchivePanelLayout.setHorizontalGroup(
            ArchivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ArchivePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DentalArchives)
                .addContainerGap())
        );
        ArchivePanelLayout.setVerticalGroup(
            ArchivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ArchivePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(DentalArchives, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        MainTab.addTab("Archives", ArchivePanel);

        ReportPanel.setBackground(new java.awt.Color(239, 249, 247));

        Title.setFont(new java.awt.Font("Avenir", 0, 24)); // NOI18N
        Title.setText("DENTAL STATISTICS");

        TABLE4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Months", "New Patient", "Patients", "Total Patient"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(TABLE4);

        NewPatients1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        NP1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        NP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NP1.setText(" New Patients");
        NP1.setToolTipText("");

        NP_Count1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        NP_Count1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NP_Count1.setText("7");
        NP_Count1.setToolTipText("");
        NP_Count1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        NewPatients1.setLayer(NP1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        NewPatients1.setLayer(NP_Count1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout NewPatients1Layout = new javax.swing.GroupLayout(NewPatients1);
        NewPatients1.setLayout(NewPatients1Layout);
        NewPatients1Layout.setHorizontalGroup(
            NewPatients1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NP1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
            .addGroup(NewPatients1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NP_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        NewPatients1Layout.setVerticalGroup(
            NewPatients1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewPatients1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NP_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NP1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        PatientsVisited1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PV1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        PV1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PV1.setText("Patients Visited");
        PV1.setToolTipText("");

        PV_Count1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        PV_Count1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PV_Count1.setText("25");
        PV_Count1.setToolTipText("");
        PV_Count1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PatientsVisited1.setLayer(PV1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        PatientsVisited1.setLayer(PV_Count1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout PatientsVisited1Layout = new javax.swing.GroupLayout(PatientsVisited1);
        PatientsVisited1.setLayout(PatientsVisited1Layout);
        PatientsVisited1Layout.setHorizontalGroup(
            PatientsVisited1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PV1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PatientsVisited1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(PV_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        PatientsVisited1Layout.setVerticalGroup(
            PatientsVisited1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PatientsVisited1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PV_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(PV1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TotalPatients1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TotalPatients1.setPreferredSize(new java.awt.Dimension(150, 125));

        TP1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        TP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TP1.setText("Total Patients");
        TP1.setToolTipText("");

        TP_Count1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        TP_Count1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TP_Count1.setText("30");
        TP_Count1.setToolTipText("");
        TP_Count1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TotalPatients1.setLayer(TP1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        TotalPatients1.setLayer(TP_Count1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout TotalPatients1Layout = new javax.swing.GroupLayout(TotalPatients1);
        TotalPatients1.setLayout(TotalPatients1Layout);
        TotalPatients1Layout.setHorizontalGroup(
            TotalPatients1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TP1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
            .addGroup(TotalPatients1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TP_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        TotalPatients1Layout.setVerticalGroup(
            TotalPatients1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TotalPatients1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TP_Count1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(TP1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(NewPatients1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(PatientsVisited1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TotalPatients1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(NewPatients1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PatientsVisited1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TotalPatients1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Monthly", jPanel3);

        TABLE5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Years", "New Patient", "Patients", "Total Patient"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(TABLE5);

        NewPatients.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        NP.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        NP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NP.setText(" New Patients");
        NP.setToolTipText("");

        NP_Count2.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        NP_Count2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NP_Count2.setText("7");
        NP_Count2.setToolTipText("");
        NP_Count2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        NewPatients.setLayer(NP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        NewPatients.setLayer(NP_Count2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout NewPatientsLayout = new javax.swing.GroupLayout(NewPatients);
        NewPatients.setLayout(NewPatientsLayout);
        NewPatientsLayout.setHorizontalGroup(
            NewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NP, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
            .addGroup(NewPatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NP_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        NewPatientsLayout.setVerticalGroup(
            NewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewPatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NP_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NP, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        PatientsVisited.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PV.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        PV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PV.setText("Patients Visited");
        PV.setToolTipText("");

        PV_Count2.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        PV_Count2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PV_Count2.setText("25");
        PV_Count2.setToolTipText("");
        PV_Count2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PatientsVisited.setLayer(PV, javax.swing.JLayeredPane.DEFAULT_LAYER);
        PatientsVisited.setLayer(PV_Count2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout PatientsVisitedLayout = new javax.swing.GroupLayout(PatientsVisited);
        PatientsVisited.setLayout(PatientsVisitedLayout);
        PatientsVisitedLayout.setHorizontalGroup(
            PatientsVisitedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PatientsVisitedLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(PV_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        PatientsVisitedLayout.setVerticalGroup(
            PatientsVisitedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PatientsVisitedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PV_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(PV, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TotalPatients.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TotalPatients.setPreferredSize(new java.awt.Dimension(150, 125));

        TP.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        TP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TP.setText("Total Patients");
        TP.setToolTipText("");

        TP_Count2.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        TP_Count2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TP_Count2.setText("30");
        TP_Count2.setToolTipText("");
        TP_Count2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TotalPatients.setLayer(TP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        TotalPatients.setLayer(TP_Count2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout TotalPatientsLayout = new javax.swing.GroupLayout(TotalPatients);
        TotalPatients.setLayout(TotalPatientsLayout);
        TotalPatientsLayout.setHorizontalGroup(
            TotalPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TP, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
            .addGroup(TotalPatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TP_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        TotalPatientsLayout.setVerticalGroup(
            TotalPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TotalPatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TP_Count2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(TP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(NewPatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PatientsVisited, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(TotalPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(NewPatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PatientsVisited, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TotalPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Yearly", jPanel5);

        javax.swing.GroupLayout ReportPanelLayout = new javax.swing.GroupLayout(ReportPanel);
        ReportPanel.setLayout(ReportPanelLayout);
        ReportPanelLayout.setHorizontalGroup(
            ReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Title)
                .addGap(314, 314, 314))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        ReportPanelLayout.setVerticalGroup(
            ReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        MainTab.addTab("Generate Report", ReportPanel);

        jPanel4.setBackground(new java.awt.Color(239, 249, 247));

        today.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        today.setText("TODAY  IS");

        historyDateToday.setFont(new java.awt.Font("Avenir", 0, 24)); // NOI18N
        historyDateToday.setText(getDate());

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Event"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setResizable(false);
            historyTable.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(today)
                        .addGap(67, 67, 67)
                        .addComponent(historyDateToday))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(today)
                    .addComponent(historyDateToday))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        MainTab.addTab("Transaction History", jPanel4);

        jPanel1.setBackground(new java.awt.Color(41, 128, 185));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Change Password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addContainerGap(794, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainTab, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainTab, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 910, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PATIENT_INFO ORDER BY PATIENT_ID");
            DefaultTableModel model = (DefaultTableModel) dentalResultTable.getModel();
            String rowData[] = new String[8];
            model.setRowCount(0);
            while (rs.next())
            {
                rowData[0] = rs.getString("PATIENT_ID");
                rowData[1] = rs.getString("LASTNAME");
                rowData[2] = rs.getString("FIRSTNAME");
                rowData[3] = rs.getString("MIDDLEINITIAL");
                rowData[4] = rs.getString("ADDRESS");
                rowData[5] = rs.getString("CONTACT_NUMBER");
                rowData[6] = rs.getString("BIRTHDAY");
                String[] date = rs.getString("BIRTHDAY").split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate bday = LocalDate.of(year, month, day);
                LocalDate today = LocalDate.now();
                Period p = Period.between(bday, today);
                int age = Integer.parseInt("" + p.getYears());
                if (age < 0)
                {
                    age = 0;
                }
                rowData[7] = "" + age;
                model.addRow(rowData);
            }
            clck();
            sort();
        }
        catch (Exception e)
        {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchBtnDentalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchBtnDentalActionPerformed
    {//GEN-HEADEREND:event_searchBtnDentalActionPerformed
        searchingItems();
    }//GEN-LAST:event_searchBtnDentalActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_logOutBtnActionPerformed
    {//GEN-HEADEREND:event_logOutBtnActionPerformed
        new LogIn_Form().setVisible(true);
        super.dispose();
    }//GEN-LAST:event_logOutBtnActionPerformed

    private void addDentalRecordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addDentalRecordActionPerformed
    {//GEN-HEADEREND:event_addDentalRecordActionPerformed
        new AddPatientRecord().setVisible(true);
        super.dispose();
    }//GEN-LAST:event_addDentalRecordActionPerformed

    private void searchDentalKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_searchDentalKeyPressed
    {//GEN-HEADEREND:event_searchDentalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            searchingItems();
        }
    }//GEN-LAST:event_searchDentalKeyPressed

    private void searchAppoint(ResultSet rs, String searchText2, String col)
    {
        try
        {
            DefaultTableModel model = (DefaultTableModel) appointmentTable17.getModel();
            model.setRowCount(0);
            String rowData[] = new String[8];
            while (rs.next())
            {
                if (rs.getString(col).toLowerCase().contains(searchText2.toLowerCase()))
                {
                    rowData[0] = rs.getString("PATIENT_ID");
                    rowData[1] = rs.getString("APP_ID");
                    rowData[2] = rs.getString("LASTNAME");
                    rowData[3] = rs.getString("FIRSTNAME");
                    rowData[4] = rs.getString("MIDDLEINITIAL");
                    rowData[5] = rs.getString("CONTACT_NUMBER");
                    rowData[6] = rs.getString("VISIT_DATE");
                    rowData[7] = rs.getString("VISIT_TIME");
                    model.addRow(rowData);
                }
            }
        }
        catch(Exception e)
        {}
    }

    private void searchBtnAppointmentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchBtnAppointmentActionPerformed
    {//GEN-HEADEREND:event_searchBtnAppointmentActionPerformed
        try
        {
            rs = stmt.executeQuery("SELECT * FROM APPOINTMENT ORDER BY APP_ID");
            String searchBy2 = sortAppointment.getSelectedItem().toString();
            String searchText2 = searchAppointment.getText();
            String col;
            if (searchText2.equals("") || searchText2.equals(" "))
            {
                JOptionPane.showMessageDialog(null, "Search Textbox Cannot Be Blank!");
            }
            else if (searchBy2.equals("ID"))
            {
                if (!isAlpha(searchText2))
                {
                    col = "APP_ID";
                    searchAppoint(rs, searchText2, col);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else if (searchBy2.equals("Last Name"))
            {

            }
            else if (searchBy2.equals("First Name"))
            {
                col = "FIRSTNAME";
                searchAppoint(rs, searchText2, col);
            }
            else if (searchBy2.equals("Middle Initial"))
            {
                col = "MIDDLEINITIAL";
                searchAppoint(rs, searchText2, col);
            }

            else if (searchBy2.equals("Contact Number"))
            {
                if (!isAlpha(searchText2))
                {
                    col = "CONTACT_NUMBER";
                    searchAppoint(rs, searchText2, col);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else if (searchBy2.equals("Date"))
            {
                if (!isAlpha(searchText2))
                {
                    col = "VISIT_DATE";
                    searchAppoint(rs, searchText2, col);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            else if (searchBy2.equals("Time"))
            {
                if (!isAlpha(searchText2))
                {
                    col = "VISIT_TIME";
                    searchAppoint(rs, searchText2, col);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Search text should be numbers");
                }
            }
            sort();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_searchBtnAppointmentActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        try
        {
            rs = stmt.executeQuery("SELECT * FROM APPOINTMENT ORDER BY APP_ID");
            DefaultTableModel model = (DefaultTableModel) appointmentTable17.getModel();
            String rowData[] = new String[8];
            model.setRowCount(0);
            while (rs.next())
            {
                rowData[0] = rs.getString("APP_ID");
                rowData[1] = rs.getString("LASTNAME");
                rowData[2] = rs.getString("FIRSTNAME");
                rowData[3] = rs.getString("MIDDLEINITIAL");
                rowData[4] = rs.getString("CONTACT_NUMBER");
                rowData[5] = rs.getString("VISIT_DATE");
                rowData[6] = rs.getString("VISIT_TIME");
                model.addRow(rowData);
            }
            clckApp();
            sort();
        }
        catch (Exception e)
        {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

        private String getDate(){
         
    String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM)).toString();//new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime());
        
        return timeStamp;
    }

    
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MouseClicked
    {//GEN-HEADEREND:event_jPanel1MouseClicked
        new ChangePass().setVisible(true);
        super.dispose();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MouseEntered
    {//GEN-HEADEREND:event_jPanel1MouseEntered
        jPanel1.setBackground(new Color(52,152,219));
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MouseExited
    {//GEN-HEADEREND:event_jPanel1MouseExited
        jPanel1.setBackground(new Color(41,128,185));
    }//GEN-LAST:event_jPanel1MouseExited

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MousePressed
    {//GEN-HEADEREND:event_jPanel1MousePressed
        jPanel1.setBackground(new Color(201, 211, 208));
    }//GEN-LAST:event_jPanel1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(PatientRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(PatientRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(PatientRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(PatientRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                PatientRecords drs = new PatientRecords();
                drs.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AppointmentPanel;
    private javax.swing.JPanel ArchivePanel;
    private javax.swing.JTabbedPane DentalArchives;
    private javax.swing.JPanel DentalRecordsPanel;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTabbedPane MainTab;
    private javax.swing.JLabel NP;
    private javax.swing.JLabel NP1;
    private javax.swing.JLabel NP_Count1;
    private javax.swing.JLabel NP_Count2;
    private javax.swing.JLayeredPane NewPatients;
    private javax.swing.JLayeredPane NewPatients1;
    private javax.swing.JLabel PV;
    private javax.swing.JLabel PV1;
    private javax.swing.JLabel PV_Count1;
    private javax.swing.JLabel PV_Count2;
    private javax.swing.JLayeredPane PatientsVisited;
    private javax.swing.JLayeredPane PatientsVisited1;
    private javax.swing.JPanel ReportPanel;
    private javax.swing.JScrollPane TABLE;
    private javax.swing.JScrollPane TABLE2;
    private javax.swing.JTable TABLE4;
    private javax.swing.JTable TABLE5;
    private javax.swing.JLabel TP;
    private javax.swing.JLabel TP1;
    private javax.swing.JLabel TP_Count1;
    private javax.swing.JLabel TP_Count2;
    private javax.swing.JLabel Title;
    private javax.swing.JLayeredPane TotalPatients;
    private javax.swing.JLayeredPane TotalPatients1;
    private javax.swing.JButton addDentalRecord;
    private javax.swing.JTable appointmentTable17;
    private javax.swing.JTable archiveA;
    private javax.swing.JTable archiveDR;
    private javax.swing.JTable archivePR;
    private javax.swing.JTable dentalResultTable;
    private javax.swing.JLabel historyDateToday;
    private javax.swing.JTable historyTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JTextField searchAppointment;
    private javax.swing.JButton searchBtnAppointment;
    private javax.swing.JButton searchBtnDental;
    private javax.swing.JTextField searchDental;
    private javax.swing.JComboBox<String> sortAppointment;
    private javax.swing.JComboBox<String> sortDental;
    private javax.swing.JLabel today;
    // End of variables declaration//GEN-END:variables
}
