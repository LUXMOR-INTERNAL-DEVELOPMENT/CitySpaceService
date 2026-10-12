package app.dao;

import java.math.BigDecimal;

import app.dto.ProductUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;

public interface VendorDao {

    int addProduct(StoreProduct product);
    
    int updateProduct(
            String productId,
            ProductUpdateRequest request,
            String updatedBy);

    int addProductVariant(
            String productId,
            String variantId,
            String size,
            String color,
            String variantSku,
            BigDecimal variantPrice,
            Integer variantStock,
            String createdBy
    );
    
    StoreProduct getProductById(String productId);
    
    StoreDetails getStoreName(String storeId);

    int updateStoreName(
            String storeId,
            String storeName,
            String storeUrl,
            String updatedBy);
    
    StoreDetails getStoreLogo(String storeId);
    
    int updateStoreLogo(
            String storeId,
            String storeLogo,
            String updatedBy);
    
    StoreDetails getStoreDescription(String storeId);

    int updateStoreDescription(
            String storeId,
            String shortDescription,
            String aboutStore,
            String storeKeywords,
            String updatedBy);

    
    StoreDetails getStoreDetails(String storeId);
    
   
    
}