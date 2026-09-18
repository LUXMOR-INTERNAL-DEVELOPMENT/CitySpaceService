package app.service;

import app.entity.Vendor;

public interface VendorService {

    Vendor getVendorById(String vendorId);

    Vendor updateVendor(String vendorId, Vendor updatedVendor);

    Vendor updateVendorStatus(String vendorId, String status);
}