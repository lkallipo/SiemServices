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
 * @description user to produce nvd3-specific format of json response
 */
public class CipiValuePair {

    private Date timestamp;
    private String value;

    public CipiValuePair(Date timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }   

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLoadvalue() {
        return value;
    }

    public void setLoadvalue(String loadvalue) {
        this.value = loadvalue;
    }

}
