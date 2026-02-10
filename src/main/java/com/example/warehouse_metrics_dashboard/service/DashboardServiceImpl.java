package com.example.warehouse_metrics_dashboard.service;

import com.example.warehouse_metrics_dashboard.dto.DbhLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.LiftDTO;
import com.example.warehouse_metrics_dashboard.dto.PbLaneDTO;
import com.example.warehouse_metrics_dashboard.dto.TransporterDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final JdbcTemplate wmsJDBCTemplate;
    private final JdbcTemplate wcsJDBCTemplate;
    private final JdbcTemplate srcJDBCTemplate;
    private final JdbcTemplate wcshJDBCTemplate;

    public DashboardServiceImpl(@Qualifier("JDBCTemplateWMS")JdbcTemplate wmsJDBCTemplate, @Qualifier("JDBCTemplateWCS")JdbcTemplate wcsJDBCTemplate,
                                @Qualifier("JDBCTemplateSRC")JdbcTemplate srcJDBCTemplate, @Qualifier("JDBCTemplateWCSH")JdbcTemplate wcshJDBCTemplate) {
        this.wmsJDBCTemplate = wmsJDBCTemplate;
        this.wcsJDBCTemplate = wcsJDBCTemplate;
        this.srcJDBCTemplate = srcJDBCTemplate;
        this.wcshJDBCTemplate = wcshJDBCTemplate;
    }

    @Override
    public String getLDDs() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT count(*) FROM LOGICAL_ORDER WHERE TRUNC(despatch_date) <= TRUNC(sysdate) 
                AND logical_order_status not in ('CANCELED','FINISHED')
                """;
        return wmsJDBCTemplate.queryForList(sql,String.class).get(0);
    }
    @Override
    public String getDbhFillLevel() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT filllevel FROM PICKBUFFER_AREAS""";

        return wcshJDBCTemplate.queryForList(sql,String.class).get(0);
    }

    @Override
    public String getEpbLevel() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
               SELECT filled_percent FROM EMPTY_POCKET_AREA
               """;
        return wcshJDBCTemplate.queryForList(sql,String.class).get(0);
    }


    @Override
    public String getReleaseProblems() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT count(*) FROM ALLOCATION_PROBLEMS WHERE trunc(ldd) <= trunc(sysdate) 
                AND available_stock_count > 0 and release_tries >= 5""";
        return wmsJDBCTemplate.queryForList(sql, String.class).get(0);
    }

    @Override
    public String getOSR1FillLevel() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT COUNT(*) AS "TOTAL TOTES IN OSR" FROM container_locations 
                WHERE cl_osr_id = 1""";
        List<String> result =  srcJDBCTemplate.queryForList(sql, String.class);
        float osr1fill = Float.parseFloat(result.get(0));
        osr1fill = osr1fill / 612213 * 100;
        osr1fill = Math.round(osr1fill * 100) / 100.0f;
        return String.valueOf(osr1fill);
    }

    @Override
    public String getOSR2FillLevel() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT COUNT(*) AS "TOTAL TOTES IN OSR" 
                FROM container_locations WHERE cl_osr_id = 2""";
        List<String> result =  srcJDBCTemplate.queryForList(sql, String.class);
        float osr2fill = Float.parseFloat(result.get(0));
        osr2fill = osr2fill / 37604 * 100;
        osr2fill = Math.round(osr2fill * 100) / 100.0f;
        return String.valueOf(osr2fill);
    }

    @Override
    public List<PbLaneDTO> getPbLanes() throws ClassNotFoundException {
        String sql = """
                select name, unitid, filllevel as fill,case when objectstate = 4 then 'ERROR' when objectstate = 5 then 'JAM' else 'UNKNOWN' END as state from KC_B01_2_PICKBUFFER_LANES_VO where objectstate in (2,3,4) ORDER BY unitid""";
        return wcshJDBCTemplate.query(sql,new BeanPropertyRowMapper<>(PbLaneDTO.class));
    }

    @Override
    public String getWip() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT total_wip AS \"TOTAL WIP\" FROM (SELECT SUM(wpol.quantity)-SUM(coltolog.delivered_qty)AS TOTAL_WIP, SUM(CASE WHEN bu.MANUAL = 1 THEN wpol.quantity- coltolog.delivered_qty WHEN bu.disable_osr = 1 THEN wpol.quantity-coltolog.delivered_qty ELSE 0 END) AS MANUAL_WIP, SUM(CASE WHEN bu.trayable = 1 AND bu.toteable_ki_soft = 0 AND bu.disable_osr = 0 THEN wpol.quantity- coltolog.delivered_qty ELSE 0 END) AS OSR_XXL_WIP FROM warehouse_processing_order wpo JOIN co_header coh ON coh.ID = wpo.customer_order_id JOIN wpo_line wpol ON wpol.wpo_id = wpo.ID JOIN bundle bu ON bu.ID = wpol.bundle_id JOIN item it ON it.ID = bu.item_id JOIN col_to_log_order_line coltolog ON coltolog.log_order_line_id = wpol.ID WHERE wpo.dtype = 'KiLogicalOrderExt' AND coh.type_id <= 6 AND wpo.status NOT IN('FINISHED','CANCELED') AND wpol.status NOT IN ('CANCELED','FINISHED'))";
        return wmsJDBCTemplate.queryForList(sql, String.class).get(0);
    }

    @Override
    public String getP2pPicks() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select PICK2POCKET2ORDER AS \"PICK_TO_POCKET\" from KC_D91_PICK_REPORT_HH where trunc(date_time) = trunc(sysdate) and HOUR_START = extract(hour from systimestamp)";
        return wcshJDBCTemplate.queryForList(sql, String.class).get(0);
    }

    @Override
    public String getDbhPicks() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select DYNAMIC_BUFFER2ORDER AS \"DYNAIC_BUFFER\" from KC_D91_PICK_REPORT_HH where trunc(date_time) = trunc(sysdate) and HOUR_START = extract(hour from systimestamp)";
        return wcshJDBCTemplate.queryForList(sql, String.class).get(0);
    }

    @Override
    public List<DbhLaneDTO> getLDDsDBH() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select unitid, name,count(*) AS lddsinlane FROM ( select distinct(KC_B91_OPEN_PICK_REQUESTS.ident),KC_B91_OPEN_PICK_REQUESTS.sku,to_char(KC_B91_OPEN_PICK_REQUESTS.created, 'dd.mm.yy HH24:MI:SS') AS CREATED," +
                "unitid,pu.area_name,name,created_since FROM KC_B91_OPEN_PICK_REQUESTS LEFT JOIN V_AF_OUTBOUND_ALLOCS@WCSH2WMS pu ON pu.item_name = KC_B91_OPEN_PICK_REQUESTS.sku " +
                "LEFT JOIN co_header@WCSH2WMS co on co.order_number = pu.customer_order_no WHERE pu.area_name = 'M2_SORTER' and trunc(last_despatch_date) = trunc(sysdate) and created_since >= 30) group by name,unitid ORDER BY lddsinlane DESC";
        return wcshJDBCTemplate.query(sql,new BeanPropertyRowMapper<>(DbhLaneDTO.class));
    }

    @Override
    public List<TransporterDTO> getTransportersDown() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT transporter_name,CASE WHEN transporter_state='-10' THEN 'DE-ACTIVATED' WHEN transporter_state ='10' THEN 'MAINTENANCE' WHEN transporter_state ='-5' THEN 'SUSPENDED' ELSE 'UNKNOWN' END AS \"TRANSPORTER_STATE\", 
                transporter_pos AS \"TRANSPORTER_POSITION\",CASE WHEN osr='1' THEN 'OSR' WHEN osr='2' THEN 'XXL' ELSE 'UNKNOWN' END AS \"OSR\", SUM(CASE WHEN TRUNC(despatch_date) = TRUNC(sysdate) THEN 1 ELSE 0 END) AS TODAY_ORDERS 
                FROM ( SELECT DISTINCT src.transporter_name AS transporter_name, src.transporter_state AS transporter_state, src.transporter_pos AS transporter_pos, src.transporter_osr_id as OSR, wpo.ID AS wpo_id, TRUNC(wpo.despatch_date) AS despatch_date 
                FROM wpo_line@hostdb.remote wpl LEFT JOIN bundle@Hostdb.RemotE bu ON bu.ID = wpl.bundle_id RIGHT JOIN (SELECT scnt_slot_tray_id, pri_code, transporter_name, transporter_osr_id, transporter_state, transporter_pos, slot_state, cl_loc_id FROM slot_contents@osrdb.remote scnt1 INNER JOIN product_infos@osrdb.remote ON pri_id = scnt_pri_id JOIN slots@osrdb.remote ON scnt_slot_tray_id = slot_tray_id JOIN container_locations@osrdb.remote ON scnt_slot_tray_id = cl_cont_id JOIN transporter_locations@osrdb.remote ON cl_loc_id = tl_loc_id JOIN transporters@osrdb.Remote ON tl_transporter_id = transporter_id WHERE transporter_name LIKE '%SHU%' AND transporter_osr_id IN (1,2) AND scnt_slot_tray_osr_id = 1 AND 1 = (SELECT COUNT(scnt_slot_tray_id) FROM slot_contents@osrdb.remote scnt2 WHERE scnt_slot_tray_osr_id = 1 AND scnt1.scnt_pri_id = scnt2.scnt_pri_id) AND (transporter_state <> '0' OR slot_state != 0) ORDER BY scnt_unreserved) src ON src.pri_code = bu.name INNER JOIN warehouse_processing_order@hostdb.remote wpo ON wpl.wpo_id = wpo.ID WHERE wpl.status NOT IN ('FINISHED','CANCELED','CANCELATION_PENDING','CREATED') AND wpl.dtype = 'KiLogicalOrderLineExt' AND src.TRANSPORTER_STATE <> '0') 
                GROUP BY osr, transporter_name,transporter_state,transporter_pos ORDER BY today_orders DESC""";
        return wcsJDBCTemplate.query(sql,new BeanPropertyRowMapper<>(TransporterDTO.class));
    }

    @Override
    public List<LiftDTO> getLiftsDown() throws SQLException, IOException, ClassNotFoundException {
        String sql = """
                SELECT transporter_name,CASE WHEN transporter_osr_id='1' THEN 'OSR' WHEN transporter_osr_id='2' THEN 'XXL' ELSE 'None' END AS \"OSR\", transporter_storage_block AS AISLE 
                FROM transporters@osrdb.remote WHERE transporter_name LIKE 'LIFT%' AND transporter_state <> '0'""";
        return wcsJDBCTemplate.query(sql,new BeanPropertyRowMapper<>(LiftDTO.class));
    }
}
