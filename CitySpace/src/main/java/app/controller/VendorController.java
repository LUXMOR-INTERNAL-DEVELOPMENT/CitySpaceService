
package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Vendor;
import app.service.VendorServiceImpl;


@RestController
@RequestMapping("/api")
public class VendorController {
    
	@Autowired
    private  VendorServiceImpl vendorService;

   


    // GET VENDOR PROFILE


    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<Vendor> getVendor(
            @PathVariable String vendorId) {

        return ResponseEntity.ok(
                vendorService.getVendorById(vendorId));
    }



    // UPDATE VENDOR PROFILE
 

    @PutMapping("/vendors/profile/update/{vendorId}")
    public ResponseEntity<Vendor> updateVendor(
            @PathVariable String vendorId,
            @RequestBody Vendor vendor) {

        return ResponseEntity.ok(
                vendorService.updateVendor(vendorId, vendor));
    }


    
    @PutMapping("/users/updatestatus/{vendorId}")
    public ResponseEntity<Vendor> updateVendorStatus(
            @PathVariable String vendorId,
            @RequestBody Vendor vendor) {

        return ResponseEntity.ok(
                vendorService.updateVendorStatus(vendorId, vendor.getStatus()));
    }
}