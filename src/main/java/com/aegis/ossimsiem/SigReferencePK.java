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

/**
 *
 * @author lkallipolitis
 */
@Embeddable
public class SigReferencePK implements Serializable {

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
    @Column(name = "ref_id")
    private int refId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "ctx")
    private byte[] ctx;

    public SigReferencePK() {
    }

    public SigReferencePK(int pluginId, int pluginSid, int refId, byte[] ctx) {
        this.pluginId = pluginId;
        this.pluginSid = pluginSid;
        this.refId = refId;
        this.ctx = ctx;
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

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public byte[] getCtx() {
        return ctx;
    }

    public void setCtx(byte[] ctx) {
        this.ctx = ctx;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pluginId;
        hash += (int) pluginSid;
        hash += (int) refId;
        hash += (ctx != null ? ctx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SigReferencePK)) {
            return false;
        }
        SigReferencePK other = (SigReferencePK) object;
        if (this.pluginId != other.pluginId) {
            return false;
        }
        if (this.pluginSid != other.pluginSid) {
            return false;
        }
        if (this.refId != other.refId) {
            return false;
        }
        if ((this.ctx == null && other.ctx != null) || (this.ctx != null && !this.ctx.equals(other.ctx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.SigReferencePK[ pluginId=" + pluginId + ", pluginSid=" + pluginSid + ", refId=" + refId + ", ctx=" + ctx + " ]";
    }
    
}
