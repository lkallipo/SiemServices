/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.controllers;

import com.aegis.messages.CipiNVD3Chart;
import com.aegis.messages.CipiSeries;
import com.aegis.messages.CipiValuePair;
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
import com.aegis.messages.SimpleResponse;
import com.aegis.ossimsiem.AcidEvent;
import com.aegis.ossimsiem.Device;
import com.aegis.ossimsiem.ExtraData;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.stream.Stream;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lkallipolitis
 */
public class ServicesHandler {

    private final EntityManager em;
    private Properties props;
    final static Logger LOGGER = Logger.getLogger("NetFlowAgent");

    public ServicesHandler(EntityManager em) {
        this.em = em;
        String resourceName = "config.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        props = new Properties();
        InputStream resourceStream = loader.getResourceAsStream(resourceName);
        try {
            props.load(resourceStream);
        } catch (IOException ex) {
            //Logger.getLogger(ServicesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
            //extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e WHERE e.userdata1 LIKE :userdata1").setParameter("userdata1", "%" + userDataValue +"%").getResultList();
            if (!extraDataparamsList.isEmpty()) {
                extraDataList = new ArrayList<>();
                response.setExtraData(extraDataList);

                for (ExtraData extra : extraDataparamsList) {

                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);

                    /* XL-SIEM adaptation*/
                    int severitypos = 0;
                    if (userDataValue.equals("Current Load")) {
                        severitypos = extra.getUserdata1().indexOf("Current Load;") + 13;
                    } else if (userDataValue.equals("Total Processes")) {
                        severitypos = extra.getUserdata1().indexOf("Total Processes;") + 16;
                    }
                    if (severitypos > 0) {
                        currentEventSeverity = extra.getUserdata1().substring(severitypos, extra.getUserdata1().indexOf(";", severitypos));
                    }

                    if (severity) {
                        if (currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL")) {
                            isWarnOrError = true;
                        }
                    }

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            GetAcidEventsListResponse acideventResponse = new GetAcidEventsListResponse(
                                    extra.getRelatedEvent().getId(),
                                    extra.getRelatedEvent().getDeviceId(),
                                    extra.getRelatedEvent().getTimestamp(),
                                    extra.getRelatedEvent().getSrcHostname());

                            switch (userDataValue) {
                                case "Current Load": {
                                    //if (userDataValue.equals("Current Load")) {
                                    String[] loadvalues = new String[3];
                                    String values = "";
                                    if (extra.getUserdata5().contains("average")) {
                                        values = extra.getUserdata5();
                                    } else if (extra.getUserdata4().contains("average")) {
                                        values = extra.getUserdata4();
                                    } // XL-SIEM database
                                    else if (extra.getUserdata1().contains("average")) {
                                        values = extra.getUserdata1();
                                    }
                                    if (!values.equals("")) {
                                        String loadvalue = values.substring(values.lastIndexOf("average: ") + 9);
                                        loadvalues = loadvalue.split(", ");
                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                //extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                currentEventSeverity,
                                                loadvalues[0],
                                                loadvalues[1],
                                                loadvalues[2].trim(),
                                                null,
                                                null,
                                                acideventResponse
                                        ));
                                    }
                                    response.setExtraData(extraDataList);
                                    break;
                                }
                                /* HCPB Pilot */
                                case "CPU Load": {
                                    //if (userDataValue.equals("Current Load")) {
                                    String[] loadvalues = new String[3];
                                    String values = "";
                                    if (extra.getUserdata3().contains("1m")) {
                                        values = extra.getUserdata3();
                                    }
                                    if (!values.equals("")) {
                                        loadvalues = values.split(", ");
                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                //extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                currentEventSeverity,
                                                loadvalues[0].trim().substring(loadvalues[0].indexOf(": ") + 2, loadvalues[0].indexOf("%")),
                                                loadvalues[1].trim().substring(loadvalues[1].indexOf(": ") + 2, loadvalues[1].indexOf("%")),
                                                loadvalues[2].trim().substring(loadvalues[2].indexOf(": ") + 2, loadvalues[2].indexOf("%")),
                                                null,
                                                null,
                                                acideventResponse
                                        ));
                                    }
                                    response.setExtraData(extraDataList);
                                    break;
                                }
                                case "Total Processes": {
                                    String values = "";
                                    if (extra.getUserdata4().contains("processes")) {
                                        values = extra.getUserdata4();
                                    } else if (extra.getUserdata5().contains("processes")) {
                                        values = extra.getUserdata5();
                                    } // XL-SIEM database
                                    else if (extra.getUserdata1().contains("processes")) {
                                        values = extra.getUserdata1();
                                    }
                                    /*review hack*/
                                    if (currentEventSeverity.equals("CRITICAL")) {
                                        currentEventSeverity = "WARNING";
                                    }
                                    if (!values.equals("")) {
                                        String loadvalue = values.substring(values.lastIndexOf(": ") + 2, values.lastIndexOf(" processes"));

                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                currentEventSeverity,
                                                null,
                                                null,
                                                null,
                                                loadvalue,
                                                null,
                                                acideventResponse
                                        ));
                                    }
                                    response.setExtraData(extraDataList);
                                    break;
                                }
                                case "Current Users": {
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
                                    break;
                                }
                                case "HTTP": {
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
                                    break;
                                }
                                case "Network Connections": {
                                    String values = "";
                                    if (extra.getDataPayload().contains("Network Connections")) {
                                        values = extra.getUserdata2();
                                    }

                                    if (!values.equals("")) {
                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                null,
                                                null,
                                                null,
                                                null,
                                                values,
                                                acideventResponse
                                        ));
                                    }
                                    response.setExtraData(extraDataList);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                return response;
            } else {
                extraDataList = new ArrayList<>();
                response.setExtraData(extraDataList);
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getExtaData

    /*
    * Gets extradata that associated Event happened after startDate
     */
    public CipiNVD3Chart getExtraDataNvd3(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        CipiNVD3Chart response = new CipiNVD3Chart();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetExtraDataListResponse> extraDataList;
        ArrayList<GetHttpStatusResponse> httpResponseList = new ArrayList<GetHttpStatusResponse>();

        List<CipiSeries> cipiSeriesList = new ArrayList<>();
        List<CipiValuePair> load1m = new ArrayList();
        List<CipiValuePair> load5m = new ArrayList();
        List<CipiValuePair> load15m = new ArrayList();

        TypedQuery query;    
        
        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
            //extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e WHERE e.userdata1 LIKE :userdata1").setParameter("userdata1", "%" + userDataValue +"%").getResultList();
            if (!extraDataparamsList.isEmpty()) {
                extraDataList = new ArrayList<>();
                //response.setExtraData(extraDataList);

                for (ExtraData extra : extraDataparamsList) {

                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);

                    /* XL-SIEM adaptation*/
                    int severitypos = 0;
                    if (userDataValue.equals("Current Load")) {
                        severitypos = extra.getUserdata1().indexOf("Current Load;") + 13;
                    } else if (userDataValue.equals("Total Processes")) {
                        severitypos = extra.getUserdata1().indexOf("Total Processes;") + 16;
                    }
                    if (severitypos > 0) {
                        currentEventSeverity = extra.getUserdata1().substring(severitypos, extra.getUserdata1().indexOf(";", severitypos));
                    }

                    if (severity) {
                        if (currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL")) {
                            isWarnOrError = true;
                        }
                    }

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            GetAcidEventsListResponse acideventResponse = new GetAcidEventsListResponse(
                                    extra.getRelatedEvent().getId(),
                                    extra.getRelatedEvent().getDeviceId(),
                                    extra.getRelatedEvent().getTimestamp(),
                                    extra.getRelatedEvent().getSrcHostname());

                            switch (userDataValue) {
                                case "Current Load": {
                                    //if (userDataValue.equals("Current Load")) {
                                    String[] loadvalues = new String[3];
                                    String values = "";
                                    if (extra.getUserdata5().contains("average")) {
                                        values = extra.getUserdata5();
                                    } else if (extra.getUserdata4().contains("average")) {
                                        values = extra.getUserdata4();
                                    } // XL-SIEM database
                                    else if (extra.getUserdata1().contains("average")) {
                                        values = extra.getUserdata1();
                                    }
                                    if (!values.equals("")) {
                                        String loadvalue = values.substring(values.lastIndexOf("average: ") + 9);
                                        loadvalues = loadvalue.split(", ");
                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                //extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                currentEventSeverity,
                                                loadvalues[0],
                                                loadvalues[1],
                                                loadvalues[2].trim(),
                                                null,
                                                null,
                                                acideventResponse
                                        ));
                                    }       //response.setExtraData(extraDataList);
                                    break;
                                }
                                /* HCPB Pilot */
                                case "CPU Load": {                                    
                                    //if (userDataValue.equals("Current Load")) {
                                    String[] loadvalues = new String[3];
                                    String values = "";
                                    if (extra.getUserdata3().contains("1m")) {
                                        values = extra.getUserdata3();
                                    }
                                    if (!values.equals("")) {
                                        loadvalues = values.split(", ");

                                        load1m.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(loadvalues[0].trim().substring(loadvalues[0].indexOf(": ") + 2, loadvalues[0].indexOf("%")))
                                        ));
                                        load5m.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(loadvalues[1].trim().substring(loadvalues[01].indexOf(": ") + 2, loadvalues[1].indexOf("%")))
                                        ));
                                        load15m.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(loadvalues[2].trim().substring(loadvalues[2].indexOf(": ") + 2, loadvalues[2].indexOf("%")))
                                        ));

                                        /*extraDataList.add(new GetExtraDataListResponse(
                                                    extra.getEventId(),
                                                    extra.getDataPayload(),
                                                    //extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                    currentEventSeverity,
                                                    loadvalues[0].trim().substring(loadvalues[0].indexOf(": ") + 2, loadvalues[0].indexOf("%")),
                                                    loadvalues[1].trim().substring(loadvalues[1].indexOf(": ") + 2, loadvalues[1].indexOf("%")),
                                                    loadvalues[2].trim().substring(loadvalues[2].indexOf(": ") + 2, loadvalues[2].indexOf("%")),
                                                    null,
                                                    null,
                                                    acideventResponse
                                            ));*/
                                    }
                                    //response.setChart(cipiSeriesList);
                                    break;
                                }
                                case "Total Processes": {
                                    String values = "";
                                    if (extra.getUserdata4().contains("processes")) {
                                        values = extra.getUserdata4();
                                    } else if (extra.getUserdata5().contains("processes")) {
                                        values = extra.getUserdata5();
                                    } // XL-SIEM database
                                    else if (extra.getUserdata1().contains("processes")) {
                                        values = extra.getUserdata1();
                                    }
                                    /*review hack*/
                                    if (currentEventSeverity.equals("CRITICAL")) {
                                        currentEventSeverity = "WARNING";
                                    }
                                    if (!values.equals("")) {
                                        String loadvalue = values.substring(values.lastIndexOf(": ") + 2, values.lastIndexOf(" processes"));

                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                currentEventSeverity,
                                                null,
                                                null,
                                                null,
                                                loadvalue,
                                                null,
                                                acideventResponse
                                        ));
                                    }
                                    //response.setExtraData(extraDataList);
                                    break;
                                }
                                case "Current Users": {
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
                                    //response.setExtraData(extraDataList);
                                    break;
                                }
                                case "HTTP": {
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
                                    //response.setExtraData(httpResponseList);
                                    break;
                                }
                                case "Network Connections": {
                                    String values = "";
                                    if (extra.getDataPayload().contains("Network Connections")) {
                                        values = extra.getUserdata2();
                                    }

                                    if (!values.equals("")) {
                                        extraDataList.add(new GetExtraDataListResponse(
                                                extra.getEventId(),
                                                extra.getDataPayload(),
                                                extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1),
                                                null,
                                                null,
                                                null,
                                                null,
                                                values,
                                                acideventResponse
                                        ));
                                    }
                                   // response.setExtraData(extraDataList);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                CipiSeries series1m = new CipiSeries("Load1m",load1m);
                CipiSeries series5m = new CipiSeries("Load5m",load5m);
                CipiSeries series15m = new CipiSeries("Load15m",load15m);
                
                cipiSeriesList.add(series1m);
                cipiSeriesList.add(series5m);
                cipiSeriesList.add(series15m);
   
                response.setChart(cipiSeriesList);
                
                return response;
            } else {
                extraDataList = new ArrayList<>();
                //response.setExtraData(extraDataList);
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getExtaData

    /*
    * Gets extradata that associated Event happened after startDate
     */
    public GetNetworkConnsResponse getNetConnectionsdata(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        GetNetworkConnsResponse response = new GetNetworkConnsResponse();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkConnsListResponse> extraDataList = new ArrayList<>();

        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();

            if (!extraDataparamsList.isEmpty()) {

                for (ExtraData extra : extraDataparamsList) {

                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);

                    if (severity) {
                        if (currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL")) {
                            isWarnOrError = true;
                        }
                    }
                   /* AcidEvent relatedAcidEvent = new AcidEvent();
                    try {
                        List<AcidEvent> relatedEvents;
                        relatedEvents = em.createQuery("SELECT a FROM AcidEvent a WHERE a.id = :eventId").setParameter("eventId", extra.getEventId()).getResultList();
                        if (!relatedEvents.isEmpty()) {
                            relatedAcidEvent = relatedEvents.get(0);
                        }
                        /*TypedQuery<AcidEvent> query1;
                    query1 = (TypedQuery) em.createQuery("SELECT a FROM AcidEvent a WHERE a.id = :eventId");
                    query1.setParameter("eventId", extra.getEventId());
                    Object objec = query1.getSingleResult();
                    relatedAcidEvent = query1.getSingleResult();*/
/*
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }*/

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Connections": {
                                    String values = "";
                                    String datetime = "";
                                    if (extra.getDataPayload().contains("Network Connections")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                    }

                                    if (!values.equals("")) {
                                        extraDataList.add(new GetNetworkConnsListResponse(
                                                datetime,
                                                Integer.parseInt(values),
                                                currentEventSeverity));
                                    }
                                    response.setNetworkConnections(extraDataList);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                return response;
            } else {
                response.setNetworkConnections(extraDataList);
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetFlowdata

    
    /*
    * Gets extradata that associated Event happened after startDate
     */
    public CipiNVD3Chart getNetConnectionsdataNvd3(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        CipiNVD3Chart response = new CipiNVD3Chart();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkConnsListResponse> extraDataList = new ArrayList<>();
        
        List<CipiSeries> cipiSeriesList = new ArrayList<>();
        List<CipiValuePair> netconnsList = new ArrayList();  

        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();

            if (!extraDataparamsList.isEmpty()) {

                for (ExtraData extra : extraDataparamsList) {

                    boolean isWarnOrError = false;
                    String currentEventSeverity = extra.getUserdata1().substring(extra.getUserdata1().lastIndexOf(" ") + 1);

                    if (severity) {
                        if (currentEventSeverity.equals("WARNING") || currentEventSeverity.equals("CRITICAL")) {
                            isWarnOrError = true;
                        }
                    }
 
                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Connections": {
                                    String values = "";
                                    String datetime = "";
                                    if (extra.getDataPayload().contains("Network Connections")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                    }

                                    if (!values.equals("")) {                                         
                                        netconnsList.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(values)
                                        ));
                                    }
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                CipiSeries netconnsSeries = new CipiSeries("connections",netconnsList);
                cipiSeriesList.add(netconnsSeries);                
                response.setChart(cipiSeriesList);
                return response;
            } else {
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetFlowdata
    
    
    /*
    * Gets extradata that associated Event happened after startDate
     */
    public GetNetworkLoadResponse getNetLoaddata(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        GetNetworkLoadResponse response = new GetNetworkLoadResponse();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkLoadListResponse> extraDataList;
/* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
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

                    /*AcidEvent relatedAcidEvent = new AcidEvent();
                    try {
                        List<AcidEvent> relatedEvents;
                        relatedEvents = em.createQuery("SELECT a FROM AcidEvent a WHERE a.id = :eventId").setParameter("eventId", extra.getEventId()).getResultList();
                        if (!relatedEvents.isEmpty()) {
                            relatedAcidEvent = relatedEvents.get(0);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }*/

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }


                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Load": {
                                    String values = "";
                                    String datetime = "";
                                    Double packets = 0.0;
                                    if (extra.getDataPayload().contains("Network Load")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                        packets = Double.parseDouble(values.substring(0, values.indexOf(" ")));
                                    }

                                    if (!values.equals("")) {
                                        extraDataList.add(new GetNetworkLoadListResponse(
                                                datetime,
                                                packets,
                                                currentEventSeverity));
                                    }
                                    response.setNetworkLoad(extraDataList);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                return response;
            } else {
                extraDataList = new ArrayList<>();
                response.setNetworkLoad(extraDataList);
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetLoaddata
    
    /*
    * Gets extradata that associated Event happened after startDate
     */
    public CipiNVD3Chart getNetLoaddataNvd3(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        CipiNVD3Chart response = new CipiNVD3Chart();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkLoadListResponse> extraDataList;
        
        List<CipiSeries> cipiSeriesList = new ArrayList<>();
        List<CipiValuePair> netloadList = new ArrayList();         
        
        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        try {
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
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
                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Load": {
                                    String values = "";
                                    String datetime = "";
                                    Double packets = 0.0;
                                    if (extra.getDataPayload().contains("Network Load")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                        packets = Double.parseDouble(values.substring(0, values.indexOf(" ")));
                                    }

                                    if (!values.equals("")) {
                                        
                                        netloadList.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(values.substring(0, values.indexOf(" ")))
                                        ));                                                                               
                                    }
                                    
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                CipiSeries netloadSeries = new CipiSeries("packets/sec",netloadList);
                cipiSeriesList.add(netloadSeries);                
                response.setChart(cipiSeriesList);
                
                return response;
            } else {
                extraDataList = new ArrayList<>();
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetLoaddata

    /*
    * Gets extradata that associated Event happened after startDate
     */
    public GetNetworkSpeedResponse getNetSpeeddata(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        GetNetworkSpeedResponse response = new GetNetworkSpeedResponse();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkSpeedListResponse> extraDataList;
/* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        try {
            //extraDataparamsList = em.createNamedQuery("ExtraData.findByUserdata2").setParameter("userdata2", userDataValue).getResultList();
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
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
                    /*AcidEvent relatedAcidEvent = new AcidEvent();
                    try {
                        List<AcidEvent> relatedEvents;
                        relatedEvents = em.createQuery("SELECT a FROM AcidEvent a WHERE a.id = :eventId").setParameter("eventId", extra.getEventId()).getResultList();
                        if (!relatedEvents.isEmpty()) {
                            relatedAcidEvent = relatedEvents.get(0);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }*/

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Speed": {
                                    String values = "";
                                    String datetime = "";
                                    Double bytes = 0.0;
                                    if (extra.getDataPayload().contains("Network Speed")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                        bytes = Double.parseDouble(values.substring(0, values.indexOf(" ")));
                                    }

                                    if (!values.equals("")) {
                                        extraDataList.add(new GetNetworkSpeedListResponse(
                                                datetime,
                                                bytes,
                                                currentEventSeverity));
                                    }
                                    response.setNetworkSpeed(extraDataList);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                return response;
            } else {
                extraDataList = new ArrayList<>();
                response.setNetworkSpeed(extraDataList);
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetSpeeddata

    /*
    * Gets extradata that associated Event happened after startDate
     */
    public CipiNVD3Chart getNetSpeeddataNvd3(String userDataValue, long startDate, long endDate, String srcHost, Boolean severity) {
        //*********************** Variables ***************************
        CipiNVD3Chart response = new CipiNVD3Chart();
        List<ExtraData> extraDataparamsList;
        ArrayList<GetNetworkLoadListResponse> extraDataList;
        
        List<CipiSeries> cipiSeriesList = new ArrayList<>();
        List<CipiValuePair> netspeedList = new ArrayList(); 
        
 
        /* Have a default because cloud xl-siem database cannot handle big resultset */
        int deviceid = 9; // prototype
        if (!srcHost.equals("any")) {
               //checkHostName = srcHost;
                deviceid = Integer.parseInt(srcHost);
          }
        try {
            extraDataparamsList = em.createQuery("SELECT e FROM ExtraData e inner join e.relatedEvent re WHERE re.deviceId= :deviceId AND e.dataPayload LIKE :dataPayload")
                                    .setParameter("deviceId", deviceid)
                                    .setParameter("dataPayload", "%" + userDataValue + "%")
                                    .getResultList();
            
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

                    if (extra.getRelatedEvent().getId() == null) {
                        continue;
                    }

                    /* Assign checkHostName the name of the event by default
                       but if passed as a parameter then set it to it.
                     */
                    int checkDeviceId = extra.getRelatedEvent().getDeviceId();
                    // HCPB: check device id instead of srcHostname

                    if (!srcHost.equals("any")) {
                        //checkHostName = srcHost;
                        checkDeviceId = Integer.parseInt(srcHost);
                    }

                    if (!severity || (severity && isWarnOrError)) {
                        //if (relatedAcidEvent.getSrcHostname().equals(checkHostName)                       
                        // HCPB: check device id instead of srcHostname
                        if (extra.getRelatedEvent().getDeviceId() == checkDeviceId
                                && extra.getRelatedEvent().getTimestamp().getTime() >= startDate
                                && extra.getRelatedEvent().getTimestamp().getTime() <= endDate) {

                            switch (userDataValue) {
                                case "Network Speed": {
                                    String values = "";
                                    String datetime = "";
                                    Double bytes = 0.0;
                                    if (extra.getDataPayload().contains("Network Speed")) {
                                        values = extra.getUserdata2();
                                        datetime = extra.getUserdata3();
                                        bytes = Double.parseDouble(values.substring(0, values.indexOf(" ")));
                                    }

                                    if (!values.equals("")) {
                                        
                                        netspeedList.add(new CipiValuePair(
                                                extra.getRelatedEvent().getTimestamp(),
                                                Float.parseFloat(values.substring(0, values.indexOf(" ")))
                                        ));
                                    }
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
                CipiSeries netloadSeries = new CipiSeries("bytes/sec",netspeedList);
                cipiSeriesList.add(netloadSeries);                
                response.setChart(cipiSeriesList);
                return response;
            } else {
                extraDataList = new ArrayList<>();
                return response;
            }
        } catch (Exception e) {
        }
        return response;
    }//end getNetSpeeddata
    
    
    /*
    * Gets extradata that associated Event happened after startDate
     */
    public GetExtraDataListResponse getClosestValueByTime(String cipi, long timestamp, String srcHost) {
        //*********************** Variables ***************************
        GetExtraDataListResponse response = new GetExtraDataListResponse();
        ExtraData extra = new ExtraData();

        //*********************** Action ***************************
        try {
            java.util.Date time = new java.util.Date((long) timestamp);
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

    /* 
     *  Returns netflows by reading all nfcapd files with flows captured in
     *  between the given dates
     */
    public GetNetflowResponse getNetFlow(long startDate, long endDate) {

        GetNetflowResponse response = new GetNetflowResponse();
        ArrayList<GetNetflowListResponse> netflowList = new ArrayList<>();
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        final File folder;
        List<String> csvlist = new ArrayList<>();

        try {
            folder = new File(netflowCsv).getCanonicalFile();
            csvlist = listFilesForFolder(folder, startDate, endDate);
        } catch (IOException ex) {
            Logger.getLogger(ServicesHandler.class.getName()).error(ex);
        }

        for (String csvfile : csvlist) {
            System.out.println("Now processing " + csvfile);

            try {
                reader = new CSVReader(new FileReader(netflowCsv + "/" + csvfile));
                String[] line = reader.readNext();
                boolean finished = false;

                while ((line = reader.readNext()) != null && !finished) {
                    if (!line[0].contains("Summary")) {
                        //ts,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,...
//                netflowList.add(new GetNetflowListResponse(
//                        line[0],
//                        line[1],
//                        Float.parseFloat(line[2]),
//                        line[3],
//                        Integer.parseInt(line[5]),
//                        line[4],
//                        Integer.parseInt(line[6]),
//                        line[7],
//                        Integer.parseInt(line[11]),
//                        Integer.parseInt(line[13]),
//                        Integer.parseInt(line[12]),
//                        Integer.parseInt(line[14])));

                        // Date first seen, Date last seen, Duration, Proto,Src IP Addr, Src Port, Dst IP Addr, Dst Port,Packets,Bytes,bps,pps
                        double flowbytes = 0;
                        if (!line[9].contains("M")) {
                            flowbytes = Double.parseDouble(line[9].trim());
                        } else {
                            flowbytes = Double.parseDouble(line[9].trim().substring(0, line[9].trim().indexOf(" M"))) * 1000000;
                        }

                        double bps = 0;
                        if (!line[10].contains("M")) {
                            bps = Double.parseDouble(line[10].trim());
                        } else {
                            bps = Double.parseDouble(line[10].trim().substring(0, line[10].trim().indexOf(" M"))) * 1000000;
                        }

                        netflowList.add(new GetNetflowListResponse(
                                line[0], // Date first seen
                                line[1], // Date last seen
                                Float.parseFloat(line[2].trim()), //Duration
                                line[4].trim(), //Src IP Addr
                                Integer.parseInt(line[5].trim()), //Src Port
                                line[6].trim(), //Dst IP Addr
                                Integer.parseInt(line[7].trim().replace(".", "")), //Dst Port
                                line[3].trim(), //Proto
                                Integer.parseInt(line[8].trim()), // Packets
                                flowbytes, //Bytes
                                (int) bps, //bps
                                Integer.parseInt(line[11].trim())) //pps
                        );

                        /* netflowList.add(new GetNetflowListResponse(
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
                        Integer.parseInt(line[16])));*/
                    } else {
                        finished = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setNetflowList(netflowList);
        return response;
    }//end getNetFlow

    public GetNetworkLoadResponse getNetworkLoad(String srcHost) {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkLoadResponse response = new GetNetworkLoadResponse();
        ArrayList<GetNetworkLoadListResponse> networkloadList = new ArrayList<GetNetworkLoadListResponse>();
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;

            Map<String, Double> netvals = new LinkedHashMap<>();
            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].startsWith("Summary")) {
                    Float duration = Float.parseFloat(line[2].trim());
                    Float inpackets = Float.parseFloat(line[8].trim());
                    double pktps = duration > 0.009 ? inpackets / duration : inpackets;

                    if (netvals.containsKey(line[1].trim())) {
                        netvals.put(line[1].trim(), (netvals.get(line[1].trim()) + pktps) / 2);
                    } else {
                        netvals.put(line[1].trim(), pktps);
                    }
                } else {
                    finished = true;
                }
            }

            double nload;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nload = (double) pair.getValue();
                if (nload <= 900) {
                    nseverity = "OK";
                } else if (nload > 900 && nload < 1500) {
                    nseverity = "WARNING";
                } else // nload >1500
                {
                    nseverity = "CRITICAL";
                }
                networkloadList.add(new GetNetworkLoadListResponse(
                        pair.getKey().toString(),
                        nload,
                        nseverity));
                //LOGGER. info(srcHost + "; Network Load;" +  nseverity +";NLOAD " + nload +" at " + pair.getKey().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setNetworkLoad(networkloadList);

        return response;
    }//end getNetworkLoad

    public SimpleResponse logNetworkLoad(String srcHost) {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr             
        CSVReader reader = null;
        SimpleResponse response = new SimpleResponse("unknown", "unknown");
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;

            Map<String, Double> netvals = new LinkedHashMap<>();
            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].equals("Summary")) {
                    Float duration = Float.parseFloat(line[2]);
                    Float inpackets = Float.parseFloat(line[11]);
                    double pktps = duration > 0.009 ? inpackets / duration : inpackets;

                    if (netvals.containsKey(line[1])) {
                        netvals.put(line[1], (netvals.get(line[1]) + pktps) / 2);
                    } else {
                        netvals.put(line[1], pktps);
                    }
                } else {
                    finished = true;
                }
            }
            double nload;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nload = (double) pair.getValue();
                if (nload <= 900) {
                    nseverity = "OK";
                } else if (nload > 900 && nload < 1500) {
                    nseverity = "WARNING";
                } else // nload >1500
                {
                    nseverity = "CRITICAL";
                }
                LOGGER.info(srcHost + "; Network Load;" + nseverity + ";Network Load " + nload + " packets/sec at " + pair.getKey().toString());
                response.setMessage("Network Load Logged Successfully");
                response.setStatus("success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }//end logNetworkLoad

    public GetNetworkConnsResponse getNetworkConnections() {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkConnsResponse response = new GetNetworkConnsResponse();
        ArrayList<GetNetworkConnsListResponse> networkconnsList = new ArrayList<>();
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            Map<String, Integer> netvals = new LinkedHashMap<>();

            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].equals("Summary")) {  //Read flow's start datetime e.g. 2017-06-26 6:17:17
                    String date = line[0].substring(0, line[0].indexOf(":") + 3);

                    if (netvals.containsKey(date)) {
                        netvals.put(date, netvals.get(date) + 1);
                    } else {
                        netvals.put(date, 1);
                    }
                } else {
                    finished = true;
                }
            }
            int nconns;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nconns = (int) pair.getValue();
                if (nconns <= 2) {
                    nseverity = "OK";
                } else if (nconns > 2 && nconns < 4) {
                    nseverity = "WARNING";
                } else // nconns >4
                {
                    nseverity = "CRITICAL";
                }
                networkconnsList.add(new GetNetworkConnsListResponse(
                        pair.getKey().toString(),
                        nconns,
                        nseverity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setNetworkConnections(networkconnsList);
        return response;
    }//end getNetworkConnections

    public SimpleResponse logNetworkConnections(String srcHost) {
        //ts,ts,te,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        SimpleResponse response = new SimpleResponse("unknown", "unknown");
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;
            Map<String, Integer> netvals = new LinkedHashMap<>();

            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].equals("Summary")) {  //Read flow's start datetime e.g. 2017-06-26 6:17:17
                    String date = line[0].substring(0, line[0].indexOf(":") + 3);

                    if (netvals.containsKey(date)) {
                        netvals.put(date, netvals.get(date) + 1);
                    } else {
                        netvals.put(date, 1);
                    }
                } else {
                    finished = true;
                }
            }
            int nconns;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nconns = (int) pair.getValue();
                if (nconns <= 2) {
                    nseverity = "OK";
                } else if (nconns > 2 && nconns < 4) {
                    nseverity = "WARNING";
                } else // nconns >4
                {
                    nseverity = "CRITICAL";
                }

                LOGGER.info(srcHost + "; Network Connections;" + nseverity + ";Network Connections " + nconns + " at " + pair.getKey().toString());
                response.setMessage("Network Connections Logged Successfully");
                response.setStatus("success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }//end logNetworkConnections   

    public GetNetworkSpeedResponse getNetworkSpeed() {
        //ts,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        GetNetworkSpeedResponse response = new GetNetworkSpeedResponse();
        ArrayList<GetNetworkSpeedListResponse> networkspeedList = new ArrayList<>();
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;

            Map<String, Double> netvals = new LinkedHashMap<>();
            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].equals("Summary")) {
                    Float duration = Float.parseFloat(line[2]);
                    Float inbytes = Float.parseFloat(line[12]);
                    double bps = duration > 0.009 ? inbytes / duration : inbytes;

                    if (netvals.containsKey(line[1])) {
                        netvals.put(line[1], (netvals.get(line[1]) + bps) / 2);
                    } else {
                        netvals.put(line[1], bps);
                    }
                } else {
                    finished = true;
                }
            }
            double nspeed;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nspeed = (double) pair.getValue();
                if (nspeed > 2) {
                    nseverity = "OK";
                } else if (nspeed > 1.71 && nspeed < 2) {
                    nseverity = "WARNING";
                } else // nspeed < 2
                {
                    nseverity = "CRITICAL";
                }
                networkspeedList.add(new GetNetworkSpeedListResponse(
                        pair.getKey().toString(),
                        nspeed,
                        nseverity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setNetworkSpeed(networkspeedList);
        return response;
    }//end getNetworkSpeed

    public SimpleResponse logNetworkSpeed(String srcHost) {
        //ts,te,td,sa,da,sp,dp,pr,flg,fwd,stos,ipkt,ibyt,opkt,obyt,in,out,sas,das,smk,dmk,dtos,dir,nh,nhb,svln,dvln,ismc,odmc,idmc,osmc,mpls1,mpls2,mpls3,mpls4,mpls5,mpls6,mpls7,mpls8,mpls9,mpls10,cl,sl,al,ra,eng,exid,tr
        SimpleResponse response = new SimpleResponse("unknown", "unknown");
        CSVReader reader = null;
        String netflowCsv = props.getProperty("netflowcsv");

        try {
            reader = new CSVReader(new FileReader(netflowCsv));
            String[] line = reader.readNext();
            boolean finished = false;

            Map<String, Double> netvals = new LinkedHashMap<>();
            while ((line = reader.readNext()) != null && !finished) {
                if (!line[0].equals("Summary")) {
                    Float duration = Float.parseFloat(line[2]);
                    Float inbytes = Float.parseFloat(line[12]);
                    double bps = duration > 0.009 ? inbytes / duration : inbytes;

                    if (netvals.containsKey(line[1])) {
                        netvals.put(line[1], (netvals.get(line[1]) + bps) / 2);
                    } else {
                        netvals.put(line[1], bps);
                    }
                } else {
                    finished = true;
                }
            }
            double nspeed;
            String nseverity = "";
            for (Map.Entry pair : netvals.entrySet()) {
                nspeed = (double) pair.getValue();
                if (nspeed > 2) {
                    nseverity = "OK";
                } else if (nspeed > 1.71 && nspeed < 2) {
                    nseverity = "WARNING";
                } else // nspeed < 2
                {
                    nseverity = "CRITICAL";
                }
                LOGGER.info(srcHost + "; Network Speed;" + nseverity + ";Network Speed " + nspeed + " bytes/sec at " + pair.getKey().toString());
                response.setMessage("Network Speed Logged Successfully");
                response.setStatus("success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }//end logNetworkSpeed

    private static String getIpfromBytes(byte[] byteip) {
        if (byteip != null && byteip.length > 1) {
            try {
                return InetAddress.getByAddress(byteip).getHostAddress();
            } catch (UnknownHostException ex) {
                LOGGER.error(null, ex);
                return "";
            }
        } else {
            return "";
        }
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

    /* Retuns all files in given folder with date in name between given dates */
    public List<String> listFilesForFolder(final File folder, long startDate, long endDate) {
        List<String> csvfiles = new ArrayList<>();

        SimpleDateFormat sdfstart = new SimpleDateFormat("yyyyMMddHHmm");
        sdfstart.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeStart = sdfstart.format(startDate);
        SimpleDateFormat sdfend = new SimpleDateFormat("yyyyMMddHHmm");
        sdfend.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeEnd = sdfstart.format(endDate);

        System.out.println("Start and End " + timeStart + "  " + timeEnd);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, startDate, endDate);
            } else {
                String fileName = fileEntry.getName();
                System.out.println("Processing file: " + fileName);
                if (!fileName.contains("nfcapd")) {
                    continue;
                }
                String fileDate = fileName.substring(fileName.indexOf(".") + 1, fileName.indexOf(".csv"));
                //Long fileDateLong = Long.parseLong(fileDate);
                //System.out.println("Start    End    fileDateLong " + timeStart + "  " + timeEnd + "  " + fileDateLong);  
                if (timeStart.compareTo(fileDate) <= 0 && fileDate.compareTo(timeEnd) <= 0) {
                    csvfiles.add(fileEntry.getName());
                }
                //System.out.println(fileEntry.getName());
            }
        }
        return csvfiles;
    }

}
