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
public class GetNetworkConnsListResponse {
    
    private String time;    

    private int connections;

    public GetNetworkConnsListResponse(String time, int connections) {
        this.time = time;
        this.connections = connections;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }
       
}
