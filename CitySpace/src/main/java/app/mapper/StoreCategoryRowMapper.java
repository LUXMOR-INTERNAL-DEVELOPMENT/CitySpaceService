package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.StoreCategory;

@Component
public class StoreCategoryRowMapper implements RowMapper<StoreCategory> {

    @Override
    public StoreCategory mapRow(ResultSet rs, int rowNum) throws SQLException {

        StoreCategory category = new StoreCategory();

        category.setCategoryId(rs.getString("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryDescription(rs.getString("category_description"));

        category.setRating(rs.getBigDecimal("rating"));
        category.setReviewCount(rs.getInt("review_count"));
        category.setReviewComment(rs.getString("review_comment"));

        category.setStatus(rs.getString("status"));

        if (rs.getTimestamp("created_at") != null) {
            category.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        category.setCreatedBy(rs.getString("created_by"));

        if (rs.getTimestamp("updated_at") != null) {
            category.setUpdatedAt(
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        category.setUpdatedBy(rs.getString("updated_by"));

        return category;
    }
}