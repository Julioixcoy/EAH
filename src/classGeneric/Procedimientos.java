/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classGeneric;

import Conexion.JdbConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author julio
 */
public class Procedimientos {
    public void callLogin(){
          try {
            Connection c = JdbConnection.getConection();
            CallableStatement miProcedure = c.prepareCall("{call login}");
            ResultSet rs = miProcedure.executeQuery();
            while (rs.next()) {
                System.out.println("rs = "+" "+ rs.getString(1) + rs.getString(2)+" "+ rs.getString(3)+" "+ rs.getString(4));
            }
        } catch (Exception e) {
        }
    }
}
