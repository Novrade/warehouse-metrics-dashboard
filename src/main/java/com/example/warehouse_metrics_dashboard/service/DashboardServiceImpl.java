package com.example.warehouse_metrics_dashboard.service;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.LiftDTO;
import com.example.warehouse_metrics_dashboard.dto.PbLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.TransporterDTO;
import com.example.warehouse_metrics_dashboard.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository repo;

    public DashboardServiceImpl(DashboardRepository repo) {
        this.repo = repo;
    }

    @Override
    public String getLDDs() {
        return repo.getLDDs();
    }

    @Override
    public String getArea1FillLevel() {
        return repo.getArea1FillLevel();
    }

    @Override
    public String getArea2FillLevel() {
        return repo.getArea2FillLevel();
    }

    @Override
    public String getReleaseProblems() {
        return repo.getReleaseProblems();
    }

    @Override
    public String getArea3FillLevel() {
        return repo.getArea3FillLevel();
    }

    @Override
    public String getArea4FillLevel() {
        return repo.getArea4FillLevel();
    }

    @Override
    public List<PbLaneDTO> getPbLanes() {
        return repo.getPbLanes();
    }

    @Override
    public String getWip() {
        return repo.getWip();
    }

    @Override
    public String getP2pPicks() {
        return repo.getP2pPicks();
    }

    @Override
    public String getDbhPicks() {
        return repo.getDbhPicks();
    }

    @Override
    public List<DbhLaneDTO> getLDDsDBH() {
        return repo.getLDDsDBH();
    }

    @Override
    public List<TransporterDTO> getTransportersDown() {
        return repo.getTransportersDown();
    }

    @Override
    public List<LiftDTO> getLiftsDown() {
        return repo.getLiftsDown();
    }
}


