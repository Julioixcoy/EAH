/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Init;

import Conexion.Conexion;
//import Entidades.TblPropertiesSystem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author julio ixcoy
 */
public class PreloaderClass extends Preloader {

private Stage preloader;
private Scene scene; 
private String ipServer;
private String user;
private String password;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloader = primaryStage;
        preloader.setScene(scene);
        preloader.initStyle(StageStyle.UNDECORATED);
        loadLogo(preloader);
        preloader.show();
    }
    @Override
    public void init() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
         scene= new Scene(root);
    }
@Override
    public void handleApplicationNotification( PreloaderNotification notification){
        
        if (notification instanceof ProgressNotification) {
            
            FXMLDocumentController.label.setText("Cargando "+  ((ProgressNotification) notification).getProgress()*100 + "%");
            FXMLDocumentController.statProgressBar.setProgress(((ProgressNotification) notification).getProgress() );
          //  FXMLDocumentController.statProgressBar.setProgress(m.conection().);
        }
        
    }
    public void handleStateChangeNotification( StateChangeNotification notification){
        
        
        StateChangeNotification.Type type= notification.getType();
        Main2 m = new Main2();
        switch (type) {
            case BEFORE_START:
               
               {
                    try {
                        m.start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(PreloaderClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               
               preloader.hide();
            break;
//            case BEFORE_LOAD:
//        {
//            try {
//                readFile();
//                Connection test = DriverManager.getConnection("jdbc:mysql://"+this.ipServer+":3306/BussinesUP", this.user, this.password);
//                } catch (SQLException ex) {
//                            Alertas.Alerta.AlertError("Conexion", "Error", "No Se Encontro Servidor");
//                        stopThread();
//                Logger.getLogger(PreloaderClass.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//                //m.conection();
//                //crear hilo que abra conexion con la base de datos
//            break;
            case BEFORE_INIT:
                m.conection();
            break;
                
        }
    }
    
    public void stopThread(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex1) {
            Logger.getLogger(PreloaderClass.class.getName()).log(Level.SEVERE, null, ex1);
        }
        System.exit(0);
    }
    public void readFile(){
       
      String cadena;
      
      
    try {
        FileReader f = new FileReader("src/META-INF/persistence.xml");
    
      BufferedReader b = new BufferedReader(f);
    
        while((cadena = b.readLine())!=null) {
          //  System.out.println(cadena);
            if (cadena.contains("jdbc:mysql://")) {
                //System.out.println(cadena);
                String[] value = cadena.split("://");
                String[] ipServer =value[1].split(":");
               
                this.ipServer=ipServer[0];
                System.out.println(ipServer[0]);
            }else if (cadena.contains("jdbc.user\" value=\"")) {
                String[] usr = cadena.split("jdbc.user\" value=\"");
                this.user=usr[1];
                this.user=this.user.replaceAll("\"/>", "");
            }else if (cadena.contains("jdbc.password\" value=\"")) {
                String[] pass = cadena.split("jdbc.password\" value=\"");
                this.password=pass[1].replace("\"/>", "");
            }
{
                
            }
        }
    
     
    
    
        b.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
}
    
    public void loadLogo(Stage primaryStage ){
    
    try {
    primaryStage.getIcons().add(new Image("/Recursos/logo.jpg"));  
    //primaryStage.setTitle(tps.getValue());

    } catch (Exception e) {
    System.out.println("Excepcion Capturada No se Encontro Logo");
    }
   }
    public EntityManager conection(){
      EntityManagerFactory  emf = Conexion.getInstancia().getEMF();
      EntityManager em = emf.createEntityManager();
        return em;
    }
}
