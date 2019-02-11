/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class ListParent {
    public final SimpleStringProperty codigo;
    public final SimpleStringProperty nombre;
    public final SimpleStringProperty apellido;
    public final SimpleIntegerProperty idPerson;
    public final SimpleIntegerProperty idParent;

    public ListParent(String codigo, String nombre, String apellido,Integer idPerson, Integer idParent) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.idPerson =new SimpleIntegerProperty(idPerson);
        this.idParent = new SimpleIntegerProperty(idParent);
    }
     public void setCodigo(String v){
        this.codigo.setValue(v);
    }
    public String getCodigo(){
        return this.codigo.get();
    }
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }
    public String getNombre(){
        return this.nombre.get();
    }
    public void setApellido(String nombre){
        this.apellido.set(nombre);
    }
    public String getApellido(){
        return this.apellido.get();
    }
    public void setIdPerson(int edad){
        this.idPerson.set(edad);
    }
    public int getIdPerson(){
        return this.idPerson.get();
    }
    public void setIdParent(int edad){
        this.idParent.set(edad);
    }
    public int getIdParent(){
        return this.idParent.get();
    }
    
}
