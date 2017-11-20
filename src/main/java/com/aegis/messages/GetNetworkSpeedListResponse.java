/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;

/**
 *
 * @author lkallipolitis
 */
public class GetNetworkSpeedListResponse {
    
    private String time;    

    private double bytesPerSec;

    private String severity;
    
    public GetNetworkSpeedListResponse(String time, double bytesPerSec, String severity) {
        this.time = time;
        this.bytesPerSec = bytesPerSec;
        this.severity = severity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getBytesPerSec() {
        return bytesPerSec;
    }

    public void setBytesPerSec(double bytesPerSec) {
        this.bytesPerSec = bytesPerSec;
    }     

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }    
}
