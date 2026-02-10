package com.example.warehouse_metrics_dashboard.service;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.LiftDTO;
import com.example.warehouse_metrics_dashboard.dto.TransporterDTO;
import com.example.warehouse_metrics_dashboard.dto.PbLaneDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DashboardService {

    String getLDDs() throws SQLException, IOException, ClassNotFoundException;
    String getDbhFillLevel() throws SQLException, IOException, ClassNotFoundException;
    String getEpbLevel() throws SQLException, IOException, ClassNotFoundException;
    String getReleaseProblems() throws SQLException, IOException, ClassNotFoundException;
    String getOSR1FillLevel() throws SQLException, IOException, ClassNotFoundException;
    String getOSR2FillLevel() throws SQLException, IOException, ClassNotFoundException;
    String getWip() throws SQLException, IOException, ClassNotFoundException;
    String getP2pPicks() throws SQLException, IOException, ClassNotFoundException;
    String getDbhPicks() throws SQLException, IOException, ClassNotFoundException;
    List<DbhLaneDTO> getLDDsDBH() throws SQLException, IOException, ClassNotFoundException;
    List<TransporterDTO> getTransportersDown() throws SQLException, IOException, ClassNotFoundException;
    List<LiftDTO>  getLiftsDown() throws SQLException, IOException, ClassNotFoundException;
    List<PbLaneDTO> getPbLanes() throws ClassNotFoundException;
}
