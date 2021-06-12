/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.DriverManager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mahmudul Hasan
 */
public class ImageUpload extends javax.swing.JFrame {

    /**
     * Creates new form ImageUpload
     */
    public ImageUpload() {
        initComponents();
    }
    String fileName=null;
    byte holdImage[]=new byte[103224];
    Connection conn=null;
    PreparedStatement ps=null;
    String url = "jdbc:mysql://localhost:3306/primaryschool";
    String un = "root";
    String pw = "";
    void dbconnect(){
        try {
            conn=DriverManager.getConnection(url,un,pw);
            dataConsole.setText("Database Connected!");
        } catch (SQLException ex) {
            Logger.getLogger(ImageUpload.class.getName()).log(Level.SEVERE, null, ex);
            dataConsole.setText(""+ex);
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

        imageHolder = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        console = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        dataConsole = new javax.swing.JTextField();
        byteArrayConsole = new javax.swing.JTextField();
        upToDatabase = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        imageHolder.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        imageHolder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(imageHolder);
        imageHolder.setBounds(0, 110, 450, 410);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Upload Image");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 6, 910, 80);

        jButton1.setText("Upload");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(490, 130, 240, 22);
        getContentPane().add(console);
        console.setBounds(500, 220, 210, 60);

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(490, 160, 240, 22);

        jButton3.setText("open byte array converted image");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(490, 190, 240, 22);
        getContentPane().add(dataConsole);
        dataConsole.setBounds(710, 220, 190, 60);
        getContentPane().add(byteArrayConsole);
        byteArrayConsole.setBounds(500, 290, 220, 190);

        upToDatabase.setText("Up to Database");
        upToDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upToDatabaseActionPerformed(evt);
            }
        });
        getContentPane().add(upToDatabase);
        upToDatabase.setBounds(500, 480, 220, 22);

        setSize(new java.awt.Dimension(931, 557));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
        JFileChooser jf=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("jpeg","jpg","gif","png");
        jf.setFileFilter(filter);
        int returnValue=jf.showOpenDialog(null);
        File f=jf.getSelectedFile();
        if(returnValue==JFileChooser.APPROVE_OPTION){
        fileName=f.getAbsolutePath();
        ImageIcon img=new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(imageHolder.getWidth(),imageHolder.getHeight(), Image.SCALE_SMOOTH));
        imageHolder.setIcon(img);  
        console.setText(f.getName());
        //convert to byte array
            BufferedImage bimage=ImageIO.read(f);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ImageIO.write(bimage, fileName.substring(fileName.lastIndexOf("."),fileName.length()), bos);
            holdImage=bos.toByteArray();
            byteArrayConsole.setText(""+holdImage);
        }else if(returnValue==JFileChooser.CANCEL_OPTION){
            console.setText("Cancelled");
        }
        
        } catch (Exception e) {
            console.setText(""+e);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        imageHolder.setIcon(null);
        console.setText("Image Cleared!");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
dbconnect();
String query="Select * from `Image Upload`";
        try {
            ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                console.setText(rs.getString(1));
                byte getimage[]=rs.getBytes(2);
                ImageIcon img=new ImageIcon(new ImageIcon(getimage).getImage().getScaledInstance(imageHolder.getWidth(), imageHolder.getHeight(), Image.SCALE_SMOOTH));
                imageHolder.setIcon(img);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageUpload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void upToDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upToDatabaseActionPerformed
        // TODO add your handling code here:
        dbconnect();
        String query="INSERT INTO `Image Upload`(`ImageDetails`,`image`) VALUES(?,?)";
        try {
            ps=conn.prepareStatement(query);
            ps.setString(1, console.getText());
            ps.setBytes(2, holdImage);
            boolean r=ps.execute();
            if(!r){
                dataConsole.setText("Uploaded");
            }else{
                dataConsole.setText("Failed!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_upToDatabaseActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImageUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImageUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImageUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImageUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImageUpload().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField byteArrayConsole;
    private javax.swing.JTextField console;
    private javax.swing.JTextField dataConsole;
    private javax.swing.JLabel imageHolder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton upToDatabase;
    // End of variables declaration//GEN-END:variables
}