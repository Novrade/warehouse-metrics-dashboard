package com.example.warehouse_metrics_dashboard.repository;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.LiftDTO;
import com.example.warehouse_metrics_dashboard.dto.PbLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.TransporterDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DashboardRepository {

    String getLDDs();
    String getArea1FillLevel();
    String getArea2FillLevel();
    String getReleaseProblems();
    String getArea3FillLevel();
    String getArea4FillLevel();
    List<PbLaneDTO> getPbLanes();
    String getWip();
    String getP2pPicks();
    String getDbhPicks();
    List<DbhLaneDTO> getLDDsDBH();
    List<TransporterDTO> getTransportersDown();
    List<LiftDTO> getLiftsDown();
}

