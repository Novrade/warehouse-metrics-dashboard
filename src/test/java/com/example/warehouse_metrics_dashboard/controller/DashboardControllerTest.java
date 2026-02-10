package com.example.warehouse_metrics_dashboard.controller;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @MockitoBean
    private DashboardService dashboardService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void dashboardAllDataRetrieved200() throws Exception {
        DbhLaneDTO dbhLaneDTO = new DbhLaneDTO("1","transporter1","20");

        Mockito.when(dashboardService.getLDDs()).thenReturn("20");
        Mockito.when(dashboardService.getWip()).thenReturn("10");
        Mockito.when(dashboardService.getLDDsDBH()).thenReturn(List.of(dbhLaneDTO));

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("homes"))
                .andExpect(model().attributeExists("lddCount"))
                .andExpect(model().attributeExists("wip"))
                .andExpect(model().attributeExists("dbhLdds"));

        Mockito.verify(dashboardService).getLDDsDBH();
        Mockito.verify(dashboardService).getLDDs();
        Mockito.verify(dashboardService).getWip();
    }

}