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
public class GetDevicesResponse {
    
    List<GetDevicesListResponse> devices;

    public List<GetDevicesListResponse> getDevices() {
        return devices;
    }

    public void setDevices(List<GetDevicesListResponse> devices) {
        this.devices = devices;
    }
    
}
