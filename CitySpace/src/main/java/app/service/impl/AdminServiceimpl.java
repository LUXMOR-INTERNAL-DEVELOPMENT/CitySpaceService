package app.service.impl;

import org.springframework.stereotype.Service;

import app.dao.AdminDao;



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