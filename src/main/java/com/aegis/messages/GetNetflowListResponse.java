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
public class GetNetflowListResponse {
    
    //ts
    private String timestart;
    
    //te
    private String timeend;
    
    //td
    private float duration;
    
    //sa
    private String srcAddress;
    
    //sp
    private int srcPort;
    
    //da
    private String dstAddress;
    
    //dp
    private int dstPort;
    
    //pr
    private String protocol;
    
    //ipkt
    private int incomingPackets;
    
    //opkt
    private int outgoingPackets;
    

    public GetNetflowListResponse(String timestart, String timeend, float duration, String srcAddress, int srcPort, String dstAddress, int dstPort, String protocol, int incomingPackets, int outgoingPackets) {
        this.timestart = timestart;
        this.timeend = timeend;
        this.duration = duration;
        this.srcAddress = srcAddress;
        this.srcPort = srcPort;
        this.dstAddress = dstAddress;
        this.dstPort = dstPort;
        this.protocol = protocol;
        this.incomingPackets = incomingPackets;
        this.outgoingPackets = outgoingPackets;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getIncomingPackets() {
        return incomingPackets;
    }

    public void setIncomingPackets(int incomingPackets) {
        this.incomingPackets = incomingPackets;
    }

    public int getOutgoingPackets() {
        return outgoingPackets;
    }

    public void setOutgoingPackets(int outgoingPackets) {
        this.outgoingPackets = outgoingPackets;
    }
    
}
