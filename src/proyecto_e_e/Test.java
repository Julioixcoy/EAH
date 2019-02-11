/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_e_e;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author julio
 */
public class Test extends Preloader {
    
    ProgressBar bar;
    Stage stage;
    private Stage preloader;
private Scene scene; 
private String ipServer;
private String user;
private String password;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
//        this.stage = stage;
//        stage.setScene(createPreloaderScene());        
//        stage.show();
this.preloader = primaryStage;
        preloader.setScene(scene);
        preloader.initStyle(StageStyle.UNDECORATED);
        loadLogo(preloader);
        preloader.show();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }    
    @Override
    public void init()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
         scene= new Scene(root);
    }
    public void loadLogo(Stage primaryStage ){
    
    try {
    primaryStage.getIcons().add(new Image("/Recursos/logo.png"));  
    //primaryStage.setTitle(tps.getValue());

    } catch (Exception e) {
    System.out.println("Excepcion Capturada No se Encontro Logo");
    }
   }
}
