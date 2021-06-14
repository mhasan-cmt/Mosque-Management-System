/*
DEVELOPED BY MAHMUDUL HASAN SHAFIN-UTC-FPI-2021
*/
package main;

//IMPORTS

import java.sql.PreparedStatement;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mahmudul Hasan
 */
public class databaseConection {
    //GLOBAL VARIABLES DECLARATION
    String id, name, mobile, address, shop;
    Connection con = null;
    Statement st = null;
    PreparedStatement pst=null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/mosquemanage";
    String un = "root";
    String pw = "";
    //METHOD FOR DATABASE CONNECTION USING JDBC
    void dbconnect() {
        try {
            con = DriverManager.getConnection(url, un, pw);
            st = con.createStatement();
            System.out.println("Database Conected");
        } catch (SQLException ex) {
            Logger.getLogger(databaseConection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //METHOD FOR QUERY FROM DATABASE SINGLE STRING DATA
    String searchData(String query) throws SQLException{
        st=null;
        String data="";
        dbconnect();
            rs = st.executeQuery(query);
            if (rs.next()) {
                data = rs.getString(1);
            }
            return data;
    }
    //METHOD FOR ADDING NEW DATA INTO DATABASE
   void addData(String q, Component frame) {
        try {
            dbconnect();
            int a = st.executeUpdate(q);
            if (a > 0) {
                JOptionPane.showMessageDialog(frame, "Data Added!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(databaseConection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frame, "ERROR:" + ex);
        }
    }
   //METHOD FOR SIGN IN INTO HOME PAGE
   void signIn(String user,String pass,JFrame form){
        try {
            dbconnect();
            pst=null;
            rs=null;
            String query="Select * from `user` where `username`=? AND `password`=?";
            pst=con.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, pass);
            rs=pst.executeQuery();
            if(rs.next()){
                new Home().setVisible(true);
                form.dispose();
                System.out.println("login succeed...");
            }else{
                System.out.println("login failed...");
            }
        } catch (SQLException ex) {
            Logger.getLogger(databaseConection.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
