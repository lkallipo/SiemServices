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
public class GetExtraDataResponse {
    
    List<?> extraData;

    public List<?> getExtraData() {
        return extraData;
    }

    public void setExtraData(List<?> extraData) {
        this.extraData = extraData;
    }    
}
