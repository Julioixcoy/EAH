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
public class EmotionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEmotion")
    private int idEmotion;
    @Basic(optional = false)
    @Column(name = "Student_idStudent")
    private int studentidStudent;
    @Basic(optional = false)
    @Column(name = "Student_Persona_idPersona")
    private int studentPersonaidPersona;
    @Basic(optional = false)
    @Column(name = "Student_Family_idFamily")
    private int studentFamilyidFamily;
    @Basic(optional = false)
    @Column(name = "Student_Family_Parents_idParents")
    private int studentFamilyParentsidParents;
    @Basic(optional = false)
    @Column(name = "Student_Family_Parents_Persona_idPersona")
    private int studentFamilyParentsPersonaidPersona;

    public EmotionPK() {
    }

    public EmotionPK(int idEmotion, int studentidStudent, int studentPersonaidPersona, int studentFamilyidFamily, int studentFamilyParentsidParents, int studentFamilyParentsPersonaidPersona) {
        this.idEmotion = idEmotion;
        this.studentidStudent = studentidStudent;
        this.studentPersonaidPersona = studentPersonaidPersona;
        this.studentFamilyidFamily = studentFamilyidFamily;
        this.studentFamilyParentsidParents = studentFamilyParentsidParents;
        this.studentFamilyParentsPersonaidPersona = studentFamilyParentsPersonaidPersona;
    }

    public int getIdEmotion() {
        return idEmotion;
    }

    public void setIdEmotion(int idEmotion) {
        this.idEmotion = idEmotion;
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

    public int getStudentFamilyidFamily() {
        return studentFamilyidFamily;
    }

    public void setStudentFamilyidFamily(int studentFamilyidFamily) {
        this.studentFamilyidFamily = studentFamilyidFamily;
    }

    public int getStudentFamilyParentsidParents() {
        return studentFamilyParentsidParents;
    }

    public void setStudentFamilyParentsidParents(int studentFamilyParentsidParents) {
        this.studentFamilyParentsidParents = studentFamilyParentsidParents;
    }

    public int getStudentFamilyParentsPersonaidPersona() {
        return studentFamilyParentsPersonaidPersona;
    }

    public void setStudentFamilyParentsPersonaidPersona(int studentFamilyParentsPersonaidPersona) {
        this.studentFamilyParentsPersonaidPersona = studentFamilyParentsPersonaidPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEmotion;
        hash += (int) studentidStudent;
        hash += (int) studentPersonaidPersona;
        hash += (int) studentFamilyidFamily;
        hash += (int) studentFamilyParentsidParents;
        hash += (int) studentFamilyParentsPersonaidPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmotionPK)) {
            return false;
        }
        EmotionPK other = (EmotionPK) object;
        if (this.idEmotion != other.idEmotion) {
            return false;
        }
        if (this.studentidStudent != other.studentidStudent) {
            return false;
        }
        if (this.studentPersonaidPersona != other.studentPersonaidPersona) {
            return false;
        }
        if (this.studentFamilyidFamily != other.studentFamilyidFamily) {
            return false;
        }
        if (this.studentFamilyParentsidParents != other.studentFamilyParentsidParents) {
            return false;
        }
        if (this.studentFamilyParentsPersonaidPersona != other.studentFamilyParentsPersonaidPersona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EmotionPK[ idEmotion=" + idEmotion + ", studentidStudent=" + studentidStudent + ", studentPersonaidPersona=" + studentPersonaidPersona + ", studentFamilyidFamily=" + studentFamilyidFamily + ", studentFamilyParentsidParents=" + studentFamilyParentsidParents + ", studentFamilyParentsPersonaidPersona=" + studentFamilyParentsPersonaidPersona + " ]";
    }
    
}
