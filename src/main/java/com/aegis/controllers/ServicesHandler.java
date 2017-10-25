/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.controllers;

import com.aegis.messages.GetAcidEventsListResponse;
import com.aegis.messages.GetAcidEventsResponse;
import com.aegis.messages.GetDevicesListResponse;
import com.aegis.messages.GetDevicesResponse;
import com.aegis.messages.GetEventsTimeframeResponse;
import com.aegis.messages.GetExtraDataListResponse;
import com.aegis.messages.GetExtraDataResponse;
import com.aegis.messages.GetNetflowListResponse;
import com.aegis.messages.GetNetflowResponse;
import com.aegis.messages.GetNetworkLoadListResponse;
import com.aegis.messages.GetNetworkLoadResponse;
import com.aegis.ossimsiem.AcidEvent;
import com.aegis.ossimsiem.Device;
import com.aegis.ossimsiem.ExtraData;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lkallipolitis
 */
public class ServicesHandler {

    private final EntityManager em;

    public ServicesHandler(EntityManager em) {
        this.em = em;
    }

    public GetDevicesResponse getDevices() {
        //*********************** Variables ***************************
        GetDevicesResponse response = new GetDevicesResponse();
        List<Device> devicesparamsList;
        ArrayList<GetDevicesListResponse> devicesList;
        TypedQuery query;

        //*********************** Action ***************************
        try {
            query = (TypedQuery) em.createQuery("SELECT d FROM Device d ");
            //we use list to avoid "not found" exception
            devicesparamsList = query.getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (!devicesparamsList.isEmpty()) {
                devicesList = new ArrayList<GetDevicesListResponse>();
                for (Device dev : devicesparamsList) {
                    devicesList.add(new GetDevicesListResponse(dev.getId(), 
                            getIpfromBytes(dev.getDeviceIp()), 
                            dev.getInterface1(), 
                            getIpfromBytes(dev.getSensorId())));
                }
                response.setDevices(devicesList);
                return response;
            } else {
                devicesList = new ArrayList<GetDevicesListResponse>();
                response.setDevices(devicesList);
                return response;
            }

        } catch (Exception e) {
        }
        return response;
    }//end getDevices

    public GetAcidEventsResponse getAcidEvents() {
        //*********************** Variables ***************************
        GetAcidEventsResponse response = new GetAcidEventsResponse();
        List<AcidEvent> acidEventsparamsList;
        ArrayList<GetAcidEventsListResponse> acidEventsList;
        TypedQuery query;

        //*********************** Action ***************************
        try {
            //query =  em.createNamedQuery(AcidEvent.findAll); //createQuery("SELECT d FROM Device d ");
            //we use list to avoid "not found" exception
            acidEventsparamsList = em.createNamedQuery("AcidEvent.findAll").setMaxResults(100).getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (!acidEventsparamsList.isEmpty()) {
                acidEventsList = new ArrayList<>();

                for (AcidEvent acidev : acidEventsparamsList) {
                    
                    TypedQuery<ExtraData> query1;
                    query1 = (TypedQuery) em.createQuery("SELECT d FROM ExtraData d WHERE d.eventId = :eventId");
                    query1.setParameter("eventId", acidev.getId());
                    
                    ExtraData extradata = query1.getSingleResult();                   
                                      
                    String extra = 
                            extradata.getFilename() + " " +
                            extradata.getUserdata1() + " " +
                            extradata.getUserdata2() + " " +
                            extradata.getUserdata3() + " " +
                            extradata.getUserdata4() + " " +
                            extradata.getUserdata5() + " " +
                            extradata.getUserdata6() + " " +
                            extradata.getUserdata7() + " " +
                            extradata.getUserdata8() + " " +
                            extradata.getUserdata9();
                           
                    acidEventsList.add(new GetAcidEventsListResponse(
                            acidev.getId(),
                            acidev.getDeviceId(),
                            //acidev.getCtx(),
                            acidev.getTimestamp()/*,
                            extra,
                            getIpfromBytes(acidev.getIpSrc()) ,
                            getIpfromBytes(acidev.getIpDst()),
                            acidev.getIpProto(),
                            acidev.getLayer4Sport(),
                            acidev.getLayer4Dport(),
                            acidev.getOssimPriority(),
                            acidev.getOssimReliability(),
                            acidev.getOssimAssetSrc(),
                            acidev.getOssimAssetDst(),
                            acidev.getOssimRiskC(),
                            acidev.getOssimRiskA(),
                            acidev.getPluginId(),
                            acidev.getPluginSid(),
                            acidev.getTzone(),
                            acidev.getOssimCorrelation()*/,
                            acidev.getSrcHostname()/*,
                            acidev.getDstHostname(),
                            getIpfromBytes(acidev.getSrcMac()),
                            getIpfromBytes(acidev.getDstMac()),
                            getIpfromBytes(acidev.getSrcHost()),
                            getIpfromBytes(acidev.getDstHost()),
                            getIpfromBytes(acidev.getSrcNet()),
                            getIpfromBytes(acidev.getDstNet())*/));
                }
                response.setAcidEvents(acidEventsList);
                return response;
            } else {
                acidEventsList = new ArrayList<GetAcidEventsListResponse>();
                response.setAcidEvents(acidEventsList);
                return response;
            }

        } catch (Exception e) {
        }
        return response;
    }//end getAcidEvents
    
    
    public GetEventsTimeframeResponse getAcidEventsTimeframe() {
        //*********************** Variables ***************************
        GetEventsTimeframeResponse response = null;
        Date oldest = new Date();
        Date newest = new Date();
        List<AcidEvent> oldestEventsList;
        List<AcidEvent> newestEventsList;

        //*********************** Action ***************************
        try {
            //we use list to avoid "not found" exception
            oldestEventsList = em.createQuery("SELECT a FROM AcidEvent a ORDER BY a.timestamp ASC").setMaxResults(1).getResultList();
            newestEventsList = em.createQuery("SELECT a FROM AcidEvent a ORDER BY a.timestamp DESC").setMaxResults(1).getResultList();

            //if we found no results, no events are stored so return empty list
            if (!oldestEventsList.isEmpty()) {        

                for (AcidEvent oldestEvent : oldestEventsList) {                    
                    oldest = oldestEvent.getTimestamp();
                    break; // we have one result anyway                   
                }
                
                for (AcidEvent latestEvent : newestEventsList) {                    
                    newest = latestEvent.getTimestamp();
                    break; // we have one result anyway                   
                }               
                
                response = new GetEventsTimeframeResponse(oldest,newest);
                return response;
            } else {                
                response = new GetEventsTimeframeResponse(null,null);
                return response;
            }

        } catch (Exception e) {
        }
        return response;
    }//end getAcidEventsTimeframe
    
    
    /*
    * Gets extradata that associated Event happened after startDate
    */
     public GetExtraDataResponse getExtraData(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        GetExtraDataResponse response = new GetExtraDataResponse();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetExtraDataListResponse> extraDataList;
        TypedQuery query;

        //*********************** Action ***************************
        try {
            //query =  em.createNamedQuery(AcidEvent.findAll); //createQuery("SELECT d FROM Device d ");
            //we use list to avoid "not found" exception
            extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2",userDataValue ).getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (!extraDataparamsList.isEmpty()) {
                extraDataList = new ArrayList<>();

                for (ExtraData extra : extraDataparamsList) {
                    
                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);
                    if(severity)
                    {
                        if(currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL"))
                        {
                            isWarnOrError = true;
                        }
                    }
                    
                    TypedQuery<AcidEvent> query1;
                    query1 = (TypedQuery) em.createQuery("SELECT a FROM AcidEvent a WHERE a.id = :eventId");
                    query1.setParameter("eventId", extra.getEventId());                    
                    AcidEvent relatedAcidEvent = query1.getSingleResult();
                    
                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                    */
                    String checkHostName = relatedAcidEvent.getSrcHostname();
                    if(!srcHost.equals("any"))
                    {
                        checkHostName = srcHost;
                    }
                    
                    if(!severity || (severity && isWarnOrError))
                    {
                    if( relatedAcidEvent.getSrcHostname().equals(checkHostName) &&
                        relatedAcidEvent.getTimestamp().getTime() >= startDate &&
                        relatedAcidEvent.getTimestamp().getTime() <= endDate)
                    {  
                        
                        GetAcidEventsListResponse acideventResponse = new 
                            GetAcidEventsListResponse(
                                relatedAcidEvent.getId(),
                                relatedAcidEvent.getDeviceId(),
                                //relatedAcidEvent.getCtx(),
                                relatedAcidEvent.getTimestamp()/*,
                                null, // no extra needed here
                                getIpfromBytes(relatedAcidEvent.getIpSrc()),
                                getIpfromBytes(relatedAcidEvent.getIpDst()),
                                relatedAcidEvent.getIpProto(),
                                relatedAcidEvent.getLayer4Sport(),
                                relatedAcidEvent.getLayer4Dport(),
                                relatedAcidEvent.getOssimPriority(),
                                relatedAcidEvent.getOssimReliability(),
                                relatedAcidEvent.getOssimAssetSrc(),
                                relatedAcidEvent.getOssimAssetDst(),
                                relatedAcidEvent.getOssimRiskC(),
                                relatedAcidEvent.getOssimRiskA(),
                                relatedAcidEvent.getPluginId(),
                                relatedAcidEvent.getPluginSid(),
                                relatedAcidEvent.getTzone(),
                                relatedAcidEvent.getOssimCorrelation()*/,
                                relatedAcidEvent.getSrcHostname()/*,
                                relatedAcidEvent.getDstHostname(),
                                getIpfromBytes(relatedAcidEvent.getSrcMac()),
                                getIpfromBytes(relatedAcidEvent.getDstMac()),
                                getIpfromBytes(relatedAcidEvent.getSrcHost()),
                                getIpfromBytes(relatedAcidEvent.getDstHost()),
                                getIpfromBytes(relatedAcidEvent.getSrcNet()),
                                getIpfromBytes(relatedAcidEvent.getDstNet())*/);


                        if (userDataValue.equals("Server Load")) {
                            String[] loadvalues = new String[3];
                            String values = "";
                            if (extra.getUserdata5().contains("average")) {
                                values = extra.getUserdata5();
                            } else if (extra.getUserdata4().contains("average")) {
                                values = extra.getUserdata4();
                            }
                            if (!values.equals("")) {
                                String loadvalue = values.substring(values.lastIndexOf("average: ") + 9);
                                loadvalues = loadvalue.split(", ");
                                extraDataList.add(new GetExtraDataListResponse(
                                        extra.getEventId(),                                        
                                        extra.getDataPayload(),
                                        extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                        loadvalues[0],
                                        loadvalues[1],
                                        loadvalues[2].substring(0,loadvalues[2].indexOf("\n")),
                                        null,
                                        null,
                                        acideventResponse
                                ));
                            }
                        }
                        else if(userDataValue.equals("Total Processes")){
                            String values = "";
                            if (extra.getUserdata4().contains("processes")) {
                                values = extra.getUserdata4();
                            } else if (extra.getUserdata5().contains("processes")) {
                                values = extra.getUserdata5();
                            }
                            if (!values.equals("")) {
                                String loadvalue = values.substring(values.lastIndexOf(": ") + 2, values.lastIndexOf(" processes"));
                                
                                extraDataList.add(new GetExtraDataListResponse(
                                        extra.getEventId(),                                        
                                        extra.getDataPayload(),
                                        extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                        null,
                                        null,
                                        null,                                        
                                        loadvalue,
                                        null,
                                        acideventResponse
                                ));
                            }
                        }
                        else if(userDataValue.equals("Current Users")){
                            String values = "";
                            if (extra.getUserdata4().contains("users currently")) {
                                values = extra.getUserdata4();
                            } else if (extra.getUserdata5().contains("users currently")) {
                                values = extra.getUserdata5();
                            }
                                                       
                            
                            if (!values.equals("")) {
                                // e.g.USERS OK - 0 users currently logged in 
                                String usersvalue = values.substring(values.lastIndexOf("- ") + 2, values.lastIndexOf(" users"));
                                
                                extraDataList.add(new GetExtraDataListResponse(
                                        extra.getEventId(),                                       
                                        extra.getDataPayload(),
                                        extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                        null,
                                        null,
                                        null,
                                        null,
                                        usersvalue,
                                        acideventResponse
                                ));
                            }
                        }
                    }
                    }
                }
                response.setExtraData(extraDataList);
                return response;
            } else {
                extraDataList = new ArrayList<GetExtraDataListResponse>();
                response.setExtraData(extraDataList);
                return response;
            }

        } catch (Exception e) {
        }
        return response;
    }//end getProjects
    
     
    /*
    * Gets extradata that associated Event happened after startDate
    */
     public GetExtraDataListResponse getClosestValueByTime(String cipi, long timestamp, String srcHost) {
        //*********************** Variables ***************************
        GetExtraDataListResponse response = new GetExtraDataListResponse();
        ExtraData extra = new ExtraData();

        //*********************** Action ***************************
        try {
            java.util.Date time=new java.util.Date((long)timestamp);
            TypedQuery<ExtraData> query1;
                    query1 = (TypedQuery) em.createQuery("SELECT d FROM ExtraData d WHERE d.relatedEvent.timestamp = :timestamp");
                    query1.setParameter("timestamp", time.toString());                    
                    extra = query1.getSingleResult();
         
            
                response= new GetExtraDataListResponse(
                                        extra.getEventId(),                                       
                                        extra.getDataPayload(),
                                        extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null);
                return response;
           

        } catch (Exception e) {
        }
        return response;
    }//end getProjects
     
    public GetNetflowResponse getNetFlow() {

        GetNetflowResponse response = new GetNetflowResponse();        
        ArrayList<GetNetflowListResponse> netflowList = new ArrayList<GetNetflowListResponse>();
        CSVReader reader = null;
        String netflowCsv = "C:\\Users\\lkallipolitis\\Documents\\mine\\cipsec\\flowsAllFixed.csv";
        
        try{
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            while ((line=reader.readNext()) != null && !finished){
                if(!line[0].equals("Summary"))
                {
               //ts,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,...
                netflowList.add(new GetNetflowListResponse(
                        line[1],
                        line[3],
                        Float.parseFloat(line[4]),
                        line[5],
                        Integer.parseInt(line[7]),
                        line[6],
                        Integer.parseInt(line[8]),
                        line[9],
                        Integer.parseInt(line[13]),
                        Integer.parseInt(line[15]),
                        Integer.parseInt(line[14]),
                        Integer.parseInt(line[16])));
                }
                else{
                    finished = true;
                }
            }            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        response.setNetflowList(netflowList);
        
        return response;
    }//end getNetFlow
    
     public GetNetworkLoadResponse getNetworkLoad() {

        GetNetworkLoadResponse response = new GetNetworkLoadResponse();        
        ArrayList <GetNetworkLoadListResponse> networkloadList = new ArrayList<GetNetworkLoadListResponse>();
        CSVReader reader = null;
        String netflowCsv = "C:\\Users\\lkallipolitis\\Documents\\mine\\cipsec\\flowsAllFixed.csv";
        
        try{
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            
            Map <String,Double> netvals = new LinkedHashMap<String,Double>();
            while ((line=reader.readNext()) != null && !finished){
                if(!line[0].equals("Summary"))
                {
                    
                    Float duration = Float.parseFloat(line[4]);
                    Float inbytes = Float.parseFloat(line[14]);
                    
                    double bps = duration > 0 ? inbytes/duration : 0.01;
                    netvals.put(line[3], bps);
                    
                    if(netvals.containsKey(line[3]))
                    {
                        netvals.put(line[3], (netvals.get(line[3]) + bps) /2);
                    }
                    else
                    {
                        netvals.put(line[3],bps);
                    }    
                }
                else{
                    finished = true;
                }
            }
            
            Iterator it = netvals.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                networkloadList.add(new GetNetworkLoadListResponse(                       
                        pair.getKey().toString(),                        
                        (double)pair.getValue()));
            }
            
            //ts,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,...
                
        }catch(IOException e){
            e.printStackTrace();
        }
        
        response.setNetworkLoad(networkloadList);
        
        return response;
    }//end getNetworkLoad
     
    private static String getIpfromBytes(byte[] byteip){
     if ( byteip != null &&  byteip.length > 1) {
         try {
             return InetAddress.getByAddress(byteip).getHostAddress();
         } catch (UnknownHostException ex) {
             Logger.getLogger(ServicesHandler.class.getName()).log(Level.SEVERE, null, ex);
             return "";
         }
       }
     else
         return "";
    }
}
