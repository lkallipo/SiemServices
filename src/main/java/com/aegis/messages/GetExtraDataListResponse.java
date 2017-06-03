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

    private String filename;

    private String username;

    private String password;

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

    private byte[] binaryData;
    
    private AcidEvent relatedEvent;

    public GetExtraDataListResponse(byte[] eventId, String filename, String username, String password, String userdata1, String userdata2, String userdata3, String userdata4, String userdata5, String userdata6, String userdata7, String userdata8, String userdata9, String dataPayload, byte[] binaryData, AcidEvent relatedEvent) {
        this.eventId = eventId;
        this.filename = filename;
        this.username = username;
        this.password = password;
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
        this.binaryData = binaryData;
        this.relatedEvent = relatedEvent;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public AcidEvent getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(AcidEvent relatedEvent) {
        this.relatedEvent = relatedEvent;
    }
    
    
}
