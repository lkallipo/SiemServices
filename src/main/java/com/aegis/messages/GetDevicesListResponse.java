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
public class GetDevicesListResponse {
    

    private Integer id;

    private String deviceIp;

    private String interface1;

    private String sensorId;
    
    public GetDevicesListResponse(Integer id,String deviceIp, String interface1, String sensorId) {
        this.id = id;
        this.deviceIp = deviceIp;
        this.interface1 = interface1;
        this.sensorId = sensorId;        
    }
    
     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
        public String getDeviceIp() {
        return deviceIp;
    }

    public String getInterface1() {
        return interface1;
    }

    public String getSensorId() {
        return sensorId;
    }
    
        public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public void setInterface1(String interface1) {
        this.interface1 = interface1;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
