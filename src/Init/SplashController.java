/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Init;

import Conexion.Conexion;
import Entidades.PropertiesSystem;
import com.sun.javafx.application.LauncherImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class SplashController  extends Application {

    
    
  
    public static final int COUNT_LIMIT=10;
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @Override
    public void start(Stage stage) throws Exception{
        
        /*Parent root = FXMLLoader.load(getClass().getResource("/UIfX/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loadLogo(stage);
        stage.show();*/
    }
    public void init() throws Exception{
        for (int i = 0; i < COUNT_LIMIT+1; i++) {
            double progress =(double) i/COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
           Thread.sleep(0);
            
        }
        
    }
      public static void main(String[]args){
        //launch(args);
        LauncherImpl.launchApplication(SplashController.class,PreloaderClass.class, args);
    }
  
}
