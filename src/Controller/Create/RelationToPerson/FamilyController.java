/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;;

import Conexion.Conexion;
import DAO.FamilyCompleteJpaController;
import DAO.FamilyJpaController;
import DAO.StudentJpaController;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Family;
import Entidades.FamilyComplete;
import Entidades.Parents;
import Entidades.Persona;
import Entidades.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import org.controlsfx.control.PopOver;
import tableView.ListParent;
import tableView.ListPerson;
import tableView.StudentSon;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class FamilyController implements Initializable {

    @FXML
    private TableColumn<ListPerson, String> codigoPadreTab;
    @FXML
    private TableColumn<ListPerson, String> nombrePadreTab;
    @FXML
    private TableColumn<ListPerson, String> apellidoPadreTab;
    @FXML
    private TableColumn<StudentSon, String> codigoHijoTab;
    @FXML
    private TableColumn<StudentSon, String> nombreHijoTab;
    @FXML
    private TableColumn<StudentSon, String> apellidoHijoTab;
    @FXML
    private Label lblCodigoMadre;
    @FXML
    private Label lblCodigoPadre;
    @FXML
    private Label lblMadre;
    @FXML
    private Label lblPadre;
    @FXML
    private TableView<StudentSon> tableHijosFamilia;
    @FXML
    private TableColumn<StudentSon, String> hijosCodigo;
    @FXML
    private TableColumn<StudentSon, String> nombresHijos;
    @FXML
    private TableView<ListPerson> tableParent;
    @FXML
    private JFXTextField btnBuscarPadres;
    @FXML
    private JFXButton btnAddPadre;
    @FXML
    private JFXTextField btnBuscarHijos;
    @FXML
    private TableView<StudentSon> tableSon;
    @FXML
    private JFXButton btnAddHijo;
    @FXML
    private JFXToggleButton momExist;
    @FXML
    private JFXToggleButton dadExist;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         codigoHijoTab.setCellValueFactory(new PropertyValueFactory<StudentSon,String>("codigo"));
         nombreHijoTab.setCellValueFactory(new PropertyValueFactory<StudentSon, String>("nombre"));
         apellidoHijoTab.setCellValueFactory(new PropertyValueFactory<StudentSon,String>("edad"));
         
         codigoPadreTab.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("codigo"));
         nombrePadreTab.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("nombre"));
         apellidoPadreTab.setCellValueFactory(new PropertyValueFactory<ListPerson,String>("edad"));
         
         hijosCodigo.setCellValueFactory(new PropertyValueFactory<StudentSon,String>("codigo"));
         nombresHijos.setCellValueFactory(new PropertyValueFactory<StudentSon,String>("nombre"));
         
         eventInTables();
        showSon();
        showParent();
    }   
    public void eventInTables(){
        tableParent.setOnMouseClicked(e->{
            if (e.getClickCount()==2) {
                System.out.println("copiar item a tableHijoFamilia");
                questionMom();
            }
        });
        tableSon.setOnMouseClicked(e->{
            if (e.getClickCount()==2) {
                copyItemSelect();
                System.out.println("copiar item a tableHijoFamilia");
            }
        });
    }
    
    public List<Student> loadSon(){
        
        Query q = conection().createNamedQuery("Student.findByUse").setParameter("isUse", false);
        return q.getResultList();
    }
    public void showSon(){
        ObservableList<StudentSon> list = FXCollections.observableArrayList();
        for (Student student : loadSon()) {
            list.add(new StudentSon(student.getPersona().getCodigoPerson(), student.getPersona().getNombre(), student.getPersona().getAge(), student));
        }
        tableSon.setItems(list);
    }
    public List<Parents>  loadParent(){
        Query query = conection().createNamedQuery("Parents.findAll");
        return query.getResultList();
    }
    public void showParent(){
        ObservableList<ListPerson> list = FXCollections.observableArrayList();
        for (Parents parent : loadParent()) {
            list.add(new ListPerson(parent.getPersona().getCodigoPerson(), parent.getPersona().getNombre(), parent.getPersona().getAge(), parent.getPersona()));
        }
        tableParent.setItems(list);
    }
    public void copyItemSelect(){
        StudentSon listStudent=tableSon.getSelectionModel().getSelectedItem();
        ObservableList<StudentSon> list= tableHijosFamilia.getItems();
        list.add(listStudent);
        tableHijosFamilia.setItems(list);
        
    }
    public void questionMom(){
        
        Query q = conection().createNamedQuery("Parents.findByPersonaidPersona").setParameter("personaidPersona", tableParent.getSelectionModel().getSelectedItem().getPersona().getIdPersona());
        for (int i = 0; i < q.getResultList().size(); i++) {
           q.getResultList().get(i);
            System.out.println("codigo"+q.getResultList().get(i));
        }
        Parents parents =(Parents) q.getResultList().get(0);
        if (parents.getIsMom()) {
            lblCodigoMadre.setText(tableParent.getSelectionModel().getSelectedItem().getCodigo());
            lblMadre.setText(tableParent.getSelectionModel().getSelectedItem().getNombre());
            madre=parents;
            momExist.setSelected(madre.getIsLives());
        }else{
            lblCodigoPadre.setText(tableParent.getSelectionModel().getSelectedItem().getCodigo());
            lblPadre.setText(tableParent.getSelectionModel().getSelectedItem().getNombre());
            padre=parents;
            dadExist.setSelected(padre.getIsLives());
        }
    }
    public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
    public void editStudent(Family familyComplete,boolean isHuerfano){
        Student studentModify=null;
        EntityManagerFactory emf = Conexion.getInstancia().getEMF();
       
        StudentJpaController controller = new StudentJpaController(emf);
        for (StudentSon item : tableHijosFamilia.getItems()) {
                studentModify = item.getStudent();
                studentModify.setFamilyidFamily(familyComplete);
                studentModify.setIsUse(Boolean.TRUE);
                studentModify.setIsHuerfano(isHuerfano);
            try {
                controller.edit(studentModify);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FamilyController.class.getName()).log(Level.SEVERE, null, ex);
                Excepciones.Alerta.AlertError("Error", "Fatal", ex.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(FamilyController.class.getName()).log(Level.SEVERE, null, ex);
                Excepciones.Alerta.AlertError("Error", "Fatal", ex.getMessage());
            }
        }
    }
    public void createFamilyComplete(Family family , Parents parents){
        try {
            EntityManagerFactory emf = Conexion.getInstancia().getEMF();
            FamilyComplete complete = new FamilyComplete();
            FamilyCompleteJpaController controller= new FamilyCompleteJpaController(emf);
            complete.setFamily(family);
            complete.setParents(parents);
            controller.create(complete);
//        PopOver over = new PopOver();
//        over.setAnchorX(60);
//        over.setAnchorY(30);
//        JFXTextField field = new JFXTextField();
//       
//        field.setPromptText("cod Familiar...");
        Excepciones.Alerta.AlertConfirmation("Informacion", "Familia", "Se Creo Correctamente");;
        } catch (Exception ex) {
            Logger.getLogger(FamilyController.class.getName()).log(Level.SEVERE, null, ex);
            Excepciones.Alerta.AlertError("Error", "Comunicacion", ex.getMessage());
        }
        
    }
    @FXML
    private void save(ActionEvent event) {
        EntityManagerFactory emf = Conexion.getInstancia().getEMF();
        if (madre==null || padre==null) {
            Excepciones.Alerta.AlertInformation("Familia", "Falta Parametros", "Debe Seleccionar Padres");
        }else{
            Family family = new Family();
            FamilyJpaController controller = new FamilyJpaController(emf);
            family.setDescripcion(description.getText());
            family.setEnableFather(dadExist.isSelected());
            family.setEnableMom(momExist.isSelected());
            
            controller.create(family);
            Query q = conection().createNamedQuery("Family.findAll");
            family = (Family) q.getResultList().get((q.getResultList().size()-1));
            if (dadExist.isSelected() && momExist.isSelected()) {
                editStudent(family, false);//enviar familia completa
                createFamilyComplete(family,padre);
                createFamilyComplete(family,madre);
            }else{
                editStudent(family, true);
                createFamilyComplete(family,padre);
                createFamilyComplete(family,madre);
            }
        }
            
    }
private Parents madre;
private Parents padre;

}
