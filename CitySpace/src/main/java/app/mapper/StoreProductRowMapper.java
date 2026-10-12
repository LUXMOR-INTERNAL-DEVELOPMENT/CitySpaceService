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
        product.setShortDescription(rs.getString("short_description"));
        product.setProductDescription(rs.getString("product_description"));
        product.setSku(rs.getString("sku"));
        product.setBrand(rs.getString("brand"));

        if (rs.getDate("product_date") != null) {
            product.setProductDate(
                    rs.getDate("product_date").toLocalDate()
            );
        }

        product.setProductLocation(rs.getString("product_location"));
        product.setProductTags(rs.getString("product_tags"));
        product.setProductType(rs.getString("product_type"));

        product.setProductPrice(rs.getBigDecimal("product_price"));
        product.setOriginalPrice(rs.getBigDecimal("original_price"));
        product.setDiscountPercentage(
                rs.getBigDecimal("discount_percentage")
        );

        product.setStockStatus(rs.getString("stock_status"));
        product.setAvailableQuantity(
                rs.getInt("available_quantity")
        );

        product.setProductImage(rs.getString("product_image"));

        product.setRating(rs.getBigDecimal("rating"));
        product.setReviewCount(rs.getInt("review_count"));

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