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
public class GetHttpStatusResponse {    

    private byte[] eventId;

    private String dataPayload;
    
    private String severity;
    
    private String throuput;

    private GetAcidEventsListResponse relatedEvent;

    public GetHttpStatusResponse(byte[] eventId, String dataPayload, String severity, String throuput, GetAcidEventsListResponse relatedEvent) {
        this.eventId = eventId;
        this.dataPayload = dataPayload;
        this.severity = severity;
        this.throuput = throuput;
        this.relatedEvent = relatedEvent;
    }
    
    public GetHttpStatusResponse(){        
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

    public String getThrouput() {
        return throuput;
    }

    public void setThrouput(String throuput) {
        this.throuput = throuput;
    }    

    public GetAcidEventsListResponse getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(GetAcidEventsListResponse relatedEvent) {
        this.relatedEvent = relatedEvent;
    }   
}
