package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.TimeSlot;

@Component
public class TimeSlotRowMapper implements RowMapper<TimeSlot> {

    @Override
    public TimeSlot mapRow(
            ResultSet rs,
            int rowNum) throws SQLException {

        TimeSlot slot = new TimeSlot();

        slot.setSlotId(rs.getString("slot_id"));
        slot.setVendorId(rs.getString("vendor_id"));
        slot.setSlotStartTime(
                rs.getString("slot_start_time"));
        slot.setSlotEndTime(
                rs.getString("slot_end_time"));
        slot.setStatus(
                rs.getString("status"));

        return slot;
    }
}
