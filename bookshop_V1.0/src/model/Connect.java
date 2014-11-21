/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author K55VM
 */
public class Connect {
    public Connection getCnn() {
        return cnn;
    }

    public void setCnn(Connection cnn) {
        this.cnn = cnn;
    }
    private Connection cnn = null;
    /*Ham tao ket noi den co so du lieu mysql*/
    public Connect(String url) throws Exception {
        try {
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection(url, username, password);
            System.out.println("ket noi csdl thanh cong");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connect Fails " + e.getMessage());
        }
    }
    
    public void Close() throws SQLException {
        if (cnn != null) {
            cnn.close();
        }

    }
    
}
