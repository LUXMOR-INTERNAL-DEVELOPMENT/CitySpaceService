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

        return category;
    }
}