/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;

import Conexion.Conexion;
import DAO.PersonaJpaController;
import DAO.StudentJpaController;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Persona;
import Entidades.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import tableView.ListPerson;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class StudentController implements Initializable {

    @FXML
    private TableView<ListPerson> tablePerson;
    @FXML
    private TableColumn<ListPerson, String> codigo;
    @FXML
    private TableColumn<ListPerson, String> nombre;
    @FXML
    private TableColumn<ListPerson, Integer> edad;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblCod;
    @FXML
    private Label lblDate;
    @FXML
    private JFXTextField txtBucar;
    @FXML
    private JFXComboBox<String> levelAcademic;
    @FXML
    private JFXTextField year;
    @FXML
    private JFXTextField semester;
    @FXML
    private JFXToggleButton regularIO;
    @FXML
    private JFXButton btnSave;
    @FXML
    private DatePicker txtDateInit;
    @FXML
    private JFXToggleButton toggleMasculino;
    @FXML
    private JFXComboBox<String> comboLivesWith;
    @FXML
    private JFXTextArea txtDescripcion;
    private Persona persona;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        codigo.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("codigo"));
        nombre.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("nombre"));
        edad.setCellValueFactory(new PropertyValueFactory<ListPerson,Integer>("edad"));
        //eventos de la tabla
        tablePerson.setOnMouseClicked(e->showDataPerson());
        tablePerson.setOnKeyReleased(e->showDataPerson());
        semester.setVisible(false);
        levelAcademic.setOnAction(e-> mostrar());
        showPerson();
        levelAcademic();
    }    
    public void mostrar(){
        if (levelAcademic.getSelectionModel().getSelectedItem().contains("Universidad")) {
            semester.setVisible(true);
        }else semester.setVisible(false);
    }
    @FXML
    private void txtBuscarActionfilter(ActionEvent event) {
    
    }

    @FXML
    private void btnSave(ActionEvent event) {
        if (lblCod.getText() ==null || levelAcademic == null 
            || year.getText().isEmpty() || comboLivesWith.getSelectionModel().isEmpty()
            || txtDateInit.getValue()== null ) {
            
            Excepciones.Alerta.AlertInformation("Datos", "Faltan Campos", "Debe llenar  o "+System.lineSeparator()
            +"Seleccionar todos los campos "+System.lineSeparator()+"Obligatorios");
        
        }else{
                catchStudent();
        }
    }
    /**
     * busca en la tabla de personas, las cuales sean enables
     * @return List<Persona>
     */
    public List<Persona> loadPerson(){
        Query query = conection().createNamedQuery("Persona.findByStudentAndEnable")
                .setParameter("isStudent", true)
                .setParameter("enable", true);
        return query.getResultList();
    }
    public void showPerson(){
        ObservableList<ListPerson> listPersons = FXCollections.observableArrayList();
        for (Persona persona : loadPerson()) {
            listPersons.add(new ListPerson(persona.getCodigoPerson(), persona.getNombre(), persona.getAge(), persona));
        }
        tablePerson.setItems(listPersons);
    }
    
    public void showDataPerson(){
        
        if (tablePerson.getSelectionModel().getSelectedIndex()>-1) {
            ListPerson listPerson = tablePerson.getSelectionModel().getSelectedItem();
            lblCod.setText(listPerson.getCodigo());
            lblNombre.setText(listPerson.getNombre());
            lblDate.setText(String.valueOf(listPerson.getEdad()));
            this.persona = tablePerson.getSelectionModel().getSelectedItem().getPersona();
        }
    }
    public void levelAcademic(){
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list.addAll("Guarderia","Primaria","Basico","Diversificado","Universidad","otro");
        list2.addAll("Familia","Madre","Padre","Hermanos","Abuelos","Tios","Solo","Otros");
        comboLivesWith.setItems(list2);
        levelAcademic.setItems(list);
     }
    public void catchStudent(){
        try {
            EntityManagerFactory emf = Conexion.getInstancia().getEMF();
            Student  s = new Student();
            StudentJpaController jpaController = new StudentJpaController(emf);
            
            PersonaJpaController pjc = new PersonaJpaController(emf);
            persona.setEnable(false);
            
            pjc.edit(persona);
            
            s.setDateInit(java.sql.Date.valueOf(txtDateInit.getValue()));
            s.setEnable(true);
            s.setDescripcion(txtDescripcion.getText());
            s.setLivesWith(comboLivesWith.getSelectionModel().getSelectedItem());
            s.setIsUse(false);
            
            if (toggleMasculino.isSelected()) {
                s.setGenero("Masculino");
            }else s.setGenero("Femenino");
            
            s.setPersona(persona);
            
            jpaController.create(s);
            Excepciones.Alerta.AlertConfirmation("Estudiante", "Creado", "Correctamente");
            tablePerson.refresh();
            
        } catch (Exception ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            Excepciones.Alerta.AlertError("Error", "No  Creado!!", ex.getMessage());
        }
        
        
    }
    
    public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
    
}
