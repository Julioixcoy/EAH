/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class FamilyParentController implements Initializable {

    @FXML
    private TableView<?> tableActual;
    @FXML
    private TableColumn<?, ?> codActual;
    @FXML
    private TableColumn<?, ?> nombreActual;
    @FXML
    private TableView<?> tableTemp;
    @FXML
    private TableColumn<?, ?> codTemp;
    @FXML
    private TableColumn<?, ?> nombreTemp;
    @FXML
    private TableColumn<?, ?> codBuscar;
    @FXML
    private TableColumn<?, ?> nombreBuscar;
    @FXML
    private TableColumn<?, ?> apellidoBuscar;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     * cambia los datos de los padres
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
    }
    
}
