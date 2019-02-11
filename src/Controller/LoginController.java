/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entidades.User;
import Entidades.UserModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class LoginController implements Initializable {

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
    }

    @FXML
    private void Aceptar(ActionEvent event) {
        if(ValidateInformation()){
            loadConfigAccesSystem();
            try {
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/View/MainAdministrador.fxml"));
                Parent homeAdmin= loader.load();
                Scene homescene= new Scene(homeAdmin);
                Stage homestage= (Stage)((Node) event.getSource()).getScene().getWindow();
                MainAdministradorController controller= loader.<MainAdministradorController>getController();
                controller.setUser(user.get(0));
                controller.setConfigurationUser(configurationUser);
                controller.setnom("Inside");
                homestage.hide();
                homestage.setScene(homescene);
               
                homestage.show(); 
               
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    public void loadConfigAccesSystem(){
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        configurationUser= new HashMap<>();
        //Query qr= em.createNamedQuery("User.findByIduser").setParameter("iduser", user.get(0).getIduser());
       // Query qr= em.createNamedQuery("User.findConfigurationUser").setParameter("idPerson", user.get(0).getPersonaidPersona().getIdPersona());
        Query qr = em.createNamedQuery("UserModule.findByIdUser").setParameter("idUser",user.get(0).getIduser() );
        TblUsersModule= qr.getResultList();
        for (int i = 0; i < TblUsersModule.size(); i++) {
            configurationUser.put(TblUsersModule.get(i).getItem(), TblUsersModule.get(i).getEnable());
        }
    }

    public User getUser(){
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("User.findByName").setParameter("name",TxtNameUser.getText());
        user=  qr.getResultList();
        MainAdministradorController.user=user.get(0);
        return user.get(0);
    }
    
    //verifica el login
    public boolean ValidateInformation(){ 
        if(getUser().getName().trim().equals(TxtNameUser.getText().trim())&& getUser().getPassword().trim().equals(TxtPassword.getText().trim())){
            return true;
        }else{
            return false;
        }
    }
}
