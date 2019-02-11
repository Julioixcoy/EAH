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
public class VBoxSettingsController implements Initializable {

    @FXML
    private JFXButton btnRealUser;
    @FXML
    private JFXButton btnManageUsers;
    @FXML
    private JFXButton btnConfigDocuments;
    @FXML
    private JFXButton btnTools;
    @FXML
    private JFXButton btnPreferenceSystem;
    @FXML
    private JFXButton btnSupport;
    @FXML
    private JFXButton btnLogOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ShowUserInfoNow(ActionEvent event) {
    }

    @FXML
    private void showManageUsers(ActionEvent event) {
        showView("ManageUsersProperties");
    }

    @FXML
    private void ShowPreferenceSystem(ActionEvent event) {
    }

    @FXML
    private void logoutSystem(ActionEvent event) {
    }
    public void showView(String pane){
      try {
                    MainAdministradorController.rootP.getChildren().removeAll();
                    MainAdministradorController.rootP.setCenter(FXMLLoader.load(getClass().getResource("/View/"+pane+".fxml")));
                } catch (IOException ex) {
                    Logger.getLogger(Controller.VerticalBox.VBoxCreateController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
}
