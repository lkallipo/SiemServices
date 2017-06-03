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
public class GetAcidEventsResponse {
    
    List<GetAcidEventsListResponse> acidEvents;

    public List<GetAcidEventsListResponse> getAcidEvents() {
        return acidEvents;
    }

    public void setAcidEvents(List<GetAcidEventsListResponse> acidEvents) {
        this.acidEvents = acidEvents;
    }
    
}
