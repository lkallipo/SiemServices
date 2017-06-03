/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "reference_system")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReferenceSystem.findAll", query = "SELECT r FROM ReferenceSystem r"),
    @NamedQuery(name = "ReferenceSystem.findByRefSystemId", query = "SELECT r FROM ReferenceSystem r WHERE r.refSystemId = :refSystemId"),
    @NamedQuery(name = "ReferenceSystem.findByRefSystemName", query = "SELECT r FROM ReferenceSystem r WHERE r.refSystemName = :refSystemName"),
    @NamedQuery(name = "ReferenceSystem.findByUrl", query = "SELECT r FROM ReferenceSystem r WHERE r.url = :url")})
public class ReferenceSystem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ref_system_id")
    private Integer refSystemId;
    @Size(max = 20)
    @Column(name = "ref_system_name")
    private String refSystemName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "icon")
    private byte[] icon;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url")
    private String url;

    public ReferenceSystem() {
    }

    public ReferenceSystem(Integer refSystemId) {
        this.refSystemId = refSystemId;
    }

    public ReferenceSystem(Integer refSystemId, byte[] icon, String url) {
        this.refSystemId = refSystemId;
        this.icon = icon;
        this.url = url;
    }

    public Integer getRefSystemId() {
        return refSystemId;
    }

    public void setRefSystemId(Integer refSystemId) {
        this.refSystemId = refSystemId;
    }

    public String getRefSystemName() {
        return refSystemName;
    }

    public void setRefSystemName(String refSystemName) {
        this.refSystemName = refSystemName;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (refSystemId != null ? refSystemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenceSystem)) {
            return false;
        }
        ReferenceSystem other = (ReferenceSystem) object;
        if ((this.refSystemId == null && other.refSystemId != null) || (this.refSystemId != null && !this.refSystemId.equals(other.refSystemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.ReferenceSystem[ refSystemId=" + refSystemId + " ]";
    }
    
}
