package api.Dao;

import api.entity.Vendor;

public interface VendorDao {

    Vendor getVendorById(String vendorId);
}