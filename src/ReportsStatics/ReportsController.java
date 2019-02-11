/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsStatics;

import Conexion.JdbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class ReportsController implements Initializable {

    @FXML
    private JFXButton btnPerson;
    @FXML
    private JFXButton btnStudent;
    @FXML
    private JFXButton btnParents;
    @FXML
    private JFXButton btnFamily;
    @FXML
    private JFXRadioButton radioBtn;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnPerson(ActionEvent event) {
    }

    @FXML
    private void btnStudent(ActionEvent event) {
        report("src/Report/Student.jasper");
    }

    @FXML
    private void btnParents(ActionEvent event) {
        report("src/Report/Parents.jasper");
    }

    @FXML
    private void btnFamily(ActionEvent event) {
        report("src/Report/Family.jasper");
    }
    private void report(String path){
          
        try {
            //  try {
            Connection c = JdbConnection.getConection();
            //String path = "src/Report/Student.jasper";
            
            JasperReport report = null;
            //String path = 
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            
            JasperPrint jPrint = JasperFillManager.fillReport(report, null, c);
            JasperViewer viewer = new JasperViewer(jPrint,false);
            viewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            viewer.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
