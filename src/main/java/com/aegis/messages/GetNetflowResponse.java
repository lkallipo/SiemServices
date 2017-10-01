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
public class GetNetflowResponse {
    
    List<GetNetflowListResponse> netflowlist;

    public List<GetNetflowListResponse> getNetflowList() {
        return netflowlist;
    }

    public void setNetflowList(List<GetNetflowListResponse> netflowlist) {
        this.netflowlist = netflowlist;
    }    
}
