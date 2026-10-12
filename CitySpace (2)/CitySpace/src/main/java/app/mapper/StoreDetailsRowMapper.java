package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.StoreDetails;

@Component
public class StoreDetailsRowMapper implements RowMapper<StoreDetails> {

    @Override
    public StoreDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

        StoreDetails storeDetails = new StoreDetails();

        storeDetails.setStoreId(rs.getString("store_id"));
        storeDetails.setVendorId(rs.getString("vendor_id"));
        storeDetails.setStoreName(rs.getString("store_name"));
        storeDetails.setStoreLogo(rs.getString("store_logo"));
        storeDetails.setShortDescription(rs.getString("short_description"));
        storeDetails.setAboutStore(rs.getString("about_store"));
        storeDetails.setStoreKeywords(rs.getString("store_keywords"));
        storeDetails.setStatus(rs.getString("status"));

        if (rs.getTimestamp("created_at") != null) {
            storeDetails.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        storeDetails.setCreatedBy(rs.getString("created_by"));

        if (rs.getTimestamp("updated_at") != null) {
            storeDetails.setUpdatedAt(
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        storeDetails.setUpdatedBy(rs.getString("updated_by"));

        return storeDetails;
    }
}