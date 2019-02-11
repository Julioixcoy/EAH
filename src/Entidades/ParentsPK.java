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
public class ParentsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idParents")
    private int idParents;
    @Basic(optional = false)
    @Column(name = "Persona_idPersona")
    private int personaidPersona;

    public ParentsPK() {
    }

    public ParentsPK(int idParents, int personaidPersona) {
        this.idParents = idParents;
        this.personaidPersona = personaidPersona;
    }

    public int getIdParents() {
        return idParents;
    }

    public void setIdParents(int idParents) {
        this.idParents = idParents;
    }

    public int getPersonaidPersona() {
        return personaidPersona;
    }

    public void setPersonaidPersona(int personaidPersona) {
        this.personaidPersona = personaidPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idParents;
        hash += (int) personaidPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentsPK)) {
            return false;
        }
        ParentsPK other = (ParentsPK) object;
        if (this.idParents != other.idParents) {
            return false;
        }
        if (this.personaidPersona != other.personaidPersona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParentsPK[ idParents=" + idParents + ", personaidPersona=" + personaidPersona + " ]";
    }
    
}
