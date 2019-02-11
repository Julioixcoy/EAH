/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Init;

import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXProgressBar pBar;
    @FXML
    private Label progress;
    
    public static JFXProgressBar statProgressBar;
    public static Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        label = progress;
        //pBar.setBorder(new Border(new BorderImage (new Image("/Recursos/Admiracion.jpg"), BorderWidths.EMPTY, Insets.EMPTY, BorderWidths.EMPTY, true, BorderRepeat.REPEAT, BorderRepeat.REPEAT)));
        statProgressBar =pBar;
        //statProgressBar.getBackground().getFills().set(0,new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY));
        
    }    
    
}
