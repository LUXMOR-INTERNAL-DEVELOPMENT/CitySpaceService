package app.Service;

import org.springframework.stereotype.Service;

import app.Dao.VendorDao;
import app.entity.Vendor;

@Service
public class VendorServiceimpl implements VendorService {

    private final VendorDao vendorDao;

    public VendorServiceimpl(VendorDao vendorDao) {
        this.vendorDao = vendorDao;
    }

    @Override
    public Vendor getVendorById(String vendorId) {
        return vendorDao.getVendorById(vendorId);
    }
}