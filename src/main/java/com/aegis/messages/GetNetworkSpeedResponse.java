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
public class GetNetworkSpeedResponse {
    
    List<GetNetworkSpeedListResponse> networkspeed;

    public List<GetNetworkSpeedListResponse> getNetworkSpeed() {
        return networkspeed;
    }

    public void setNetworkSpeed(List<GetNetworkSpeedListResponse> networkspeed) {
        this.networkspeed = networkspeed;
    }    
}
