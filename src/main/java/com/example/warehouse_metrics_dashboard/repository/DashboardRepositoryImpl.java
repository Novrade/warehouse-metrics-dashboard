
package com.example.warehouse_metrics_dashboard.repository;

import com.example.warehouse_metrics_dashboard.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    private final JdbcTemplate db1;
    private final JdbcTemplate db2;
    private final JdbcTemplate db3;
    private final JdbcTemplate db4;

    public DashboardRepositoryImpl(
            @Qualifier("JDBCTemplateDB1") JdbcTemplate db1,
            @Qualifier("JDBCTemplateDB2") JdbcTemplate db2,
            @Qualifier("JDBCTemplateDB3") JdbcTemplate db3,
            @Qualifier("JDBCTemplateDB4") JdbcTemplate db4) {
        this.db1 = db1;
        this.db2 = db2;
        this.db3 = db3;
        this.db4 = db4;
    }

    @Override
    public String getLDDs() {
        String sql = """
                SELECT COUNT(*)
                FROM tasks
                WHERE TRUNC(due_date) <= TRUNC(CURRENT_DATE)
                  AND status NOT IN ('CANCELED', 'FINISHED')
                """;
        return db1.queryForObject(sql, String.class);
    }

    @Override
    public String getArea1FillLevel() {
        String sql = "SELECT filllevel FROM area";
        return db3.queryForObject(sql, String.class);
    }

    @Override
    public String getArea2FillLevel() {
        String sql = "SELECT filled_percent FROM empty_area";
        return db3.queryForObject(sql, String.class);
    }

    @Override
    public String getReleaseProblems() {
        String sql = """
            SELECT count(*) 
            FROM problems 
            WHERE trunc(ldd) <= trunc(sysdate)
              AND available_qty > 0 
              AND retry_count >= 5
            """;
        return db1.queryForObject(sql, String.class);
    }

    @Override
    public String getArea3FillLevel() {
        String sql = """
            SELECT COUNT(*) FROM locations WHERE type = 1
        """;
        float count = Float.parseFloat(db4.queryForObject(sql, String.class));
        return String.valueOf(Math.round((count / 612213 * 100) * 100) / 100.0f);
    }

    @Override
    public String getArea4FillLevel() {
        String sql = """
            SELECT COUNT(*) FROM locations WHERE type = 2
        """;
        float count = Float.parseFloat(db4.queryForObject(sql, String.class));
        return String.valueOf(Math.round((count / 37604 * 100) * 100) / 100.0f);
    }

    @Override
    public List<PbLaneDTO> getPbLanes() {
        String sql = """
            SELECT name, id, filllevel as fill,
                   CASE 
                       WHEN state = 4 THEN 'ERROR' 
                       WHEN state = 5 THEN 'JAM'
                       ELSE 'UNKNOWN'
                   END as state
            FROM lanes
            WHERE state IN (2,3,4) 
            ORDER BY id
        """;
        return db3.query(sql, new BeanPropertyRowMapper<>(PbLaneDTO.class));
    }

    @Override
    public String getWip() {
        String sql = """
            WITH dl_agg AS (
              SELECT dl.order_line_id,
                     SUM(dl.delivered_qty) AS delivered_qty
              FROM delivery_lines dl
              GROUP BY dl.order_line_id
            )
            SELECT
              SUM(ol.quantity) - SUM(COALESCE(da.delivered_qty, 0))
            FROM orders o
            JOIN customer_orders co ON co.id = o.customer_order_id
            JOIN order_lines ol ON ol.order_id = o.id
            JOIN bundles b ON b.id = ol.bundle_id
            JOIN products p ON p.id = b.product_id
            LEFT JOIN dl_agg da ON da.order_line_id = ol.id
            WHERE o.order_type = 'standard'
              AND co.type_id <= 6
              AND o.status NOT IN ('FINISHED','CANCELED')
              AND ol.status NOT IN ('CANCELED','FINISHED')
        """;
        return db1.queryForObject(sql, String.class);
    }

    @Override
    public String getP2pPicks() {
        String sql = """
            SELECT order AS pick
            FROM report
            WHERE trunc(date) = trunc(sysdate)
              AND HOUR_START = extract(hour from systimestamp)
        """;
        return db3.queryForObject(sql, String.class);
    }

    @Override
    public String getDbhPicks() {
        String sql = """
            SELECT order AS buffer
            FROM report
            WHERE trunc(date) = trunc(sysdate)
              AND HOUR_START = extract(hour from systimestamp)
        """;
        return db3.queryForObject(sql, String.class);
    }

    @Override
    public List<DbhLaneDTO> getLDDsDBH() {
        String sql = """
            SELECT zone_id, zone_name, COUNT(*) AS items_in_zone
            FROM (
                SELECT DISTINCT
                       p.item_id,
                       p.product_code,
                       TO_CHAR(p.created_at, 'dd.mm.yy HH24:MI:SS') AS created_at,
                       z.zone_id,
                       z.zone_name,
                       p.age_minutes
                FROM picks p
                LEFT JOIN allocations a ON a.product_code = p.product_code
                LEFT JOIN orders o ON o.order_number = a.order_number
                WHERE z.zone_name = 'ZONE_A'
                  AND TRUNC(p.last_processed_date) = TRUNC(CURRENT_DATE)
                  AND p.age_minutes >= 30
            )
            GROUP BY zone_name, zone_id
            ORDER BY items_in_zone DESC
        """;
        return db3.query(sql, new BeanPropertyRowMapper<>(DbhLaneDTO.class));
    }

    @Override
    public List<TransporterDTO> getTransportersDown() {
        String sql = """
            SELECT
                name,
                state_label,
                position,
                area_label,
                SUM(today_flag) AS orders
            FROM (
                SELECT
                    wpl.name,
                    wpl.position,
                    CASE WHEN wpl.state = '-1' THEN 'DE-ACTIVATED'
                         WHEN wpl.state = '1'  THEN 'MAINTENANCE'
                         WHEN wpl.state = '-2' THEN 'SUSPENDED'
                         ELSE 'UNKNOWN'
                    END AS state_label,
                    CASE WHEN wpl.unit = '2' THEN 'AREA1'
                         WHEN wpl.unit = '3' THEN 'AREA2'
                         ELSE 'UNKNOWN'
                    END AS area_label,
                    CASE WHEN TRUNC(wpl.date) = TRUNC(SYSDATE) THEN 1 ELSE 0 END AS today_flag
                FROM table1 wpl
                LEFT JOIN table2 bu ON bu.id = wpl.bundle_id
            )
            GROUP BY name, state_label, position, area_label
            ORDER BY orders DESC
        """;
        return db2.query(sql, new BeanPropertyRowMapper<>(TransporterDTO.class));
    }

    @Override
    public List<LiftDTO> getLiftsDown() {
        String sql = """
            SELECT
                name,
                CASE WHEN id = '3' THEN 'AREA1'
                     WHEN id = '4' THEN 'AREA2'
                     ELSE 'NONE'
                END AS area,
                storage_block AS aisle
            FROM table1
            WHERE name LIKE 'LIFT%'
              AND state <> '3'
        """;
        return db2.query(sql, new BeanPropertyRowMapper<>(LiftDTO.class));
    }
}
