package app.dao.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.VendorDao;
import app.dto.ProductUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;
import app.repository.VendorRepository;

@Component
public class VendorDaoImpl implements VendorDao {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public int addProduct(StoreProduct product) {

        return vendorRepository.addProduct(product);
    }
    
    @Override
    public int updateProduct(
            String productId,
            ProductUpdateRequest request,
            String updatedBy) {

        return vendorRepository.updateProduct(
                productId,
                request,
                updatedBy
        );
    }

    @Override
    public int addProductVariant(
            String productId,
            String variantId,
            String size,
            String color,
            String variantSku,
            BigDecimal variantPrice,
            Integer variantStock,
            String createdBy) {

        return vendorRepository.addProductVariant(
                productId,
                variantId,
                size,
                color,
                variantSku,
                variantPrice,
                variantStock,
                createdBy
        );
    }
    
 
    
    @Override
    public StoreProduct getProductById(String productId) {
        return vendorRepository.getProductById(productId);
    }
    
    @Override
    public StoreDetails getStoreDetails(String storeId) {
        return vendorRepository.getStoreDetails(storeId);
    }
    
    @Override
    public StoreDetails getStoreName(String storeId) {
        return vendorRepository.getStoreDetails(storeId);
    }
    
    @Override
    public int updateStoreName(
            String storeId,
            String storeName,
            String storeUrl,
            String updatedBy) {

        return vendorRepository.updateStoreName(
                storeId,
                storeName,
                storeUrl,
                updatedBy
        );
    }
    
    @Override
    public StoreDetails getStoreLogo(String storeId) {

        return vendorRepository.getStoreLogo(storeId);
    }
    
    @Override
    public int updateStoreLogo(
            String storeId,
            String storeLogo,
            String updatedBy) {

        return vendorRepository.updateStoreLogo(
                storeId,
                storeLogo,
                updatedBy
        );
    }
    
    @Override
    public StoreDetails getStoreDescription(String storeId) {

        return vendorRepository.getStoreDescription(storeId);
    }
    
    @Override
    public int updateStoreDescription(
            String storeId,
            String shortDescription,
            String aboutStore,
            String storeKeywords,
            String updatedBy) {

        return vendorRepository.updateStoreDescription(
                storeId,
                shortDescription,
                aboutStore,
                storeKeywords,
                updatedBy
        );
    }
 
    
 
}