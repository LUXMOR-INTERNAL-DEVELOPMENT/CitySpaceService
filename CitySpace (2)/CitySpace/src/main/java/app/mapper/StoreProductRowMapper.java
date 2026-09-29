package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.StoreProduct;

@Component
public class StoreProductRowMapper implements RowMapper<StoreProduct> {

    @Override
    public StoreProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

        StoreProduct product = new StoreProduct();

        product.setProductId(rs.getString("product_id"));
        product.setSubcategoryId(rs.getString("subcategory_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setProductPrice(rs.getBigDecimal("product_price"));
        product.setAvailableQuantity(rs.getInt("available_quantity"));
        product.setProductImage(rs.getString("product_image"));
        product.setStatus(rs.getString("status"));

        if (rs.getTimestamp("created_at") != null) {
            product.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        product.setCreatedBy(rs.getString("created_by"));

        if (rs.getTimestamp("updated_at") != null) {
            product.setUpdatedAt(
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        product.setUpdatedBy(rs.getString("updated_by"));

        return product;
    }
}