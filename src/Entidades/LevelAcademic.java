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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "LevelAcademic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LevelAcademic.findAll", query = "SELECT l FROM LevelAcademic l")
    , @NamedQuery(name = "LevelAcademic.findByIdLevelAcademic", query = "SELECT l FROM LevelAcademic l WHERE l.idLevelAcademic = :idLevelAcademic")
    , @NamedQuery(name = "LevelAcademic.findByLevel", query = "SELECT l FROM LevelAcademic l WHERE l.level = :level")
    , @NamedQuery(name = "LevelAcademic.findByYear", query = "SELECT l FROM LevelAcademic l WHERE l.year = :year")
    , @NamedQuery(name = "LevelAcademic.findByRepit", query = "SELECT l FROM LevelAcademic l WHERE l.repit = :repit")
    , @NamedQuery(name = "LevelAcademic.findByYearRepit", query = "SELECT l FROM LevelAcademic l WHERE l.yearRepit = :yearRepit")
    , @NamedQuery(name = "LevelAcademic.findByDescripcion", query = "SELECT l FROM LevelAcademic l WHERE l.descripcion = :descripcion")
    , @NamedQuery(name = "LevelAcademic.findBySemester", query = "SELECT l FROM LevelAcademic l WHERE l.semester = :semester")})
public class LevelAcademic implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelAcademicidLevelAcademic")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLevelAcademic")
    private Integer idLevelAcademic;
    @Column(name = "level")
    private Integer level;
    @Column(name = "year")
    private Integer year;
    @Column(name = "repit")
    private Boolean repit;
    @Column(name = "yearRepit")
    private Integer yearRepit;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "semester")
    private Integer semester;
    @JoinTable(name = "LevelAcademic_has_Student", joinColumns = {
        @JoinColumn(name = "LevelAcademic_idLevelAcademic", referencedColumnName = "idLevelAcademic")}, inverseJoinColumns = {
        @JoinColumn(name = "Student_idStudent", referencedColumnName = "idStudent")
        , @JoinColumn(name = "Student_Persona_idPersona", referencedColumnName = "Persona_idPersona")})
    @ManyToMany
    private Collection<Student> studentCollection;

    public LevelAcademic() {
    }

    public LevelAcademic(Integer idLevelAcademic) {
        this.idLevelAcademic = idLevelAcademic;
    }

    public Integer getIdLevelAcademic() {
        return idLevelAcademic;
    }

    public void setIdLevelAcademic(Integer idLevelAcademic) {
        this.idLevelAcademic = idLevelAcademic;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getRepit() {
        return repit;
    }

    public void setRepit(Boolean repit) {
        this.repit = repit;
    }

    public Integer getYearRepit() {
        return yearRepit;
    }

    public void setYearRepit(Integer yearRepit) {
        this.yearRepit = yearRepit;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLevelAcademic != null ? idLevelAcademic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LevelAcademic)) {
            return false;
        }
        LevelAcademic other = (LevelAcademic) object;
        if ((this.idLevelAcademic == null && other.idLevelAcademic != null) || (this.idLevelAcademic != null && !this.idLevelAcademic.equals(other.idLevelAcademic))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LevelAcademic[ idLevelAcademic=" + idLevelAcademic + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
