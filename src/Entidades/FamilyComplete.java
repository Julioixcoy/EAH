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
import javax.persistence.JoinColumns;
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
@Table(name = "FamilyComplete")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FamilyComplete.findAll", query = "SELECT f FROM FamilyComplete f")
    , @NamedQuery(name = "FamilyComplete.findByFamilyCompleteID", query = "SELECT f FROM FamilyComplete f WHERE f.familyCompletePK.familyCompleteID = :familyCompleteID")
    , @NamedQuery(name = "FamilyComplete.findByParentsidParents", query = "SELECT f FROM FamilyComplete f WHERE f.familyCompletePK.parentsidParents = :parentsidParents")
    , @NamedQuery(name = "FamilyComplete.findByParentsPersonaidPersona", query = "SELECT f FROM FamilyComplete f WHERE f.familyCompletePK.parentsPersonaidPersona = :parentsPersonaidPersona")
    , @NamedQuery(name = "FamilyComplete.findByFamilyidFamily", query = "SELECT f FROM FamilyComplete f WHERE f.familyCompletePK.familyidFamily = :familyidFamily")
    , @NamedQuery(name = "FamilyComplete.findByCodFamiliar", query = "SELECT f FROM FamilyComplete f WHERE f.codFamiliar = :codFamiliar")})
public class FamilyComplete implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familyComplete")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FamilyCompletePK familyCompletePK;
    @Column(name = "codFamiliar")
    private String codFamiliar;
    @JoinColumn(name = "Family_idFamily", referencedColumnName = "idFamily", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Family family;
    @JoinColumns({
        @JoinColumn(name = "Parents_idParents", referencedColumnName = "idParents", insertable = false, updatable = false)
        , @JoinColumn(name = "Parents_Persona_idPersona", referencedColumnName = "Persona_idPersona", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Parents parents;

    public FamilyComplete() {
    }

    public FamilyComplete(FamilyCompletePK familyCompletePK) {
        this.familyCompletePK = familyCompletePK;
    }

    public FamilyComplete(int familyCompleteID, int parentsidParents, int parentsPersonaidPersona, int familyidFamily) {
        this.familyCompletePK = new FamilyCompletePK(familyCompleteID, parentsidParents, parentsPersonaidPersona, familyidFamily);
    }

    public FamilyCompletePK getFamilyCompletePK() {
        return familyCompletePK;
    }

    public void setFamilyCompletePK(FamilyCompletePK familyCompletePK) {
        this.familyCompletePK = familyCompletePK;
    }

    public String getCodFamiliar() {
        return codFamiliar;
    }

    public void setCodFamiliar(String codFamiliar) {
        this.codFamiliar = codFamiliar;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Parents getParents() {
        return parents;
    }

    public void setParents(Parents parents) {
        this.parents = parents;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familyCompletePK != null ? familyCompletePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamilyComplete)) {
            return false;
        }
        FamilyComplete other = (FamilyComplete) object;
        if ((this.familyCompletePK == null && other.familyCompletePK != null) || (this.familyCompletePK != null && !this.familyCompletePK.equals(other.familyCompletePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FamilyComplete[ familyCompletePK=" + familyCompletePK + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
