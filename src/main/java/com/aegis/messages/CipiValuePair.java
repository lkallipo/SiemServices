/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;

import java.util.Date;

/**
 * Used to produce nvd3-specific format of json response
 * 
 * @author lkallipolitis
 *  
 */
public class CipiValuePair {

    private Date x;
    private float y;

    public CipiValuePair(Date x, float y) {
        this.x = x;
        this.y = y;
    }

    public Date getX() {
        return x;
    }

    public void setX(Date x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }



}
