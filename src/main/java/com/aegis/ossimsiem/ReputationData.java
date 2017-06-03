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
@Table(name = "reputation_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReputationData.findAll", query = "SELECT r FROM ReputationData r"),
    @NamedQuery(name = "ReputationData.findByRepPrioSrc", query = "SELECT r FROM ReputationData r WHERE r.repPrioSrc = :repPrioSrc"),
    @NamedQuery(name = "ReputationData.findByRepPrioDst", query = "SELECT r FROM ReputationData r WHERE r.repPrioDst = :repPrioDst"),
    @NamedQuery(name = "ReputationData.findByRepRelSrc", query = "SELECT r FROM ReputationData r WHERE r.repRelSrc = :repRelSrc"),
    @NamedQuery(name = "ReputationData.findByRepRelDst", query = "SELECT r FROM ReputationData r WHERE r.repRelDst = :repRelDst"),
    @NamedQuery(name = "ReputationData.findByRepActSrc", query = "SELECT r FROM ReputationData r WHERE r.repActSrc = :repActSrc"),
    @NamedQuery(name = "ReputationData.findByRepActDst", query = "SELECT r FROM ReputationData r WHERE r.repActDst = :repActDst")})
public class ReputationData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "event_id")
    private byte[] eventId;
    @Lob
    @Column(name = "rep_ip_src")
    private byte[] repIpSrc;
    @Lob
    @Column(name = "rep_ip_dst")
    private byte[] repIpDst;
    @Column(name = "rep_prio_src")
    private Short repPrioSrc;
    @Column(name = "rep_prio_dst")
    private Short repPrioDst;
    @Column(name = "rep_rel_src")
    private Short repRelSrc;
    @Column(name = "rep_rel_dst")
    private Short repRelDst;
    @Size(max = 64)
    @Column(name = "rep_act_src")
    private String repActSrc;
    @Size(max = 64)
    @Column(name = "rep_act_dst")
    private String repActDst;

    public ReputationData() {
    }

    public ReputationData(byte[] eventId) {
        this.eventId = eventId;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public byte[] getRepIpSrc() {
        return repIpSrc;
    }

    public void setRepIpSrc(byte[] repIpSrc) {
        this.repIpSrc = repIpSrc;
    }

    public byte[] getRepIpDst() {
        return repIpDst;
    }

    public void setRepIpDst(byte[] repIpDst) {
        this.repIpDst = repIpDst;
    }

    public Short getRepPrioSrc() {
        return repPrioSrc;
    }

    public void setRepPrioSrc(Short repPrioSrc) {
        this.repPrioSrc = repPrioSrc;
    }

    public Short getRepPrioDst() {
        return repPrioDst;
    }

    public void setRepPrioDst(Short repPrioDst) {
        this.repPrioDst = repPrioDst;
    }

    public Short getRepRelSrc() {
        return repRelSrc;
    }

    public void setRepRelSrc(Short repRelSrc) {
        this.repRelSrc = repRelSrc;
    }

    public Short getRepRelDst() {
        return repRelDst;
    }

    public void setRepRelDst(Short repRelDst) {
        this.repRelDst = repRelDst;
    }

    public String getRepActSrc() {
        return repActSrc;
    }

    public void setRepActSrc(String repActSrc) {
        this.repActSrc = repActSrc;
    }

    public String getRepActDst() {
        return repActDst;
    }

    public void setRepActDst(String repActDst) {
        this.repActDst = repActDst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReputationData)) {
            return false;
        }
        ReputationData other = (ReputationData) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.ReputationData[ eventId=" + eventId + " ]";
    }
    
}
