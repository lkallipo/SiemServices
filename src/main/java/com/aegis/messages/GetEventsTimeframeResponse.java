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
 */
public class GetEventsTimeframeResponse {    

    private Date oldestTimestamp;
    private Date newestTimestamp;
            
    public GetEventsTimeframeResponse(Date oldestTimestamp,Date newestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
        this.newestTimestamp = newestTimestamp;     
    }
    
    public Date getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(Date oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public Date getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(Date newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }  
}
