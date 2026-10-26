package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.dto.StoreListResponse;

@Component
public class StoreListRowMapper implements RowMapper<StoreListResponse> {

    @Override
    public StoreListResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        StoreListResponse response = new StoreListResponse();

        response.setStoreId(rs.getString("event_id"));

        response.setStoreName(rs.getString("event_name"));
        response.setStoreLogo(rs.getString("event_image"));
        response.setStoreLocation(rs.getString("event_location"));
        response.setStoreCategory(rs.getString("store_category"));
        response.setOffer(rs.getString("offer_text"));

        return response;
    }
}