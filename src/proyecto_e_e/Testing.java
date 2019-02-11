/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_e_e;

import Conexion.Conexion;
import DAO.ModuleJpaController;
import Entidades.Module;
import Entidades.User;
import Excepciones.Alerta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class Testing extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
public void loadView(Stage primaryStage){
      String packege = "Create";
            String panel="CreateNewUserAccount";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/"+packege+"/"+panel+".fxml"));
//FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
//CreaeNewCashAccouting vBoxAdministration

        
        try {
            Parent parent;
            parent = loader.load();
            
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        
        primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
    @Override
    public void start(Stage primaryStage) throws Exception {
          
        //loadView(primaryStage);
        methodStart(primaryStage);

    }
    public void methodStart(Stage primaryStage){
        
        if(getInitSystem()){
            //CreateModules();
            Alerta.AlertConfirmation("System", "Load", "Config");
         /*   CreateModules();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIfX/CreateNewUser.fxml"));
            Parent parent= loader.load();
            CreateNewUserController controller= loader.<CreateNewUserController>getController();
            controller.setTitle("Ingrese Sus Datos Personales");
            controller.setInitialSystem(true);
            Scene scene = new Scene(parent);
           
            primaryStage.setScene(scene);
            loadLogo(primaryStage);

            primaryStage.show();*/
            
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent parent;
            try {
                parent = loader.load();
            

            Scene scene = new Scene(parent);

            primaryStage.setScene(scene);
            //loadLogo(primaryStage);

            primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public void CreateModules(){
           EntityManagerFactory emf= Conexion.getInstancia().getEMF();
           EntityManager em= emf.createEntityManager();
           ModuleJpaController controllermController= new ModuleJpaController(emf);
           ArrayList<String> modulesArrayList= new ArrayList<>();
           modulesArrayList.add("Ingresar");
           modulesArrayList.add("Operaciones");
           modulesArrayList.add("Actualizar");
           modulesArrayList.add("Configuracion");
           try {
                for (int i = 0; i < modulesArrayList.size(); i++) {
                   Module module= new Module();
                   module.setEnable(true);
                   module.setNameModule(modulesArrayList.get(i));
                   controllermController.create(module);
                }
           }catch(Exception e){
               e.printStackTrace();
           }
           
       }
}
