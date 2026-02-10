package com.example.warehouse_metrics_dashboard.dto;

public class LiftDTO {

    private String name;
    private String osr;
    private String aisle;

    public LiftDTO(String name, String osr, String aisle ) {
        this.aisle = aisle;
        this.osr = osr;
        this.name = name;
    }

    public LiftDTO() {
    }

    public String getAisle() {
        return aisle;
    }

    public String getOsr() {
        return osr;
    }

    public String getName() {
        return name;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public void setOsr(String osr) {
        this.osr = osr;
    }

    public void setName(String name) {
        this.name = name;
    }
}

