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
import view.BookForm;
/**
 *
 * @author Hoa
 */
public class Book_ctr {
    private BookForm form = new BookForm();
    private Connect cnn;
    Connection cn;
    public MainForm index;
    private int type;
    private String currentid;

    public Book_ctr() {
    }
    //Khoi tao
    public Book_ctr(int type, String currentid) throws Exception {
        this.type = type;
        this.currentid = currentid;
        //Hien thi form, tao ket noi
        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");
        cn = cnn.getCnn();
        PQuyen(type);
        loadTableStart();
        eventBtn();
    }
    //Lam mo di cac chuc nang ma Employee trong duoc dung
    private void PQuyen(int a) {
        if (a == 3) {
            form.getBtnThemType().setEnabled(false);
            form.getBtnSuaType().setEnabled(false);
            form.getBtnXoaType().setEnabled(false);
            form.getTfTypeID().setEnabled(false);
            form.getTfTypeName().setEnabled(false);
            form.getBtnThem().setEnabled(false);
            form.getBtnSua().setEnabled(false);
            form.getBtnXoa().setEnabled(false);
            form.getBtnNhapLai().setEnabled(false);
            form.getTfDonGia().setEnabled(false);
            form.getTfDonVi().setEnabled(false);
            form.getTfName().setEnabled(false);
            form.getTfType().setEnabled(false);
            form.getTfIDThem().setEnabled(false);
            form.getjLabel1().setEnabled(false);
            form.getjLabel2().setEnabled(false);
            form.getjLabel3().setEnabled(false);
            form.getjLabel6().setEnabled(false);
            form.getjLabel7().setEnabled(false);
            form.getjPanel1().setEnabled(false);
        }
    }
    //Cac su kien se co trong form
    private void eventBtn() {
        eventBtnLoadData();
        eventBtnThongTinTB();
        eventBtnThem();
        eventBtnSua();
        eventBtnXoa();
        eventBtnThemType();
        eventBtnSuaType();
        eventBtnXoaType();
        eventBtnQuayLai();
        eventBtnNhapLai();
    }
    //Ham xu li su kien Refresh Date khi database thay doi
    private void eventBtnLoadData() {
        form.getBtnLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadTable();
            }

            private void loadTable() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String sql = "select * from book ";
                    ResultSet rs = stt.executeQuery(sql);
                    form.getTableCSDL().setModel(new TableModel(rs));
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String sql = "select * from bookcategories ";
                    ResultSet rs = stt.executeQuery(sql);
                    form.getTbType().setModel(new TableModel(rs));
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien Search Book
    private void eventBtnThongTinTB() {

        form.getBtnThongtin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThongTin();
            }

            private void actionThongTin() {
                try {
                    String tukhoa = form.getTfIDTB().getText();
                    String tk = form.getCbTK().getSelectedItem().toString();
                    if("".equals(tukhoa))
                        JOptionPane.showMessageDialog(form, "You must enter keyword");
                    else{
                        Connection cnt = cnn.getCnn();
                        Statement stt = cnt.createStatement();
                        if("Book Category".equals(tk)){
                            String sql = "select * from book where categoryid like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);//đây
                            form.getTableCSDL().setModel(new TableModel(rs));
                        }
                        if("Book Name".equals(tk)){
                            String sql = "select * from book where bookname like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);//đây
                            form.getTableCSDL().setModel(new TableModel(rs));
                        }
                        if("Amount".equals(tk)){
                            String sql = "select * from book where amount= '" + tukhoa + "'";
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
    //Ham xu li su kien load du lieu vao Table luc mo form
    private void loadTableStart() {
        try {
            Connection cnt = cnn.getCnn();
            Statement stt = cnt.createStatement();
            String sql = "select * from bookcategories ";
            ResultSet rs = stt.executeQuery(sql);
            form.getTbType().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
        try {
            Connection cnt = cnn.getCnn();
            Statement stt = cnt.createStatement();
            String sql = "select * from book ";
            ResultSet rs = stt.executeQuery(sql);
            form.getTableCSDL().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
    }
    //Ham xu li su kien nut Add in Book
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
                    int dongia = Integer.parseInt(form.getTfDonGia().getText());
                    int donvi = Integer.parseInt(form.getTfDonVi().getText());
                    String id = form.getTfIDThem().getText();
                    String tentb = form.getTfName().getText();
                    String type = form.getTfType().getText();
                    int kt = 1;
                    for(int i=0 ;i < form.getTableCSDL().getRowCount()-1;i++){
                        if(id.toUpperCase().equals(form.getTableCSDL().getValueAt(i, 0).toString().toUpperCase()))
                            kt=0;
                    }
                    if("".equals(id))
                        JOptionPane.showMessageDialog(form, "You must enter bookid!");
                    else{
                        if(kt==0)
                            JOptionPane.showMessageDialog(form, "This bookid already exists!");
                        else{
                            if(donvi<0 || dongia<0)
                                JOptionPane.showMessageDialog(form, "Amount, Unit price must be nonnegative numbers!");
                            else{
                                String sql = "INSERT INTO book "
                                                + "VALUES('" + id + "','" + tentb + "','" + type + "',"
                                                + donvi + "," + dongia + ");";
                                int rs = stt.executeUpdate(sql);
                                if (rs == 1) {
                                    JOptionPane.showMessageDialog(form, "Success!!");
                                } else {
                                    JOptionPane.showMessageDialog(form, "Error!!");
                                }
                            }
                        }
                    }
                    
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });

    }
    //Ham xu li su kien nut Edit in Book
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
                    String tentb = "bookname='" + form.getTfName().getText() + "'";
                    int dongia = Integer.parseInt(form.getTfDonGia().getText());
                    int donvi = Integer.parseInt(form.getTfDonVi().getText());
                    String type = "categoryid='" + form.getTfType().getText() + "'";
                    if(donvi<0 || dongia<0)
                        JOptionPane.showMessageDialog(form, "Amount, Unit price must be nonnegative numbers!");
                    else{
                        String sql = "update book "
                            + "set " + tentb + "," + type + ",amount=" + donvi + ",unitprice="
                            + dongia + " where bookid='" + id + "'";
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
    //Ham xu li su kien nut Delete in Book
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
                        String sql = "DELETE FROM book" + " WHERE bookid='" + id + "'";
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
    //Ham xu li su kien nut Back
    private void eventBtnQuayLai() {
        form.getBtnQuayLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                form.setVisible(false);
                index = new MainForm(type,currentid);
                index.setVisible(true);
            }
        });
    }
    //Ham xu li su kien nut Reset
    private void eventBtnNhapLai() {
        form.getBtnNhapLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                form.getTfDonGia().setText("0");
                form.getTfDonVi().setText("0");
                form.getTfIDTB().setText("");
                form.getTfIDThem().setText("");
                form.getTfName().setText("");
                form.getTfType().setText("");
                form.getTfTypeID().setText("");
                form.getTfTypeName().setText("");
            }
        });

    }
    //Ham xu li su kien nut Add in BookCategories
    private void eventBtnThemType() {
        form.getBtnThemType().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThemType();
            }

            private void actionThemType() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();

                    String id = form.getTfTypeID().getText();
                    String tentype = form.getTfTypeName().getText();
                    int kt = 1;
                    for(int i=0 ;i < form.getTbType().getRowCount()-1;i++){
                        if(id.toUpperCase().equals(form.getTbType().getValueAt(i, 0).toString().toUpperCase()))
                            kt=0;
                    }
                    if("".equals(id))
                        JOptionPane.showMessageDialog(form, "You must enter book category id!");
                    else{
                        if(kt==0)
                            JOptionPane.showMessageDialog(form, "This book category id already exists!");
                        else{
                            String sql = "INSERT INTO bookcategories "
                            + "VALUES('" + id + "','" + tentype + "');";
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
    //Ham xu li su kien nut Edit in BookCategories
    private void eventBtnSuaType() {
        form.getBtnSuaType().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actionSuaType();
            }

            private void actionSuaType() {
                try{
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String id = form.getTfTypeID().getText();
                    String tentype = "categoryname= '" + form.getTfTypeName().getText() + "'";
                    String sql = "update bookcategories "
                            + "set " + tentype + " where categoryid='" + id + "'";
                    int rs = stt.executeUpdate(sql);
                    if (rs == 1) {
                        JOptionPane.showMessageDialog(form, "Success!!");
                    } else {
                        JOptionPane.showMessageDialog(form, "Error!!");
                    }

                }catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
            
        });
    }
    //Ham xu li su kien nut Delete in BookCategories
    private void eventBtnXoaType() {
        form.getBtnXoaType().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionXoaType();
            }

            private void actionXoaType() {
                try {
                    String id = form.getTfTypeID().getText();
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    int kq =JOptionPane.showConfirmDialog(form, "Are you sure?", "Waring", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    if(kq == JOptionPane.OK_OPTION){
                        String sql = "DELETE FROM bookcategories" + " WHERE categoryid='" + id + "'";
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
}
