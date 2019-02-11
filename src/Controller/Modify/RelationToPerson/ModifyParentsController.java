/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Conexion.Conexion;
import DAO.ParentsJpaController;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Parents;
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
import javafx.geometry.Point2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import tableView.ListParent;
import tableView.ListPerson;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class ModifyParentsController implements Initializable {

    @FXML
    private TableView<ListParent> table;
    @FXML
    private TableColumn<ListParent, String> codigo;
    @FXML
    private TableColumn<ListParent, String > nombre;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXToggleButton toggleLive;
    @FXML
    private JFXToggleButton toggleMom;
    @FXML
    private JFXToggleButton toggleDependency;
    @FXML
    private JFXTextField txtTypeDenpendency;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXToggleButton toggleAlpha;
    private Parents parents;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        disableToggleButton(true);
        codigo.setCellValueFactory(new PropertyValueFactory<ListParent,String>("codigo"));
        nombre.setCellValueFactory(new PropertyValueFactory<ListParent,String>("nombre"));
        fillTable();
        actionTable();
    }    

    @FXML
    private void save(ActionEvent event) {
        if (toggleAlpha.isDisabled()) {
            Excepciones.Alerta.AlertInformation("Modificacion", "Anulada", "No cambie de item antes de guardar");
        }else{
            try {
                this.parents.setIsMom(toggleMom.isSelected());
                this.parents.setIsAlphabet(toggleAlpha.isSelected());
                this.parents.setIsDependent(toggleDependency.isSelected());
                this.parents.setIsLives(toggleLive.isSelected());
                this.parents.setDependency(txtTypeDenpendency.getText());
                EntityManagerFactory emf = Conexion.getInstancia().getEMF();
                ParentsJpaController controller = new ParentsJpaController(emf);
                controller.edit(parents);
                Excepciones.Alerta.AlertConfirmation("Modificacion", "Succesfull", "modificado correctamente");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ModifyParentsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ModifyParentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
     
    }
    public void fillTable(){
        ObservableList<ListParent> listParents = FXCollections.observableArrayList();
        for (Parents p : loadParent()) {
            listParents.add(new ListParent(p.getPersona().getCodigoPerson(),p.getPersona().getNombre(),
                    p.getPersona().getApellido(),p.getPersona().getIdPersona(),
                    Integer.valueOf(p.getParentsPK().getIdParents())));
        }
        table.setItems(listParents);
    }
    public List<Parents> loadParent(){
         Query query = conection().createNamedQuery("Parents.findAll");
         return query.getResultList();
    }
    //acciones sobre la tabla
    public void actionTable(){
        table.setOnMouseClicked(e->{
            System.out.println("click");
            
            if (e.getClickCount()==2) {
                disableToggleButton(false);
                shareParent(table.getSelectionModel().getSelectedItem().getIdParent());
            }
            if (e.getClickCount() == 1) {
                disableToggleButton(true);
            }
            
        });

    }
    public void shareParent(int idParent){
        System.out.println("idParent = " + idParent);
        Query q = conection().createNamedQuery("Parents.findByIdParents").setParameter("idParents", idParent);
        parents = (Parents) q.getResultList().get(0);
        toggleAlpha.setSelected(parents.getIsAlphabet());
        toggleDependency.setSelected(parents.getIsDependent());
        toggleLive.setSelected(parents.getIsLives());
        toggleMom.setSelected(parents.getIsMom());
        txtTypeDenpendency.setText(parents.getDependency());
    }
    public void disableToggleButton(boolean show){
        toggleAlpha.setDisable(show);
        toggleDependency.setDisable(show);
        toggleLive.setDisable(show);
        toggleMom.setDisable(show);
    }
     public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
    
}
