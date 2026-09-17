package api.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import api.entity.Vendor;

@Repository
public class VendorDaoimpl implements VendorDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${vendor.getVendorById}")
    private String getVendorByIdQuery;

    public VendorDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Vendor getVendorById(String vendorId) {

        List<Vendor> vendors = jdbcTemplate.query(
                getVendorByIdQuery,
                (rs, rowNum) -> {

                    Vendor vendor = new Vendor();

                    vendor.setId(rs.getLong("id"));
                    vendor.setVendorId(rs.getString("vendor_id"));
                    vendor.setAuthId(rs.getLong("auth_id"));

                    if (rs.getTimestamp("created_at") != null) {
                        vendor.setCreatedAt(
                                rs.getTimestamp("created_at").toLocalDateTime()
                        );
                    }

                    if (rs.getTimestamp("updated_at") != null) {
                        vendor.setUpdatedAt(
                                rs.getTimestamp("updated_at").toLocalDateTime()
                        );
                    }

                    vendor.setUpdatedBy(rs.getString("updated_by"));

                    return vendor;
                },
                vendorId
        );

        if (vendors.isEmpty()) {
            return null;
        }

        return vendors.get(0);
    }
}