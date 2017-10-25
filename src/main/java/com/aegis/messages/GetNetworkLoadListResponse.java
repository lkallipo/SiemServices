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
public class GetNetworkLoadListResponse {
    
    private String time;    

    private double bytesPerSec;

    public GetNetworkLoadListResponse(String time, double bytesPerSec) {
        this.time = time;
        this.bytesPerSec = bytesPerSec;
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
       
}
