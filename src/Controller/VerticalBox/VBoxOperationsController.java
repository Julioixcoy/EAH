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
public class VBoxOperationsController implements Initializable {

    @FXML
    private JFXButton btnFinancieras;
    @FXML
    private JFXButton btnMedicas;
    @FXML
    private JFXButton btnEmocionales;
    @FXML
    private JFXButton btnEstudiantes;
    @FXML
    private JFXButton btnReports;
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
                showView("operations"+"");//anidar el nombre de la vista de 1 a 5;
                   break;
            }
            case 2:{
                showView("Parent");
                   break;
            }
            case 3:{
                showView("Student");
                   break;
            }
            case 4:{
                showView("Family");
                   break;
            }case 5:{
                showView("Reports");
                   break;
            }
            //CreateNewUserAccount
        }
    }

     @FXML
    private void SelectOption(ActionEvent event) {  
        System.out.println("hola ");
        JFXButton btn= (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch(btn.getText().trim()){
            case "Financieras": {this.opcion = 1; System.out.println("personas");break;}
            case "Medicas" : {this.opcion=2; break;}
            case "Emocionales" :{this.opcion=3; break;}
            case "Estudiantes" :{this.opcion=4; break;}
            case "Reportes" :{this.opcion=5; break;}
            
        }
         ShowMain();
    }

    public void showView(String pane){
      try {
                    MainAdministradorController.rootP.getChildren().removeAll();
                    MainAdministradorController.rootP.setCenter(FXMLLoader.load(getClass().getResource("/View/Operations/"+pane+".fxml")));
                } catch (IOException ex) {
                    Logger.getLogger(VBoxCreateController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }

   
    public void LoadConfigCatalog(){
        //Es necesario negar !MainAdminsitradorcontroller .... :D
        //btnFamilia.setVisible(MainAdministradorController.);
        /*System.out.println(MainAdministradorController.configurationUser);
        BtnBank.setDisable(!MainAdministradorController.configurationUser.get("Bancos"));
        BtnCash.setDisable(!MainAdministradorController.configurationUser.get("Cajas"));
        BtnCliente.setDisable(!MainAdministradorController.configurationUser.get("Clientes"));
        BtnProductos.setDisable(!MainAdministradorController.configurationUser.get("Productos"));
        BtnProveedores.setDisable(!MainAdministradorController.configurationUser.get("Proveedores"));
        BtnUsuarios.setDisable(!MainAdministradorController.configurationUser.get("Usuarios"));
        btnStorage.setDisable(!MainAdministradorController.configurationUser.get("Bodegas"));*/
    }
   
   
    
}
