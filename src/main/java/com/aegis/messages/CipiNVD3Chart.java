/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegis.messages;

import java.util.Collections;
import java.util.List;

/**
 * used to produce nvd3-specific format of json response
 * 
 * @author lkallipolitis
 * 
 */
public class CipiNVD3Chart {

    private List<CipiSeries> chart;

    public CipiNVD3Chart() {
        this.chart = Collections.EMPTY_LIST;
    }

    public List<CipiSeries> getChart() {
        return chart;
    }

    public void setChart(List<CipiSeries> chart) {
        this.chart = chart;
    }
 
}
