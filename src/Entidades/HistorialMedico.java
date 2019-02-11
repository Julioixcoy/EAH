/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julio Fernando Ixcoy
 */
@Entity
@Table(name = "HistorialMedico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialMedico.findAll", query = "SELECT h FROM HistorialMedico h")
    , @NamedQuery(name = "HistorialMedico.findByIdHistorialMedico", query = "SELECT h FROM HistorialMedico h WHERE h.idHistorialMedico = :idHistorialMedico")
    , @NamedQuery(name = "HistorialMedico.findByType", query = "SELECT h FROM HistorialMedico h WHERE h.type = :type")
    , @NamedQuery(name = "HistorialMedico.findByReason", query = "SELECT h FROM HistorialMedico h WHERE h.reason = :reason")
    , @NamedQuery(name = "HistorialMedico.findByMedicamento", query = "SELECT h FROM HistorialMedico h WHERE h.medicamento = :medicamento")
    , @NamedQuery(name = "HistorialMedico.findByTherapy", query = "SELECT h FROM HistorialMedico h WHERE h.therapy = :therapy")
    , @NamedQuery(name = "HistorialMedico.findByMonitoringAndControl", query = "SELECT h FROM HistorialMedico h WHERE h.monitoringAndControl = :monitoringAndControl")})
public class HistorialMedico implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialMedicoidHistorialMedico")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHistorialMedico")
    private Integer idHistorialMedico;
    @Column(name = "type")
    private String type;
    @Column(name = "reason")
    private String reason;
    @Column(name = "medicamento")
    private String medicamento;
    @Column(name = "therapy")
    private Boolean therapy;
    @Basic(optional = false)
    @Column(name = "monitoringAndControl")
    private boolean monitoringAndControl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialMedico")
    private Collection<Finance> financeCollection;

    public HistorialMedico() {
    }

    public HistorialMedico(Integer idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }

    public HistorialMedico(Integer idHistorialMedico, boolean monitoringAndControl) {
        this.idHistorialMedico = idHistorialMedico;
        this.monitoringAndControl = monitoringAndControl;
    }

    public Integer getIdHistorialMedico() {
        return idHistorialMedico;
    }

    public void setIdHistorialMedico(Integer idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public Boolean getTherapy() {
        return therapy;
    }

    public void setTherapy(Boolean therapy) {
        this.therapy = therapy;
    }

    public boolean getMonitoringAndControl() {
        return monitoringAndControl;
    }

    public void setMonitoringAndControl(boolean monitoringAndControl) {
        this.monitoringAndControl = monitoringAndControl;
    }

    @XmlTransient
    public Collection<Finance> getFinanceCollection() {
        return financeCollection;
    }

    public void setFinanceCollection(Collection<Finance> financeCollection) {
        this.financeCollection = financeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialMedico != null ? idHistorialMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialMedico)) {
            return false;
        }
        HistorialMedico other = (HistorialMedico) object;
        if ((this.idHistorialMedico == null && other.idHistorialMedico != null) || (this.idHistorialMedico != null && !this.idHistorialMedico.equals(other.idHistorialMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HistorialMedico[ idHistorialMedico=" + idHistorialMedico + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
