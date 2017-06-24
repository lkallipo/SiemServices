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

    private String userdata1;

    private String userdata2;

    private String userdata3;

    private String userdata4;

    private String userdata5;

    private String userdata6;

    private String userdata7;

    private String userdata8;

    private String userdata9;

    private String dataPayload;
    
    private String severity;
   
    private String load1min;

    private String load5min;

    private String load15min;
    
    private String totalprocesses;

    private GetAcidEventsListResponse relatedEvent;

    public GetExtraDataListResponse(byte[] eventId, String userdata1, String userdata2, String userdata3, String userdata4, String userdata5, String userdata6, String userdata7, String userdata8, String userdata9, String dataPayload, String severity, String load1min, String load5min, String load15min, String totalprocesses, GetAcidEventsListResponse relatedEvent) {
        this.eventId = eventId;
        this.userdata1 = userdata1;
        this.userdata2 = userdata2;
        this.userdata3 = userdata3;
        this.userdata4 = userdata4;
        this.userdata5 = userdata5;
        this.userdata6 = userdata6;
        this.userdata7 = userdata7;
        this.userdata8 = userdata8;
        this.userdata9 = userdata9;
        this.dataPayload = dataPayload;
        this.severity = severity;
        this.load1min = load1min;
        this.load5min = load5min;
        this.load15min = load15min;
        this.totalprocesses = totalprocesses;
        this.relatedEvent = relatedEvent;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public String getUserdata1() {
        return userdata1;
    }

    public void setUserdata1(String userdata1) {
        this.userdata1 = userdata1;
    }

    public String getUserdata2() {
        return userdata2;
    }

    public void setUserdata2(String userdata2) {
        this.userdata2 = userdata2;
    }

    public String getUserdata3() {
        return userdata3;
    }

    public void setUserdata3(String userdata3) {
        this.userdata3 = userdata3;
    }

    public String getUserdata4() {
        return userdata4;
    }

    public void setUserdata4(String userdata4) {
        this.userdata4 = userdata4;
    }

    public String getUserdata5() {
        return userdata5;
    }

    public void setUserdata5(String userdata5) {
        this.userdata5 = userdata5;
    }

    public String getUserdata6() {
        return userdata6;
    }

    public void setUserdata6(String userdata6) {
        this.userdata6 = userdata6;
    }

    public String getUserdata7() {
        return userdata7;
    }

    public void setUserdata7(String userdata7) {
        this.userdata7 = userdata7;
    }

    public String getUserdata8() {
        return userdata8;
    }

    public void setUserdata8(String userdata8) {
        this.userdata8 = userdata8;
    }

    public String getUserdata9() {
        return userdata9;
    }

    public void setUserdata9(String userdata9) {
        this.userdata9 = userdata9;
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

    public GetAcidEventsListResponse getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(GetAcidEventsListResponse relatedEvent) {
        this.relatedEvent = relatedEvent;
    }
    
    
}
