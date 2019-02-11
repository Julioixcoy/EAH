/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;

import Conexion.Conexion;
import DAO.ParentsJpaController;
import Entidades.Parents;
import Entidades.Persona;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class ParentController implements Initializable {

    @FXML
    private TableView<ListPerson> tablePerson;
    @FXML
    private TableColumn<ListPerson, String> codigo;
    @FXML
    private TableColumn<ListPerson, String> nombre;
    @FXML
    private TableColumn<ListPerson, Integer> apellido;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXToggleButton toggleMadre;
    @FXML
    private JFXToggleButton toggleDependencias;
    @FXML
    private JFXTextField txtCodigoParent;
    @FXML
    private JFXTextField txtNombreParent;
    @FXML
    private JFXTextField txtDependencia;
    @FXML
    private JFXToggleButton toggleVive;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXToggleButton toggleAlfabeta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.gc();
        codigo.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("codigo"));
        nombre.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<ListPerson,Integer>("edad"));
        txtDependencia.setVisible(false);
        toggleDependencias.setOnAction(e-> txtDependencia.setVisible(true));
        tablePerson.setOnKeyReleased(e-> selectPerson());
        showPersona();
        selectParent();
        
    }    
    public List<Persona> loadPersona(){
        Query q = conection().createNamedQuery("Persona.findByIsParent").setParameter("isParent", true);
        return q.getResultList();
        
    }
    public void showPersona(){
        System.out.println("cargando personas");
        ObservableList<ListPerson> listPersons = FXCollections.observableArrayList();
        for (Persona persona : loadPersona()) {
            listPersons.add(new ListPerson(persona.getCodigoPerson(), persona.getNombre(), persona.getAge(), persona));
        }
        tablePerson.setItems(listPersons);
    }
    public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
    public void selectParent(){
        tablePerson.setOnMouseClicked(e->{
            if (e.getClickCount()==2) {
                selectPerson();
            }
        });
        tablePerson.setOnKeyReleased(e->{
             selectPerson();
        });
    }
    public void selectPerson(){
        p = tablePerson.getSelectionModel().getSelectedItem().getPersona();
                txtApellido.setText(p.getApellido());
                txtCodigoParent.setText(p.getCodigoPerson());
                txtNombreParent.setText(p.getNombre());
    }
    public void createParent(){
        try {
        EntityManagerFactory emf = Conexion.getInstancia().getEMF();
        ParentsJpaController jpaController = new ParentsJpaController(emf);
        Parents parents = new Parents();
        
        parents.setPersona(p);
        parents.setIsDependent(toggleDependencias.isSelected());
        if (toggleDependencias.isSelected()) {
            if (txtDependencia.getText().isEmpty()) {
                Excepciones.Alerta.AlertInformation("Informacion", "Depencias", "Es campo Obligatorio");
            }else
            parents.setDependency(txtDependencia.getText());
        }else parents.setDependency("Ninguna");
        parents.setIsAlphabet(toggleAlfabeta.isSelected());
        parents.setIsLives(toggleVive.isSelected());
        parents.setIsMom(toggleMadre.isSelected());
        
        
        
            jpaController.create(parents);
            Excepciones.Alerta.AlertInformation("Informacion", "Padre", "Creado Exitosamente!");
        } catch (Exception ex) {
            Logger.getLogger(ParentController.class.getName()).log(Level.SEVERE, null, ex);
            Excepciones.Alerta.AlertError("Error", "Fallo", "Creacion de Padre "+System.lineSeparator()+ex.getMessage());
        }
        
    }

    @FXML
    private void btnGuardarPadre(ActionEvent event) {
        if (!personUsed()) {
            createParent();    
        }else Excepciones.Alerta.AlertInformation("Informacion", "Persona", "Ya Ha Sido Vinculada Con Anterioridad");
        
    }
    private boolean personUsed(){
        Query q = conection().createNamedQuery("Parents.findByPersonaidPersona").setParameter("personaidPersona", p.getIdPersona());
        if (q.getResultList().size()>0) {
            return true;
        }else return false;
        
    }
    private Persona p ;
}
