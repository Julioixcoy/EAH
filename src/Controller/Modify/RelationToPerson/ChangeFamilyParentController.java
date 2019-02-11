/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Controller.Modify.RelationToPerson.ModifyFamilyController;
import Conexion.Conexion;
import Entidades.Parents;
import Entidades.Persona;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
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
import tableView.PersonCod;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
/**
 * esta clase realizara un cambio de padres
 * a una familia, 
 * al cargar la vista se mostraran tres tablas
 * una mostrara los padres, que fueron asignados previamente
 * la otra mostrara los nuevos padres
 * y la ultima table mostrara todos los padres, en la cual se
 * eligira al padre a cambiar
 * 
 * 
 */
public class ChangeFamilyParentController implements Initializable {

    @FXML
    private TableView<PersonCod> table;
    @FXML
    private TableColumn<PersonCod, String> codigo;
    @FXML
    private TableColumn<PersonCod, String> nombre;
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
    private JFXToggleButton isStudent;
    @FXML
    private JFXToggleButton isDependient;
    @FXML
    private JFXToggleButton isLive;
    @FXML
    private JFXToggleButton isAlfabeto;
    @FXML
    private JFXTextField txtDependency;
    @FXML
    private JFXButton btnSave;

    /**
     * Initializes the controller class.
     * cambia "modifica" a los padres de una familia
     * "es" por si se asigno un padre que no
     * pertenece a la familia, o sse escribio mal
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.codigo.setCellValueFactory(new PropertyValueFactory<PersonCod,String>("codigo") );
        this.nombre.setCellValueFactory(new PropertyValueFactory<PersonCod,String>("nombre") );
        
        table.setOnMouseClicked(e->{
            fillText();
        });
        table.setOnKeyPressed(e->{
            fillText();
        });
    }    

    @FXML
    private void save(ActionEvent event) {
    }
    public void fillText(){
        PersonCod cod =table.getSelectionModel().getSelectedItem();
        Query q = conection().createNamedQuery("Parents.findByPersonaidPersona")
                    .setParameter("personaidPersona",cod.getIdPersona() );
        Parents p =(Parents) q.getResultList().get(0);
        
        txtApellido.setText(p.getPersona().getApellido());
        txtCodigo.setText(p.getPersona().getCodigoPerson());
        txtDependency.setText(p.getDependency());
        txtDireccion.setText(p.getPersona().getDireccion());
        //txtEdad.setText(((p.getPersona().getAge())));
        txtEtnia.setText(p.getPersona().getEtnia());
        txtNombre.setText(p.getPersona().getNombre());
        
        isStudent.setSelected(p.getPersona().getIsStudent());
        isDependient.setSelected(p.getIsDependent());
        isLive.setSelected(p.getIsLives());
        isAlfabeto.setSelected(p.getIsAlphabet());
    }
    public void loadParent(){
        Query q = conection().createNamedQuery("Parents.findByPersonaidPersona").
                setParameter("personaidPersona",ModifyFamilyController.idMadre );
        Parents p = (Parents) q.getResultList().get(0);
        Query q1 = conection().createNamedQuery("Parents.findByPersonaidPersona").
                setParameter("personaidPersona",ModifyFamilyController.idPadre );
        Parents p2 = (Parents) q.getResultList().get(0);
        ObservableList<PersonCod> list = FXCollections.observableArrayList();
        list.add(new PersonCod(p.getPersona().getCodigoPerson(), p.getPersona().getNombre(), p.getPersona().getAge(), p.getPersona().getIdPersona()));
        list.add(new PersonCod(p2.getPersona().getCodigoPerson(), p2.getPersona().getNombre(), p2.getPersona().getAge(), p2.getPersona().getIdPersona()));
        table.setItems(list);
    }
    
     public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }

}
