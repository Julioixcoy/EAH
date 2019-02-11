/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Modify.RelationToPerson;

import Conexion.Conexion;
import Entidades.Family;
import Entidades.FamilyComplete;
import Entidades.Parents;
import Entidades.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import tableView.ListParent;
import tableView.ParentsCod;
import tableView.StudentCod;
import tableView.StudentSon;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class ModifyFamilyController implements Initializable {

    @FXML
    private TableView<ParentsCod> tableParents;
    @FXML
    private TableColumn<ParentsCod, String> codigoMadre;
    @FXML
    private TableColumn<ParentsCod, String> madre;
    @FXML
    private TableColumn<ParentsCod, String> codigoPadre;
    @FXML
    private TableColumn<ParentsCod, String> padre;
    @FXML
    private JFXButton btnModifyParents;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXTextField txtCodFamiliar;
    @FXML
    private JFXToggleButton toggleMomLive;
    @FXML
    private JFXToggleButton toggleDadLive;
    @FXML
    private TableColumn<StudentCod, String> codigoHijo;
    @FXML
    private TableColumn<StudentCod, String> nombreHijo;
    @FXML
    private JFXButton modifyHijo;
    @FXML
    private JFXButton btnSave;
    @FXML
    private TableView<StudentCod> tableSon;
    public static int idMadre=0;
    public static int idPadre=0;
    /**
     * Initializes the controller class.
     * modifica la familia
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.codigoMadre.setCellValueFactory(new PropertyValueFactory<ParentsCod,String>("codigoMom"));
        this.codigoPadre.setCellValueFactory(new PropertyValueFactory<ParentsCod,String>("codigoDa"));
        this.madre.setCellValueFactory(new PropertyValueFactory<ParentsCod,String>("nombreMom"));
        this.padre.setCellValueFactory(new PropertyValueFactory<ParentsCod,String>("nombreDa"));
        
        this.codigoHijo.setCellValueFactory(new PropertyValueFactory<StudentCod,String>("codigo"));
        this.nombreHijo.setCellValueFactory(new PropertyValueFactory<StudentCod,String>("nombre"));
         
        toggleDadLive.setDisable(true);
        toggleMomLive.setDisable(true);
        
        loadParentForFamily();
        
        tableParents.setOnMouseClicked(e->{
               eventTableParent();
           });
            
            tableParents.setOnKeyPressed(e->{
                eventTableParent();
            });
    }    
    public void eventTableParent(){
        loadSon(tableParents.getSelectionModel().getSelectedItem().getIdFamily());
        loadParentExist(tableParents.getSelectionModel().getSelectedItem().getIdPersonDa());
        loadParentExist(tableParents.getSelectionModel().getSelectedItem().getIdPersonMom());
        idMadre=tableParents.getSelectionModel().getSelectedItem().getIdPersonMom();
        idPadre=tableParents.getSelectionModel().getSelectedItem().getIdPersonDa();
    }

    @FXML
    private void modifyParents(ActionEvent event) {
        //make a popover for show the next menu
        try {   
            showWindowsParents("FamilyParent");//modifica datos generales del padre la diferencia con el de arriba es que este se enfoca solo en padre
        } catch (IOException ex) {
            Logger.getLogger(ModifyFamilyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modifyHijo(ActionEvent event) {
        try {
            showWindowsParents("ChangeFamilySon");
        } catch (IOException ex) {
            Logger.getLogger(ModifyFamilyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveAll(ActionEvent event) {
    }
    /**
     * obtiene una lista de los hijos que 
     * Pertenecen a la familia seleccionada
     * y la muuestra en una tabla
     * @param idFamilia 
     */
    public void loadSon(int idFamilia){
        Query q = conection().createNamedQuery("Student.findByidFamily").
                setParameter("idFamily", idFamilia);
        List<Student> list = q.getResultList();
        ObservableList<StudentCod> observableList =FXCollections.observableArrayList();
       
        for (Student student : list) {
            observableList.add(new StudentCod(
                                student.getPersona().getCodigoPerson(),
                                student.getPersona().getNombre(), 
                                student.getPersona().getAge(), 
                                student.getStudentPK().getIdStudent(), 
                                student.getPersona().getIdPersona()));
        }
        tableSon.setItems(observableList);
        
        
        
        
        
        
    }
    public void loadParentExist(int idPerson){

        Query q = conection().createNamedQuery("Parents.findByPersonaidPersona").
                setParameter("personaidPersona", idPerson);
        Parents p = (Parents) q.getResultList().get(0);
        
        if (p.getIsMom()) {
            toggleMomLive.setSelected(p.getIsLives());
        }else toggleDadLive.setSelected(p.getIsLives());
    }
    public void loadParentForFamily(){
        Query q = conection().createNamedQuery("Family.findAll");
        List<Family> list = q.getResultList();
        
        ObservableList<ParentsCod> parents = FXCollections.observableArrayList();
        List<FamilyComplete> fam =null;
       Parents madre=null;
       Parents padre=null;
        for (Family family : list) {
            
            Query query = conection().createNamedQuery("FamilyComplete.findByFamilyidFamily").
                    setParameter("familyidFamily", family.getIdFamily());
                
            fam=query.getResultList();
                
            for (FamilyComplete fc : fam) {
                if (fc.getParents().getIsMom()) {
                    madre=fc.getParents();
                }else padre=fc.getParents();
            }
            if (madre!=null && padre!=null) {
                parents.add(new ParentsCod(
                    madre.getPersona().getCodigoPerson(), madre.getPersona().getNombre(), madre.getPersona().getApellido(), madre.getPersona().getIdPersona(), madre.getParentsPK().getIdParents(), 
                    padre.getPersona().getCodigoPerson(), padre.getPersona().getNombre(), padre.getPersona().getApellido(), padre.getPersona().getIdPersona(), padre.getParentsPK().getIdParents()
                    ,family.getIdFamily()));
            }else if(madre==null && padre!=null){
                parents.add(new ParentsCod(
                    "---","---","---",000,000,
                    padre.getPersona().getCodigoPerson(), padre.getPersona().getNombre(), padre.getPersona().getApellido(), padre.getPersona().getIdPersona(), padre.getParentsPK().getIdParents(),family.getIdFamily()));
            }else if (padre==null && madre!=null){
                parents.add(new ParentsCod(
                    madre.getPersona().getCodigoPerson(), madre.getPersona().getNombre(), madre.getPersona().getApellido(), madre.getPersona().getIdPersona(), madre.getParentsPK().getIdParents(), 
                    "---","---","---",000,000,family.getIdFamily()));
            }else if(padre==null && madre==null){
                parents.add(new ParentsCod(    "---","---","---",000,000,    "---","---","---",000,000,family.getIdFamily()));
            }
    
            
        }
       this.tableParents.setItems(parents);
    }
    public void showWindowsParents(String pane) throws IOException{
        Stage stage = new Stage(StageStyle.UNIFIED);
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Modify/"+pane+".fxml"));
         
        Parent parent= loader.load(); 
        
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Actualizar >Familia>>Padres");
        stage.show();
    }
    public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
}
