package app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.Vendor;
import app.repository.VendorRepository;

@Repository
public class VendorDaoImpl implements VendorDao {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor getVendorById(String vendorId) {
        return vendorRepository.getById(vendorId);
    }

    @Override
    public int updateVendor(Vendor vendor) {
        return vendorRepository.update(vendor);
    }

    @Override
    public int deactivateVendor(String vendorId, String updatedBy) {
        return vendorRepository.deactivate(
                vendorId,
                updatedBy
        );
    }

    @Override
    public boolean existsById(String vendorId) {
        return vendorRepository.existsById(vendorId);
    }
}