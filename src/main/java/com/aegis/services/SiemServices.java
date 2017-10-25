/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.services;

import com.aegis.controllers.ServicesHandler;
import com.aegis.messages.GetAcidEventsResponse;
import com.aegis.messages.GetDevicesResponse;
import com.aegis.messages.GetEventsTimeframeResponse;
import com.aegis.messages.GetExtraDataListResponse;
import com.aegis.messages.GetExtraDataResponse;
import com.aegis.messages.GetNetflowListResponse;
import com.aegis.messages.GetNetflowResponse;
import com.aegis.messages.GetNetworkLoadResponse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author lkallipolitis
 */
@Stateless
@Path("api")
public class SiemServices {

    @PersistenceContext(unitName = "OssimSiemPU")
    private EntityManager em;

    public void init() {

        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OssimSiemPU");
        em = factory.createEntityManager();
    }

    @GET
    @Path("/getDevices")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetDevicesResponse getDevices() throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetDevicesResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getDevices();
        return response;
    }
    
    @GET
    @Path("/getAcidEvents")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetAcidEventsResponse getAcidEvents() throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetAcidEventsResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getAcidEvents();
        return response;
    }
    
    @GET
    @Path("/getEventsTimeframe")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetEventsTimeframeResponse getAcidEventsTimeframe() throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetEventsTimeframeResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getAcidEventsTimeframe();
        return response;
    }
    
    @GET
    @Path("/getCurrentLoad")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataResponse getCurrentLoad(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp, @QueryParam("srcHost") String srcHost, @QueryParam("severity") @DefaultValue("false") String severity) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getExtraData("Current Load",Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost, Boolean.valueOf(severity));
        return response;
    }
    
        
    @GET
    @Path("/getServerLoad")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataResponse getServerLoad(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp, @QueryParam("srcHost") String srcHost, @QueryParam("severity") @DefaultValue("false") String severity) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getExtraData("Server Load",Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost, Boolean.valueOf(severity));
        return response;
    }
    
    @GET
    @Path("/getCurrentProcesses")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataResponse getCurrentProcesses(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp, @QueryParam("srcHost") String srcHost, @QueryParam("severity") @DefaultValue("false") String severity) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getExtraData("Total Processes",Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost, Boolean.valueOf(severity));
        return response;
    }
    
    @GET
    @Path("/getCurrentUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataResponse getCurrentUsers(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp, @QueryParam("srcHost") String srcHost, @QueryParam("severity") @DefaultValue("false") String severity) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getExtraData("Current Users",Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost, Boolean.valueOf(severity));
        return response;
    }
    
    @GET
    @Path("/getClosestValueByTime")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataListResponse getClosestValueByTime(@QueryParam("cipi") String cipi, @QueryParam("timestamp") String timestamp,  @QueryParam("srcHost") String srcHost) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataListResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getClosestValueByTime(cipi,Long.parseLong(timestamp),srcHost);
        return response;
    }
    
    @GET
    @Path("/getNetFlow")    
    @Produces(MediaType.APPLICATION_JSON)
    public GetNetflowResponse getNetFlow(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp,  @QueryParam("srcHost") String srcHost) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetNetflowResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getNetFlow(/*Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost*/);
        return response;
    }
    
    @GET
    @Path("/getNetworkLoad")    
    @Produces(MediaType.APPLICATION_JSON)
    public GetNetworkLoadResponse getNetWorkLoad(@QueryParam("startTimestamp") String starttimestamp, @QueryParam("endTimestamp") String endtimestamp,  @QueryParam("srcHost") String srcHost) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetNetworkLoadResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getNetworkLoad(/*Long.parseLong(starttimestamp),Long.parseLong(endtimestamp),srcHost*/);
        return response;
    }
}
