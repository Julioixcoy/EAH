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
public class FinancePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idFinance")
    private int idFinance;
    @Basic(optional = false)
    @Column(name = "HistorialMedico_idHistorialMedico")
    private int historialMedicoidHistorialMedico;
    @Basic(optional = false)
    @Column(name = "Student_idStudent")
    private int studentidStudent;
    @Basic(optional = false)
    @Column(name = "Student_Persona_idPersona")
    private int studentPersonaidPersona;

    public FinancePK() {
    }

    public FinancePK(int idFinance, int historialMedicoidHistorialMedico, int studentidStudent, int studentPersonaidPersona) {
        this.idFinance = idFinance;
        this.historialMedicoidHistorialMedico = historialMedicoidHistorialMedico;
        this.studentidStudent = studentidStudent;
        this.studentPersonaidPersona = studentPersonaidPersona;
    }

    public int getIdFinance() {
        return idFinance;
    }

    public void setIdFinance(int idFinance) {
        this.idFinance = idFinance;
    }

    public int getHistorialMedicoidHistorialMedico() {
        return historialMedicoidHistorialMedico;
    }

    public void setHistorialMedicoidHistorialMedico(int historialMedicoidHistorialMedico) {
        this.historialMedicoidHistorialMedico = historialMedicoidHistorialMedico;
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
        hash += (int) idFinance;
        hash += (int) historialMedicoidHistorialMedico;
        hash += (int) studentidStudent;
        hash += (int) studentPersonaidPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinancePK)) {
            return false;
        }
        FinancePK other = (FinancePK) object;
        if (this.idFinance != other.idFinance) {
            return false;
        }
        if (this.historialMedicoidHistorialMedico != other.historialMedicoidHistorialMedico) {
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
        return "Entidades.FinancePK[ idFinance=" + idFinance + ", historialMedicoidHistorialMedico=" + historialMedicoidHistorialMedico + ", studentidStudent=" + studentidStudent + ", studentPersonaidPersona=" + studentPersonaidPersona + " ]";
    }
    
}