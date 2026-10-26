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
import org.springframework.web.bind.annotation.CrossOrigin;

import app.dto.ApplyCouponRequest;
import app.dto.ApplyCouponResponse;
import app.dto.CouponApplicableDetailsResponse;
import app.dto.CouponResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.NearbyStoreCandidate;
import app.dto.PaymentConfirmationRequest;
import app.dto.ReviewRequest;
import app.dto.ReviewResponse;
import app.dto.StatusUpdate;
import app.dto.StoreBookingRequest;
import app.dto.StoreBookingResponse;

import app.dto.StoreDetailsResponse;
import app.dto.StoreListResponse;
import app.dto.StoreProductResponse;
import app.entity.Event;
import app.service.PaymentService;
import app.service.impl.UserServiceImpl;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

    
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

        @Autowired
        private UserServiceImpl userService;

//        @Autowired
//        private PaymentService paymentService;
//
//        @GetMapping("/home")
//    public ResponseEntity<?> getHomeEvents(
//
//            @RequestParam(required = false) String userId,
//
//            @RequestParam(required = false) Double latitude,
//
//            @RequestParam(required = false) Double longitude,
//
//            @RequestParam(required = false, defaultValue = "Chennai") String location,
//
//            @RequestParam(defaultValue = "10") double radius) {
//
//        HomeResponse response = userService.getHomeEvents(
//                userId,
//                latitude,
//                longitude,
//                location,
//                radius
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//
//        @GetMapping("/list/popularity")
//     
//
//                            @RequestPa
//                            @RequestParam(required 
//                                    @RequestParam(re
//                                    @RequestParam(
//                        
//
//                                        user
//                                                latitude,
//                                                       
//                                                     
//                                                        ra
//                                return new Re  
//         
//
//        
//            @GetMapping("/list/weekend")
//                        ic ResponseEntity<?> weekEnd
//                                    @RequestParam 
//                                    @RequestParam(required 
//
//                                @RequestPara
//                                        @RequestParam(def
//                                                
//                                                ntRes
//                                                        
//                                               
//         
//
//                    return new Respon
//                                    event,
//                                            HttpStatus.OK);
//
//                 ing("/{id}")
//
//                                @PathVariable Str
//        
//
//                                
//                                    HttpStatus.OK);
//
//                        
//
//                        ic ResponseEntity<?> updateProfileById(
//
//                                    @RequestParam String role,
//
//                        
//
//                                            userService.updateProfileById
//
//                                                            role,                 
//
//                         
//
//                public ResponseEntity<?> update (      @RequestParam String u
//                                @RequestParam
//                                @RequestBody Sta
//                                
//                                ew Respon
//                                        us
//                                         
//                                        
//
//                }
//        
//
//            public ResponseEntity<?> get
//                            @RequestBody EvenFilterRequest filter) {
//
//                        List<EventFilterResponse> events = userService.get
//
//                        return ResponseEntity.ok
//         
//
//                                userService.getNearbyStoreCategories(
//                                                latitude,
//                                                longitude,
//                                                radius));
//        }

        @GetMapping("/store/nearby")
        public ResponseEntity<?> getNearbyStores(

                        @RequestParam @DecimalMin(value = "-90.0", message = "Latitude must be at least -90") @DecimalMax(value = "90.0", message = "Latitude must not exceed 90") double latitude,

                        @RequestParam @DecimalMin(value = "-180.0", message = "Longitude must be at least -180") @DecimalMax(value = "180.0", message = "Longitude must not exceed 180") double longitude,

                        @RequestParam(required = false, defaultValue = "none") @NotBlank(message = "Location must not be blank") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Location must contain only letters and spaces") String location,

                        @RequestParam(defaultValue = "10") @DecimalMin(value = "0.1", message = "Radius must be greater than zero") @DecimalMax(value = "100.0", message = "Radius must not exceed 100 km") double radius) {

                return ResponseEntity.ok(
                                userService.getNearbyStores(
                                                latitude,
                                                longitude,
                                                location,
                                                radius));
        }

        @GetMapping("/store/by-location")
        public ResponseEntity<?> getStoresByLocation(
                        @RequestParam String location) {

                List<NearbyStoreCandidate> stores = userService.getStoresByLocation(location);

                return ResponseEntity.ok(stores);
        }

        @GetMapping("/store/category")
        public ResponseEntity<List<StoreListResponse>> getAllStores(
                        @RequestParam String categoryName) {

                List<StoreListResponse> stores = userService.getAllStores(categoryName);

                return ResponseEntity.ok(stores);
        }

        @GetMapping("/store/filter")
        public ResponseEntity<?> filterStoresByCategory(

                        @RequestParam String storeCategory) {

                return ResponseEntity.ok(
                                userService.filterStoresByCategory(storeCategory));
        }

        @GetMapping("/store/{storeId}")
        public ResponseEntity<StoreDetailsResponse> getStoreDetails(
                        @PathVariable String storeId) {

                StoreDetailsResponse response = userService.getStoresDetails(storeId);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/store/products")
        public ResponseEntity<List<StoreProductResponse>> getProducts() {

                return ResponseEntity.ok(
                                userService.getProducts());
        }

        @GetMapping("/store/product/{productId}")
        public ResponseEntity<StoreProductResponse> getProductById(
                        @PathVariable String productId) {

                return ResponseEntity.ok(
                                userService.getProductById(productId));
        }

        @PostMapping("/store/review")
        public ResponseEntity<String> submitStoreReview(
                        @RequestBody ReviewRequest request) {

                userService.submitStoreReview(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body("Store review submitted successfully");
        }

        @GetMapping("/store/{storeId}/reviews")
        public ResponseEntity<List<ReviewResponse>> getRecentReviews(
                        @PathVariable String storeId) {

                return ResponseEntity.ok(
                                userService.getRecentReviews(storeId));
        }

        // COMMAND:
        // Return all active and currently valid coupons.
        @GetMapping("/coupon/list")
        public ResponseEntity<?> getCouponList() {

                // COMMAND:
                // Get the coupon list through the existing UserServiceImpl.
                List<CouponResponse> coupons = userService.getCouponList();

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
                ApplyCouponResponse response = userService.applyCoupon(request);

                // COMMAND:
                // Return the calculated coupon result with HTTP 200 OK.
                return ResponseEntity.ok(response);
        }

        @GetMapping("/store/coupon/{couponId}")
        public ResponseEntity<CouponApplicableDetailsResponse> getApplicableCouponDetails(
                        @PathVariable @NotBlank(message = "Coupon ID is required") String couponId) {

                CouponApplicableDetailsResponse response = userService.getApplicableCouponDetails(couponId);

                return ResponseEntity.ok(response);
        }

}
