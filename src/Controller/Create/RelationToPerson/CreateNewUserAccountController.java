/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Create.RelationToPerson;

import ComboFilter.ComboBoxAutoComplete;
import Conexion.Conexion;
import Controller.ManageUsersPropertiesController;
import DAO.TypeUserJpaController;
import DAO.UserJpaController;
import DAO.UserModuleJpaController;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Module;
import Entidades.Persona;
import Entidades.TypeUser;
import Entidades.User;
import Entidades.UserModule;
import Pojos.UserModuleItemEnable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class CreateNewUserAccountController implements Initializable {

    @FXML
    private Label LabelTitleForm;
    @FXML
    private JFXTextField TxtNameUser;
    @FXML
    private JFXPasswordField TxtPassword1;
    @FXML
    private JFXPasswordField TxtPassword2;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXComboBox<String> ComboTypeUser;
    @FXML
    private JFXButton AddTypeAccount;
    @FXML
    private JFXComboBox<String> comboPersona;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getallTypeUsers();
        getAllPersonsUsers();
        comboPersona.setTooltip(new Tooltip());
      
        comboPersona.getItems().setAll(obsList);
        new ComboBoxAutoComplete<String>(comboPersona);
        
        FilteredList DataFilter= new FilteredList(obsList, p->true);
        SortedList<String> SortedData = new SortedList<>(DataFilter);
       comboPersona.valueProperty().addListener(new ChangeListener<String>() {
          
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               System.out.println();
           }
       });
       
       comboPersona.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(comboPersona.getSelectionModel().getSelectedIndex());
           }
       });
    /*   comboPersona.itemsProperty().bind(new ListBinding<String>() {
           @Override
           protected ObservableList<String> computeValue() {
               //To change body of generated methods, choose Tools | Templates.
           }
       });*/
        comboPersona.setItems(obsList);
       
    }    

    @FXML
    private void CloseWindow(ActionEvent event) {
    Stage btn= (Stage) btnCancel.getScene().getWindow();
              btn.close();
    }

    @FXML
    private void SaveUser(ActionEvent event) {
        
          if(modifyColaborator){
            if(TxtNameUser.getText().isEmpty() || TxtPassword1.getText().isEmpty()||  TxtPassword2.getText().isEmpty()){
                 Excepciones.Alerta.AlertWarning("Usuario", "Modificacion De Usuarios", "Debe De Llentar Todos Los Campos");
            }else{
                if((TxtPassword1.getText().trim().equals(TxtPassword2.getText().trim()) && !TxtNameUser.getText().trim().isEmpty()) && !comboPersona.getSelectionModel().getSelectedItem().isEmpty()){
                     EntityManagerFactory emf= Conexion.getInstancia().getEMF();
                     EntityManager em= emf.createEntityManager();
                     UserJpaController UserControler= new UserJpaController(emf);
                     Query qr= em.createNamedQuery("Users.findByidPersona").setParameter("iduser",ListPerson.get(comboPersona.getSelectionModel().getSelectedIndex()).getIdPersona());
                     User users= (User) qr.getResultList().get(0);
                     users.setPassword(TxtPassword1.getText());
                     users.setName(TxtNameUser.getText());
                     users.setEnable(Boolean.TRUE);
                    try {
                        UserControler.create(users);
                        Excepciones.Alerta.AlertConfirmation("Usuarios", "Modificacion de usuario", "Modificacion efectuada exitosamente ");
                        CloseWindow(event);
                    } catch (NonexistentEntityException ex) {
                        Excepciones.Alerta.AlertError("Usuarios", "Modificacion de usuario", "no se puede efectar \n"
                                + "contacte al proveedor del sistema");
                        Logger.getLogger(CreateNewUserAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Excepciones.Alerta.AlertError("Cuenta de usuario", "Modificacion", "asegurese de llenar todos los campos");
                        Logger.getLogger(CreateNewUserAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            
        }else{
            try {
            EntityManagerFactory emf= Conexion.getInstancia().getEMF();
            UserJpaController UserControler= new UserJpaController(emf);
            if(TxtNameUser.getText().isEmpty() || TxtPassword2.getText().isEmpty()|| TxtNameUser.getText().isEmpty()){
                Excepciones.Alerta.AlertWarning("Usuario", "Creacion De Usuarios", "Debe De Llenar Todos Los Campos");
            }else{
                if((TxtPassword1.getText().trim().equals(TxtPassword2.getText().trim()) && !TxtNameUser.getText().trim().isEmpty()) && !comboPersona.getSelectionModel().getSelectedItem().isEmpty()){
                    if(ifExistsUser(comboPersona.getSelectionModel().getSelectedIndex())){
                        Excepciones.Alerta.AlertConfirmation("Usuarios", "Creacion", "El usuario que intenta crear ya tiene un username"
                                + " \n intente modificar");
                    }else{
                         User usuario = BuildUser(true, TxtNameUser.getText(), TxtPassword1.getText(),ListaTypeUsers.get(ComboTypeUser.getSelectionModel().getSelectedIndex()), ListPerson.get(comboPersona.getSelectionModel().getSelectedIndex()));
                            UserControler.create(usuario);
                            Query consulta = conection().createNamedQuery("User.findAll");
                            CreateConfigurationUser((User) consulta.getResultList().get(consulta.getResultList().size()-1));
                            Excepciones.Alerta.AlertConfirmation("Usuarios", "Creacion de usuario", "Usuario Creado Exitosamente");
                            CloseWindow(event);
                        if (initSystem) {
//                            CreateConfigurationUser( ListPerson.get(comboPersona.getSelectionModel().getSelectedIndex()).getIdPersona());
                            CloseWindow(event);
                       }
                    }
                    
                    
                }
            }
           } catch (Exception ex) {
            Logger.getLogger(CreateNewUserAccountController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
      
    }
     public EntityManager conection(){
          EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            return em;
    }
public void getallTypeUsers(){
        EntityManagerFactory emf= Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr=  em.createNamedQuery("TypeUser.findAll");
        ListaTypeUsers= qr.getResultList();
        obsTypeusers= FXCollections.observableArrayList();
        for (int i = 0; i < ListaTypeUsers.size(); i++) {
            if(ListaTypeUsers.get(i).getEnable()){
                obsTypeusers.add(ListaTypeUsers.get(i).getIdTypeUser());
            }
        }
       ComboTypeUser.setItems(obsTypeusers);
    }
          

    @FXML
    private void showNewTypeAccount(ActionEvent event) {
        CreateNewTypeAccount();
    }
    public List<Persona> getAllPersonsUsers(){
        EntityManagerFactory emf= Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("Persona.findAll");
        ListPerson= qr.getResultList();
        obsList=FXCollections.observableArrayList();
        for (int i = 0; i < ListPerson.size(); i++) {
             obsList.add(ListPerson.get(i).getNombre()+" "+ ListPerson.get(i).getApellido());
        }
        
        return ListPerson;
    }
    
    public boolean ifExistsUser(int index){
           EntityManagerFactory emf=Conexion.getInstancia().getEMF();
           EntityManager em= emf.createEntityManager();
           Query qr= em.createNamedQuery("User.findByIduser").setParameter("iduser", ListPerson.get(index).getIdPersona());
           if(qr.getResultList().size()>0){
               return true;
           }else{
                return false; 
           }
          
       }
    public User BuildUser(Boolean enable, String username, String pass, TypeUser id, Persona person ){
        User NewUser= new User();
         NewUser.setEnable(enable);
         NewUser.setName(username);
         NewUser.setPassword(pass);
         NewUser.setPersonaidPersona(person);
         NewUser.setTypeUseridTypeUser(id);
        return NewUser;   
    }
    public void CreateConfigurationUser(User user){
       EntityManagerFactory emf= Conexion.getInstancia().getEMF();
       EntityManager em= emf.createEntityManager();
               
       ManageUsersPropertiesController buildconfig= new ManageUsersPropertiesController();
       UserModuleJpaController usermodController= new UserModuleJpaController(emf);
       List<UserModuleItemEnable> tableUsermodule= new LinkedList<>();
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Personas", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Pades", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Estudiantes", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Familia", true));
       /*tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Usuarios", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Usuarios", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Bodegas", true));*/
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Financieras", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Medicas", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Emocionales"
               + "es", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Estudiantes", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Reportes", true));
       /*tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Ingreso De Productos", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Traspasos", true));*/
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Personas", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Padres", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Estudiantes", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Familia", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Usuarios", true));
       /*tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Asistencia Tecnica", true));*/
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Usuario Actual", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Admon. de Usuarios", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Config. Documentos", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Herramientas", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Pref. del Sistema", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Asistencia Tecnica", true));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Logout", true));
         
        for (int i = 0; i <tableUsermodule.size(); i++) {
           try {
               UserModule usermod= buildconfig.buildconfigModul(tableUsermodule.get(i).getItem(), tableUsermodule.get(i).isConfig(), user, tableUsermodule.get(i).getModule());
               usermodController.create(usermod);
           } catch (Exception ex) {
               Logger.getLogger(CreateNewUserAccountController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }   
    }
    public void CreateNewTypeAccount(){ 
        TextInputDialog dialog = new TextInputDialog("Nuevo Typo De Cuenta");
        dialog.setTitle("Tipos De Cuenta");
        dialog.setHeaderText("Ingrese La Informacion Que Se Requiere");
        dialog.setContentText("Nuevo Tipo De Cuenta");
        Optional<String> result=dialog.showAndWait();
        result.ifPresent((t) -> {
             SaveTypeUser(t);
        });
    }
    public void SaveTypeUser(String nameType){
    try{
        EntityManagerFactory emf= Conexion.getInstancia().getEMF();
        TypeUserJpaController newType= new TypeUserJpaController(emf);
        TypeUser typeUsers= new TypeUser();
        typeUsers.setEnable(true);
        typeUsers.setName(nameType);
        newType.create(typeUsers);
        Excepciones.Alerta.AlertConfirmation("Creacion de registro", "Tipo de Usuario", "Registro creado exitosamente");
        getallTypeUsers();
        } catch (Exception e) {
            Excepciones.Alerta.AlertWithException(e);
        }
    }

    
    private ObservableList obsTypeusers;
    List<Persona> ListPerson;
    ObservableList<String> obsList;
    public boolean initSystem=false;
   public boolean modifyColaborator=false;
   private List<TypeUser> ListaTypeUsers;
}
