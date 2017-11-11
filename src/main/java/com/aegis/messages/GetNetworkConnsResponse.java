/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;
import java.util.List;
/**
 *
 * @author lkallipolitis
 */
public class GetNetworkConnsResponse {
    
    List<GetNetworkConnsListResponse> networkconnections;

    public List<GetNetworkConnsListResponse> getNetworkConnections() {
        return networkconnections;
    }

    public void setNetworkConnections(List<GetNetworkConnsListResponse> networkconnections) {
        this.networkconnections = networkconnections;
    }    
}
