/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;

import java.util.Date;

/**
 *
 * @author lkallipolitis
 */
public class GetAcidEventsListResponse {

    private byte[] id;

    private int deviceId;

    //private byte[] ctx;

    private Date timestamp;
    
    /*private String extra;

    private String ipSrc;

    private String ipDst;

    private Integer ipProto;

    private Short layer4Sport;

    private Short layer4Dport;

    private Short ossimPriority;

    private Short ossimReliability;

    private Short ossimAssetSrc;

    private Short ossimAssetDst;

    private Short ossimRiskC;

    private Short ossimRiskA;

    private Integer pluginId;

    private Integer pluginSid;

    private float tzone;

    private Short ossimCorrelation;*/

    private String srcHostname;

    /*private String dstHostname;

    private String srcMac;

    private String dstMac;

    private String srcHost;
    private String dstHost;
    private String srcNet;
    private String dstNet;*/

    public GetAcidEventsListResponse(byte[] id, int deviceId, /*byte[] ctx,*/ Date timestamp/*,String extra, String ipSrc,String ipDst, Integer ipProto, Short layer4Sport, Short layer4Dport, Short ossimPriority, Short ossimReliability, Short ossimAssetSrc, Short ossimAssetDst, Short ossimRiskC, Short ossimRiskA, Integer pluginId, Integer pluginSid, float tzone, Short ossimCorrelation*/, String srcHostname/*, String dstHostname, String srcMac, String dstMac, String srcHost, String dstHost, String srcNet, String dstNet*/) {
        this.id = id;
        this.deviceId = deviceId;
        //this.ctx = ctx;
        this.timestamp = timestamp;
        /*this.extra = extra;
        this.ipSrc = ipSrc;
        this.ipDst = ipDst;
        this.ipProto = ipProto;
        this.layer4Sport = layer4Sport;
        this.layer4Dport = layer4Dport;
        this.ossimPriority = ossimPriority;
        this.ossimReliability = ossimReliability;
        this.ossimAssetSrc = ossimAssetSrc;
        this.ossimAssetDst = ossimAssetDst;
        this.ossimRiskC = ossimRiskC;
        this.ossimRiskA = ossimRiskA;
        this.pluginId = pluginId;
        this.pluginSid = pluginSid;
        this.tzone = tzone;
        this.ossimCorrelation = ossimCorrelation;*/
        this.srcHostname = srcHostname;
        /*this.dstHostname = dstHostname;
        this.srcMac = srcMac;
        this.dstMac = dstMac;
        this.srcHost = srcHost;
        this.dstHost = dstHost;
        this.srcNet = srcNet;
        this.dstNet = dstNet;*/
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

    /*public byte[] getCtx() {
        return ctx;
    }

    public void setCtx(byte[] ctx) {
        this.ctx = ctx;
    }*/

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /*
    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getIpSrc() {
        return ipSrc;
    }

    public void setIpSrc(String ipSrc) {
        this.ipSrc = ipSrc;
    }

    public String getIpDst() {
        return ipDst;
    }

    public void setIpDst(String ipDst) {
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
    }*/

    public String getSrcHostname() {
        return srcHostname;
    }

    public void setSrcHostname(String srcHostname) {
        this.srcHostname = srcHostname;
    }

   /* public String getDstHostname() {
        return dstHostname;
    }

    public void setDstHostname(String dstHostname) {
        this.dstHostname = dstHostname;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getDstMac() {
        return dstMac;
    }

    public void setDstMac(String dstMac) {
        this.dstMac = dstMac;
    }

    public String getSrcHost() {
        return srcHost;
    }

    public void setSrcHost(String srcHost) {
        this.srcHost = srcHost;
    }

    public String getDstHost() {
        return dstHost;
    }

    public void setDstHost(String dstHost) {
        this.dstHost = dstHost;
    }

    public String getSrcNet() {
        return srcNet;
    }

    public void setSrcNet(String srcNet) {
        this.srcNet = srcNet;
    }

    public String getDstNet() {
        return dstNet;
    }

    public void setDstNet(String dstNet) {
        this.dstNet = dstNet;
    }*/
}
