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
@Table(name = "Family")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Family.findAll", query = "SELECT f FROM Family f")
    , @NamedQuery(name = "Family.findByIdFamily", query = "SELECT f FROM Family f WHERE f.idFamily = :idFamily")
    , @NamedQuery(name = "Family.findByEnableMom", query = "SELECT f FROM Family f WHERE f.enableMom = :enableMom")
    , @NamedQuery(name = "Family.findByEnableFather", query = "SELECT f FROM Family f WHERE f.enableFather = :enableFather")
    , @NamedQuery(name = "Family.findByHome", query = "SELECT f FROM Family f WHERE f.home = :home")
    , @NamedQuery(name = "Family.findByDescripcion", query = "SELECT f FROM Family f WHERE f.descripcion = :descripcion")})
public class Family implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familyidFamily")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFamily")
    private Integer idFamily;
    @Column(name = "enableMom")
    private Boolean enableMom;
    @Column(name = "enableFather")
    private Boolean enableFather;
    @Column(name = "home")
    private Boolean home;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "family")
    private Collection<FamilyComplete> familyCompleteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familyidFamily")
    private Collection<Student> studentCollection;

    public Family() {
    }

    public Family(Integer idFamily) {
        this.idFamily = idFamily;
    }

    public Integer getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(Integer idFamily) {
        this.idFamily = idFamily;
    }

    public Boolean getEnableMom() {
        return enableMom;
    }

    public void setEnableMom(Boolean enableMom) {
        this.enableMom = enableMom;
    }

    public Boolean getEnableFather() {
        return enableFather;
    }

    public void setEnableFather(Boolean enableFather) {
        this.enableFather = enableFather;
    }

    public Boolean getHome() {
        return home;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<FamilyComplete> getFamilyCompleteCollection() {
        return familyCompleteCollection;
    }

    public void setFamilyCompleteCollection(Collection<FamilyComplete> familyCompleteCollection) {
        this.familyCompleteCollection = familyCompleteCollection;
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
        hash += (idFamily != null ? idFamily.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Family)) {
            return false;
        }
        Family other = (Family) object;
        if ((this.idFamily == null && other.idFamily != null) || (this.idFamily != null && !this.idFamily.equals(other.idFamily))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Family[ idFamily=" + idFamily + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
