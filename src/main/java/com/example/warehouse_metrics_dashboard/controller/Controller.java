package com.example.warehouse_metrics_dashboard.controller;

import com.example.warehouse_metrics_dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.SQLException;

@org.springframework.stereotype.Controller
public class Controller {

    private final DashboardService dashboardService;

    @Autowired
    public Controller(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String home(Model model) throws SQLException, IOException, ClassNotFoundException {
        model.addAttribute("ldd", dashboardService.getLDDs());
        model.addAttribute("dbh", dashboardService.getArea1FillLevel());
        model.addAttribute("epb", dashboardService.getArea2FillLevel());
        model.addAttribute("release", dashboardService.getReleaseProblems());
        model.addAttribute("osr1", dashboardService.getArea3FillLevel());
        model.addAttribute("osr2", dashboardService.getArea4FillLevel());
        model.addAttribute("wip", dashboardService.getWip());
        model.addAttribute("p2p", dashboardService.getP2pPicks());
        model.addAttribute("dbhpicks", dashboardService.getDbhPicks());
        model.addAttribute("lanes", dashboardService.getPbLanes());
        model.addAttribute("transporters",dashboardService.getTransportersDown());
        model.addAttribute("lifts",dashboardService.getLiftsDown());
        model.addAttribute("lddsdbh",dashboardService.getLDDsDBH());
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

