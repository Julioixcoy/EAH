/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Parents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parents.findAll", query = "SELECT p FROM Parents p")
    , @NamedQuery(name = "Parents.findByIdParents", query = "SELECT p FROM Parents p WHERE p.parentsPK.idParents = :idParents")
    , @NamedQuery(name = "Parents.findByDependency", query = "SELECT p FROM Parents p WHERE p.dependency = :dependency")
    , @NamedQuery(name = "Parents.findByIsDependent", query = "SELECT p FROM Parents p WHERE p.isDependent = :isDependent")
    , @NamedQuery(name = "Parents.findByIsMom", query = "SELECT p FROM Parents p WHERE p.isMom = :isMom")
    , @NamedQuery(name = "Parents.findByIsLives", query = "SELECT p FROM Parents p WHERE p.isLives = :isLives")
    , @NamedQuery(name = "Parents.findByPersonaidPersona", query = "SELECT p FROM Parents p WHERE p.parentsPK.personaidPersona = :personaidPersona")
    , @NamedQuery(name = "Parents.findByIsAlphabet", query = "SELECT p FROM Parents p WHERE p.isAlphabet = :isAlphabet")})
public class Parents implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parents")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParentsPK parentsPK;
    @Column(name = "dependency")
    private String dependency;
    @Column(name = "isDependent")
    private Boolean isDependent;
    @Column(name = "isMom")
    private Boolean isMom;
    @Column(name = "isLives")
    private Boolean isLives;
    @Column(name = "isAlphabet")
    private Boolean isAlphabet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parents")
    private Collection<FamilyComplete> familyCompleteCollection;
    @JoinColumn(name = "Persona_idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;

    public Parents() {
    }

    public Parents(ParentsPK parentsPK) {
        this.parentsPK = parentsPK;
    }

    public Parents(int idParents, int personaidPersona) {
        this.parentsPK = new ParentsPK(idParents, personaidPersona);
    }

    public ParentsPK getParentsPK() {
        return parentsPK;
    }

    public void setParentsPK(ParentsPK parentsPK) {
        this.parentsPK = parentsPK;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public Boolean getIsDependent() {
        return isDependent;
    }

    public void setIsDependent(Boolean isDependent) {
        this.isDependent = isDependent;
    }

    public Boolean getIsMom() {
        return isMom;
    }

    public void setIsMom(Boolean isMom) {
        this.isMom = isMom;
    }

    public Boolean getIsLives() {
        return isLives;
    }

    public void setIsLives(Boolean isLives) {
        this.isLives = isLives;
    }

    public Boolean getIsAlphabet() {
        return isAlphabet;
    }

    public void setIsAlphabet(Boolean isAlphabet) {
        this.isAlphabet = isAlphabet;
    }

    @XmlTransient
    public Collection<FamilyComplete> getFamilyCompleteCollection() {
        return familyCompleteCollection;
    }

    public void setFamilyCompleteCollection(Collection<FamilyComplete> familyCompleteCollection) {
        this.familyCompleteCollection = familyCompleteCollection;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parentsPK != null ? parentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parents)) {
            return false;
        }
        Parents other = (Parents) object;
        if ((this.parentsPK == null && other.parentsPK != null) || (this.parentsPK != null && !this.parentsPK.equals(other.parentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parents[ parentsPK=" + parentsPK + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
