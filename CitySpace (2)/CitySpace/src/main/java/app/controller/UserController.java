package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import app.dto.ApplyCouponRequest;
import app.dto.ApplyCouponResponse;
import app.dto.CouponApplicableDetailsResponse;
import app.dto.CouponResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.PaymentConfirmationRequest;
import app.dto.StatusUpdate;
import app.dto.StoreBookingRequest;
import app.dto.StoreBookingResponse;
import app.dto.StoreProductResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Event;
import app.service.PaymentService;
import app.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;



@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllEvents(
            @RequestParam(required=false) String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<Event> event =
                userService.getAllEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/list/popularity")
    public ResponseEntity<?> TopPopularityEvents(
            @RequestParam String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<EventResponse> event =
                userService.TopPopularityEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/list/weekend")
    public ResponseEntity<?> weekEndEvents(
            @RequestParam String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<EventResponse> event =
                userService.weekEndEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable String id) {

        return new ResponseEntity<>(
                userService.getUser(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfileById(
            @RequestParam String userId,
            @RequestParam String role,
            @RequestBody UserUpdateProfileRequest userUpdate) {

        return new ResponseEntity<>(
                userService.updateProfileById(
                        userId,
                        role,
                        userUpdate
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(
            @RequestParam String userId,
            @RequestParam String role,
            @RequestBody StatusUpdate status) {

        return new ResponseEntity<>(
                userService.updateStatus(
                        userId,
                        role,
                        status
                ),
                HttpStatus.OK
        );
    }
    
    @GetMapping("/filter/events")
    public ResponseEntity<?> getFilteredEvents(
            @RequestBody EvenFilterRequest filter) {

        List<EventFilterResponse> events =
                userService.getFilteredEvents(filter);

        return ResponseEntity.ok(events);
    }
    
   
    @GetMapping("/store/categories")
    public ResponseEntity<?> getNearbyStoreCategories(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "10") double radius) {

        return ResponseEntity.ok(
                userService.getNearbyStoreCategories(
                        latitude,
                        longitude,
                        radius
                )
        );
    }
    	
    @GetMapping("/store/category")
    public ResponseEntity<?> getStoreByCategory(
            @RequestParam
            @NotBlank(message = "Category ID must not be blank")
            String categoryId) {

        return ResponseEntity.ok(
                userService.getStoreByCategory(categoryId)
        );
    }
    
    @GetMapping("/store/product/{productId}")
    public ResponseEntity<StoreProductResponse> getProductById(
            @PathVariable String productId) {

        return ResponseEntity.ok(
                userService.getProductById(productId)
        );
    }
    
 // COMMAND:
    // Return all active and currently valid coupons.
    @GetMapping("/coupon/list")
    public ResponseEntity<?> getCouponList() {

        // COMMAND:
        // Get the coupon list through the existing UserServiceImpl.
        List<CouponResponse> coupons =
                userService.getCouponList();

        // COMMAND:
        // Return the coupon list with HTTP 200 OK.
        return ResponseEntity.ok(coupons);
    }
    
 // COMMAND:
 // Apply the selected coupon to the user's booking
 // and return the calculated amount.
 @PostMapping("/coupon/apply")
 public ResponseEntity<?> applyCoupon(
         @RequestBody ApplyCouponRequest request) {

     // COMMAND:
     // Apply the coupon through the existing UserServiceImpl.
     ApplyCouponResponse response =
             userService.applyCoupon(request);

     // COMMAND:
     // Return the calculated coupon result with HTTP 200 OK.
     return ResponseEntity.ok(response);
 }
 
 @GetMapping("/store/coupon/{couponId}")
 public ResponseEntity<CouponApplicableDetailsResponse> getApplicableCouponDetails(
         @PathVariable @NotBlank(message = "Coupon ID is required") String couponId) {

     CouponApplicableDetailsResponse response =
             userService.getApplicableCouponDetails(couponId);

     return ResponseEntity.ok(response);
 }
  
    }

