/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author K55VM
 */
public class TableModel extends AbstractTableModel {

    private Vector colHeaders;                       //chua ten cot 
    private Vector tbData;                           //Chua du lieu
    /*
    *Truyen ResultSet chua du lieu lay tu database vao Table
    */
    public TableModel(ResultSet rsData) throws SQLException {
        ResultSetMetaData rsMeta = rsData.getMetaData();        //Lay meta data cua ResultSet
        int count = rsMeta.getColumnCount();                    //Lay so cot cua du lieu                       

        tbData = new Vector();
        colHeaders = new Vector(count);
        for (int i = 1; i <= count; i++) {
            colHeaders.addElement(rsMeta.getColumnName(i));
        }
        while (rsData.next()) {
            // Tao 1 vector cua du lieu theo tung hang
            Vector dataRow = new Vector(count);
            for (int i = 1; i <= count; i++) {
                dataRow.addElement(rsData.getObject(i));    //bo du lieu theo tung cot vao
            }
            tbData.addElement(dataRow);
        }
    }
    //Lay tong so hang
    @Override
    public int getRowCount() {
        return tbData.size();
    }
    //Lay tong so cot
    @Override
    public int getColumnCount() {
        return colHeaders.size();
    }
    //Doc ten cot
    public String getColumnName(int column) {
        return (String) (colHeaders.elementAt(column));
    }
    
    //Lay gia tri tu Table theo gia tri row, column
    @Override
    public Object getValueAt(int row, int column) {
        Vector rowData = (Vector) (tbData.elementAt(row));
        return rowData.elementAt(column);
    }
            
}
