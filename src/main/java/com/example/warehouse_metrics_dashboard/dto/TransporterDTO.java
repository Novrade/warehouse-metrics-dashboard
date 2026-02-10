package com.example.warehouse_metrics_dashboard.dto;

public class TransporterDTO {
    private String transporter_name;
    private String transporter_state;
    private String osr;
    private String transporter_position;
    private String today_orders;


    public TransporterDTO(String transporter_name, String transporter_state, String osr, String transporter_position, String today_orders) {
        this.transporter_name = transporter_name;
        this.transporter_state = transporter_state;
        this.osr = osr;
        this.transporter_position = transporter_position;
        this.today_orders = today_orders;
    }

    public TransporterDTO() {
    }

    public String getTransporter_position() {
        return transporter_position;
    }

    public String getOsr() {
        return osr;
    }

    public String getTransporter_state() {
        return transporter_state;
    }

    public String getTransporter_name() {
        return transporter_name;
    }

    public String getToday_orders() {
        return today_orders;
    }

    public void setTransporter_position(String transporter_position) {
        this.transporter_position = transporter_position;
    }

    public void setTransporter_name(String transporter_name) {
        this.transporter_name = transporter_name;
    }

    public void setTransporter_state(String transporter_state) {
        this.transporter_state = transporter_state;
    }

    public void setOsr(String osr) {
        this.osr = osr;
    }

    public void setToday_orders(String today_orders) {
        this.today_orders = today_orders;
    }
}

