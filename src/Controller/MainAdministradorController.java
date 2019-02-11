/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entidades.Module;
import Entidades.User;
import Entidades.UserModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.controlsfx.control.WorldMapView;

/**
 * FXML Controller class
 *
 * @author Julio Fernando Ixcoy
 */
public class MainAdministradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnoperation;

    @FXML
    private JFXButton btnsettings;

    @FXML
    private JFXButton btnmanage;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton btncatalog;

    @FXML
    private JFXHamburger hamburger;
  
   @FXML
    private StackPane ResumePanel;
    
    @FXML
    private BorderPane Main;
    
    static Map<String, String> PropertiesSystem= new HashMap<String, String>();
    
    public HashSet<Module> ModuleEnables;
    
  // static List<TblPropertiesSystem> prop;
        
  // static TblUsers user; 
   //Objetos que contienen la configuracion de usuarios
   static List<UserModule> TblUsersModule;
   static Map<String, Boolean> configurationUser;

    public static BorderPane rootP;
    
    public static int opcion;
    public static String opcionMenu;
    public static Properties Appsettings;
   // public TblUsers userNow;
    public String nombre;
    static User user; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
//        loadConfigSystemBasicsDB();
        rootP= Main;
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-0.5);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();
            
            if(drawer.isShown())
            {
                drawer.close();
            }
            else{
                    drawer.open();
                }
                
        });
        
       
        
 }
    
    public void LoadNavigationBar(){
        
        try {
           switch(opcion){
               case 1: {
              //    Main.setCenter(ResumePanel);
                  ResumePanel.getChildren().clear();
                  VBox create= FXMLLoader.load(getClass().getResource("/View/Create.fxml"));
                  drawer.setSidePane(create);
                  
                  drawer.open();
              //    ResumePanel.getChildren().add(FXMLLoader.load(getClass().getResource("/View/Parent.fxml")));
              
                  break;
               }
               case 2:{  
              //    Main.setCenter(ResumePanel);
                  ResumePanel.getChildren().clear();
                  VBox Operacion= FXMLLoader.load(getClass().getResource("/View/vBoxOperations.fxml"));
                  drawer.setSidePane(Operacion);
                  drawer.open();
               //   ResumePanel.getChildren().add(FXMLLoader.load(getClass().getResource("/View/ResumeOperation.fxml")));
                  break;
               }
               case 3:{
                   Main.setCenter(ResumePanel);
                  ResumePanel.getChildren().clear();
                  VBox Administracion= FXMLLoader.load(getClass().getResource("/View/VBoxUpdate.fxml"));
                  drawer.setSidePane(Administracion);
                  drawer.open();
               //   ResumePanel.getChildren().add(FXMLLoader.load(getClass().getResource("/View/ResumeAdministracion.fxml")));
                  break;
               }
               case 4:{
                   Main.setCenter(ResumePanel);
                   ResumePanel.getChildren().clear();
                   VBox Settings= FXMLLoader.load(getClass().getResource("/View/vBoxSettings.fxml"));
                   drawer.setSidePane(Settings);
                   drawer.open();
               //    ResumePanel.getChildren().add(FXMLLoader.load(getClass().getResource("/View/ResumeSettings.fxml")));   
                   break;
               }       
           } 
        } catch (IOException ex) {
            Excepciones.Alerta.AlertInformation("Testing", "Test", ex.getMessage());
            Logger.getLogger(MainAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
     }
      

      
    @FXML
    public void selectOption(ActionEvent event){
     JFXButton btn= (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch(btn.getText()){
            case "Ingresar": {opcion=1; break;}
            case "Operaciones": {opcion=2; break;}
            case "Actualizar": {opcion=3; break;}
            case "Configuracion": {opcion=4; break;}
        } 
        System.out.println(opcion);
        
      LoadNavigationBar();
    }
    
//    public static void LoadConfig(){
//        try {
//            settings load= new settings();
//            load.VerifyOrCreateFilSettings();
//            Appsettings= load.getAllProperties();
//        } catch (IOException ex) {
//            Logger.getLogger(MainAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    //Se Cargan las configuraciones basicas del sistema.
//    public static void loadConfigSystemBasicsDB(){
//        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
//        EntityManager em= emf.createEntityManager();
//        Query qr= em.createNamedQuery("TblPropertiesSystem.findAll");
//        prop= qr.getResultList();
//        for (int i = 0; i < prop.size(); i++) {
//            PropertiesSystem.put(prop.get(i).getItemConfig(), prop.get(i).getValue());
//        }
//    }
//    public void setUser(TblUsers user){
//        this.userNow=user;
////    }
//    public TblUsers getUser(){
//        return this.userNow;
//    }
//    
    public void setConfigurationUser(Map<String, Boolean> configurationUser){
        MainAdministradorController.configurationUser=configurationUser;
    }
    
    
//    public static void loadConfigAccesSystem(){
//        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
//        EntityManager em= emf.createEntityManager();
//        configurationUser= new HashMap<>();
//        Query qr= em.createNamedQuery("TblUsers.findConfigurationUser").setParameter("dpi", user.getTblPersonDPI());
//        TblUsersModule= qr.getResultList();
//        for (int i = 0; i < TblUsersModule.size(); i++) {
//            configurationUser.put(TblUsersModule.get(i).getItem(), TblUsersModule.get(i).getEnable());
//        }
//    }
    

public void setUser(User user){
        this.userNow=user;
    }
 public User getUser(){
        return this.userNow;
    }
    
//    public void setConfigurationUser(Map<String, Boolean> configurationUser){
//        MainAdministradorController.configurationUser=configurationUser;
//    }
    
    
    public static void loadConfigAccesSystem(){
        EntityManagerFactory emf= Conexion.Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        configurationUser= new HashMap<>();
        Query qr= em.createNamedQuery("User.findByIduser").setParameter("iduser", user.getIduser());
        TblUsersModule= qr.getResultList();
        for (int i = 0; i < TblUsersModule.size(); i++) {
            configurationUser.put(TblUsersModule.get(i).getItem(), TblUsersModule.get(i).getEnable());
        }
    }
    
    public void setnom(String nombre){
        this.nombre=nombre;
    }


   
  public User userNow;
   
}

