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
 * @description user to produce nvd3-specific format of json response
 */
public class CipiSeries {

    private String key;
    private List<CipiValuePair> values;

    public CipiSeries(String key, List<CipiValuePair> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<CipiValuePair> getValues() {
        return values;
    }

    public void setValues(List<CipiValuePair> values) {
        this.values = values;
    }
 
}
