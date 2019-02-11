/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Init;

import Conexion.Conexion;
import Controller.Create.RelationToPerson.CreateNewUserAccountController;
import DAO.ModuleJpaController;
import Entidades.Module;
import Entidades.PropertiesSystem;
import Entidades.User;
/*import DAO.TblModuleJpaController;
import Entidades.TblModule;
import Entidades.TblPropertiesSystem;
import Entidades.TblUsers;
import UIFxController.CreateNewUserController;
import UIFxController.MainAdministradorController;*/
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author frankzapeta
 */
public class Main2 extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       
        if(getInitSystem()==true){
            CreateModules();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIfX/CreateNewUser.fxml"));
            Parent parent= loader.load();
            //CreateNewUserController controller= loader.<CreateNewUserController>getController();
            CreateNewUserAccountController controller = loader.<CreateNewUserAccountController>getController();
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------
//            controller.setTitle("Ingrese Sus Datos Personales");
//            controller.setInitialSystem(true);
            Scene scene = new Scene(parent);
           
            primaryStage.setScene(scene);

            primaryStage.show();
            
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            primaryStage.setTitle("Education And Hope  EAH");
            Parent parent= loader.load();
            
            Scene scene = new Scene(parent);

            primaryStage.setScene(scene);

            primaryStage.show();

        }
    }
    public void setIcon(Stage primaryStage){
        //Query q = conection().createNamedQuery("TblPropertiesSystem.findAll");
        //TblPropertiesSystem system = (TblPropertiesSystem) q.getResultList().get(0);
        
         try {
                primaryStage.getIcons().add(new Image("/Recursos/logo.jpg"));    
                } catch (Exception e) {
                    System.out.println("Excepcion Capturada");

                }

    }
       public boolean getInitSystem(){
           
        EntityManagerFactory emf= Conexion.getInstancia().getEMF();
        EntityManager em= emf.createEntityManager();
        Query qr= em.createNamedQuery("User.findAll");
        List<User> users= qr.getResultList();
        System.out.println(users);
        if(users.size()==0){
           
            return true;
        }
        return false;
    }
      //Carga los modulos Del Sistema 
       public void CreateModules(){
           EntityManagerFactory emf= Conexion.getInstancia().getEMF();
           EntityManager em= emf.createEntityManager();
           ModuleJpaController controllermController= new ModuleJpaController(emf);
           ArrayList<String> modulesArrayList= new ArrayList<>();
           modulesArrayList.add("Ingresar");
           modulesArrayList.add("Operaciones");
           modulesArrayList.add("Actualizar");
           modulesArrayList.add("Configuracion");
           for (int i = 0; i < modulesArrayList.size(); i++) {
              Module module= new Module();
              module.setEnable(true);
              module.setNameModule(modulesArrayList.get(i));
               try {
                   controllermController.create(module);
               } catch (Exception ex) {
                   Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           
       }
        public EntityManager conection(){
        EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
          return em;
        }
        
}
