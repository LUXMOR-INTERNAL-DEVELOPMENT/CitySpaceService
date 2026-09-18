package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Vendor;
import app.repository.VendorRepository;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    // Get Vendor By ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vendorRepository.getById(id)
        );
    }

    // Approve Vendor
    @PutMapping("/approve/{id}")
    public ResponseEntity<Vendor> approveVendor(
            @PathVariable Long id) {

        vendorRepository.approveVendor(id);

        return ResponseEntity.ok(
                vendorRepository.getById(id)
        );
    }
}