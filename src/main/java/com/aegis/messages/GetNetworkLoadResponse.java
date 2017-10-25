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
public class GetNetworkLoadResponse {
    
    List<GetNetworkLoadListResponse> networkload;

    public List<GetNetworkLoadListResponse> getNetworkLoad() {
        return networkload;
    }

    public void setNetworkLoad(List<GetNetworkLoadListResponse> networkload) {
        this.networkload = networkload;
    }    
}
