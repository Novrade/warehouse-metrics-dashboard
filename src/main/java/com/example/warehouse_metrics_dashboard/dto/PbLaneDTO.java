package com.example.warehouse_metrics_dashboard.dto;

public class PbLaneDTO {

    private String name;
    private String unitId;
    private String fill;
    private String state;

    public PbLaneDTO(String name, String unitId, String fill, String state) {
        this.name = name;
        this.unitId = unitId;
        this.fill = fill;
        this.state = state;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PbLaneDTO() {
    }

    public String getName() {
        return name;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getFill() {
        return fill;
    }

    public String getState() {
        return state;
    }
}
