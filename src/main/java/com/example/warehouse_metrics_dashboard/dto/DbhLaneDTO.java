package com.example.warehouse_metrics_dashboard.dto;

public class DbhLaneDTO {
    private String unitId;
    private String name;
    private String LDDsInLane;

    public DbhLaneDTO(String unitId, String name, String LDDs) {
        this.unitId = unitId;
        this.LDDsInLane = LDDs;
        this.name = name;
    }

    public DbhLaneDTO() {
    }

    public String getUnitId() {
        return unitId;
    }

    public String getName() {
        return name;
    }

    public String getLDDsInLane() {
        return LDDsInLane;
    }

    public void setLDDsInLane(String LDDsInLane) {
        this.LDDsInLane = LDDsInLane;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}

