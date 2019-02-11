/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julio
 */
@Entity
@Table(name = "PropertiesSystem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropertiesSystem.findAll", query = "SELECT p FROM PropertiesSystem p")
    , @NamedQuery(name = "PropertiesSystem.findByIdPropertiesSystem", query = "SELECT p FROM PropertiesSystem p WHERE p.idPropertiesSystem = :idPropertiesSystem")
    , @NamedQuery(name = "PropertiesSystem.findByItem", query = "SELECT p FROM PropertiesSystem p WHERE p.item = :item")
    , @NamedQuery(name = "PropertiesSystem.findByValue", query = "SELECT p FROM PropertiesSystem p WHERE p.value = :value")
    , @NamedQuery(name = "PropertiesSystem.findByEnable", query = "SELECT p FROM PropertiesSystem p WHERE p.enable = :enable")})
public class PropertiesSystem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPropertiesSystem")
    private Integer idPropertiesSystem;
    @Column(name = "item")
    private String item;
    @Column(name = "value")
    private String value;
    @Column(name = "enable")
    private String enable;

    public PropertiesSystem() {
    }

    public PropertiesSystem(Integer idPropertiesSystem) {
        this.idPropertiesSystem = idPropertiesSystem;
    }

    public Integer getIdPropertiesSystem() {
        return idPropertiesSystem;
    }

    public void setIdPropertiesSystem(Integer idPropertiesSystem) {
        this.idPropertiesSystem = idPropertiesSystem;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropertiesSystem != null ? idPropertiesSystem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertiesSystem)) {
            return false;
        }
        PropertiesSystem other = (PropertiesSystem) object;
        if ((this.idPropertiesSystem == null && other.idPropertiesSystem != null) || (this.idPropertiesSystem != null && !this.idPropertiesSystem.equals(other.idPropertiesSystem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PropertiesSystem[ idPropertiesSystem=" + idPropertiesSystem + " ]";
    }
    
}
