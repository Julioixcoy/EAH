/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class Conexion {
    private static final Conexion objetoUnico = new Conexion();
    
    protected EntityManagerFactory emf;
     
    protected Conexion(){ }
    
    public static Conexion getInstancia(){
        return objetoUnico;
    }
    
    public EntityManagerFactory getEMF() {
        if (emf == null)
            setEmf();
        return emf;
    }
    
    public void setEmf() {
        if (emf == null)
           emf = Persistence.createEntityManagerFactory("Proyecto_E_EPU");
    }
    
    public void cerrarEMF() {
        if (this.emf != null)
            emf.close();
    }
}
