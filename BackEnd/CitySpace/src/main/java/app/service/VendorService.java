package app.service;

import app.dto.ProductRequest;
import app.dto.ProductUpdateRequest;
import app.dto.StoreDescriptionResponse;
import app.dto.StoreDescriptionUpdateRequest;

import app.dto.StoreLogoNameUpdateRequest;
import app.dto.StoreLogoResponse;
import app.dto.StoreLogoUpdateRequest;
import app.dto.StoreNameResponse;
import app.dto.StoreNameUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;

public interface VendorService {

	StoreProduct addProduct(ProductRequest productRequest);
	
	StoreProduct updateProduct(
	        String productId,
	        ProductUpdateRequest request);
    
    StoreDetails getStoreDetails(String storeId);
    
    
    StoreNameResponse getStoreName(String storeId);

    StoreNameResponse updateStoreName(
            String storeId,
            StoreNameUpdateRequest request);
    
    StoreLogoResponse getStoreLogo(String storeId);

    StoreLogoResponse updateStoreLogo(
            String storeId,
            StoreLogoUpdateRequest request);
    
    StoreDescriptionResponse getStoreDescription(String storeId);

    StoreDescriptionResponse updateStoreDescription(
            String storeId,
            StoreDescriptionUpdateRequest request);
    
    
    
    
   
}