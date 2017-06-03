/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "otx_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtxData.findAll", query = "SELECT o FROM OtxData o"),
    @NamedQuery(name = "OtxData.findByIocHash", query = "SELECT o FROM OtxData o WHERE o.otxDataPK.iocHash = :iocHash"),
    @NamedQuery(name = "OtxData.findByIocValue", query = "SELECT o FROM OtxData o WHERE o.iocValue = :iocValue")})
public class OtxData implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OtxDataPK otxDataPK;
    @Size(max = 2048)
    @Column(name = "ioc_value")
    private String iocValue;

    public OtxData() {
    }

    public OtxData(OtxDataPK otxDataPK) {
        this.otxDataPK = otxDataPK;
    }

    public OtxData(byte[] eventId, byte[] pulseId, String iocHash) {
        this.otxDataPK = new OtxDataPK(eventId, pulseId, iocHash);
    }

    public OtxDataPK getOtxDataPK() {
        return otxDataPK;
    }

    public void setOtxDataPK(OtxDataPK otxDataPK) {
        this.otxDataPK = otxDataPK;
    }

    public String getIocValue() {
        return iocValue;
    }

    public void setIocValue(String iocValue) {
        this.iocValue = iocValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otxDataPK != null ? otxDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtxData)) {
            return false;
        }
        OtxData other = (OtxData) object;
        if ((this.otxDataPK == null && other.otxDataPK != null) || (this.otxDataPK != null && !this.otxDataPK.equals(other.otxDataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.OtxData[ otxDataPK=" + otxDataPK + " ]";
    }
    
}
