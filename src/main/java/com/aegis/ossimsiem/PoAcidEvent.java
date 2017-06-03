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
@Table(name = "po_acid_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoAcidEvent.findAll", query = "SELECT p FROM PoAcidEvent p"),
    @NamedQuery(name = "PoAcidEvent.findByDeviceId", query = "SELECT p FROM PoAcidEvent p WHERE p.poAcidEventPK.deviceId = :deviceId"),
    @NamedQuery(name = "PoAcidEvent.findByPluginId", query = "SELECT p FROM PoAcidEvent p WHERE p.poAcidEventPK.pluginId = :pluginId"),
    @NamedQuery(name = "PoAcidEvent.findByPluginSid", query = "SELECT p FROM PoAcidEvent p WHERE p.poAcidEventPK.pluginSid = :pluginSid"),
    @NamedQuery(name = "PoAcidEvent.findByTimestamp", query = "SELECT p FROM PoAcidEvent p WHERE p.poAcidEventPK.timestamp = :timestamp"),
    @NamedQuery(name = "PoAcidEvent.findByCnt", query = "SELECT p FROM PoAcidEvent p WHERE p.cnt = :cnt")})
public class PoAcidEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PoAcidEventPK poAcidEventPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cnt")
    private int cnt;

    public PoAcidEvent() {
    }

    public PoAcidEvent(PoAcidEventPK poAcidEventPK) {
        this.poAcidEventPK = poAcidEventPK;
    }

    public PoAcidEvent(PoAcidEventPK poAcidEventPK, int cnt) {
        this.poAcidEventPK = poAcidEventPK;
        this.cnt = cnt;
    }

    public PoAcidEvent(byte[] ctx, int deviceId, int pluginId, int pluginSid, byte[] ipSrc, byte[] ipDst, Date timestamp, byte[] srcHost, byte[] dstHost, byte[] srcNet, byte[] dstNet) {
        this.poAcidEventPK = new PoAcidEventPK(ctx, deviceId, pluginId, pluginSid, ipSrc, ipDst, timestamp, srcHost, dstHost, srcNet, dstNet);
    }

    public PoAcidEventPK getPoAcidEventPK() {
        return poAcidEventPK;
    }

    public void setPoAcidEventPK(PoAcidEventPK poAcidEventPK) {
        this.poAcidEventPK = poAcidEventPK;
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
        hash += (poAcidEventPK != null ? poAcidEventPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoAcidEvent)) {
            return false;
        }
        PoAcidEvent other = (PoAcidEvent) object;
        if ((this.poAcidEventPK == null && other.poAcidEventPK != null) || (this.poAcidEventPK != null && !this.poAcidEventPK.equals(other.poAcidEventPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.PoAcidEvent[ poAcidEventPK=" + poAcidEventPK + " ]";
    }
    
}
