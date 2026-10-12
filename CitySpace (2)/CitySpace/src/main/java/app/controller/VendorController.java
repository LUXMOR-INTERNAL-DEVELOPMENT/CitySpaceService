package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.ProductRequest;
import app.dto.ProductUpdateRequest;
import app.dto.StoreDescriptionResponse;
import app.dto.StoreDescriptionUpdateRequest;

import app.dto.StoreLogoResponse;
import app.dto.StoreLogoUpdateRequest;
import app.dto.StoreNameResponse;
import app.dto.StoreNameUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;
import app.service.VendorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/store/product")
    public ResponseEntity<StoreProduct> addProduct(
            @RequestBody ProductRequest productRequest) {

        StoreProduct product = vendorService.addProduct(productRequest);

        return ResponseEntity.ok(product);
    }
    
    @PutMapping("/store/product/{productId}")
    public ResponseEntity<StoreProduct> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateRequest request) {

        return ResponseEntity.ok(
                vendorService.updateProduct(productId, request)
        );
    }
    
    
    @GetMapping("/store/details/{storeId}/name")
    public ResponseEntity<StoreNameResponse> getStoreName(
            @PathVariable String storeId) {

        return ResponseEntity.ok(
                vendorService.getStoreName(storeId)
        );
    }
    
    
    @PutMapping("/store/details/{storeId}/name")
    public ResponseEntity<StoreNameResponse> updateStoreName(
            @PathVariable String storeId,
            @Valid @RequestBody StoreNameUpdateRequest request) {

        return ResponseEntity.ok(
                vendorService.updateStoreName(
                        storeId,
                        request
                )
        );
    }
    
    
    @GetMapping("/store/details/{storeId}/logo")
    public ResponseEntity<StoreLogoResponse> getStoreLogo(
            @PathVariable String storeId) {

        return ResponseEntity.ok(
                vendorService.getStoreLogo(storeId)
        );
    }
    
    
    @PutMapping("/store/details/{storeId}/logo")
    public ResponseEntity<StoreLogoResponse> updateStoreLogo(
            @PathVariable String storeId,
            @Valid @RequestBody StoreLogoUpdateRequest request) {

        return ResponseEntity.ok(
                vendorService.updateStoreLogo(
                        storeId,
                        request
                )
        );
    }
    
    
    @GetMapping("/store/details/{storeId}/description")
    public ResponseEntity<StoreDescriptionResponse> getStoreDescription(
            @PathVariable String storeId) {

        return ResponseEntity.ok(
                vendorService.getStoreDescription(storeId)
        );
    }
    
    @PutMapping("/store/details/{storeId}/description")
    public ResponseEntity<StoreDescriptionResponse> updateStoreDescription(
            @PathVariable String storeId,
            @Valid @RequestBody StoreDescriptionUpdateRequest request) {

        return ResponseEntity.ok(
                vendorService.updateStoreDescription(
                        storeId,
                        request
                )
        );
    }

    @GetMapping("/store/details/{storeId}")
    public ResponseEntity<StoreDetails> getStoreDetails(
            @PathVariable String storeId) {

        StoreDetails storeDetails =
                vendorService.getStoreDetails(storeId);

        return ResponseEntity.ok(storeDetails);
    }

  
}