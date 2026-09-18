package app.repository;

import java.util.List;

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

    @Autowired
    private VendorRowMapper vendorRowMapper;

    @Value("${admin.vendor.approve}")
    private String approveVendorQuery;

    // Approve Vendor
    public int approveVendor(Long vendorId) {

        return jdbcTemplate.update(
                approveVendorQuery,
                vendorId
        );
    }

    // Get Vendor By ID
    public Vendor getById(Long vendorId) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM vendors WHERE id = ?",
                vendorRowMapper,
                vendorId
        );
    }

	public Vendor save(Vendor vendor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vendor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}