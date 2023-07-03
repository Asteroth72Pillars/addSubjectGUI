/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import za.ac.cput.connection.DBConnection;
import za.ac.cput.domain.Subject;

/**
 *
 * @author zaihd
 */
public class SubjectDAO 
{
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;

    public SubjectDAO() 
    {
        try
        {
            this.con = DBConnection.derbyConnection();
        }catch(Exception exception)
        {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Subject save(Subject subject)
    {
        int ok;
    
        String insertSql = "INSERT INTO subject VALUES(?,?)";
        try
        {
            pstmt = this.con.prepareStatement(insertSql);
            pstmt.setString(1, subject.getSubjectCode());
            pstmt.setString(2, subject.getSubjectDescription());        
            ok = pstmt.executeUpdate();
            if(ok > 0){
                return subject;
            }
            else{
                return null;
            }
        }catch (SQLException sqlexception) {
            // Handle the SQL exception appropriately
        } catch (Exception e) {
            // Handle the exception appropriately
        } finally {
            try {
                  if (stmt != null) {
                        stmt.close();
                }
            } catch (Exception exception) 
            {
                // Handle the exception appropriately
            }
        }
        
        return null;
        
    }//end save method
        
    
    public ArrayList<Subject> getAll()
    {
        ArrayList<Subject> subjectsList = new ArrayList<>();
        try
        {
            String getAllSql = "select * from subject where subject_code = ?";
            pstmt = this.con.prepareStatement(getAllSql);
            //pstmt .setString(1,"APD2");
            ResultSet rs = pstmt.executeQuery();
                if(rs != null)
                {
                    while(rs.next() )
                    {
                        System.out.println("BD table Record: "
                        + rs.getString(1) + " " +rs.getString(2));
                    
                        subjectsList.add(new Subject(rs.getString("subject_code"), rs.getString("subject_description") ));
                    }//end while    
                rs.close();        
                }
        }//close try
        catch(Exception exception)
        {
        
        }finally
        {
            try
            {
                if(pstmt != null)
                    pstmt.close();
            }catch(Exception exception)
            {
                
            }
        }
        return subjectsList;
    }
    
    
    public void closeResources()
    {
        try{
            this.con.close();
        }
        catch(SQLException exception){
        
        }
    }
    
    public String delete(String subjectCode) {
        int ok;
        String deleteSql = "DELETE FROM subject WHERE subject_code = ?";

        try {
            pstmt = this.con.prepareStatement(deleteSql);
            pstmt.setString(1, subjectCode);
            
            ok = pstmt.executeUpdate();
            if (ok > 0) {
                return subjectCode;
            }
        } catch (SQLException exception) {
            // Handle the SQL exception appropriately
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception exception) {
                // Handle the exception appropriately
            }
        }
        return null;
    }
    
}
    

