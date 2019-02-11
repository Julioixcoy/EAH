/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import Entidades.Persona;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class PersonCod {
    public final SimpleStringProperty codigo;
    public final SimpleStringProperty nombre;
    public final SimpleIntegerProperty edad;
    public final SimpleIntegerProperty idPerson;
    public PersonCod(String codigo, String nombre, Integer edad , Integer idPerson) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre =new SimpleStringProperty(nombre);
        this.edad = new SimpleIntegerProperty(edad);
        this.idPerson= new SimpleIntegerProperty(idPerson);
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
    public void setEdad(int edad){
        this.edad.set(edad);
    }
    public int getEdad(){
        return this.edad.get();
    }
    public void setIdPersona(int id){
        this.idPerson.set(id);
    }
    public int getIdPersona(){
        return this.idPerson.get();
    }
    
}
