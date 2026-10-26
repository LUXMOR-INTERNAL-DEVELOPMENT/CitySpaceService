package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.dto.RatingSummary;
import app.dto.StoreDetailsResponse;

@Component
public class UserStoreDetailsRowMapper
                implements RowMapper<StoreDetailsResponse> {

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public StoreDetailsResponse mapRow(
                        ResultSet rs,
                        int rowNum) throws SQLException {

                StoreDetailsResponse response = new StoreDetailsResponse();

                // =========================
                // Store Details
                // =========================

                response.setStoreId(
                                rs.getString("event_id"));

                response.setStoreName(
                                rs.getString("event_name"));

                response.setStoreLogo(
                                rs.getString("event_image"));

                response.setStoreLocation(
                                rs.getString("event_location"));

                response.setEventDescription(
                                rs.getString("event_description"));

                // Store category from store_details table
                response.setStoreCategory(
                                rs.getString("store_category"));

                // =========================
                // Catalogue
                // =========================

                // Catalogue is stored as JSON in store_details.
                // Expected structure: an array of JSON objects.

                String catalogueJson = rs.getString("catalogue");

                if (catalogueJson != null && !catalogueJson.isBlank()) {

                        try {

                                List<Map<String, Object>> catalogue = objectMapper.readValue(
                                                catalogueJson,
                                                new TypeReference<List<Map<String, Object>>>() {
                                                });

                                response.setEventCatalogue(catalogue);

                        } catch (Exception e) {

                                throw new SQLException(
                                                "Failed to parse catalogue JSON",
                                                e);
                        }

                } else {

                        response.setEventCatalogue(
                                        Collections.emptyList());
                }

                // =========================
                // Offer
                // =========================

                response.setOffer(
                                rs.getString("offer_text"));

                // =========================
                // Rating Summary
                // =========================

                RatingSummary ratingSummary = new RatingSummary();

                ratingSummary.setAverageRating(
                                rs.getDouble("average_rating"));

                ratingSummary.setTotalReviews(
                                rs.getInt("total_reviews"));

                ratingSummary.setFiveStarCount(
                                rs.getInt("five_star_count"));

                ratingSummary.setFourStarCount(
                                rs.getInt("four_star_count"));

                ratingSummary.setThreeStarCount(
                                rs.getInt("three_star_count"));

                ratingSummary.setTwoStarCount(
                                rs.getInt("two_star_count"));

                ratingSummary.setOneStarCount(
                                rs.getInt("one_star_count"));

                response.setRatingSummary(ratingSummary);

                // =========================
                // Return Response
                // =========================

                return response;
        }
}