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
public class ListPerson {
    public final SimpleStringProperty codigo;
    public final SimpleStringProperty nombre;
    public final SimpleIntegerProperty edad;
    private Persona persona;
    public ListPerson(String codigo, String nombre, Integer edad , Persona persona) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre =new SimpleStringProperty(nombre);
        this.edad = new SimpleIntegerProperty(edad);
        this.persona= persona;
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
    public void setPersona(Persona persona){
        this.persona= persona;
    }
    public Persona getPersona(){
        return this.persona;
    }
    
}
