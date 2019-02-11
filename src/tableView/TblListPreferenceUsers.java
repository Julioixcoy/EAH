/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

//import impl.org.controlsfx.i18n.SimpleLocalizedStringProperty;
import impl.org.controlsfx.i18n.SimpleLocalizedStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author frankzapeta
 */
public class TblListPreferenceUsers{
    
    public final SimpleStringProperty Modulo;
    public final SimpleBooleanProperty Enable;
    public final SimpleIntegerProperty idTblModuleUser;

    public TblListPreferenceUsers(String Modulo, boolean Enable, Integer id) {
        this.Modulo = new SimpleLocalizedStringProperty(Modulo);
        this.Enable = new SimpleBooleanProperty(Enable);
        this.idTblModuleUser= new SimpleIntegerProperty(id);
    }

   
   
    
    public void setModulo(String mod){
        this.Modulo.set(mod);
    }
    public String getModulo(){
      return  this.Modulo.get();
    }  
    public void setEnable(boolean enable){
        this.Enable.set(enable);
    }
    public boolean getEnable(){
        return this.Enable.get();
    }
    public void setIdTbl(int id ){
        this.idTblModuleUser.set(id);
    }
    public Integer getidTbl(){
       return this.idTblModuleUser.get();
    }
    
}
