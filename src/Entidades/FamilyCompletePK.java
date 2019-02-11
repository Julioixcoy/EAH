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
public class FamilyCompletePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "FamilyCompleteID")
    private int familyCompleteID;
    @Basic(optional = false)
    @Column(name = "Parents_idParents")
    private int parentsidParents;
    @Basic(optional = false)
    @Column(name = "Parents_Persona_idPersona")
    private int parentsPersonaidPersona;
    @Basic(optional = false)
    @Column(name = "Family_idFamily")
    private int familyidFamily;

    public FamilyCompletePK() {
    }

    public FamilyCompletePK(int familyCompleteID, int parentsidParents, int parentsPersonaidPersona, int familyidFamily) {
        this.familyCompleteID = familyCompleteID;
        this.parentsidParents = parentsidParents;
        this.parentsPersonaidPersona = parentsPersonaidPersona;
        this.familyidFamily = familyidFamily;
    }

    public int getFamilyCompleteID() {
        return familyCompleteID;
    }

    public void setFamilyCompleteID(int familyCompleteID) {
        this.familyCompleteID = familyCompleteID;
    }

    public int getParentsidParents() {
        return parentsidParents;
    }

    public void setParentsidParents(int parentsidParents) {
        this.parentsidParents = parentsidParents;
    }

    public int getParentsPersonaidPersona() {
        return parentsPersonaidPersona;
    }

    public void setParentsPersonaidPersona(int parentsPersonaidPersona) {
        this.parentsPersonaidPersona = parentsPersonaidPersona;
    }

    public int getFamilyidFamily() {
        return familyidFamily;
    }

    public void setFamilyidFamily(int familyidFamily) {
        this.familyidFamily = familyidFamily;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) familyCompleteID;
        hash += (int) parentsidParents;
        hash += (int) parentsPersonaidPersona;
        hash += (int) familyidFamily;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamilyCompletePK)) {
            return false;
        }
        FamilyCompletePK other = (FamilyCompletePK) object;
        if (this.familyCompleteID != other.familyCompleteID) {
            return false;
        }
        if (this.parentsidParents != other.parentsidParents) {
            return false;
        }
        if (this.parentsPersonaidPersona != other.parentsPersonaidPersona) {
            return false;
        }
        if (this.familyidFamily != other.familyidFamily) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FamilyCompletePK[ familyCompleteID=" + familyCompleteID + ", parentsidParents=" + parentsidParents + ", parentsPersonaidPersona=" + parentsPersonaidPersona + ", familyidFamily=" + familyidFamily + " ]";
    }
    
}
