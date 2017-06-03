/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lkallipolitis
 */
@Embeddable
public class OtxDataPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "event_id")
    private byte[] eventId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "pulse_id")
    private byte[] pulseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ioc_hash")
    private String iocHash;

    public OtxDataPK() {
    }

    public OtxDataPK(byte[] eventId, byte[] pulseId, String iocHash) {
        this.eventId = eventId;
        this.pulseId = pulseId;
        this.iocHash = iocHash;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public byte[] getPulseId() {
        return pulseId;
    }

    public void setPulseId(byte[] pulseId) {
        this.pulseId = pulseId;
    }

    public String getIocHash() {
        return iocHash;
    }

    public void setIocHash(String iocHash) {
        this.iocHash = iocHash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        hash += (pulseId != null ? pulseId.hashCode() : 0);
        hash += (iocHash != null ? iocHash.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtxDataPK)) {
            return false;
        }
        OtxDataPK other = (OtxDataPK) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        if ((this.pulseId == null && other.pulseId != null) || (this.pulseId != null && !this.pulseId.equals(other.pulseId))) {
            return false;
        }
        if ((this.iocHash == null && other.iocHash != null) || (this.iocHash != null && !this.iocHash.equals(other.iocHash))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.OtxDataPK[ eventId=" + eventId + ", pulseId=" + pulseId + ", iocHash=" + iocHash + " ]";
    }
    
}
