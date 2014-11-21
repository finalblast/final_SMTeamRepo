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
import view.ReportForm;
/**
 *
 * @author K55VM
 */
public class Report_ctr {
    private ReportForm form = new ReportForm();
    private Connect cnn;
    Connection cn;   
    private int type;
    private String currentid;
    
    public Report_ctr(int type,String currentid) throws Exception {
        //Goi giao dien va ket noi database
        this.type=type;
        this.currentid= currentid;
        form.setVisible(true);
        cnn = new Connect("jdbc:mysql://localhost:3306/bookshop");
        cn = cnn.getCnn();
        eventForm();
    }
    //Cac ham su kien trong form
    private void eventForm() {
        eventBtnQuayLai();
        eventBtnThongKe();
        eventBtnTKbest();
    }
    //Su kien nut Back
    private void eventBtnQuayLai() {
        form.getBtnQuayLai().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                form.setVisible(false);
                MainForm a=new MainForm(type,currentid);
                a.setVisible(true);
            }
        });
    }
    //Su kien nut Report
    private void eventBtnThongKe() {
        form.getBtnThongKe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionThongKe();
            }

            private void actionThongKe() {
                try {
                    //Ket noi de chua bi truy van, lay du lieu input
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String month = form.getComBoxMonth().getSelectedItem().toString();
                    String year = form.getComboxYear().getSelectedItem().toString();
                    String tk = form.getCbTK().getSelectedItem().toString();
                    if("Month".equals(tk)){
                        String sql = "select  b.bookname, bi.billid, bi.date, bide.amountsell, b.unitprice, "
                            + "bide.amountsell * b.unitprice as 'Total' "                             
                            + "FROM account AS acc , book AS b, bill AS bi, billdetail AS bide " 
                            + "where acc.id = bi.id and bi.billid = bide.billid and bide.bookid = b.bookid "
                            + "and month(bi.date)="+month+" and year(bi.date)="+year;                    
                        //Thuc hien truy van va dua du lieu ra Table
                        ResultSet rs = stt.executeQuery(sql);
                        form.getTableCSDL().setModel(new TableModel(rs));
                    }
                    if("Year".equals(tk)){
                        String sql = "select  b.bookname, bi.billid, bi.date, bide.amountsell, b.unitprice, "
                            + "bide.amountsell * b.unitprice as 'Total' "                         
                            + "FROM account AS acc , book AS b, bill AS bi, billdetail AS bide " 
                            + "where acc.id = bi.id and bi.billid = bide.billid and bide.bookid = b.bookid "
                            + " and year(bi.date)="+year;                    
                        //Thuc hien truy van va dua du lieu ra Table
                        ResultSet rs = stt.executeQuery(sql);
                        form.getTableCSDL().setModel(new TableModel(rs));
                    }
                    Tongtien();
                    
                                
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" + ae.getMessage());
                }
            }
            //ham tinh tong tien dua ra giao dien
            private void Tongtien() {
                //lay du lieu tu Table
                int lenRow = form.getTableCSDL().getRowCount();
                int sum = 0;
                for(int i=0;i<lenRow;i++){
                    sum += Integer.parseInt(form.getTableCSDL().getValueAt(i,5 ).toString());
                }
                form.getTfTongTien().setText(""+sum);
            }
        });
    }

    private void eventBtnTKbest() {
        form.getBtnTKbest().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actionBtnTKbest();
            }

            private void actionBtnTKbest() {
                try {
                    //Ket noi de chua bi truy van, lay du lieu input
                    Connection cnt = cnn.getCnn();
                    Statement stt = cnt.createStatement();
                    String month = form.getComBoxMonth().getSelectedItem().toString();
                    String year = form.getComboxYear().getSelectedItem().toString();
                    String tk = form.getCbTK().getSelectedItem().toString();
                    if("Month".equals(tk)){
                        //Cau lenh truy van
                        String sql = "SELECT b.bookid, b.bookname, SUM(bide.amountsell) as TotalSell, b.unitprice, "
                            + "SUM(bide.amountsell) * b.unitprice as 'TotalPrice' "                             
                            + "FROM book AS b, bill AS bi, billdetail AS bide " 
                            + "where bi.billid = bide.billid and bide.bookid = b.bookid "
                            + "and month(bi.date)="+month+" and year(bi.date)="+year+" GROUP BY b.bookname "
                            + "ORDER BY SUM(bide.amountsell) DESC";                    
                        //Thuc hien truy van va dua du lieu ra Table
                        ResultSet rs = stt.executeQuery(sql);
                        form.getTableCSDL().setModel(new TableModel(rs));
                    }
                    if("Year".equals(tk)){
                        //Cau lenh truy van
                        String sql = "SELECT b.bookid, b.bookname, SUM(bide.amountsell) as TotalSell, b.unitprice, "
                            + "SUM(bide.amountsell) * b.unitprice as 'TotalPrice' "                             
                            + "FROM book AS b, bill AS bi, billdetail AS bide " 
                            + "where bi.billid = bide.billid and bide.bookid = b.bookid "
                            + "and year(bi.date)="+year+" GROUP BY b.bookname "
                            + "ORDER BY SUM(bide.amountsell) DESC";
                        //Thuc hien truy van va dua du lieu ra Table
                        ResultSet rs = stt.executeQuery(sql);
                        form.getTableCSDL().setModel(new TableModel(rs));
                    }
                    
                    Tongtien();
                    
                } catch (Exception ae) {
                    JOptionPane.showMessageDialog(form, "Error!!" +ae.getMessage());
                }
            }
            //Ham tinh tong tien va dua ra giao dien
            private void Tongtien() {
                int lenRow = form.getTableCSDL().getRowCount();
                int sum = 0;
                for(int i=0;i<lenRow;i++){
                    sum += Integer.parseInt(form.getTableCSDL().getValueAt(i,4 ).toString());
                }
                form.getTfTongTien().setText(""+sum);
            }
        });
    }
    
}
