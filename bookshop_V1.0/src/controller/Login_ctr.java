/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.Connect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.MainForm;
import view.LoginForm;
/**
 *
 * @author Kim Tuan
 */
public class Login_ctr {
    private LoginForm form;
    private Connect cnn;
    Connection cn;
   
    
    public int maquyen;
    public String currentid;
    //Khoi tao
    public Login_ctr() throws Exception {
        init();
    }

    private void init() throws Exception{
        form = new LoginForm();

        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");              //Ket noi toi csdl bookshop
        cn = cnn.getCnn();
        //Bat su kien nhan nut Login
        form.getBtLog().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Lay du lieu nhap tu ban phim
                String id = form.getTfId().getText();                
                String pass = form.getTfPass().getText();
                //Rang buoc
                if("".equals(id) || "".equals(pass))
                    JOptionPane.showMessageDialog(null, "You must enter Id and Password!");
                else {
                String passtest;
                String sql = "select * from account " + "where id='" + id + "'";    //Cau lenh truy van csdl

                try {
                    //Tao ket noi de thuc hien cau lenh truy van
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    //Dua ket qua truy van vao ResultSet
                    ResultSet rs = stt.executeQuery(sql);
                    if (rs.next()) {
                        passtest = rs.getString("pass");
                        if (passtest.compareTo(pass) == 0) {                    //So sanh pass nhap vao voi pass tren csdl
                            form.setVisible(false);
                            maquyen = rs.getInt("perid");
                            currentid=rs.getString("id") ;
                            //Dang nhap thanh cong nen mo giao dien chinh
                            MainForm a = new MainForm(maquyen,currentid);
                            a.setVisible(true);
                            JOptionPane.showMessageDialog(null, "Login Success!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong Id or Password!");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Id or Password!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login_ctr.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Login_ctr.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
        });
       
    }
    
}
