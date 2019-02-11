/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conexion;

import java.io.BufferedReader;
import java.io.FileReader;
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
        readFile();
        try {
             conection = DriverManager.getConnection("jdbc:mysql://"+ipServerr+":"+port+"/mydb",user,password);
//            CallableStatement miProcedure = c.prepareCall("{call test}");
//            ResultSet rs = miProcedure.executeQuery();
//            while (rs.next()) {
//                System.out.println("rs = "+" "+ rs.getString(1) + rs.getString(2)+" "+ rs.getString(3)+" "+ rs.getString(4));
//            }
        } catch (Exception e) {
        }
    }
    
    public static void readFile(){
       
      String cadena;
      
      
    try {
        FileReader f = new FileReader("src/META-INF/persistence.xml");
    
      BufferedReader b = new BufferedReader(f);
    
        while((cadena = b.readLine())!=null) {
          //  System.out.println(cadena);
            if (cadena.contains("jdbc:mysql://")) {
                //System.out.println(cadena);
                String[] value = cadena.split("://");
                String[] ipServer =value[1].split(":");
                
               
                ipServerr=ipServer[0];
                ipServer=ipServer[1].split("/");
                port= ipServer[0];
                System.out.println(port+" this is port");
            }else if (cadena.contains("jdbc.user\" value=\"")) {
                String[] usr = cadena.split("jdbc.user\" value=\"");
                user=usr[1];
                user=user.replaceAll("\"/>", "");
            }else if (cadena.contains("jdbc.password\" value=\"")) {
                String[] pass = cadena.split("jdbc.password\" value=\"");
                password=pass[1].replace("\"/>", "");
            }
        }
        System.out.println("ruta " + ipServerr +user+password);
     
    
    
        b.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
}
static  String port;
static String ipServerr;
 static String user;
 static String password;


}
