/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author julio
 *
 */
public class JdbConnection {
    private  static Connection conection;
    private String[] dataConection;
    
    public static Connection getConection() {
        if (conection==null) {
            createConexion();
           
        }
        return conection;
    }

    public void setConection(Connection conection) {
        this.conection = conection;
    }
    
    public static void createConexion(){
        try {
             conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","password");
//            CallableStatement miProcedure = c.prepareCall("{call test}");
//            ResultSet rs = miProcedure.executeQuery();
//            while (rs.next()) {
//                System.out.println("rs = "+" "+ rs.getString(1) + rs.getString(2)+" "+ rs.getString(3)+" "+ rs.getString(4));
//            }
        } catch (Exception e) {
        }
    }

}
