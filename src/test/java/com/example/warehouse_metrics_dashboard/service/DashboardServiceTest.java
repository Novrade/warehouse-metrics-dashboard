package com.example.warehouse_metrics_dashboard.service;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.LiftDTO;
import com.example.warehouse_metrics_dashboard.dto.PbLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.TransporterDTO;
import com.example.warehouse_metrics_dashboard.repository.DashboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DashboardServiceTest {

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Mock
    private DashboardRepository dashboardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLDDs_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getLDDs()).thenReturn("101");

        String result = dashboardService.getLDDs();

        assertEquals("101",result);
        Mockito.verify(dashboardRepository).getLDDs();
    }

    @Test
    void getArea1FillLevel_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getArea1FillLevel()).thenReturn("101");
        String result = dashboardService.getArea1FillLevel();

        assertEquals("101", result);
        Mockito.verify(dashboardRepository).getArea1FillLevel();
    }

    @Test
    void getArea2FillLevel_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getArea2FillLevel()).thenReturn("101");
        String result = dashboardService.getArea2FillLevel();

        assertEquals("101", result);
        Mockito.verify(dashboardRepository).getArea2FillLevel();
    }

    @Test
    void getArea3FillLevel_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getArea3FillLevel()).thenReturn("101");
        String result = dashboardService.getArea3FillLevel();

        assertEquals("101", result);
        Mockito.verify(dashboardRepository).getArea3FillLevel();
    }

    @Test
    void getArea4FillLevel_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getArea4FillLevel()).thenReturn("101");
        String result = dashboardService.getArea4FillLevel();

        assertEquals("101", result);
        Mockito.verify(dashboardRepository).getArea4FillLevel();
    }

    @Test
    void getWIP_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getWip()).thenReturn("101");
        String result = dashboardService.getWip();

        assertEquals("101", result);

        Mockito.verify(dashboardRepository).getWip();
    }

    @Test
    void getP2PPicks_returnsItemFromRepository() {
        Mockito.when(dashboardRepository.getP2pPicks()).thenReturn("101");
        String result = dashboardService.getP2pPicks();

        assertEquals("101",result);

        Mockito.verify(dashboardRepository).getP2pPicks();
    }

    @Test
    void getDBHPicks_returnsItemFromRepository(){
        Mockito.when(dashboardRepository.getDbhPicks()).thenReturn("101");
        String result = dashboardService.getDbhPicks();

        assertEquals("101",result);

        Mockito.verify(dashboardRepository).getDbhPicks();
    }

    @Test
    void getPBLanes_returnsListFromRepository() {
        PbLaneDTO pbLaneDTO = new PbLaneDTO("lane12","1","10","open");
        Mockito.when(dashboardRepository.getPbLanes()).thenReturn(List.of(pbLaneDTO));
        List<PbLaneDTO> result = dashboardService.getPbLanes();

        assertEquals(1,result.size());
        assertEquals("lane12",result.get(0).getName());
        assertEquals("1",result.get(0).getUnitId());
        assertEquals("10",result.get(0).getFill());
        assertEquals("open",result.get(0).getState());

        Mockito.verify(dashboardRepository).getPbLanes();
    }

    @Test
    void getPBLanes_returnsEmptyList() {
        Mockito.when(dashboardRepository.getPbLanes()).thenReturn(List.of());

        List<PbLaneDTO> result = dashboardService.getPbLanes();
        assertEquals(0,result.size());

        Mockito.verify(dashboardRepository).getPbLanes();
    }
    @Test
    void getTransportersDown_returnsListFromRepository() {
        TransporterDTO transporterDTO = new TransporterDTO("name","open","osr1","position","0");
        Mockito.when(dashboardRepository.getTransportersDown()).thenReturn(List.of(transporterDTO));
        List<TransporterDTO> result = dashboardService.getTransportersDown();

        assertEquals(1,result.size());
        assertEquals("name",result.get(0).getTransporter_name());
        assertEquals("open",result.get(0).getTransporter_state());
        assertEquals("osr1",result.get(0).getOsr());
        assertEquals("position",result.get(0).getTransporter_position());
        assertEquals("0",result.get(0).getToday_orders());

        Mockito.verify(dashboardRepository).getTransportersDown();
    }

    @Test
    void getTransportersDown_returnsEmptyList() {
        Mockito.when(dashboardRepository.getTransportersDown()).thenReturn(List.of());

        List<TransporterDTO> result = dashboardService.getTransportersDown();
        assertEquals(0,result.size());

        Mockito.verify(dashboardRepository).getTransportersDown();
    }

    @Test
    void getLiftsDown_returnsListFromRepository() {
        LiftDTO liftDTO = new LiftDTO("name","osr","aisle");
        Mockito.when(dashboardRepository.getLiftsDown()).thenReturn(List.of(liftDTO));

        List<LiftDTO> result = dashboardService.getLiftsDown();

        assertEquals("name",result.get(0).getName());
        assertEquals("osr",result.get(0).getOsr());
        assertEquals("aisle",result.get(0).getAisle());

        Mockito.verify(dashboardRepository).getLiftsDown();
    }

    @Test
    void getLiftsDown_returnsEmptyList() {
        Mockito.when(dashboardRepository.getLiftsDown()).thenReturn(List.of());

        List<LiftDTO> result = dashboardService.getLiftsDown();
        assertEquals(0,result.size());

        Mockito.verify(dashboardRepository).getLiftsDown();
    }

    @Test
    void getLDDinDBH_returnsDataFromRepository() {
        DbhLaneDTO dbhLaneDTO = new DbhLaneDTO("1","name","3");
        Mockito.when(dashboardRepository.getLDDsDBH()).thenReturn(List.of(dbhLaneDTO));
        List<DbhLaneDTO> result = dashboardService.getLDDsDBH();

        assertEquals("1",result.get(0).getUnitId());
        assertEquals("name",result.get(0).getName());
        assertEquals("3",result.get(0).getLDDsInLane());

        Mockito.verify(dashboardRepository).getLDDsDBH();
    }

    @Test
    void getLDDinDBH_returnsEmptyList() {
        Mockito.when(dashboardRepository.getLDDsDBH()).thenReturn(List.of());

        List<DbhLaneDTO> result = dashboardService.getLDDsDBH();
        assertEquals(0,result.size());

        Mockito.verify(dashboardRepository).getLDDsDBH();
    }
}
