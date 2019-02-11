/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julio Fernando Ixcoy
 */
@Entity
@Table(name = "Finance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Finance.findAll", query = "SELECT f FROM Finance f")
    , @NamedQuery(name = "Finance.findByIdFinance", query = "SELECT f FROM Finance f WHERE f.financePK.idFinance = :idFinance")
    , @NamedQuery(name = "Finance.findByDate", query = "SELECT f FROM Finance f WHERE f.date = :date")
    , @NamedQuery(name = "Finance.findByAmount", query = "SELECT f FROM Finance f WHERE f.amount = :amount")
    , @NamedQuery(name = "Finance.findByDescripcion", query = "SELECT f FROM Finance f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "Finance.findByOther", query = "SELECT f FROM Finance f WHERE f.other = :other")
    , @NamedQuery(name = "Finance.findByEnable", query = "SELECT f FROM Finance f WHERE f.enable = :enable")
    , @NamedQuery(name = "Finance.findByHistorialMedicoidHistorialMedico", query = "SELECT f FROM Finance f WHERE f.financePK.historialMedicoidHistorialMedico = :historialMedicoidHistorialMedico")
    , @NamedQuery(name = "Finance.findByStudentidStudent", query = "SELECT f FROM Finance f WHERE f.financePK.studentidStudent = :studentidStudent")
    , @NamedQuery(name = "Finance.findByStudentPersonaidPersona", query = "SELECT f FROM Finance f WHERE f.financePK.studentPersonaidPersona = :studentPersonaidPersona")})
public class Finance implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "finance")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FinancePK financePK;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "other")
    private String other;
    @Column(name = "enable")
    private String enable;
    @JoinColumn(name = "HistorialMedico_idHistorialMedico", referencedColumnName = "idHistorialMedico", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private HistorialMedico historialMedico;
    @JoinColumns({
        @JoinColumn(name = "Student_idStudent", referencedColumnName = "idStudent", insertable = false, updatable = false)
        , @JoinColumn(name = "Student_Persona_idPersona", referencedColumnName = "Persona_idPersona", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Student student;

    public Finance() {
    }

    public Finance(FinancePK financePK) {
        this.financePK = financePK;
    }

    public Finance(int idFinance, int historialMedicoidHistorialMedico, int studentidStudent, int studentPersonaidPersona) {
        this.financePK = new FinancePK(idFinance, historialMedicoidHistorialMedico, studentidStudent, studentPersonaidPersona);
    }

    public FinancePK getFinancePK() {
        return financePK;
    }

    public void setFinancePK(FinancePK financePK) {
        this.financePK = financePK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (financePK != null ? financePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Finance)) {
            return false;
        }
        Finance other = (Finance) object;
        if ((this.financePK == null && other.financePK != null) || (this.financePK != null && !this.financePK.equals(other.financePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Finance[ financePK=" + financePK + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
