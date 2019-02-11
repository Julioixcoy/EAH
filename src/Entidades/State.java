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
@Table(name = "State")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s")
    , @NamedQuery(name = "State.findByIdState", query = "SELECT s FROM State s WHERE s.statePK.idState = :idState")
    , @NamedQuery(name = "State.findByEnable", query = "SELECT s FROM State s WHERE s.enable = :enable")
    , @NamedQuery(name = "State.findByDateEnable", query = "SELECT s FROM State s WHERE s.dateEnable = :dateEnable")
    , @NamedQuery(name = "State.findByRegular", query = "SELECT s FROM State s WHERE s.regular = :regular")
    , @NamedQuery(name = "State.findByDateRegular", query = "SELECT s FROM State s WHERE s.dateRegular = :dateRegular")
    , @NamedQuery(name = "State.findByStudentidStudent", query = "SELECT s FROM State s WHERE s.statePK.studentidStudent = :studentidStudent")
    , @NamedQuery(name = "State.findByStudentPersonaidPersona", query = "SELECT s FROM State s WHERE s.statePK.studentPersonaidPersona = :studentPersonaidPersona")})
public class State implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StatePK statePK;
    @Column(name = "enable")
    private String enable;
    @Column(name = "dateEnable")
    @Temporal(TemporalType.DATE)
    private Date dateEnable;
    @Column(name = "regular")
    private String regular;
    @Column(name = "dateRegular")
    @Temporal(TemporalType.DATE)
    private Date dateRegular;
    @JoinColumns({
        @JoinColumn(name = "Student_idStudent", referencedColumnName = "idStudent", insertable = false, updatable = false)
        , @JoinColumn(name = "Student_Persona_idPersona", referencedColumnName = "Persona_idPersona", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Student student;

    public State() {
    }

    public State(StatePK statePK) {
        this.statePK = statePK;
    }

    public State(int idState, int studentidStudent, int studentPersonaidPersona) {
        this.statePK = new StatePK(idState, studentidStudent, studentPersonaidPersona);
    }

    public StatePK getStatePK() {
        return statePK;
    }

    public void setStatePK(StatePK statePK) {
        this.statePK = statePK;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Date getDateEnable() {
        return dateEnable;
    }

    public void setDateEnable(Date dateEnable) {
        this.dateEnable = dateEnable;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public Date getDateRegular() {
        return dateRegular;
    }

    public void setDateRegular(Date dateRegular) {
        this.dateRegular = dateRegular;
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
        hash += (statePK != null ? statePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.statePK == null && other.statePK != null) || (this.statePK != null && !this.statePK.equals(other.statePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.State[ statePK=" + statePK + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
