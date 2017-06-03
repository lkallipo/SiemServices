/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "sig_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SigReference.findAll", query = "SELECT s FROM SigReference s"),
    @NamedQuery(name = "SigReference.findByPluginId", query = "SELECT s FROM SigReference s WHERE s.sigReferencePK.pluginId = :pluginId"),
    @NamedQuery(name = "SigReference.findByPluginSid", query = "SELECT s FROM SigReference s WHERE s.sigReferencePK.pluginSid = :pluginSid"),
    @NamedQuery(name = "SigReference.findByRefId", query = "SELECT s FROM SigReference s WHERE s.sigReferencePK.refId = :refId")})
public class SigReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SigReferencePK sigReferencePK;

    public SigReference() {
    }

    public SigReference(SigReferencePK sigReferencePK) {
        this.sigReferencePK = sigReferencePK;
    }

    public SigReference(int pluginId, int pluginSid, int refId, byte[] ctx) {
        this.sigReferencePK = new SigReferencePK(pluginId, pluginSid, refId, ctx);
    }

    public SigReferencePK getSigReferencePK() {
        return sigReferencePK;
    }

    public void setSigReferencePK(SigReferencePK sigReferencePK) {
        this.sigReferencePK = sigReferencePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sigReferencePK != null ? sigReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SigReference)) {
            return false;
        }
        SigReference other = (SigReference) object;
        if ((this.sigReferencePK == null && other.sigReferencePK != null) || (this.sigReferencePK != null && !this.sigReferencePK.equals(other.sigReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.SigReference[ sigReferencePK=" + sigReferencePK + " ]";
    }
    
}
