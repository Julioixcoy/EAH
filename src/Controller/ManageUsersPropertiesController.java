/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import DAO.UserModuleJpaController;
import Entidades.Module;
import Entidades.User;
import Entidades.UserModule;
import Pojos.UserModuleItemEnable;
import tableView.TblListPreferenceUsers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author frankzapeta
 */
public class ManageUsersPropertiesController implements Initializable {

    @FXML
    private TableView<TblListPreferenceUsers> TableViewResumeConfig;
    @FXML
    private TableColumn<TblListPreferenceUsers, String> colModulo;
    @FXML
    private TableColumn<TblListPreferenceUsers,Boolean> colItem;
    @FXML
    private JFXToggleButton ToggleEnableOrdersPurchase;
    @FXML
    private JFXToggleButton ToggleEnableConcilation;
    @FXML
    private JFXToggleButton ToggleEnablePurchase;
    @FXML
    private JFXToggleButton ToggleEnableSales;
    @FXML
    private JFXToggleButton ToggleEnableEnterProduct;
    @FXML
    private JFXToggleButton ToggleEnableTransfers;
    @FXML
    private JFXToggleButton ToggleEnableManageProvider;
    @FXML
    private JFXToggleButton ToggleEnableMoveCash;
    @FXML
    private JFXToggleButton EnableReports;
    @FXML
    private AnchorPane ToggleEnableRealUser;
    @FXML
    private JFXToggleButton ToggleEnableManageUsers;
    @FXML
    private JFXToggleButton ToggleEnableDocuments;
    @FXML
    private JFXToggleButton ToggleEnableToos;
    @FXML
    private JFXToggleButton ToggleEnablePreferenceSystem;
    @FXML
    private JFXToggleButton ToggleEnableProducts;
    @FXML
    private JFXToggleButton ToggleEnableProviders;
    @FXML
    private JFXToggleButton ToggleCashAccouting;
    private JFXToggleButton ToggleEnableStorage;
    @FXML
    private JFXButton btnSaveConfigPreferenceUser;
    
   
    @FXML
    private Tab TablResume;
    @FXML
    private Tab TabOperation;
    @FXML
    private Tab TabAdministration;
    @FXML
    private Tab TabConfiguration;
    
    Module moduleSelection;
    User userSelection;
    List<Module> ListModulo;
    List<User> ListUsers;
    List<UserModule> ListModuleUser;
    Map<String, Boolean> configHash;
    Map<Integer, String>configModuleHash;
    @FXML
    private JFXComboBox<String> ComboBoxUsuario;
    @FXML
    private JFXToggleButton ToggleEnableCustomers;
    @FXML
    private JFXToggleButton ToggleEnableBanks;
    private JFXToggleButton ToggleEnableUsers;
    @FXML
    private JFXToggleButton ToggleEnableManageCostumers;
    @FXML
    private JFXToggleButton ToggleUserReal;
    @FXML
    private Tab TabIngresar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getAllUsers();
      
//       ComboBoxUsuario.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//           loadConfigUser(newValue.intValue());
//       });
       ComboBoxUsuario.setOnAction(e->{
           System.out.println(ComboBoxUsuario.getItems().get(ComboBoxUsuario.getSelectionModel().getSelectedIndex()));
           loadConfigUser(ComboBoxUsuario.getSelectionModel().getSelectedIndex());
       });
       colModulo.setCellValueFactory(new PropertyValueFactory<TblListPreferenceUsers, String>("Modulo"));
       colItem.setCellValueFactory(c-> new SimpleBooleanProperty(c.getValue().getEnable()));
       colItem.setCellFactory(tc-> new CheckBoxTableCell<>());
       TableViewResumeConfig.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    }    

    @FXML
    private void SaveConfiguration(ActionEvent event) {
        boolean val= false;
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        UserModuleJpaController newmousController= new UserModuleJpaController(emf);
        if(ComboBoxUsuario.getSelectionModel().isEmpty()){
            Excepciones.Alerta.AlertError("Configuracion De Usuario", "Error", "Necesita Seleccionar Un Usuario Para Realizar Esta Operacion");
        }else{
            ModifyListModuleUserByHash();
            for(int i=0; i<ListModuleUser.size(); i++){
                try {
                    newmousController.edit(ListModuleUser.get(i));
                } catch (Exception ex) {
                    val= true;
                    Logger.getLogger(ManageUsersPropertiesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(val==true){
                Excepciones.Alerta.AlertError("Error", "Asignacion De Permisos", "A ocurrido un error durante"
                        + "\n esta operacion");
            }else{
                Excepciones.Alerta.AlertConfirmation("Confirmacion", "Asignacion De Permisos", "Asignacion Exitosa");
            }
            
        }
    }
    //Metodo que obtiene todos los modulos y los garda en la lista modulo
    public void getAllModules(){
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf=Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("Module.findByEnable").setParameter("enable", true);
        ListModulo= qr.getResultList();
    }
    public List<UserModule> getUserModule(String idUser, String idModule){
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf=Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("UserModule.findByidUserAndidModule").setParameter("idModule", idModule).setParameter("idUser", idUser);
        
        List<UserModule> moduser= qr.getResultList();
        return moduser;
    }
    public void getAllUsers(){
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf=Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("User.findByEnable").setParameter("enable", true);
        ListUsers= qr.getResultList();
        ObservableList<String> User =FXCollections.observableArrayList();
        for (int i = 0; i < ListUsers.size(); i++) {
             User.add(ListUsers.get(i).getIduser()+" "+ListUsers.get(i).getName());
        }
        ComboBoxUsuario.setItems(User);
    }
    public List<UserModule> testConfigExists(int i){
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("UserModule.findByIdUser").setParameter("idUser", ListUsers.get(i).getIduser());
        return qr.getResultList();
    }
   //metodo que modifica la lista y el hash
    @FXML
    private void SelectedToggle(ActionEvent event) {
        JFXToggleButton btn= (JFXToggleButton) event.getSource(); 
        System.out.println(btn.getText());
        switch(btn.getText().trim()){
            
            case "Productos":{
                configHash.put("Productos", btn.selectedProperty().get());
            
                //addproperty(btn.selectedProperty().getValue(), "1", "Productos");
                break;
            }
            case "Proveedores":{
                 configHash.put("Proveedores", btn.selectedProperty().getValue());
              
               //addproperty(btn.selectedProperty().getValue(), "1", "Proveedores");
                break;
            }
            case "Clientes":{  
                configHash.put("Clientes", btn.selectedProperty().getValue());
            
                //addproperty(btn.selectedProperty().getValue(), "1","Clientes");
                break;
            }
            case  "Cajas":{
                configHash.put("Cajas", btn.selectedProperty().getValue());
               
                  //addproperty(btn.selectedProperty().getValue(), "1","Cajas");
             break;
            }
            case "Bancos":{
                 configHash.put("Bancos", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "1", "Bancos");
                break;
            }
            case "Usuarios":{
                configHash.put("Usuarios", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "1","Usuarios");
                break;
            }
            case "Bodegas/S. Ventas":{
                configHash.put("Bodegas", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "1","Bodegas/S. Ventas");
                break;
            }
            case "Ordenes De Compra":{
                configHash.put("Ordenes De Compra", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "2", "Ordenes De Compra");
                break;
            }
            case  "Conciliaciones":{
                 configHash.put("Conciliaciones", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "2","Conciliaciones");
                break;
            }
            case "Compras":{
                configHash.put("Compras", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "2","Compras");
                break;
            }
            case "Ventas":{
                configHash.put("Ventas", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "2","Ventas");
                break;
            }
            case "Ingreso De Productos":{
                 configHash.put("Ingreso De Productos", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "2","Ingreso De Productos");
                break;
            }
            case "Traspasos":{
                  configHash.put("Traspasos", btn.selectedProperty().getValue());
                //addproperty(btn.selectedProperty().getValue(), "2","Traspasos");
                break;
            }
            case "Administracion De Clientes":{
                 configHash.put("Administracion de clientes", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "3","Administracion De Clientes");
                break;
            }
            case "Administracion De Proveedores":{
                configHash.put("Administracion de proveedores", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "3","Administracion De Proveedores");
                break;
            }
            case "Movimientos De Caja":{
                configHash.put("Movimientos De Caja", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "3","Movimientos  De Caja");
                break;
            }
            case "Reportes":{
                configHash.put("Reportes", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "3","Reportes");
                break;
            }
            case "Usuario Actual":{
                 configHash.put("Usuario Actual", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "4","Usuario Actual");
                break;
            }
            case "Administracion De Usuarios":{
                configHash.put("Administracion De Usuarios", btn.selectedProperty().getValue());
              
                  //addproperty(btn.selectedProperty().getValue(), "4","Administracion De Usuarios");
                break;
            }
            case "Preferencia Del Sistema":{
                 configHash.put("Preferencia Del Sistema", btn.selectedProperty().getValue());
                //addproperty(btn.selectedProperty().getValue(), "4","Preferencia Del Sistema");
                break;
            }
            case "Documentos":{
                configHash.put("Documentos", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "4","Documentos");
                break;
            }
            case "Herramientas":{
                configHash.put("Herramientas", btn.selectedProperty().getValue());
                  //addproperty(btn.selectedProperty().getValue(), "4","Herramientas");
                  break;
            }
        }
    }
    public void ModifyListModuleUserByHash(){
       
        for ( String key : configHash.keySet() ) {
              AdjustConfigListUser(key, configHash.get(key));  
        }
    }/*
    
    Map<String, Boolean> configHash;
    Map<Integer, String>configModuleHash;
    */
    public void loadConfigUser(int i){
       if(testConfigExists(i).isEmpty()){ 
                                                    //getPersonaidPersona().getIdPersona()
           CreateConfigurationUser((ListUsers.get(i)));

       }else{
          
           loadConfigurationUser(i);
           
       }
    }
    public void loadConfigurationUser(int j){
        ListModuleUser = testConfigExists(j);
        configHash= new HashMap<String,Boolean>();
        for (int i = 0; i < ListModuleUser.size(); i++) {
            configHash.put(ListModuleUser.get(i).getItem(), ListModuleUser.get(i).getEnable()); 
            System.out.println(ListModuleUser.get(i).getItem()+" "+ListModuleUser.get(i).getEnable());
        }
        showConfigUser();
        loadDataTableResume(j);
     
    }
   
    //Se obtiene la configuracion del item a buscar
    public boolean getConfigurationFromHashMap(String item){
        if(configHash.get(item)==null){
           return false;
        }
        return configHash.get(item);
    }
    //Se carga la informacion para posteriormente visualizarla en el frame de la configuracion
    public void showConfigUser(){
    
    ToggleEnableOrdersPurchase.setSelected(getConfigurationFromHashMap("Ordenes De Compra"));
    
    ToggleEnableConcilation.setSelected(getConfigurationFromHashMap("Conciliaciones"));
    
    ToggleEnablePurchase.setSelected(getConfigurationFromHashMap("Compras"));
    
    ToggleEnableSales.setSelected(getConfigurationFromHashMap("Ventas"));
    
    ToggleEnableEnterProduct.setSelected(getConfigurationFromHashMap("Ingreso De Productos"));
   
    ToggleEnableTransfers.setSelected(getConfigurationFromHashMap("Traspasos"));
    
    ToggleEnableManageCostumers.setSelected(getConfigurationFromHashMap("Administracion De Clientes"));
    
    ToggleEnableManageProvider.setSelected(getConfigurationFromHashMap("Administracion De Proveedores"));
  
    ToggleEnableMoveCash.setSelected(getConfigurationFromHashMap("Movimientos De Caja"));
   
    EnableReports.setSelected(getConfigurationFromHashMap("Reportes"));
    
    ToggleUserReal.setSelected(getConfigurationFromHashMap("Usuario Actual"));
    
    ToggleEnableManageUsers.setSelected(getConfigurationFromHashMap("Administracion De Usuarios"));
    
    ToggleCashAccouting.setSelected(getConfigurationFromHashMap("Cajas"));
    
    ToggleEnableDocuments.setSelected(getConfigurationFromHashMap("Documentos"));
    
    ToggleEnableToos.setSelected(getConfigurationFromHashMap("Herramientas"));
   
    ToggleEnablePreferenceSystem.setSelected(getConfigurationFromHashMap("Preferencia Del Sistema"));
    
    ToggleEnableProducts.setSelected(getConfigurationFromHashMap("Productos"));
   
    ToggleEnableProviders.setSelected(getConfigurationFromHashMap("Proveedores"));
    
    ToggleEnableStorage.setSelected(getConfigurationFromHashMap("Bodegas"));
    
    ToggleEnableBanks.setSelected(getConfigurationFromHashMap("Bancos"));
    
    ToggleEnableCustomers.setSelected(getConfigurationFromHashMap("Clientes"));
    
    ToggleEnableUsers.setSelected(getConfigurationFromHashMap("Usuarios"));
    
    ToggleEnableStorage.setSelected(getConfigurationFromHashMap("Bodegas"));
    
    
    
    
      
    }  
    public UserModule buildconfigModul(String item, boolean value, User user, Module mod){
        UserModule newUsersModule= new UserModule();
        newUsersModule.setItem(item);
        newUsersModule.setModuleidModule(mod);
        newUsersModule.setUseridUser(user);
        newUsersModule.setEnable(value);
        return newUsersModule;
       // newUsersModule.set
    }
    //metodo que se invoca al consultar un usuario y al no tener configuracion crea una que posteriormente
    //sera editada
    public void CreateConfigurationUser(User user){
        Excepciones.Alerta.CleanCache();
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        UserModuleJpaController usermodController= new UserModuleJpaController(emf);
       List<UserModuleItemEnable> tableUsermodule= new LinkedList<>();
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Personas", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Estudiantes", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Padres", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Familia", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Usuarios", false));
       /*tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Usuarios", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(1), "Bodegas", false));*/
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Financieras", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Ordenes De Compra", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Conciliaciones", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Compras", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Ventas", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Ingreso De Productos", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(2), "Traspasos", false));
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Usuario Actual", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Administracion De Usuarios", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Preferencia Del Sistema", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Documentos", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Herramientas", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(3), "Asistencia Tecnica", false));
       
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Administracion De Clientes", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Administracion De Proveedores", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Movimientos De Caja", false));
       tableUsermodule.add(new UserModuleItemEnable(new Module(4), "Reportes", false));
       
       
       
      
        for (int i = 0; i <tableUsermodule.size(); i++) {
             UserModule usermod= buildconfigModul(tableUsermodule.get(i).getItem(), tableUsermodule.get(i).isConfig(), user, tableUsermodule.get(i).getModule());
            try {
                usermodController.create(usermod);
            } catch (Exception ex) {
                Logger.getLogger(ManageUsersPropertiesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        
    }
    
    public void loadDataTableResume(int i){
        List<UserModule> data= testConfigExists(i);
        ObservableList<TblListPreferenceUsers> obslistPreference= FXCollections.observableArrayList();
        for (int j = 0; j < data.size(); j++) {
            obslistPreference.add(new TblListPreferenceUsers(data.get(j).getItem(), data.get(j).getEnable(), data.get(j).getIdUserModule()));
        }
      
        TableViewResumeConfig.setItems(obslistPreference);
    }
    public void AdjustConfigListUser(String item, boolean value){{
        int i=0;
        boolean bandera=true;
        
        while(bandera){
            System.out.println(item);
            if(i<ListModuleUser.size()-1){
                bandera= true;
            }else{
                bandera=false;
            }
            if(ListModuleUser.get(i).getItem().equals(item)){
                System.out.println("lo encontre");
                ListModuleUser.get(i).setEnable(value);
                bandera=false;
            }
            i++;
        }
     }
        
    }
    
  
}

