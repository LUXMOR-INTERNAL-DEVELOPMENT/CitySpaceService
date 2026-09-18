package app.dao;

import app.entity.Vendor;

public interface VendorDao {

    Vendor getVendorById(String vendorId);

    int updateVendor(Vendor vendor);

    int deactivateVendor(String vendorId, String updatedBy);

    boolean existsById(String vendorId);
}