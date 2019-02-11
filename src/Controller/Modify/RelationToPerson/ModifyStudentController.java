/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Conexion.Conexion;
import Entidades.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import tableView.ListPerson;
import tableView.StudentCod;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class ModifyStudentController implements Initializable {

    @FXML
    private TableView<StudentCod> tablePerson;
    @FXML
    private TableColumn<StudentCod, String> codigo;
    @FXML
    private TableColumn<StudentCod, String> nombre;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXComboBox<?> comboLive;
    @FXML
    private JFXToggleButton toggleRegular;
    @FXML
    private JFXToggleButton toggleHuerfano;
    @FXML
    private JFXComboBox<?> comboGenero;
    @FXML
    private DatePicker dateInit;
    @FXML
    private JFXComboBox<?> comboLevelAcademic;
    @FXML
    private JFXTextField txtYear;
    @FXML
    private JFXTextField txtSemester;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    codigo.setCellValueFactory(new PropertyValueFactory<StudentCod,String>("codigo"));
    nombre.setCellValueFactory(new PropertyValueFactory<StudentCod,String>("nombre"));
    fillTable();
    eventTable();
    }    
    @FXML
    private void save(ActionEvent event) {
        
    }
    
    private List<Student> loadStudent(){
        Query  q = conection().createNamedQuery("Student.findByEnable").setParameter("enable", true);
        return q.getResultList();
    }
    public void fillTable(){
        ObservableList<StudentCod> student = FXCollections.observableArrayList();
        for (Student student1 : loadStudent()) {
            student.add(new StudentCod(student1.getPersona().getCodigoPerson(), student1.getPersona().getNombre(), student1.getPersona().getAge(), Integer.valueOf(student1.getStudentPK().getIdStudent()), student1.getPersona().getIdPersona()));
            
        }
        tablePerson.setItems(student);
    }
    private void eventTable(){
   
    tablePerson.setOnMouseClicked(e->{
        if (e.getClickCount() == 2) {
            System.out.println("doble click");
            fillTxtData(tablePerson.getSelectionModel().getSelectedItem().getidStudent());
        }
    });
    }
    public void fillTxtData(int idStudent){
        Query q = conection().createNamedQuery("Student.findByIdStudent").setParameter("idStudent", idStudent);
        Student s = (Student) q.getResultList().get(0);
        txtDescripcion.setText(s.getDescripcion());
      // txtSemester.setText(s.get);
    }
    public List<Student> setTexts(int idStudent){
        Query q = conection().createNamedQuery("Student.findByEnable").setParameter("enable", true);
        return q.getResultList();
    }
    public EntityManager conection(){
        EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        return em;
    }
  
    }

            

