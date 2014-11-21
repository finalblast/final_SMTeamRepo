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
import model.TableModel;
import view.MainForm;
import view.AccountForm;
/**
 *
 * @author hong quan
 */
public class Account_ctr {
    private AccountForm form = new AccountForm();
    private Connect cnn;
    Connection cn;
    public MainForm index;
    private int type;
    private String currentid;
    //Khoi tao
    public Account_ctr(int type,String currentid) throws Exception {
        this.type=type;
        this.currentid = currentid;
        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");
        cn = cnn.getCnn();
        loadDataStart();                    
        eventBtn();                         //Ham chua cac ham bat su kien
    }
    //Cac ham bat su kien trong form
    private void eventBtn() {
        eventBtnLoadData();
        eventBtnThongTinTK();
        eventBtnThem();
        eventBtnXoa();
        eventBtnSua();
        eventBtnBack();
        eventBtnNhapLai();
    }
    //Ham load lai du lieu khi co thay doi database
    private void eventBtnLoadData() {
        //Bat su kien nut Refresh Data
        form.getBtnLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadTable();
            }
            //Ham load du lieu tu database vao Table va hien thi len form
            private void loadTable() {
                try {
                    /*
                    *Ket noi
                    *Tao cau truy van
                    *Truy van va hien thi ket qua
                    */
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String sql = "select * from account ";
                    ResultSet rs = stt.executeQuery(sql);
                    form.getTableCSDL().setModel(new TableModel(rs));
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien tim kiem
    private void eventBtnThongTinTK() {
        //Bat su kien nut search
        form.getBtnThongtin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThongTin();
            }

            private void actionThongTin() {
                try {
                    //Lay du lieu input
                    String tukhoa = form.getTfIDTK().getText();
                    String tk = form.getCbTK().getSelectedItem().toString();
                    //Rang buoc
                    if("".equals(tukhoa))
                        JOptionPane.showMessageDialog(form, "You must enter keyword!");
                    else{
                        //Thuc hien viec tim kiem va hien thi ket qua
                        Connection cnt = cnn.getCnn();
                        Statement stt = cnt.createStatement();
                        if("ID".equals(tk)){
                        String sql = "select * from account where id like '%" +tukhoa+"%'";
                        ResultSet rs = stt.executeQuery(sql);//đây
                        form.getTableCSDL().setModel(new TableModel(rs));
                        }
                        if("Name".equals(tk)){
                        String sql = "select * from account where name like '%" + tukhoa+"%'";
                        ResultSet rs = stt.executeQuery(sql);//đây
                        form.getTableCSDL().setModel(new TableModel(rs));
                        }
                        if("Permission Id".equals(tk)){
                        String sql = "select * from account where perid like '%" + tukhoa+"%'";
                        ResultSet rs = stt.executeQuery(sql);//đây
                        form.getTableCSDL().setModel(new TableModel(rs));
                        }
                    }
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham load du lieu ngay luc mo form
    private void loadDataStart() {
        try {
            Connection cnt = cnn.getCnn();
            Statement stt = cnt.createStatement();
            String sql = "select * from account ";
            ResultSet rs = stt.executeQuery(sql);
            form.getTableCSDL().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
        try {
            Connection cnt = cnn.getCnn();
            Statement stt = cnt.createStatement();
            String sql = "select * from permission ";
            ResultSet rs = stt.executeQuery(sql);
            form.getTableType().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
    }
    //Ham xu li su kien nut add
    private void eventBtnThem() {
        form.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThem();
            }

            private void actionThem() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String id = form.getTfIDThem().getText();
                    String hten = form.getTfName().getText();
                    String phone = form.getTfPhone().getText();
                    String email = form.getTfEmail().getText();
                    int maquyen = Integer.parseInt(form.getCbMQ().getSelectedItem().toString());
                    String pass = form.getTfPass().getText();
                    int kt = 1;
                    for(int i=0 ;i < form.getTableCSDL().getRowCount()-1;i++){
                        if(id.toUpperCase().equals(form.getTableCSDL().getValueAt(i, 0).toString().toUpperCase()))
                            kt=0;
                    }
                     if("".equals(id))
                        JOptionPane.showMessageDialog(form, "You must enter id!");
                    else{
                         if(kt==0)
                            JOptionPane.showMessageDialog(form, "This id already exists!");
                        else{
                            String sql = "INSERT INTO account "
                            + "VALUES('" + id + "','" + pass + "','" + maquyen + "','"
                            + hten + "','" + phone + "','" + email + "');";
                            int rs = stt.executeUpdate(sql);
                            if (rs == 1) {
                                JOptionPane.showMessageDialog(form, "Success!!");
                            } else {
                                JOptionPane.showMessageDialog(form, "Error!!");
                            }
                         }
                     }
                    
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien nut Delete
    private void eventBtnXoa() {
        form.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionXoa();
            }

            private void actionXoa() {
                try {
                    String id = form.getTfIDThem().getText();
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    int kq =JOptionPane.showConfirmDialog(form, "Are you sure?", "Waring", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    if(kq == JOptionPane.OK_OPTION){
                        String sql = "DELETE FROM account" + " WHERE id='" + id+"'";
                        int rs = stt.executeUpdate(sql);
                        if (rs == 1) {
                            JOptionPane.showMessageDialog(form, "Success!!");
                        } else {
                            JOptionPane.showMessageDialog(form, "Error!!");
                            }
                    }
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien nut Edit
    private void eventBtnSua() {
        form.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionSua();
            }

            private void actionSua() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();

                    String id = form.getTfIDThem().getText();
                    String hten = "name='" + form.getTfName().getText() + "'";
                    String phone = "phone='" + form.getTfPhone().getText() + "'";
                    String email = "email='" + form.getTfEmail().getText() + "'";
                    int maquyen = Integer.parseInt(form.getCbMQ().getSelectedItem().toString());
                    String pass = "pass='" + form.getTfPass().getText() + "'";
                    String sql = "update account "
                            + "set " + pass + ",perid=" + maquyen + "," + hten
                            + "," + phone + "," + email + " where id='" + id + "'";
                    int rs = stt.executeUpdate(sql);
                    if (rs == 1) {
                        JOptionPane.showMessageDialog(form, "Success!!");
                    } else {
                        JOptionPane.showMessageDialog(form, "Error!!");
                    }


                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien nut Back
    private void eventBtnBack() {
        form.getBtnQuayLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                form.setVisible(false);
                index=new MainForm(type,currentid);
                index.setVisible(true);
            }           
        });
    }
    //Ham xu li su kien nut Reset
    private void eventBtnNhapLai() {
        form.getBtnNhapLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                form.getTfIDTK().setText("");
                form.getTfEmail().setText("");
                form.getTfIDThem().setText("");
                form.getTfName().setText("");
                form.getTfPass().setText("");
                form.getTfPhone().setText("");
                form.getCbMQ().setSelectedIndex(0);  
            }
        });
    }
}
