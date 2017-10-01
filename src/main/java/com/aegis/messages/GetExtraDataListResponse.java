/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;

import com.aegis.ossimsiem.AcidEvent;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

/**
 *
 * @author lkallipolitis
 */
public class GetExtraDataListResponse {    

    private byte[] eventId;

    private String dataPayload;
    
    private String severity;
   
    private String load1min;

    private String load5min;

    private String load15min;
    
    private String totalprocesses;
    
    private String currentUsers;

    private GetAcidEventsListResponse relatedEvent;

    public GetExtraDataListResponse(byte[] eventId, String dataPayload, String severity, String load1min, String load5min, String load15min, String totalprocesses, String currentUsers, GetAcidEventsListResponse relatedEvent) {
        this.eventId = eventId;
        this.dataPayload = dataPayload;
        this.severity = severity;
        this.load1min = load1min;
        this.load5min = load5min;
        this.load15min = load15min;
        this.totalprocesses = totalprocesses;
        this.currentUsers = currentUsers;
        this.relatedEvent = relatedEvent;
    }
    
    public GetExtraDataListResponse(){        
    }
            
    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public String getDataPayload() {
        return dataPayload;
    }

    public void setDataPayload(String dataPayload) {
        this.dataPayload = dataPayload;
    }
    
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
        public String getLoad1min() {
        return load1min;
    }

    public void setLoad1min(String load1min) {
        this.load1min = load1min;
    }

    public String getLoad5min() {
        return load5min;
    }

    public void setLoad5min(String load5min) {
        this.load5min = load5min;
    }

    public String getLoad15min() {
        return load15min;
    }

    public void setLoad15min(String load15min) {
        this.load15min = load15min;
    }   
    
    public String getTotalprocesses() {
        return totalprocesses;
    }

    public void setTotalprocesses(String totalprocesses) {
        this.totalprocesses = totalprocesses;
    }

    public String getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(String currentUsers) {
        this.currentUsers = currentUsers;
    }

    public GetAcidEventsListResponse getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(GetAcidEventsListResponse relatedEvent) {
        this.relatedEvent = relatedEvent;
    }   
}
