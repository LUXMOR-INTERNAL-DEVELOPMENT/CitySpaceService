package api.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoimpl implements AdminDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${vendor.getAuthIdByVendorId}")
    private String getAuthIdByVendorIdQuery;

    @Value("${auth.updateVendorStatus}")
    private String updateVendorStatusQuery;

    public AdminDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String approveVendor(String vendorId) {

        List<Long> authIds = jdbcTemplate.query(
            getAuthIdByVendorIdQuery,
            (rs, rowNum) -> rs.getLong("auth_id"),
            vendorId
        );

        if (authIds.isEmpty()) {
            return "Vendor not found: " + vendorId;
        }

        Long authId = authIds.get(0);

        jdbcTemplate.update(
            updateVendorStatusQuery,
            "APPROVED",
            authId
        );

        return "Vendor Approved: " + vendorId;
    }
}