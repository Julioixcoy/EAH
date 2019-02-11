/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import Entidades.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class StudentCod {
       public final SimpleStringProperty codigo;
    public final SimpleStringProperty nombre;
    public final SimpleIntegerProperty edad;
    public final SimpleIntegerProperty idStudent;
    public final SimpleIntegerProperty idPerson;
    public StudentCod(String codigo, String nombre, Integer edad ,Integer idStudent,Integer idPerson) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre =new SimpleStringProperty(nombre);
        this.edad = new SimpleIntegerProperty(edad);
        this.idStudent = new SimpleIntegerProperty(idStudent);
        this.idPerson=new SimpleIntegerProperty(idPerson);
    }
    
    public void setIdPerson(int id){
        this.idPerson.setValue(id);
    }
    public int getidPerson(){
        return this.idPerson.get();
    }
    public void setIdStudent(int id){
        this.idStudent.setValue(id);
    }
    public int getidStudent(){
        return this.idStudent.get();
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
    
}
