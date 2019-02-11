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
public class ParentsCod {
    public final SimpleStringProperty codigoMom;
    public final SimpleStringProperty nombreMom;
    public final SimpleStringProperty apellidoMom;
    public final SimpleIntegerProperty idPersonMom;
    public final SimpleIntegerProperty idParentMom;

    public final SimpleStringProperty codigoDa;
    public final SimpleStringProperty nombreDa;
    public final SimpleStringProperty apellidoDa;
    public final SimpleIntegerProperty idPersonDa;
    public final SimpleIntegerProperty idParentDa;

    public final SimpleIntegerProperty idfamily;
    
    public ParentsCod(String codigo, String nombre, String apellido,Integer idPerson, Integer idParent,
                    String codigoda, String nombreDa, String apellidoDa,Integer idPersonDa, Integer idParentDa
                    ,Integer idFamily    ) {
        this.idfamily=new SimpleIntegerProperty(idFamily);
        
        this.codigoMom = new SimpleStringProperty(codigo);
        this.nombreMom = new SimpleStringProperty(nombre);
        this.apellidoMom = new SimpleStringProperty(apellido);
        this.idPersonMom =new SimpleIntegerProperty(idPerson);
        this.idParentMom = new SimpleIntegerProperty(idParent);
        
        this.codigoDa = new SimpleStringProperty(codigoda);
        this.nombreDa = new SimpleStringProperty(nombreDa);
        this.apellidoDa = new SimpleStringProperty(apellidoDa);
        this.idPersonDa =new SimpleIntegerProperty(idPersonDa);
        this.idParentDa = new SimpleIntegerProperty(idParentDa);
        
    }
    public void setIdFamily(int id){
        this.idfamily.setValue(id);
    }
    public int getIdFamily(){
      return this.idfamily.get();
    }
     public void setCodigoMom(String v){
        this.codigoMom.setValue(v);
    }
    public String getCodigoMom(){
        return this.codigoMom.get();
    }
    public void setNombreMom(String nombre){
        this.nombreMom.set(nombre);
    }
    public String getNombreMom(){
        return this.nombreMom.get();
    }
    public void setApellidoMom(String nombre){
        this.apellidoMom.set(nombre);
    }
    public String getApellidoMom(){
        return this.apellidoMom.get();
    }
    public void setIdPersonMom(int edad){
        this.idPersonMom.set(edad);
    }
    public int getIdPersonMom(){
        return this.idPersonMom.get();
    }
    public void setIdParentMom(int edad){
        this.idParentMom.set(edad);
    }
    public int getIdParentMom(){
        return this.idParentMom.get();
    }
    
    
     public void setCodigoDa(String v){
        this.codigoDa.setValue(v);
    }
    public String getCodigoDa(){
        return this.codigoDa.get();
    }
    public void setNombreDa(String nombre){
        this.nombreDa.set(nombre);
    }
    public String getNombreDa(){
        return this.nombreDa.get();
    }
    public void setApellidoDa(String nombre){
        this.apellidoDa.set(nombre);
    }
    public String getApellidoDa(){
        return this.apellidoDa.get();
    }
    public void setIdPersonDa(int edad){
        this.idPersonDa.set(edad);
    }
    public int getIdPersonDa(){
        return this.idPersonDa.get();
    }
    public void setIdParentDa(int edad){
        this.idParentDa.set(edad);
    }
    public int getIdParentDa(){
        return this.idParentDa.get();
    }
}
