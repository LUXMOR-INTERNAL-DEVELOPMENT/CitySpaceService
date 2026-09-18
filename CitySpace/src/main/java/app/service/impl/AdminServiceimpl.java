package app.Service;

import org.springframework.stereotype.Service;

import app.Dao.AdminDao;

@Service
public class AdminServiceimpl implements AdminService {

    private final AdminDao adminDao;

    public AdminServiceimpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public String approveVendor(String vendorId) {
        return adminDao.approveVendor(vendorId);
    }
}