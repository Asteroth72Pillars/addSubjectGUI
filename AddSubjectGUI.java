/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.addsubject_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.doa.SubjectDAO;
import za.ac.cput.domain.Subject;
/**
 *
 * @author zaihd
 */
public class AddSubjectGUI implements ActionListener, ItemListener
{
    private JFrame mainframe;
    private JPanel panelWest, panelWest1, panelWest2, panelEast,panelSouth, panelCenter;
    private JLabel lblDeleteSubject;
    private JLabel lblSubjectCode;
    private JLabel lblSubjectDescription;
    private JTextField txtSubjectCode;
    private JTextField txtSubjectDescription;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnRead;
    JComboBox<String> cmbSubjectCode;
    private DefaultTableModel tableModel;
    JTable tblSubjects;
    ArrayList<Subject> subjectsList = new ArrayList<>();
    String code;
    Subject subject;
    SubjectDAO dao;
    
    public AddSubjectGUI()
    {
        mainframe = new JFrame("Add a subject");
        panelWest = new JPanel();
        panelWest1 = new JPanel();
        panelWest2 = new JPanel();
        panelEast = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblDeleteSubject = new JLabel("Select code to delete");
        lblSubjectCode = new JLabel("Subject Code: ");
        lblSubjectDescription = new JLabel("Subject Decription: ");
        txtSubjectCode = new JTextField(20);
        txtSubjectDescription = new JTextField(20);
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        btnRead = new JButton("Read");
        
        cmbSubjectCode = new JComboBox<String>();
        
        tableModel = new DefaultTableModel();
        tblSubjects = new JTable(tableModel);
        
        
        
        
        
        
        
        
        
        dao = new SubjectDAO();
    }
    
    public void setGUI()
    {
        panelWest.setLayout(new GridLayout(2,1));
        panelWest1.setLayout(new FlowLayout());
        panelWest2.setLayout(new FlowLayout());
        panelEast.setLayout(new FlowLayout());
        panelCenter.setLayout(new FlowLayout());
        panelSouth.setLayout(new GridLayout(1,3));
        
        panelSouth.setPreferredSize(new Dimension(100,30));
        
        panelWest1.add(lblSubjectCode);
        panelWest1.add(txtSubjectCode);
        panelWest2.add(lblSubjectDescription);
        panelWest2.add(txtSubjectDescription);
        panelWest.add(panelWest1);
        panelWest.add(panelWest2);
        
        panelSouth.add(btnSave);
        panelSouth.add(btnCancel);
        panelSouth.add(btnRead);
        
        panelCenter.add(new JScrollPane(tblSubjects));
        
        cmbSubjectCode.setSize(30,5);
        panelEast.add(lblDeleteSubject);
        panelEast.add(cmbSubjectCode);
        
        mainframe.add(panelWest, BorderLayout.WEST);
        mainframe.add(panelEast, BorderLayout.EAST);
        mainframe.add(panelSouth, BorderLayout.SOUTH);
        mainframe.add(panelCenter, BorderLayout.CENTER);
        
        tableModel.addColumn("Subject Code");
        tableModel.addColumn("Description");
        
        mainframe.addWindowListener(new WindowAdapter(){
            @Override
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
        });
        
        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
        btnRead.addActionListener(this);
        cmbSubjectCode.addItemListener(this);
        mainframe.setSize(1100,500);
        mainframe.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnSave)
        {
            //get text from textfields
            String subjectCode = txtSubjectCode.getText();
            String subjectDescription = txtSubjectDescription.getText();
            
            //create worker object
            Subject subj = new Subject(subjectCode, subjectDescription);
            
            //perform db fuction(dao)
            subject = dao.save(subj);
            if(subject.equals(subj)){
                JOptionPane.showMessageDialog(null, "Success! Subject added");
            }else{
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
        else if(e.getSource() == btnCancel)
        {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Read"))
        {
            subjectsList =dao.getAll();
            
            tblSubjects.setModel(tableModel);
            tableModel = (DefaultTableModel) tblSubjects.getModel();
            tableModel.setRowCount(0);
            
            tblSubjects.setModel(tableModel);
            tableModel = (DefaultTableModel) tblSubjects.getModel();
            tableModel.setRowCount(0);
            
            for(int i = 0; i < subjectsList.size(); i++)
            {
                String subjCode = subjectsList.get(i).getSubjectCode();
                String desc = subjectsList.get(i).getSubjectDescription();
                Object []subjectData = {subjCode, desc};
                tableModel.addRow(subjectData);
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if(e.getStateChange() == ItemEvent.SELECTED)
        {
         if(!cmbSubjectCode.getSelectedItem().equals("-no Selection made-"))
             if(e.getSource() == cmbSubjectCode)
             {
                 code = dao.delete((String)cmbSubjectCode.getSelectedItem());
                 if(code!=null)
                     JOptionPane.showMessageDialog(null, "Success" + code);
                 else
                     JOptionPane.showMessageDialog(null, "Failure");
             }
        }
    }
    
    
    public static void main(String[] args)
    {
        new AddSubjectGUI().setGUI();
    }
    
}
