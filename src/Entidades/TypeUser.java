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
 * @author julio
 *
 */
@Entity
@Table(name = "TypeUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeUser.findAll", query = "SELECT t FROM TypeUser t")
    , @NamedQuery(name = "TypeUser.findByIdTypeUser", query = "SELECT t FROM TypeUser t WHERE t.idTypeUser = :idTypeUser")
    , @NamedQuery(name = "TypeUser.findByName", query = "SELECT t FROM TypeUser t WHERE t.name = :name")
    , @NamedQuery(name = "TypeUser.findByEnable", query = "SELECT t FROM TypeUser t WHERE t.enable = :enable")})
public class TypeUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTypeUser")
    private Integer idTypeUser;
    @Column(name = "name")
    private String name;
    @Column(name = "enable")
    private Boolean enable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeUseridTypeUser")
    private Collection<User> userCollection;

    public TypeUser() {
    }

    public TypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public Integer getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeUser != null ? idTypeUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeUser)) {
            return false;
        }
        TypeUser other = (TypeUser) object;
        if ((this.idTypeUser == null && other.idTypeUser != null) || (this.idTypeUser != null && !this.idTypeUser.equals(other.idTypeUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TypeUser[ idTypeUser=" + idTypeUser + " ]";
    }

}
