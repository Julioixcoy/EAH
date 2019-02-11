/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julio Fernando Ixcoy
 */
@Embeddable
public class StatePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idState")
    private int idState;
    @Basic(optional = false)
    @Column(name = "Student_idStudent")
    private int studentidStudent;
    @Basic(optional = false)
    @Column(name = "Student_Persona_idPersona")
    private int studentPersonaidPersona;

    public StatePK() {
    }

    public StatePK(int idState, int studentidStudent, int studentPersonaidPersona) {
        this.idState = idState;
        this.studentidStudent = studentidStudent;
        this.studentPersonaidPersona = studentPersonaidPersona;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public int getStudentidStudent() {
        return studentidStudent;
    }

    public void setStudentidStudent(int studentidStudent) {
        this.studentidStudent = studentidStudent;
    }

    public int getStudentPersonaidPersona() {
        return studentPersonaidPersona;
    }

    public void setStudentPersonaidPersona(int studentPersonaidPersona) {
        this.studentPersonaidPersona = studentPersonaidPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idState;
        hash += (int) studentidStudent;
        hash += (int) studentPersonaidPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatePK)) {
            return false;
        }
        StatePK other = (StatePK) object;
        if (this.idState != other.idState) {
            return false;
        }
        if (this.studentidStudent != other.studentidStudent) {
            return false;
        }
        if (this.studentPersonaidPersona != other.studentPersonaidPersona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.StatePK[ idState=" + idState + ", studentidStudent=" + studentidStudent + ", studentPersonaidPersona=" + studentPersonaidPersona + " ]";
    }
    
}
