/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.services;

import com.aegis.controllers.ServicesHandler;
import com.aegis.messages.GetAcidEventsResponse;
import com.aegis.messages.GetDevicesResponse;
import com.aegis.messages.GetExtraDataResponse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
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
@Path("siem")
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
    @Path("/getExtraData")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetExtraDataResponse getExtraData(@QueryParam("startTimestamp") String timestamp) throws ClassNotFoundException {
        //*********************** Variables ***************************
        ServicesHandler handler;
        GetExtraDataResponse response;
        //*********************** Action ***************************
        if (em == null) {
            init();
        }
        handler = new ServicesHandler(em);
        response = handler.getExtraData("Current Load",Long.parseLong(timestamp));
        return response;
    }
}
