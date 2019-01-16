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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "acid_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcidEvent.findAll", query = "SELECT a FROM AcidEvent a"),
    @NamedQuery(name = "AcidEvent.findByDeviceId", query = "SELECT a FROM AcidEvent a WHERE a.deviceId = :deviceId"),
    @NamedQuery(name = "AcidEvent.findByTimestamp", query = "SELECT a FROM AcidEvent a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "AcidEvent.findByIpProto", query = "SELECT a FROM AcidEvent a WHERE a.ipProto = :ipProto"),
    @NamedQuery(name = "AcidEvent.findByLayer4Sport", query = "SELECT a FROM AcidEvent a WHERE a.layer4Sport = :layer4Sport"),
    @NamedQuery(name = "AcidEvent.findByLayer4Dport", query = "SELECT a FROM AcidEvent a WHERE a.layer4Dport = :layer4Dport"),
    @NamedQuery(name = "AcidEvent.findByOssimPriority", query = "SELECT a FROM AcidEvent a WHERE a.ossimPriority = :ossimPriority"),
    @NamedQuery(name = "AcidEvent.findByOssimReliability", query = "SELECT a FROM AcidEvent a WHERE a.ossimReliability = :ossimReliability"),
    @NamedQuery(name = "AcidEvent.findByOssimAssetSrc", query = "SELECT a FROM AcidEvent a WHERE a.ossimAssetSrc = :ossimAssetSrc"),
    @NamedQuery(name = "AcidEvent.findByOssimAssetDst", query = "SELECT a FROM AcidEvent a WHERE a.ossimAssetDst = :ossimAssetDst"),
    @NamedQuery(name = "AcidEvent.findByOssimRiskC", query = "SELECT a FROM AcidEvent a WHERE a.ossimRiskC = :ossimRiskC"),
    @NamedQuery(name = "AcidEvent.findByOssimRiskA", query = "SELECT a FROM AcidEvent a WHERE a.ossimRiskA = :ossimRiskA"),
    @NamedQuery(name = "AcidEvent.findByPluginId", query = "SELECT a FROM AcidEvent a WHERE a.pluginId = :pluginId"),
    @NamedQuery(name = "AcidEvent.findByPluginSid", query = "SELECT a FROM AcidEvent a WHERE a.pluginSid = :pluginSid"),
    @NamedQuery(name = "AcidEvent.findByTzone", query = "SELECT a FROM AcidEvent a WHERE a.tzone = :tzone"),
    @NamedQuery(name = "AcidEvent.findByOssimCorrelation", query = "SELECT a FROM AcidEvent a WHERE a.ossimCorrelation = :ossimCorrelation"),
    @NamedQuery(name = "AcidEvent.findBySrcHostname", query = "SELECT a FROM AcidEvent a WHERE a.srcHostname = :srcHostname"),
    @NamedQuery(name = "AcidEvent.findByDstHostname", query = "SELECT a FROM AcidEvent a WHERE a.dstHostname = :dstHostname")})
public class AcidEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "id")
    private byte[] id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "device_id")
    private int deviceId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "ctx")
    private byte[] ctx;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Lob
    @Column(name = "ip_src")
    private byte[] ipSrc;
    @Lob
    @Column(name = "ip_dst")
    private byte[] ipDst;
    @Column(name = "ip_proto")
    private Integer ipProto;
    @Column(name = "layer4_sport")
    private Short layer4Sport;
    @Column(name = "layer4_dport")
    private Short layer4Dport;
    @Column(name = "ossim_priority")
    private Short ossimPriority;
    @Column(name = "ossim_reliability")
    private Short ossimReliability;
    @Column(name = "ossim_asset_src")
    private Short ossimAssetSrc;
    @Column(name = "ossim_asset_dst")
    private Short ossimAssetDst;
    @Column(name = "ossim_risk_c")
    private Short ossimRiskC;
    @Column(name = "ossim_risk_a")
    private Short ossimRiskA;
    @Column(name = "plugin_id")
    private Integer pluginId;
    @Column(name = "plugin_sid")
    private Integer pluginSid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tzone")
    private float tzone;
    @Column(name = "ossim_correlation")
    private Short ossimCorrelation;
    @Size(max = 64)
    @Column(name = "src_hostname")
    private String srcHostname;
    @Size(max = 64)
    @Column(name = "dst_hostname")
    private String dstHostname;
    @Lob
    @Column(name = "src_mac")
    private byte[] srcMac;
    @Lob
    @Column(name = "dst_mac")
    private byte[] dstMac;
    @Lob
    @Column(name = "src_host")
    private byte[] srcHost;
    @Lob
    @Column(name = "dst_host")
    private byte[] dstHost;
    @Lob
    @Column(name = "src_net")
    private byte[] srcNet;
    @Lob
    @Column(name = "dst_net")
    private byte[] dstNet;
    
    public AcidEvent() {
    }

    public AcidEvent(byte[] id) {
        this.id = id;
    }

    public AcidEvent(byte[] id, int deviceId, byte[] ctx, Date timestamp, float tzone) {
        this.id = id;
        this.deviceId = deviceId;
        this.ctx = ctx;
        this.timestamp = timestamp;
        this.tzone = tzone;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public byte[] getCtx() {
        return ctx;
    }

    public void setCtx(byte[] ctx) {
        this.ctx = ctx;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public Integer getIpProto() {
        return ipProto;
    }

    public void setIpProto(Integer ipProto) {
        this.ipProto = ipProto;
    }

    public Short getLayer4Sport() {
        return layer4Sport;
    }

    public void setLayer4Sport(Short layer4Sport) {
        this.layer4Sport = layer4Sport;
    }

    public Short getLayer4Dport() {
        return layer4Dport;
    }

    public void setLayer4Dport(Short layer4Dport) {
        this.layer4Dport = layer4Dport;
    }

    public Short getOssimPriority() {
        return ossimPriority;
    }

    public void setOssimPriority(Short ossimPriority) {
        this.ossimPriority = ossimPriority;
    }

    public Short getOssimReliability() {
        return ossimReliability;
    }

    public void setOssimReliability(Short ossimReliability) {
        this.ossimReliability = ossimReliability;
    }

    public Short getOssimAssetSrc() {
        return ossimAssetSrc;
    }

    public void setOssimAssetSrc(Short ossimAssetSrc) {
        this.ossimAssetSrc = ossimAssetSrc;
    }

    public Short getOssimAssetDst() {
        return ossimAssetDst;
    }

    public void setOssimAssetDst(Short ossimAssetDst) {
        this.ossimAssetDst = ossimAssetDst;
    }

    public Short getOssimRiskC() {
        return ossimRiskC;
    }

    public void setOssimRiskC(Short ossimRiskC) {
        this.ossimRiskC = ossimRiskC;
    }

    public Short getOssimRiskA() {
        return ossimRiskA;
    }

    public void setOssimRiskA(Short ossimRiskA) {
        this.ossimRiskA = ossimRiskA;
    }

    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    public Integer getPluginSid() {
        return pluginSid;
    }

    public void setPluginSid(Integer pluginSid) {
        this.pluginSid = pluginSid;
    }

    public float getTzone() {
        return tzone;
    }

    public void setTzone(float tzone) {
        this.tzone = tzone;
    }

    public Short getOssimCorrelation() {
        return ossimCorrelation;
    }

    public void setOssimCorrelation(Short ossimCorrelation) {
        this.ossimCorrelation = ossimCorrelation;
    }

    public String getSrcHostname() {
        return srcHostname;
    }

    public void setSrcHostname(String srcHostname) {
        this.srcHostname = srcHostname;
    }

    public String getDstHostname() {
        return dstHostname;
    }

    public void setDstHostname(String dstHostname) {
        this.dstHostname = dstHostname;
    }

    public byte[] getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(byte[] srcMac) {
        this.srcMac = srcMac;
    }

    public byte[] getDstMac() {
        return dstMac;
    }

    public void setDstMac(byte[] dstMac) {
        this.dstMac = dstMac;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcidEvent)) {
            return false;
        }
        AcidEvent other = (AcidEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.AcidEvent[ id=" + id + " ]";
    }
    
}
