package com.example.warehouse_metrics_dashboard.controller;

import com.example.warehouse_metrics_dashboard.service.DashboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;


    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String home(Model model) {
        model.addAttribute("lddCount", dashboardService.getLDDs());
        model.addAttribute("area1FillLevel", dashboardService.getArea1FillLevel());
        model.addAttribute("area2FillLevel", dashboardService.getArea2FillLevel());
        model.addAttribute("releaseIssues", dashboardService.getReleaseProblems());
        model.addAttribute("area3FillPercent", dashboardService.getArea3FillLevel());
        model.addAttribute("area4FillPercent", dashboardService.getArea4FillLevel());
        model.addAttribute("wip", dashboardService.getWip());
        model.addAttribute("p2pPicks", dashboardService.getP2pPicks());
        model.addAttribute("bufferPicks", dashboardService.getDbhPicks());
        model.addAttribute("lanes", dashboardService.getPbLanes());
        model.addAttribute("transporters", dashboardService.getTransportersDown());
        model.addAttribute("lifts", dashboardService.getLiftsDown());
        model.addAttribute("dbhLdds", dashboardService.getLDDsDBH());

        return "homes";
    }
}

