/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Conexion.Conexion;
import DAO.PersonaJpaController;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Persona;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
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
public class ModifyPersonController implements Initializable {

    @FXML
    private TableView<ListPerson> tablePerson;
    @FXML
    private TableColumn<ListPerson,String> codigo;
    @FXML
    private TableColumn<ListPerson, String> nombre;
    @FXML
    private TableColumn<ListPerson, Integer> apellido;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtAge;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtTel;
    @FXML
    private JFXToggleButton togglePadre;
    @FXML
    private DatePicker txtWasBorn;
    @FXML
    private JFXToggleButton toggleStudent;
    @FXML
    private JFXButton btnGuardar;
    private Persona p;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        codigo.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("codigo"));
        nombre.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<ListPerson,Integer>("edad"));
        loadPerson();
        
        txtBuscar.setOnKeyPressed(e->{
            search(txtBuscar.getText());
        });
        tablePerson.setOnMouseClicked(e->{
             if (e.getClickCount()==2) {
                loadPersonModify();
            }
        });
    }    
private void loadPersonModify(){
     this.p = tablePerson.getSelectionModel().getSelectedItem().getPersona();
    txtAge.setText(String.valueOf(p.getAge()));
    txtApellido.setText(p.getApellido());
    txtCodigo.setText(p.getCodigoPerson());
    txtDireccion.setText(p.getDireccion());
    txtNombre.setText(p.getNombre());
    txtTel.setText(String.valueOf(p.getTel()));
    txtWasBorn.setValue(p.getWasBorn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    
    togglePadre.setSelected(p.getIsParent());
    toggleStudent.setSelected(p.getIsStudent());
   
}
    private void editPerson(){
        p.setAge(Integer.parseInt(txtAge.getText()));
        p.setApellido(txtApellido.getText());
        p.setCodigoPerson(txtCodigo.getText());
        p.setDireccion(txtDireccion.getText());
        //p.setEnable(Boolean.FALSE);
        //p.setEtnia(txtDireccion);
        p.setIsParent(togglePadre.isSelected());
        p.setIsStudent(toggleStudent.isSelected());
        p.setNombre(txtNombre.getText());
        p.setTel(txtTel.getText());
        p.setWasBorn(java.sql.Date.valueOf(txtWasBorn.getValue()));
         EntityManagerFactory emf = Conexion.getInstancia().getEMF();
         PersonaJpaController controller = new PersonaJpaController(emf);
        try {
            controller.edit(p);
            Excepciones.Alerta.AlertInformation("Modificacion", "Almacenado", "Correctamente");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ModifyPersonController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ModifyPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void save(ActionEvent event) {
        editPerson();
    }
    private void loadPerson(){
        
        Query q = conection().createNamedQuery("Persona.findAll");
        List<Persona> list = q.getResultList();
        fillTable(list);
       
    }
    
    private void search(String characteres){
        Query q = conection().createNamedQuery("Persona.findByNombre").setParameter("nombre", characteres);
        List<Persona> personas = q.getResultList();
        fillTable(personas);
        
    }
    
    private void fillTable(List<Persona> list){
          ObservableList<ListPerson> listPersons = FXCollections.observableArrayList();
        for (Persona persona : list) {
            listPersons.add(new ListPerson(persona.getCodigoPerson(), persona.getNombre(), persona.getAge(), persona));
        }
        tablePerson.setItems(listPersons);
    }
    
     public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
    
}
