/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.ossimsiem;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkallipolitis
 */
@Entity
@Table(name = "schema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schema.findAll", query = "SELECT s FROM Schema s"),
    @NamedQuery(name = "Schema.findByVseq", query = "SELECT s FROM Schema s WHERE s.vseq = :vseq"),
    @NamedQuery(name = "Schema.findByCtime", query = "SELECT s FROM Schema s WHERE s.ctime = :ctime")})
public class Schema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "vseq")
    private Integer vseq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    public Schema() {
    }

    public Schema(Integer vseq) {
        this.vseq = vseq;
    }

    public Schema(Integer vseq, Date ctime) {
        this.vseq = vseq;
        this.ctime = ctime;
    }

    public Integer getVseq() {
        return vseq;
    }

    public void setVseq(Integer vseq) {
        this.vseq = vseq;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vseq != null ? vseq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schema)) {
            return false;
        }
        Schema other = (Schema) object;
        if ((this.vseq == null && other.vseq != null) || (this.vseq != null && !this.vseq.equals(other.vseq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aegis.ossimsiem.Schema[ vseq=" + vseq + " ]";
    }
    
}
