/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.JdbConnection;
import Entidades.User;
import Entidades.UserModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class Login_1Controller implements Initializable {

    @FXML
    private JFXTextField TxtNameUser;
    @FXML
    private JFXPasswordField TxtPassword;
    @FXML
    private JFXButton btnCanel;
    @FXML
    private JFXButton btnAceptar;

    List<User> user;
    Map<String, Boolean> configurationUser;
    List<UserModule> TblUsersModule;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         btnAceptar.getStylesheets().add("/CSS/btnAceptar.css");
        btnCanel.getStylesheets().add("/CSS/btnAceptar.css");
        TxtPassword.setOnAction((event) -> {
          Aceptar(event);
      });
    }    

    @FXML
    private void Cancelar(ActionEvent event) {
        System.out.println("event = " + TxtNameUser.getText());
        System.out.println("event = " + TxtPassword.getText());
        System.exit(0);
    }

    @FXML
    private void Aceptar(ActionEvent event) {
         try {
            Connection c = JdbConnection.getConection();
            CallableStatement miProcedure = c.prepareCall("{call Login(?,?)}");
            
            miProcedure.setString( 1,TxtNameUser.getText());
            miProcedure.setString(2,TxtPassword.getText());
            
            ResultSet rs = miProcedure.executeQuery();
            
             if (rs.getString(2).equals(TxtNameUser.getText()) && rs.getString(3).equals(TxtPassword.getText()) ) {
                 System.out.println("Se encontro usuario");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void catalogo(){
        
    }
}
