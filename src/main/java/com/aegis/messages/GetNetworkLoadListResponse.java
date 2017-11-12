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
public class GetNetworkLoadListResponse {
    
    private String time;    

    private double packetsPerSec;

    public GetNetworkLoadListResponse(String time, double packetsPerSec) {
        this.time = time;
        this.packetsPerSec = packetsPerSec;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPacketsPerSec() {
        return packetsPerSec;
    }

    public void setPacketsPerSec(double packetsPerSec) {
        this.packetsPerSec = packetsPerSec;
    }       
}
