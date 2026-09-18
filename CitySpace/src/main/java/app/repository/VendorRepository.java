package app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.entity.Vendor;
import app.mapper.VendorRowMapper;

@Repository
public class VendorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${vendor.getById}")
    private String getByIdSql;

    @Value("${vendor.update}")
    private String updateSql;

    @Value("${vendor.deactivate}")
    private String deactivateSql;

    @Value("${vendor.existsById}")
    private String existsByIdSql;

    @Autowired
    private VendorRowMapper vendorRowMapper;


    // =========================
    // GET VENDOR BY ID
    // =========================
    public Vendor getById(String vendorId) {

        return jdbcTemplate.queryForObject(
                getByIdSql,
                vendorRowMapper,
                vendorId
        );
    }


    // =========================
    // UPDATE VENDOR
    // =========================
    public int update(Vendor vendor) {

        return jdbcTemplate.update(
                updateSql,
                vendor.getVendorName(),          // 1
                vendor.getVendorEmail(),         // 2
                vendor.getVendorContact(),       // 3
                vendor.getVendorLocation(),      // 4
                vendor.getBankAccountNumber(),   // 5
                vendor.getIfscCode(),            // 6
                vendor.getAccountHolderName(),   // 7
                vendor.getBankName(),            // 8
                vendor.getPan(),                 // 9
                vendor.getGst(),                 // 10
                vendor.getRating(),              // 11
                vendor.getUpdatedBy(),           // 12
                vendor.getVendorId()             // 13
        );
    }


    // =========================
    // DEACTIVATE VENDOR
    // =========================
    public int deactivate(String vendorId, String updatedBy) {

        return jdbcTemplate.update(
                deactivateSql,
                updatedBy,
                vendorId
        );
    }


    // =========================
    // CHECK VENDOR EXISTS
    // =========================
    public boolean existsById(String vendorId) {

        Integer count = jdbcTemplate.queryForObject(
                existsByIdSql,
                Integer.class,
                vendorId
        );

        return count != null && count > 0;
    }
}


