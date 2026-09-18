package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Event;
import app.entity.User;
import app.entity.Vendor;
import app.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    // =========================================
    // GET ALL USERS
    // =========================================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }


    // =========================================
    // GET ALL EVENTS
    // =========================================

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {

        return ResponseEntity.ok(
                adminService.getAllEvents()
        );
    }


    // =========================================
    // APPROVE EVENT
    // =========================================

    @PutMapping("/events/approve/{eventId}")
    public ResponseEntity<Event> approveEvent(
            @PathVariable String eventId) {

        return ResponseEntity.ok(
                adminService.approveEvent(eventId)
        );
    }


    // =========================================
    // APPROVE VENDOR
    // =========================================

    @PutMapping("/vendor/approve/{vendorId}")
    public ResponseEntity<Vendor> approveVendor(
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(
                adminService.approveVendor(vendorId)
        );
    }
}