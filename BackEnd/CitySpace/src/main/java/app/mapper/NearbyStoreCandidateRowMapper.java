package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.dto.NearbyStoreCandidate;

@Component
public class NearbyStoreCandidateRowMapper
                implements RowMapper<NearbyStoreCandidate> {

        @Override
        public NearbyStoreCandidate mapRow(
                        ResultSet rs,
                        int rowNum) throws SQLException {

                NearbyStoreCandidate candidate = new NearbyStoreCandidate();

                // Map store ID
                candidate.setStoreId(
                                rs.getString("event_id"));

                // Map store details
                candidate.setStoreName(
                                rs.getString("event_name"));

                candidate.setStoreLogo(
                                rs.getString("event_image"));

                candidate.setStoreLocation(
                                rs.getString("event_location"));

                candidate.setStoreCategory(
                                rs.getString("store_category"));

                candidate.setOffer(
                                rs.getString("offer_text"));

                // Map coordinates for internal distance calculations
                candidate.setLatitude(
                                rs.getObject("latitude", Double.class));

                candidate.setLongitude(
                                rs.getObject("longitude", Double.class));

                return candidate;
        }
}