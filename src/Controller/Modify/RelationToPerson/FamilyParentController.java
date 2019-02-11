/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Conexion.JdbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tableView.PersonCod;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class FamilyParentController implements Initializable {

    @FXML
    private TableView<ResultSet> tableActual;
    @FXML
    private TableColumn<ResultSet, String> codActual;
    @FXML
    private TableColumn<ResultSet, String> nombreActual;
    @FXML
    private TableView<ResultSet> tableTemp;
    @FXML
    private TableColumn<ResultSet, String> codTemp;
    @FXML
    private TableColumn<ResultSet, String> nombreTemp;
    @FXML
    private TableColumn<ResultSet, String> codBuscar;
    @FXML
    private TableColumn<ResultSet, String> nombreBuscar;
    @FXML
    private TableColumn<ResultSet, String> apellidoBuscar;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
    }
    public void loadparentActual(){
       // Connection  c= (Connection) JdbConnection.getConection().prepareStatement("SELECT * FROM Persona");;
        //PreparedStatement statement =  
    }
}
