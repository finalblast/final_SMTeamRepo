/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import model.Connect;
import view.RegisterForm;

/**
 *
 * @author K55VM
 */
public class Register_ctr {
    
    private RegisterForm form;
    private Connect cnn;
    Connection cn;
    //Khoi tao
    public Register_ctr() throws Exception{
    init();
    }

    private void init() throws Exception{
        //Hien thi form va ket noi database
        form = new RegisterForm();
        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");
        cn = cnn.getCnn();
        //Bat su kien but OK
        form.getBtnOK().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Lay du lieu input
                String id = form.getTfId().getText();
                String pass = form.getTfPass().getText();
                String pass2 = form.getTfPass2().getText();
                String name = form.getTfName().getText();
                String phone = form.getTfPhone().getText();
                String email = form.getTfEmail().getText();
                //Check rang buoc
                if("".equals(id) || "".equals(pass) || "".equals(pass2)){
                    JOptionPane.showMessageDialog(null, "You must enter items marked *");
                }
                else{
                    String checkid;
                    String sql = "select * from account " + "where id='" + id + "'";
                    try {
                        Connection cnt = cnn.getCnn();
                        Statement stt = cnt.createStatement();
                        ResultSet rs = stt.executeQuery(sql);
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(form, "Id already exists!");
                        }
                        else{
                            //Dang ky account moi vao database
                            if(pass2.equals(pass)){
                                sql = "INSERT INTO account "
                                + "VALUES('" + id + "','" + pass + "','3','"
                                + name + "','" + phone + "','" + email + "');";
                                int rs1 = stt.executeUpdate(sql);
                                if (rs1 == 1) {
                                    JOptionPane.showMessageDialog(form, "Success!!");
                                }
                                form.setVisible(false);
                                    
                            }else
                                JOptionPane.showMessageDialog(form, "Confirm pass and pass not the same!");
                        }
                        
                    } catch (Exception ae) {
                        JOptionPane.showMessageDialog(form, ae);
                    }
                }
            }
        });
    }
     
}
