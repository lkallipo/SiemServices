/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "extra_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExtraData.findAll", query = "SELECT e FROM ExtraData e"),
    @NamedQuery(name = "ExtraData.findByFilename", query = "SELECT e FROM ExtraData e WHERE e.filename = :filename"),
    @NamedQuery(name = "ExtraData.findByUsername", query = "SELECT e FROM ExtraData e WHERE e.username = :username"),
    @NamedQuery(name = "ExtraData.findByPassword", query = "SELECT e FROM ExtraData e WHERE e.password = :password"),
    @NamedQuery(name = "ExtraData.findByUserdata1", query = "SELECT e FROM ExtraData e WHERE e.userdata1 = :userdata1"),
    @NamedQuery(name = "ExtraData.findByUserdata2", query = "SELECT e FROM ExtraData e WHERE e.userdata2 = :userdata2"),
    @NamedQuery(name = "ExtraData.findByUserdata3", query = "SELECT e FROM ExtraData e WHERE e.userdata3 = :userdata3"),
    @NamedQuery(name = "ExtraData.findByUserdata4", query = "SELECT e FROM ExtraData e WHERE e.userdata4 = :userdata4"),
    @NamedQuery(name = "ExtraData.findByUserdata5", query = "SELECT e FROM ExtraData e WHERE e.userdata5 = :userdata5"),
    @NamedQuery(name = "ExtraData.findByUserdata6", query = "SELECT e FROM ExtraData e WHERE e.userdata6 = :userdata6"),
    @NamedQuery(name = "ExtraData.findByUserdata7", query = "SELECT e FROM ExtraData e WHERE e.userdata7 = :userdata7"),
    @NamedQuery(name = "ExtraData.findByUserdata8", query = "SELECT e FROM ExtraData e WHERE e.userdata8 = :userdata8"),
    @NamedQuery(name = "ExtraData.findByUserdata9", query = "SELECT e FROM ExtraData e WHERE e.userdata9 = :userdata9")})
public class ExtraData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    /*@Basic(optional = false)
    @NotNull
    @Lob*/
    //@Column(name = "event_id")
    private byte[] eventId;
    @Size(max = 256)
    @Column(name = "filename")
    private String filename;
    @Size(max = 64)
    @Column(name = "username")
    private String username;
    @Size(max = 64)
    @Column(name = "password")
    private String password;
    @Size(max = 1024)
    @Column(name = "userdata1")
    private String userdata1;
    @Size(max = 1024)
    @Column(name = "userdata2")
    private String userdata2;
    @Size(max = 1024)
    @Column(name = "userdata3")
    private String userdata3;
    @Size(max = 1024)
    @Column(name = "userdata4")
    private String userdata4;
    @Size(max = 1024)
    @Column(name = "userdata5")
    private String userdata5;
    @Size(max = 1024)
    @Column(name = "userdata6")
    private String userdata6;
    @Size(max = 1024)
    @Column(name = "userdata7")
    private String userdata7;
    @Size(max = 1024)
    @Column(name = "userdata8")
    private String userdata8;
    @Size(max = 1024)
    @Column(name = "userdata9")
    private String userdata9;
    @Lob
    @Size(max = 65535)
    @Column(name = "data_payload")
    private String dataPayload;
    @Lob
    @Column(name = "binary_data")
    private byte[] binaryData;
    
    @OneToOne (optional = true)
    @JoinColumn(name = "event_id",referencedColumnName ="id")
    @MapsId
    private AcidEvent relatedEvent;   
    

    public ExtraData() {
    }

    public ExtraData(byte[] eventId) {
        this.eventId = eventId;
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
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExtraData)) {
            return false;
        }
        ExtraData other = (ExtraData) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.ExtraData[ eventId=" + eventId + " ]";
    }
    
}
