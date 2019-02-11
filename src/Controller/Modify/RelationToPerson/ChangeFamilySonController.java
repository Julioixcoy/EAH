/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class ChangeFamilySonController implements Initializable {

    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> codigo;
    @FXML
    private TableColumn<?, ?> nombre;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtEdad;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private DatePicker dateWasBorn;
    @FXML
    private JFXTextField txtEtnia;
    @FXML
    private DatePicker dateInit;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXTextField txtDependency;
    @FXML
    private JFXToggleButton isAlfabeto;
    @FXML
    private JFXToggleButton isLive;
    @FXML
    private JFXToggleButton isDependient;
    @FXML
    private JFXToggleButton isStudent;
    @FXML
    private JFXToggleButton dependencias;

    /**
     * Initializes the controller class.
     * quita o agrega hijos a la familia
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
