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
@Table(name = "Module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
    , @NamedQuery(name = "Module.findByIdModule", query = "SELECT m FROM Module m WHERE m.idModule = :idModule")
    , @NamedQuery(name = "Module.findByNameModule", query = "SELECT m FROM Module m WHERE m.nameModule = :nameModule")
    , @NamedQuery(name = "Module.findByEnable", query = "SELECT m FROM Module m WHERE m.enable = :enable")})
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idModule")
    private Integer idModule;
    @Column(name = "nameModule")
    private String nameModule;
    @Column(name = "enable")
    private Boolean enable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleidModule")
    private Collection<BlackBox> blackBoxCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleidModule")
    private Collection<UserModule> userModuleCollection;

    public Module() {
    }

    public Module(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getNameModule() {
        return nameModule;
    }

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }

    @XmlTransient
    public Collection<UserModule> getUserModuleCollection() {
        return userModuleCollection;
    }

    public void setUserModuleCollection(Collection<UserModule> userModuleCollection) {
        this.userModuleCollection = userModuleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModule != null ? idModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.idModule == null && other.idModule != null) || (this.idModule != null && !this.idModule.equals(other.idModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Module[ idModule=" + idModule + " ]";
    }

}
