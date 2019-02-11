/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_e_e;

//import com.sun.tools.doclint.Entity;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class Proyecto_E_E extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login_1.fxml"));
        
//CreaeNewCashAccouting vBoxAdministration

        Parent parent= loader.load();
        
        Scene scene = new Scene(parent);
        scene.getWindow();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        try {
        primaryStage.getIcons().add(new Image("/Recursos/logo.png"));    
        } catch (Exception e) {
        System.out.println("Excepcion Capturada");

        }
        primaryStage.setTitle("Education And Hope  EAH");
        
        primaryStage.show();
    }
   
    
}
