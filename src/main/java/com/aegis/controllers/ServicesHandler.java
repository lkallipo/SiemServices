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
import com.aegis.messages.GetHttpStatusResponse;
import com.aegis.messages.GetNetflowListResponse;
import com.aegis.messages.GetNetflowResponse;
import com.aegis.messages.GetNetworkConnsListResponse;
import com.aegis.messages.GetNetworkConnsResponse;
import com.aegis.messages.GetNetworkLoadListResponse;
import com.aegis.messages.GetNetworkLoadResponse;
import com.aegis.messages.GetNetworkSpeedListResponse;
import com.aegis.messages.GetNetworkSpeedResponse;
import com.aegis.ossimsiem.AcidEvent;
import com.aegis.ossimsiem.Device;
import com.aegis.ossimsiem.ExtraData;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
            devicesparamsList = query.getResultList();
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
            acidEventsparamsList = em.createNamedQuery("AcidEvent.findAll").setMaxResults(100).getResultList();
           
            if (!acidEventsparamsList.isEmpty()) {
                acidEventsList = new ArrayList<>();

                for (AcidEvent acidev : acidEventsparamsList) {
                    
                    TypedQuery<ExtraData> query1;
                    query1 = (TypedQuery) em.createQuery("SELECT d FROM ExtraData d WHERE d.eventId = :eventId");
                    query1.setParameter("eventId", acidev.getId());
                    
                    ExtraData extradata = query1.getSingleResult();                   
                                      
                    /*String extra = 
                            extradata.getFilename() + " " +
                            extradata.getUserdata1() + " " +
                            extradata.getUserdata2() + " " +
                            extradata.getUserdata3() + " " +
                            extradata.getUserdata4() + " " +
                            extradata.getUserdata5() + " " +
                            extradata.getUserdata6() + " " +
                            extradata.getUserdata7() + " " +
                            extradata.getUserdata8() + " " +
                            extradata.getUserdata9();*/
                           
                    acidEventsList.add(new GetAcidEventsListResponse(
                            acidev.getId(),
                            acidev.getDeviceId(),                           
                            acidev.getTimestamp(),                            
                            acidev.getSrcHostname()));
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
                response = new GetEventsTimeframeResponse(oldest, newest);
                return response;
            } else {
                response = new GetEventsTimeframeResponse(null, null);
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
        ArrayList<GetHttpStatusResponse> httpResponseList = new ArrayList<GetHttpStatusResponse>();
        TypedQuery query;
      
        try {
            //query =  em.createNamedQuery(AcidEvent.findAll); //createQuery("SELECT d FROM Device d ");
            //we use list to avoid "not found" exception
            extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (!extraDataparamsList.isEmpty()) {
                extraDataList = new ArrayList<>();

                for (ExtraData extra : extraDataparamsList) {

                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);
                    if (severity) {
                        if (currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL")) {
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
                    if (!srcHost.equals("any")) {
                        checkHostName = srcHost;
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        if (relatedAcidEvent.getSrcHostname().equals(checkHostName)
                                && relatedAcidEvent.getTimestamp().getTime() >= startDate
                                && relatedAcidEvent.getTimestamp().getTime() <= endDate) {

                            GetAcidEventsListResponse acideventResponse = new GetAcidEventsListResponse(
                                    relatedAcidEvent.getId(),
                                    relatedAcidEvent.getDeviceId(),
                                    relatedAcidEvent.getTimestamp(),
                                    relatedAcidEvent.getSrcHostname());

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
                                            loadvalues[2].trim(),
                                            null,
                                            null,
                                            acideventResponse
                                    ));
                                }
                                response.setExtraData(extraDataList);
                            } else if (userDataValue.equals("Total Processes")) {
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
                                response.setExtraData(extraDataList);
                            } else if (userDataValue.equals("Current Users")) {
                                String values = "";
                                if (extra.getUserdata4().contains("users currently")) {
                                    values = extra.getUserdata4();
                                } else if (extra.getUserdata5().contains("users currently")) {
                                    values = extra.getUserdata5();
                                }

                                if (!values.equals("")) {
                                    // e.g.USERS OK - 0 users currently logged in 
                                    if (values.contains("- ") && values.contains(" users")
                                            && extra.getUserdata1().contains(" ")) {
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
                                response.setExtraData(extraDataList);
                            } else if (userDataValue.equals("HTTP")) {

                                String values = "";
                                /* Userdata 4 gets populated when 3xx http response */
                                if (extra.getUserdata4().contains("HTTP OK")) {
                                    values = extra.getUserdata4();
                                    /* Userdata  gets populated when 2xx http response */
                                } else if (extra.getUserdata5().contains("HTTP OK")) {
                                    values = extra.getUserdata5();
                                } else {
                                    values = "0";
                                }

                                if (!values.equals("")) {
                                    // e.g.USERS OK - 0 users currently logged in 
                                    if (values.contains("- ") && values.contains("in ")
                                            && values.contains(" bytes") && values.contains(" second")) {
                                        String bytes = values.substring(values.lastIndexOf("- ") + 2, values.lastIndexOf(" bytes"));
                                        String seconds = values.substring(values.lastIndexOf("in ") + 2, values.lastIndexOf(" second"));

                                        double throuput = Double.parseDouble(bytes) / Double.parseDouble(seconds) / 1000;

                                        httpResponseList.add(new GetHttpStatusResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                String.format("%.2f", throuput),
                                                acideventResponse
                                        ));
                                    } else if (values.equals("0")) {
                                        httpResponseList.add(new GetHttpStatusResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                "0",
                                                acideventResponse
                                        ));
                                    }
                                }
                                response.setExtraData(httpResponseList);
                            }
                        }
                    }
                }
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
            
            response = new GetExtraDataListResponse(
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
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkLoadResponse response = new GetNetworkLoadResponse();        
        ArrayList <GetNetworkLoadListResponse> networkloadList = new ArrayList<GetNetworkLoadListResponse>();
        CSVReader reader = null;
        String netflowCsv = "C:\\Users\\ispais\\Documents\\cipsec\\flowsAllFixed.csv";
        
        try{
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            
            Map <String,Double> netvals = new LinkedHashMap<>();
            while ((line=reader.readNext()) != null && !finished){
                if(!line[0].equals("Summary"))
                {                    
                    Float duration = Float.parseFloat(line[4]);
                    Float inpackets = Float.parseFloat(line[13]);                    
                    double pktps = duration > 0 ? inpackets/duration : 0.01;
                                        
                    if(netvals.containsKey(line[2]))
                    {
                        netvals.put(line[2], (netvals.get(line[2]) + pktps) /2);
                    }
                    else
                    {
                        netvals.put(line[2],pktps);
                    }    
                }
                else{
                    finished = true;
                }
            }    
            
            for (Map.Entry pair : netvals.entrySet()) {
                networkloadList.add(new GetNetworkLoadListResponse(
                        pair.getKey().toString(),                        
                        (double)pair.getValue()));
            }                
        }catch(IOException e){
            e.printStackTrace();
        }
        
        response.setNetworkLoad(networkloadList);
        
        return response;
    }//end getNetworkLoad
     
    public GetNetworkConnsResponse getNetworkConnections() {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkConnsResponse response = new GetNetworkConnsResponse();        
        ArrayList <GetNetworkConnsListResponse> networkconnsList = new ArrayList<>();
        CSVReader reader = null;
        String netflowCsv = "C:\\Users\\ispais\\Documents\\cipsec\\flowsAllFixed.csv";
        
        try{
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;            
            Map <String,Integer> netvals = new LinkedHashMap<>();
            
            while ((line=reader.readNext()) != null && !finished){
                if(!line[0].equals("Summary"))
                {  //Read flow's start datetime e.g. 2017-06-26 6:17:17
                   String date = line[1].substring(0,line[1].indexOf(":")+3);
                   
                   if(netvals.containsKey(date))
                    {
                        netvals.put(date, netvals.get(date) + 1);
                    }
                    else
                    {
                        netvals.put(date,1);
                    } 
                }
                else{
                    finished = true;
                }
            }                        
            for (Map.Entry pair : netvals.entrySet()) {
                networkconnsList.add(new GetNetworkConnsListResponse(
                        pair.getKey().toString(),                        
                        (int)pair.getValue()));
            }                
        }catch(IOException e){
            e.printStackTrace();
        }
        
        response.setNetworkConnections(networkconnsList);        
        return response;
    }//end getNetworkConnections
    
    public GetNetworkSpeedResponse getNetworkSpeed() {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkSpeedResponse response = new GetNetworkSpeedResponse();        
        ArrayList <GetNetworkSpeedListResponse> networkspeedList = new ArrayList<>();
        CSVReader reader = null;
        String netflowCsv = "C:\\Users\\ispais\\Documents\\cipsec\\flowsAllFixed.csv";
        
        try{
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            
            Map <String,Double> netvals = new LinkedHashMap<>();
            while ((line=reader.readNext()) != null && !finished){
                if(!line[0].equals("Summary"))
                {                    
                    Float duration = Float.parseFloat(line[4]);
                    Float inbytes = Float.parseFloat(line[14]);                    
                    double bps = duration > 0 ? inbytes/duration : 0.01;
                                        
                    if(netvals.containsKey(line[2]))
                    {
                        netvals.put(line[2], (netvals.get(line[2]) + bps) /2);
                    }
                    else
                    {
                        netvals.put(line[2],bps);
                    }    
                }
                else{
                    finished = true;
                }
            }                      
            for (Map.Entry pair : netvals.entrySet()) {
                networkspeedList.add(new GetNetworkSpeedListResponse(
                        pair.getKey().toString(),                        
                        (double)pair.getValue()));
            }                
        }catch(IOException e){
            e.printStackTrace();
        }        
        response.setNetworkSpeed(networkspeedList);        
        return response;
    }//end getNetworkSpeed
     
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
    
    static <K, V> void orderByValue(LinkedHashMap<K, V> m, final Comparator<? super K> c) {
        List<Map.Entry<K, V>> entries = new ArrayList<>(m.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> lhs, Map.Entry<K, V> rhs) {
                return c.compare(lhs.getKey(), rhs.getKey());
            }
        });
        m.clear();
        for (Map.Entry<K, V> e : entries) {
            m.put(e.getKey(), e.getValue());
        }
    }
}
