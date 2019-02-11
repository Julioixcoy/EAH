/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tableView;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author julio
 *
 */
public class ListCombo {
     final SimpleStringProperty codigo;
      final SimpleStringProperty name;

    public ListCombo(String Codigo, String name) {
         this.codigo = new SimpleStringProperty(Codigo);
        this.name = new SimpleStringProperty(name);

    }

    public String getcodigo() {
        return codigo.get();
    }
    public String getname() {
        return name.get();
    }
    public void setname(String name){
        this.name.set(name);
    }
    public void setcodigo(String codigo){
        this.codigo.set(codigo);
    }

    @Override
    public String toString() {
        return  name.getValue() ;
    }
    
    

}
