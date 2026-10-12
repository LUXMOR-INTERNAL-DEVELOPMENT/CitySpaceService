package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.Coupon;

@Component
public class CouponRowMapper implements RowMapper<Coupon> {

    @Override
    public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {

        // COMMAND:
        // Create a Coupon entity for the current database row.
        Coupon coupon = new Coupon();

        // COMMAND:
        // Map the basic coupon information.
        coupon.setCouponId(rs.getString("coupon_id"));
        coupon.setName(rs.getString("name"));
        coupon.setDescription(rs.getString("description"));
        coupon.setCouponCode(rs.getString("coupon_code"));

        // COMMAND:
        // Map the coupon image file name.
        coupon.setImage(rs.getString("image"));

        // COMMAND:
        // Map the discount text displayed in the UI.
        // Examples: "20% OFF", "₹200 OFF".
        coupon.setDiscountDisplay(
                rs.getString("discount_display"));

        // COMMAND:
        // Map the internal fields used when applying the coupon.
        coupon.setDiscountType(
                rs.getString("discount_type"));

        coupon.setDiscountValue(
                rs.getBigDecimal("discount_value"));

        coupon.setMinimumBookingAmount(
                rs.getBigDecimal("minimum_booking_amount"));

        coupon.setMaximumDiscount(
                rs.getBigDecimal("maximum_discount"));

        coupon.setOfferType(
                rs.getString("offer_type"));

        // COMMAND:
        // Map the coupon validity dates.
        coupon.setValidFrom(
                rs.getDate("valid_from").toLocalDate());

        coupon.setValidUntil(
                rs.getDate("valid_until").toLocalDate());

        // COMMAND:
        // Map the current coupon status.
        coupon.setStatus(
                rs.getString("status"));
        
        coupon.setCategoryId(rs.getString("category_id"));
        
        coupon.setVendorId(rs.getString("vendor_id"));

        return coupon;
    }
}