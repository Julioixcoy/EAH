/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.VerticalBox;

import Controller.MainAdministradorController;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class VBoxUpdateController implements Initializable {
    @FXML
    private JFXButton BtnProveedores;
    @FXML
    private JFXButton BtnCliente;
    @FXML
    private JFXButton BtnProductos;
    @FXML
    private JFXButton BtnCash;
    @FXML
    private JFXButton BtnUsuarios;
    private int opcion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
     public void ShowMain(){
        System.out.println(opcion);
        switch(opcion){
            case 1:{
                showView("Person");
                   break;
            }
            case 2:{
                showView("Parents");
                   break;
            }
            case 3:{
                showView("Student");
                   break;
            }
            case 4:{
                showView("Family");
                   break;
            }
        }
    }

   
    public void showView(String pane){
      try {
                    MainAdministradorController.rootP.getChildren().removeAll();
                    MainAdministradorController.rootP.setCenter(FXMLLoader.load(getClass().getResource("/View/Modify/"+pane+".fxml")));
                    
                } catch (IOException ex) {
                    Logger.getLogger(VBoxCreateController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }

    @FXML
    private void SelectOption(ActionEvent event) {  
        System.out.println("hola ");
        JFXButton btn= (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch(btn.getText().trim()){
            case "Personas": {this.opcion = 1; System.out.println("personas");break;}
            case "Padres" : {this.opcion=2; break;}
            case "Estudiantes" :{this.opcion=3; break;}
            case "Familia" :{this.opcion=4; break;}
            
        }
         ShowMain();
    }


/*    @FXML
    private void SelectOption(ActionEvent event) {
    }
  */  
}
