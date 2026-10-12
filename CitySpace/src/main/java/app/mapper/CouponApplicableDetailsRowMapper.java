package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.dto.CouponApplicableDetailsResponse;

@Component
public class CouponApplicableDetailsRowMapper
        implements RowMapper<CouponApplicableDetailsResponse> {

    @Override
    public CouponApplicableDetailsResponse mapRow(
            ResultSet rs, int rowNum) throws SQLException {

        CouponApplicableDetailsResponse response =
                new CouponApplicableDetailsResponse();

        response.setCouponCode(rs.getString("coupon_code"));
        response.setCategoryName(rs.getString("category_name"));
        response.setCategoryType(rs.getString("category_type"));
        response.setCategoryDescription(
                rs.getString("category_description"));
        response.setCategoryPrice(
                rs.getBigDecimal("category_price"));

        return response;
    }
}