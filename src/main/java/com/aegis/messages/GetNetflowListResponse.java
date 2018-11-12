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
    private int packets;
    
    //opkt
    private double bytes;
    
    //ipkt
    private int bytesPerSec;
    
    //opkt
    private int packetsPerSec;

    public GetNetflowListResponse(String timestart, String timeend, float duration, 
            String srcAddress, int srcPort, String dstAddress, int dstPort, 
            String protocol, int packets, double bytes, 
            int bytesPerSec, int packetsPerSec) {
        this.timestart = timestart;
        this.timeend = timeend;
        this.duration = duration;
        this.srcAddress = srcAddress;
        this.srcPort = srcPort;
        this.dstAddress = dstAddress;
        this.dstPort = dstPort;
        this.protocol = protocol;
        this.packets = packets;
        this.bytes = bytes;
        this.bytesPerSec = bytesPerSec;
        this.packetsPerSec = packetsPerSec;
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

    public int getPackets() {
        return packets;
    }

    public void setPackets(int packets) {
        this.packets = packets;
    }

    public double getBytes() {
        return bytes;
    }

    public void setBytes(double bytes) {
        this.bytes = bytes;
    }

    public int getBytesPerSec() {
        return bytesPerSec;
    }

    public void setBytesPerSec(int bytesPerSec) {
        this.bytesPerSec = bytesPerSec;
    }

    public int getPacketsPerSec() {
        return packetsPerSec;
    }

    public void setPacketsPerSec(int packetsPerSec) {
        this.packetsPerSec = packetsPerSec;
    }
    
}
