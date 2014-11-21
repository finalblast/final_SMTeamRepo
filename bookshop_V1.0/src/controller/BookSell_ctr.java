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
import view.BookSellForm;
import view.MainForm;
/**
 *
 * @author huuquy
 */
public class BookSell_ctr {
    
    private BookSellForm form = new BookSellForm();
    private Connect cnn;
    Connection cn;
    public MainForm index;
    private int type;
    private String currentid;
    //Khoi tao
    public BookSell_ctr(int type, String currentid) throws Exception {
        this.type=type;
        this.currentid = currentid;
        //Hien thi form, tao ket noi
        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");
        cn = cnn.getCnn();
        loadTableStart();
        eventBtn();
    }
    //Cac su kien se co trong form
    private void eventBtn() {
        eventBtnLoadData();
        eventBtnThongTinTBCTHD();
        eventBtnThongTinTBHD();
        eventBtnThemCHTD();
        eventBtnThemHD();
        eventBtnSuaCTHD();
        eventBtnSuaHD();
        eventBtnXoaCTHD();
        eventBtnXoaHD();
        eventBtnNhapLai();
        eventBtnQuayLai();
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
                    String sql = "select * from billdetail";
                    ResultSet rs = stt.executeQuery(sql);
                    form.getTableCSDLCTHD().setModel(new TableModel(rs));
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String sql = "select * from bill ";
                    ResultSet rs = stt.executeQuery(sql);
                    form.getTableCSDLHD().setModel(new TableModel(rs));
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    //Ham xu li su kien Reset
    private void eventBtnNhapLai() {
        form.getBtnNhapLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                form.getTfIDNVien().setText("");
                form.getTfIDTB().setText("");
                form.getTfSLuong().setText("0");
                form.getTfIDCTHD().setText("");
                form.getTfIDHD().setText("");
                form.getTfIDHDon().setText("");
                form.getTfIDCTHDon().setText("");
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
    //Ham xu li su kien nut Search BillDetail
    private void eventBtnThongTinTBCTHD() {
        form.getBtnThongtinCTHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThongTin();
            }

            private void actionThongTin() {
                try {
                    String tukhoa = form.getTfIDCTHDon().getText();
                    String tk = form.getCbTKCT().getSelectedItem().toString();
                    if ("".equals(tukhoa))
                        JOptionPane.showMessageDialog(form, "You must enter keyword!");
                    else{
                        Connection cnt = cnn.getCnn();
                        Statement stt = cnt.createStatement();
                        if("Bill Id".equals(tk)){
                            String sql = "select * from billdetail where billid like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);
                            form.getTableCSDLCTHD().setModel(new TableModel(rs));
                        }
                        if("Book Id".equals(tk)){
                            String sql = "select * from billdetail where bookid like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);
                            form.getTableCSDLCTHD().setModel(new TableModel(rs));
                        }
                    }
                    
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());

                }              
            }
        });
    }
    //Ham xu li su kien nut Search Bill
    private void eventBtnThongTinTBHD() {
        form.getBtnThongtinHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThongTin();
            }

            private void actionThongTin() {
                try {
                    String tukhoa = form.getTfIDHDon().getText();
                    String tk = form.getCbTK().getSelectedItem().toString();
                    if ("".equals(tukhoa))
                        JOptionPane.showMessageDialog(form, "You must enter keyword!");
                    else{
                        Connection cnt = cnn.getCnn();
                        Statement stt = cnt.createStatement();
                        if("Bill Id".equals(tk)){
                            String sql = "select * from bill where billid like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);
                            form.getTableCSDLHD().setModel(new TableModel(rs));
                        }
                        if("User Id".equals(tk)){
                            String sql = "select * from bill where id like '%" + tukhoa + "%'";
                            ResultSet rs = stt.executeQuery(sql);
                            form.getTableCSDLHD().setModel(new TableModel(rs));
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
            String sql = "select * from billdetail";
            ResultSet rs = stt.executeQuery(sql);
            form.getTableCSDLCTHD().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
        try {
            Connection cnt = cnn.getCnn();
            Statement stt = cnt.createStatement();
            String sql = "select * from bill ";
            ResultSet rs = stt.executeQuery(sql);
            form.getTableCSDLHD().setModel(new TableModel(rs));
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
        }
    }
    
    private void eventBtnThemCHTD() {
        form.getBtnThemCTHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThemCTHD();
            }

            private void actionThemCTHD() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    
                    //String IDHoaDon = form.getTfIDHD().getText();
                    int soluong = Integer.parseInt(form.getTfSLuong().getText());
                    String IDCTHD = form.getTfIDCTHD().getText();
                    String matb = form.getTfIDTB().getText();
                    int kt = 1;
                    for(int i=0 ;i < form.getTableCSDLCTHD().getRowCount()-1;i++){
                        if(IDCTHD.toUpperCase().equals(form.getTableCSDLCTHD().getValueAt(i, 0).toString().toUpperCase())
                                && matb.toUpperCase().equals(form.getTableCSDLCTHD().getValueAt(i, 1).toString().toUpperCase()))
                            kt=0;
                    }
                    if("".equals(IDCTHD))
                        JOptionPane.showMessageDialog(form, "You must enter bill id!");
                    else{
                        if(kt==0)
                            JOptionPane.showMessageDialog(form, "This bill id and book id already exists!");
                        else{
                            if(soluong<0)
                                JOptionPane.showMessageDialog(form, "Amountsell must be nonnegative numbers!");
                            else{
                                String sql = "INSERT INTO billdetail "
                                        + "VALUES('" + IDCTHD + "','" + matb + "','" + soluong + "');";                 
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
    
    private void eventBtnThemHD() {
        form.getBtnThemHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThemHD();
            }

            private void actionThemHD() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String id = form.getTfIDNVien().getText();
                    String IDHoaDon = form.getTfIDHD().getText();
                    int kt = 1;
                    for(int i=0 ;i < form.getTableCSDLCTHD().getRowCount()-1;i++){
                        if(IDHoaDon.toUpperCase().equals(form.getTableCSDLCTHD().getValueAt(i, 0).toString().toUpperCase()))
                            kt=0;
                    }
                    if("".equals(IDHoaDon))
                        JOptionPane.showMessageDialog(form, "You must enter bill id!");
                    else{
                        if(kt==0)
                            JOptionPane.showMessageDialog(form, "This bill id already exists!");
                        else{
                            String sql = "INSERT INTO bill "
                                    + "VALUES('" + IDHoaDon + "','" + id + "', default) ";
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
    
    private void eventBtnSuaCTHD() {
        form.getBtnSuaCTHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionSua();
            }
            private void actionSua() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    int soluong = Integer.parseInt(form.getTfSLuong().getText());
                    String IDThietbi = "bookid='" + form.getTfIDTB().getText() + "'";
                    String IDCTHDon = form.getTfIDCTHD().getText();                   
                    if(soluong<0)
                        JOptionPane.showMessageDialog(form, "Amountsell must be nonnegative numbers!");
                    else{
                        String sql = "update billdetail "
                            + "set amountsell=" + soluong 
                            + " where billid='" + IDCTHDon + "' and " + IDThietbi;                   
                        int rs = stt.executeUpdate(sql);
                        if (rs == 1) {
                            JOptionPane.showMessageDialog(form, "Success!!");
                        } else{
                            JOptionPane.showMessageDialog(form, "Error!!");
                            }
                    }
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    
    private void eventBtnSuaHD() {
        form.getBtnSuaHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionSua();
            }
            private void actionSua() {
                try {
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    
                    String IDHdon = form.getTfIDHD().getText();
                    String IDNVien = "id='" + form.getTfIDNVien().getText() + "'";                                 
                    System.out.println(""+IDNVien+" "+IDHdon);
                    String sql = "update bill "
                            + "set " + IDNVien
                            + " where billid='" + IDHdon + "'";                                       
                    int rs = stt.executeUpdate(sql);
                    if (rs == 1) {
                        JOptionPane.showMessageDialog(form, "Success!!");
                    } else{
                        JOptionPane.showMessageDialog(form, "Error!!");
                    }


                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
        });
    }
    
    private void eventBtnXoaCTHD() {
        form.getBtnXoaCTHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionXoa();
            }

            private void actionXoa() {
                try {
                    String id = form.getTfIDCTHD().getText();
                    String matb = form.getTfIDTB().getText();
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();

                    String sql = "DELETE FROM billdetail" + " WHERE billid='" + id + "' and bookid='" + matb + "'";

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
    
    private void eventBtnXoaHD() {
        form.getBtnXoaHD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionXoa();
            }

            private void actionXoa() {
                try {
                    String id = form.getTfIDHD().getText();
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    int kq =JOptionPane.showConfirmDialog(form, "Are you sure?", "Waring", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    if(kq == JOptionPane.OK_OPTION){
                        String sql = "DELETE FROM bill WHERE billid='" + id + "'";
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
