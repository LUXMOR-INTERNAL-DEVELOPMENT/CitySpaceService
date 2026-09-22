package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.dto.AvailableTable;

@Component
public class AvailableTableRowMapper
        implements RowMapper<AvailableTable> {

    @Override
    public AvailableTable mapRow(
            ResultSet rs,
            int rowNum) throws SQLException {

        AvailableTable table = new AvailableTable();

        table.setTableId(
                rs.getString("table_id"));

        table.setVendorId(
                rs.getString("vendor_id"));

        table.setTableNumber(
                rs.getString("table_number"));

        table.setCapacity(
                rs.getInt("capacity"));

        table.setTableStatus(
                rs.getString("table_status"));

        

        return table;
    }
}
