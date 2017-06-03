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
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lkallipolitis
 */
@Embeddable
public class PoAcidEventPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "ctx")
    private byte[] ctx;
    @Basic(optional = false)
    @NotNull
    @Column(name = "device_id")
    private int deviceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plugin_id")
    private int pluginId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plugin_sid")
    private int pluginSid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "ip_src")
    private byte[] ipSrc;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "ip_dst")
    private byte[] ipDst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "src_host")
    private byte[] srcHost;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "dst_host")
    private byte[] dstHost;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "src_net")
    private byte[] srcNet;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "dst_net")
    private byte[] dstNet;

    public PoAcidEventPK() {
    }

    public PoAcidEventPK(byte[] ctx, int deviceId, int pluginId, int pluginSid, byte[] ipSrc, byte[] ipDst, Date timestamp, byte[] srcHost, byte[] dstHost, byte[] srcNet, byte[] dstNet) {
        this.ctx = ctx;
        this.deviceId = deviceId;
        this.pluginId = pluginId;
        this.pluginSid = pluginSid;
        this.ipSrc = ipSrc;
        this.ipDst = ipDst;
        this.timestamp = timestamp;
        this.srcHost = srcHost;
        this.dstHost = dstHost;
        this.srcNet = srcNet;
        this.dstNet = dstNet;
    }

    public byte[] getCtx() {
        return ctx;
    }

    public void setCtx(byte[] ctx) {
        this.ctx = ctx;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getPluginId() {
        return pluginId;
    }

    public void setPluginId(int pluginId) {
        this.pluginId = pluginId;
    }

    public int getPluginSid() {
        return pluginSid;
    }

    public void setPluginSid(int pluginSid) {
        this.pluginSid = pluginSid;
    }

    public byte[] getIpSrc() {
        return ipSrc;
    }

    public void setIpSrc(byte[] ipSrc) {
        this.ipSrc = ipSrc;
    }

    public byte[] getIpDst() {
        return ipDst;
    }

    public void setIpDst(byte[] ipDst) {
        this.ipDst = ipDst;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getSrcHost() {
        return srcHost;
    }

    public void setSrcHost(byte[] srcHost) {
        this.srcHost = srcHost;
    }

    public byte[] getDstHost() {
        return dstHost;
    }

    public void setDstHost(byte[] dstHost) {
        this.dstHost = dstHost;
    }

    public byte[] getSrcNet() {
        return srcNet;
    }

    public void setSrcNet(byte[] srcNet) {
        this.srcNet = srcNet;
    }

    public byte[] getDstNet() {
        return dstNet;
    }

    public void setDstNet(byte[] dstNet) {
        this.dstNet = dstNet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctx != null ? ctx.hashCode() : 0);
        hash += (int) deviceId;
        hash += (int) pluginId;
        hash += (int) pluginSid;
        hash += (ipSrc != null ? ipSrc.hashCode() : 0);
        hash += (ipDst != null ? ipDst.hashCode() : 0);
        hash += (timestamp != null ? timestamp.hashCode() : 0);
        hash += (srcHost != null ? srcHost.hashCode() : 0);
        hash += (dstHost != null ? dstHost.hashCode() : 0);
        hash += (srcNet != null ? srcNet.hashCode() : 0);
        hash += (dstNet != null ? dstNet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoAcidEventPK)) {
            return false;
        }
        PoAcidEventPK other = (PoAcidEventPK) object;
        if ((this.ctx == null && other.ctx != null) || (this.ctx != null && !this.ctx.equals(other.ctx))) {
            return false;
        }
        if (this.deviceId != other.deviceId) {
            return false;
        }
        if (this.pluginId != other.pluginId) {
            return false;
        }
        if (this.pluginSid != other.pluginSid) {
            return false;
        }
        if ((this.ipSrc == null && other.ipSrc != null) || (this.ipSrc != null && !this.ipSrc.equals(other.ipSrc))) {
            return false;
        }
        if ((this.ipDst == null && other.ipDst != null) || (this.ipDst != null && !this.ipDst.equals(other.ipDst))) {
            return false;
        }
        if ((this.timestamp == null && other.timestamp != null) || (this.timestamp != null && !this.timestamp.equals(other.timestamp))) {
            return false;
        }
        if ((this.srcHost == null && other.srcHost != null) || (this.srcHost != null && !this.srcHost.equals(other.srcHost))) {
            return false;
        }
        if ((this.dstHost == null && other.dstHost != null) || (this.dstHost != null && !this.dstHost.equals(other.dstHost))) {
            return false;
        }
        if ((this.srcNet == null && other.srcNet != null) || (this.srcNet != null && !this.srcNet.equals(other.srcNet))) {
            return false;
        }
        if ((this.dstNet == null && other.dstNet != null) || (this.dstNet != null && !this.dstNet.equals(other.dstNet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.PoAcidEventPK[ ctx=" + ctx + ", deviceId=" + deviceId + ", pluginId=" + pluginId + ", pluginSid=" + pluginSid + ", ipSrc=" + ipSrc + ", ipDst=" + ipDst + ", timestamp=" + timestamp + ", srcHost=" + srcHost + ", dstHost=" + dstHost + ", srcNet=" + srcNet + ", dstNet=" + dstNet + " ]";
    }
    
}
