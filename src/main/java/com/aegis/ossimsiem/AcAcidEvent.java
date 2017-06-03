/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "ac_acid_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcAcidEvent.findAll", query = "SELECT a FROM AcAcidEvent a"),
    @NamedQuery(name = "AcAcidEvent.findByDeviceId", query = "SELECT a FROM AcAcidEvent a WHERE a.acAcidEventPK.deviceId = :deviceId"),
    @NamedQuery(name = "AcAcidEvent.findByPluginId", query = "SELECT a FROM AcAcidEvent a WHERE a.acAcidEventPK.pluginId = :pluginId"),
    @NamedQuery(name = "AcAcidEvent.findByPluginSid", query = "SELECT a FROM AcAcidEvent a WHERE a.acAcidEventPK.pluginSid = :pluginSid"),
    @NamedQuery(name = "AcAcidEvent.findByTimestamp", query = "SELECT a FROM AcAcidEvent a WHERE a.acAcidEventPK.timestamp = :timestamp"),
    @NamedQuery(name = "AcAcidEvent.findByCnt", query = "SELECT a FROM AcAcidEvent a WHERE a.cnt = :cnt")})
public class AcAcidEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AcAcidEventPK acAcidEventPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cnt")
    private int cnt;

    public AcAcidEvent() {
    }

    public AcAcidEvent(AcAcidEventPK acAcidEventPK) {
        this.acAcidEventPK = acAcidEventPK;
    }

    public AcAcidEvent(AcAcidEventPK acAcidEventPK, int cnt) {
        this.acAcidEventPK = acAcidEventPK;
        this.cnt = cnt;
    }

    public AcAcidEvent(byte[] ctx, int deviceId, int pluginId, int pluginSid, Date timestamp, byte[] srcHost, byte[] dstHost, byte[] srcNet, byte[] dstNet) {
        this.acAcidEventPK = new AcAcidEventPK(ctx, deviceId, pluginId, pluginSid, timestamp, srcHost, dstHost, srcNet, dstNet);
    }

    public AcAcidEventPK getAcAcidEventPK() {
        return acAcidEventPK;
    }

    public void setAcAcidEventPK(AcAcidEventPK acAcidEventPK) {
        this.acAcidEventPK = acAcidEventPK;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acAcidEventPK != null ? acAcidEventPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcAcidEvent)) {
            return false;
        }
        AcAcidEvent other = (AcAcidEvent) object;
        if ((this.acAcidEventPK == null && other.acAcidEventPK != null) || (this.acAcidEventPK != null && !this.acAcidEventPK.equals(other.acAcidEventPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.AcAcidEvent[ acAcidEventPK=" + acAcidEventPK + " ]";
    }
    
}
