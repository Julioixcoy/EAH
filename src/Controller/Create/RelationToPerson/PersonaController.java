/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;;

import javafx.scene.control.Label;
import Conexion.Conexion;
import Conexion.JdbConnection;
import DAO.PersonaJpaController;
import Entidades.Persona;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManagerFactory;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class PersonaController implements Initializable {

    @FXML
    private DatePicker dateWasBorn;
    @FXML
    private JFXTextField txtCod;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtApellidos;
    @FXML
    private JFXTextField txtAge;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXToggleButton btnToggle;
    @FXML
    private JFXToggleButton isStudent;
    @FXML
    private JFXTextField txtEtnia;
    @FXML
    private Label labelField;
    private JFXButton btnAceptar;
    @FXML
    private JFXButton btnSaveAll;
    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
        // TODO
         labelField.setVisible(false);
        
        accionesbtn();
       
       
        
    }    
    
public void accionesbtn(){
     btnSaveAll.setOnAction(e->{
            catchData();
        });
     btnSaveAll.setOnKeyTyped(e->{
         if (e.getCode()== KeyCode.ENTER) {
         catchData();    
         }
         
     });
     btnSaveAll.setOnMouseClicked(e->{
         catchData();
     });
}
    public void catchData(){
        if (txtAge.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
                txtCod.getText().isEmpty() || txtCod.getText().isEmpty() ||
                txtDireccion.getText().isEmpty() || txtName.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtEtnia.getText().isEmpty()) {
            Excepciones.Alerta.AlertInformation("Informacion", "Datos", "LLenar los Campos Obligatorios");
            labelField.setVisible(true);
        }else {
            CreatePersonjdbc();
            clearData();
            
        }
    }
    public void saveData(){
        try {
            EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            PersonaJpaController personJpaController = new PersonaJpaController(emf);
            Persona person = new Persona();
            
            //BlackBox bb = new BlackBox();
            //BlackBoxJpaController bbjc = new BlackBoxJpaController(emf);
            
            person.setIsParent(btnToggle.isSelected());
            person.setCodigoPerson(txtCod.getText());
            person.setNombre(txtName.getText());
            person.setApellido(txtApellidos.getText());
            person.setWasBorn(java.sql.Date.valueOf(dateWasBorn.getValue()));
            person.setAge(Integer.parseInt(txtAge.getText()));
            person.setDireccion(txtDireccion.getText());
            person.setEnable(true);
            person.setTel(txtTelefono.getText());
            person.setEtnia(txtEtnia.getText());
            person.setIsStudent(isStudent.isSelected());
            personJpaController.create(person);
            Date date = null;
            GregorianCalendar d= GregorianCalendar.from(ZonedDateTime.now());
//            bb.setDate(d.getTime());
//            bb.setJustification("Create Person");
//            
//            bb.setPersonaidPersona(person);
//            bbjc.create(bb);
            
       //     Excepciones.Alerta.AlertConfirmation("Persona", "Se almaceno", "Correctamente");
        } catch (Exception ex) {
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
            Excepciones.Alerta.AlertError("Almacenamiento", "Informacion", ex.getMessage());
        }
        
    }
    public void CreatePersonjdbc(){
       JdbConnection conection = new JdbConnection();
       
        try {
            PreparedStatement myStatement = conection.getConection().prepareStatement("insert  into Persona ( codigoPerson,nombre,apellido,age,wasBorn,direccion,enable,tel,isParent,isStudent,etnia) values (?,?,?,?,?,?,?,?,?,?,?)");
            myStatement.setString(1, txtCod.getText());
            myStatement.setString(2, txtName.getText());
            myStatement.setString(3, txtApellidos.getText());
            myStatement.setInt(4, Integer.parseInt(txtAge.getText()));
            myStatement.setDate(5, java.sql.Date.valueOf(dateWasBorn.getValue()));
            myStatement.setString(6, txtDireccion.getText());
            myStatement.setBoolean(7, true);
            myStatement.setString(8, txtTelefono.getText());
            myStatement.setBoolean(9, btnToggle.isSelected());
            myStatement.setBoolean(10, isStudent.isSelected());
            myStatement.setString(11, txtEtnia.getText());
            myStatement.execute();
            Excepciones.Alerta.AlertConfirmation("Persona", "Se almaceno", "Correctamente");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

      
    public void clearData(){
//          txtDescripcion.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
//        txtTelefono2.setText("");
        txtEtnia.setText("");
        txtAge.setText("");
            txtApellidos.setText("");
            txtCod.setText("");
            txtName.setText("");
           dateWasBorn.setValue(null);
           labelField.setVisible(false);
    }

    @FXML
    private void btnSaveAll(ActionEvent event) {
    }
}
