/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

import Conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.jpa.JpaEntityManager;

/**
 *
 * @author frankzapeta
 */
public class Alerta {
    
    /** clase dedicada a lanzar las excepciones de acuerdo a al tipo de alerta que deberia ser lanzada al usuario
     * 
     * 
     type:
     * 1 : Confirmacion
     * 2 : Error
     * 3 : Information
     * 4 : None
     * 5 : Warning
     
     */
    
   public static void Alerta(int type, String Title, String HeaderTxt, String Contenttxt){
       if(type==1){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
       }else{
           if(type==2){
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
           }else{
               if(type==3){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
               }else{
                   if(type==4){
                       Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setTitle(Title);
                        alert.setHeaderText(HeaderTxt);
                        alert.setContentText(Contenttxt);
                        alert.showAndWait();
                   }else{
                       if(type==5){
                           Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle(Title);
                            alert.setHeaderText(HeaderTxt);
                            alert.setContentText(Contenttxt);
                            alert.showAndWait();
                       }
                   }
               }
               
           }
           
       }
   
   }
   
   public static void AlertConfirmation(String Title, String HeaderTxt, String Contenttxt){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   }

     public static void AlertError(String Title, String HeaderTxt, String Contenttxt){
          Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   }
     
     public static void AlertInformation(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
     public static void AlertNone(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
    public static void AlertWarning(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
    /*
    
    Metodo para crear una alerta y de esta manera determinar que tipo de producto es el que se desea crear.
    
    */
    public int OpcionAlerta(String Title, String HeaderTxt, String Contenttxt){
        int opcion;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add("/CSS/Styles.css");
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                
                ButtonType btnDerivate= new ButtonType("Derivado");
                ButtonType btnCollection= new ButtonType("Coleccion");
                ButtonType btnCombo= new ButtonType("Combo");
                ButtonType btnPrecio= new ButtonType("Agregar Precio");
                ButtonType btnCancel= new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);  
                alert.getButtonTypes().setAll(btnDerivate, btnCollection, btnCombo, btnCancel);
                Optional<ButtonType> result= alert.showAndWait();
                if(result.get().equals(btnDerivate)){
                    return 1;
                }else{
                    if(result.get().equals(btnCollection)){
                        return 2;
                    }else{
                        if(result.get().equals(btnCombo)){
                            return 3;
                        }else{
                            if(result.get().equals(btnCancel)){
                                return 4;
                            }else{
                                if(result.get().equals(btnPrecio)){
                                    return 5;
                                }
                            }
                        }
                    }
                }        
        return 0; 
    }
    
     public int val;
    public void jfxAlerta(StackPane root){
   
        JFXDialogLayout cont= new JFXDialogLayout();
        cont.setHeading(new Text("Informacion Importante para ti..."));
        cont.setBody(new Text("Actualmente el producto se encuentra creado, por favor seleccione alguna de las opciones \n"
                + "Crear: \n"));
        JFXDialog dialog= new JFXDialog(root, cont, JFXDialog.DialogTransition.CENTER);
        JFXButton btnDerivado= new JFXButton("Derivado");
        btnDerivado.setButtonType(JFXButton.ButtonType.RAISED);
        btnDerivado.setStyle("-fx-text-fill:WHITE; -fx-background-color:#5264AE; -fx-font-size:14px;");
        btnDerivado.setOnAction((event) -> {
            val=1;
            dialog.close();;
        });
        
        JFXButton btnColeccion= new JFXButton("Coleccion");
        btnColeccion.setButtonType(JFXButton.ButtonType.RAISED);
        btnColeccion.setStyle("-fx-text-fill:WHITE; -fx-background-color:#5264AE; -fx-font-size:14px;");
        btnColeccion.setOnAction((event) -> {
            val=2;
            dialog.close();
          
        });
        
        JFXButton btnCombo= new JFXButton("Combo");
        btnCombo.setButtonType(JFXButton.ButtonType.RAISED);
        btnCombo.setStyle("-fx-text-fill:WHITE; -fx-background-color:#5264AE; -fx-font-size:14px;");
        btnCombo.setOnAction((event) -> {
             val=3;
            dialog.close();
            
        });
        
       
        JFXButton btnCancel= new JFXButton("Cancelar");
        btnCancel.setButtonType(JFXButton.ButtonType.RAISED);
        btnCancel.setStyle("-fx-text-fill:WHITE; -fx-background-color:#5264AE; -fx-font-size:14px;");
        btnCancel.setOnAction((event) -> {
            dialog.close();
        });
        
        cont.setActions(btnDerivado, btnCombo, btnColeccion, btnCancel);
        
       dialog.show();
    }
     
     public int getVal(){
         return this.val;
     }
     
     static public void AlertWithException(Exception ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Look, an Exception Dialog");
            alert.setContentText("Could not find file blabla.txt!");

           

            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
         }
     
     static public void AlertWithComboBoxAndTxtField(List<?> lista){
         
     }
     
     public static int AlertProductExists(String Title, String HeaderTxt, String Contenttxt){
                Alert alert= new Alert(AlertType.INFORMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                
                ButtonType Yes= new ButtonType("Si");
                ButtonType No= new ButtonType("No");
                
                ButtonType btnCancel= new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);  
                alert.getButtonTypes().setAll(Yes, No, btnCancel);
                Optional<ButtonType> result= alert.showAndWait();
                
                if (result.get().equals(Yes)){
                   return 1;
                }else{
                    if(result.get().equals(No)){
                        return 2;
                    }else{
                        if(result.get().equals(btnCancel)){
                            return 3;
                        }
                    }
                }
                return 0;
     }
     
     public static boolean AlertConfirmationCustom(String title, String header, String Context){
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(Context);
                Optional<ButtonType> result= alert.showAndWait();
                
                if(result.get()==ButtonType.OK){
                    return true;
                }else{
                   return false;
                }
               
     }
     
     static public void CleanCache(){
         EntityManagerFactory emf= Conexion.getInstancia().getEMF();
         EntityManager em=emf.createEntityManager();
         ((JpaEntityManager)em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
     }
    
}

