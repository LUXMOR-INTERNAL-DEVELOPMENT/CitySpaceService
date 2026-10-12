package app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.VendorDao;
import app.dto.ProductRequest;
import app.dto.ProductUpdateRequest;
import app.dto.ProductVariantRequest;
import app.dto.StoreDescriptionResponse;
import app.dto.StoreDescriptionUpdateRequest;
import app.dto.StoreLogoResponse;
import app.dto.StoreLogoUpdateRequest;
import app.dto.StoreNameResponse;
import app.dto.StoreNameUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;
import app.exception.ResourceNotFoundException;
import app.service.VendorService;
import jakarta.transaction.Transactional;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorDao vendorDao;


    // =========================================================
    // STORE NAME
    // =========================================================

    @Override
    public StoreNameResponse getStoreName(String storeId) {

        StoreDetails storeDetails =
                vendorDao.getStoreDetails(storeId);

        StoreNameResponse response =
                new StoreNameResponse();

        response.setStoreName(storeDetails.getStoreName());
        response.setStoreUrl(storeDetails.getStoreUrl());

        return response;
    }


    @Override
    public StoreNameResponse updateStoreName(
            String storeId,
            StoreNameUpdateRequest request) {

        int updated = vendorDao.updateStoreName(
                storeId,
                request.getStoreName(),
                request.getStoreUrl(),
                storeId
        );

        if (updated == 0) {
            throw new ResourceNotFoundException(
                    "Store not found"
            );
        }

        return getStoreName(storeId);
    }


    // =========================================================
    // STORE LOGO
    // =========================================================

    @Override
    public StoreLogoResponse getStoreLogo(String storeId) {

        StoreDetails storeDetails =
                vendorDao.getStoreDetails(storeId);

        StoreLogoResponse response =
                new StoreLogoResponse();

        response.setStoreLogo(
                storeDetails.getStoreLogo()
        );

        return response;
    }


    @Override
    public StoreLogoResponse updateStoreLogo(
            String storeId,
            StoreLogoUpdateRequest request) {

        int updated = vendorDao.updateStoreLogo(
                storeId,
                request.getStoreLogo(),
                storeId
        );

        if (updated == 0) {
            throw new ResourceNotFoundException(
                    "Store not found"
            );
        }

        StoreDetails storeDetails =
                vendorDao.getStoreDetails(storeId);

        StoreLogoResponse response =
                new StoreLogoResponse();

        response.setStoreLogo(
                storeDetails.getStoreLogo()
        );

        return response;
    }


    // =========================================================
    // STORE DESCRIPTION
    // =========================================================

    @Override
    public StoreDescriptionResponse getStoreDescription(
            String storeId) {

        StoreDetails storeDetails =
                vendorDao.getStoreDescription(storeId);

        StoreDescriptionResponse response =
                new StoreDescriptionResponse();

        response.setShortDescription(
                storeDetails.getShortDescription()
        );

        response.setAboutStore(
                storeDetails.getAboutStore()
        );

        response.setStoreKeywords(
                storeDetails.getStoreKeywords()
        );

        return response;
    }


    @Override
    public StoreDescriptionResponse updateStoreDescription(
            String storeId,
            StoreDescriptionUpdateRequest request) {

        int updated = vendorDao.updateStoreDescription(
                storeId,
                request.getShortDescription(),
                request.getAboutStore(),
                request.getStoreKeywords(),
                storeId
        );

        if (updated == 0) {
            throw new ResourceNotFoundException(
                    "Store not found"
            );
        }

        return getStoreDescription(storeId);
    }


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    @Override
    @Transactional
    public StoreProduct addProduct(ProductRequest productRequest) {

        // Validate product details
        validateProductRequest(productRequest);

        // Validate all variants before inserting product
        if (productRequest.getVariants() != null) {

            for (ProductVariantRequest variant
                    : productRequest.getVariants()) {

                validateProductVariant(variant);
            }
        }

        // Convert ProductRequest to StoreProduct
        StoreProduct product = new StoreProduct();

        BeanUtils.copyProperties(
                productRequest,
                product
        );

        product.setCreatedAt(LocalDateTime.now());

        // Save main product
        int productResult =
                vendorDao.addProduct(product);

        // Save variants
        if (productResult > 0
                && productRequest.getVariants() != null) {

            for (ProductVariantRequest variant
                    : productRequest.getVariants()) {

                vendorDao.addProductVariant(
                        productRequest.getProductId(),
                        variant.getVariantId(),
                        variant.getSize(),
                        variant.getColor(),
                        variant.getVariantSku(),
                        variant.getVariantPrice(),
                        variant.getVariantStock(),
                        productRequest.getCreatedBy()
                );
            }
        }

        // Fetch and return newly added product
        return vendorDao.getProductById(
                productRequest.getProductId()
        );
    }


    // =========================================================
    // PRODUCT VALIDATION
    // =========================================================

    private void validateProductRequest(
            ProductRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Product request cannot be null"
            );
        }


        // Product ID
        if (request.getProductId() == null
                || request.getProductId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Product ID is required"
            );
        }


        // Vendor ID
        if (request.getVendorId() == null
                || request.getVendorId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Vendor ID is required"
            );
        }


        // Subcategory ID
        if (request.getSubcategoryId() == null
                || request.getSubcategoryId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Subcategory ID is required"
            );
        }


        // Product Name
        if (request.getProductName() == null
                || request.getProductName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Product name is required"
            );
        }


        // SKU
        if (request.getSku() == null
                || request.getSku().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "SKU is required"
            );
        }


        // Product Price
        if (request.getProductPrice() == null) {

            throw new IllegalArgumentException(
                    "Product price is required"
            );
        }

        if (request.getProductPrice().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Product price must be greater than 0"
            );
        }


        // Original Price
        if (request.getOriginalPrice() == null) {

            throw new IllegalArgumentException(
                    "Original price is required"
            );
        }

        if (request.getOriginalPrice().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Original price must be greater than 0"
            );
        }


        // Product Price cannot be greater
        // than Original Price
        if (request.getProductPrice()
                .compareTo(request.getOriginalPrice()) > 0) {

            throw new IllegalArgumentException(
                    "Product price cannot be greater than original price"
            );
        }


        // Discount Percentage
        if (request.getDiscountPercentage() == null) {

            throw new IllegalArgumentException(
                    "Discount percentage is required"
            );
        }

        if (request.getDiscountPercentage()
                .compareTo(BigDecimal.ZERO) < 0
                || request.getDiscountPercentage()
                    .compareTo(new BigDecimal("100")) > 0) {

            throw new IllegalArgumentException(
                    "Discount percentage must be between 0 and 100"
            );
        }


        // Available Quantity
        if (request.getAvailableQuantity() == null) {

            throw new IllegalArgumentException(
                    "Available quantity is required"
            );
        }

        if (request.getAvailableQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Available quantity cannot be negative"
            );
        }


        // Rating
        if (request.getRating() != null) {

            if (request.getRating()
                    .compareTo(BigDecimal.ZERO) < 0
                    || request.getRating()
                        .compareTo(new BigDecimal("5")) > 0) {

                throw new IllegalArgumentException(
                        "Rating must be between 0 and 5"
                );
            }
        }


        // Review Count
        if (request.getReviewCount() != null
                && request.getReviewCount() < 0) {

            throw new IllegalArgumentException(
                    "Review count cannot be negative"
            );
        }


        // Stock Status
        if (request.getStockStatus() == null
                || request.getStockStatus().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Stock status is required"
            );
        }


        // Product Status
        if (request.getStatus() == null
                || request.getStatus().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Product status is required"
            );
        }


        // Created By
        if (request.getCreatedBy() == null
                || request.getCreatedBy().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Created by is required"
            );
        }
    }


    // =========================================================
    // PRODUCT VARIANT VALIDATION
    // =========================================================

    private void validateProductVariant(
            ProductVariantRequest variant) {

        if (variant == null) {
            throw new IllegalArgumentException(
                    "Variant cannot be null"
            );
        }


        // Variant ID
        if (variant.getVariantId() == null
                || variant.getVariantId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Variant ID is required"
            );
        }


        // Size
        if (variant.getSize() == null
                || variant.getSize().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Variant size is required"
            );
        }


        // Color
        if (variant.getColor() == null
                || variant.getColor().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Variant color is required"
            );
        }


        // Variant SKU
        if (variant.getVariantSku() == null
                || variant.getVariantSku().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Variant SKU is required"
            );
        }


        // Variant Price
        if (variant.getVariantPrice() == null) {

            throw new IllegalArgumentException(
                    "Variant price is required"
            );
        }

        if (variant.getVariantPrice().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Variant price must be greater than 0"
            );
        }


        // Variant Stock
        if (variant.getVariantStock() == null) {

            throw new IllegalArgumentException(
                    "Variant stock is required"
            );
        }

        if (variant.getVariantStock() < 0) {

            throw new IllegalArgumentException(
                    "Variant stock cannot be negative"
            );
        }
    }
    
    @Override
    public StoreProduct updateProduct(
            String productId,
            ProductUpdateRequest request) {

        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID is required");
        }

        validateProductUpdateRequest(request);

        StoreProduct existingProduct =
                vendorDao.getProductById(productId);

        if (existingProduct == null) {
            throw new ResourceNotFoundException(
                    "Product not found: " + productId
            );
        }

        int updated = vendorDao.updateProduct(
                productId,
                request,
                request.getVendorId()
        );

        if (updated == 0) {
            throw new ResourceNotFoundException(
                    "Product not found: " + productId
            );
        }

        return vendorDao.getProductById(productId);
    }
    
    private void validateProductUpdateRequest(
            ProductUpdateRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Product update request is required"
            );
        }

        if (request.getOriginalPrice() != null
                && request.getProductPrice() != null
                && request.getProductPrice()
                        .compareTo(request.getOriginalPrice()) > 0) {

            throw new IllegalArgumentException(
                    "Product price cannot be greater than original price"
            );
        }

        if (request.getDiscountPercentage() != null) {

            if (request.getDiscountPercentage()
                    .compareTo(BigDecimal.ZERO) < 0
                    || request.getDiscountPercentage()
                    .compareTo(new BigDecimal("100")) > 0) {

                throw new IllegalArgumentException(
                        "Discount percentage must be between 0 and 100"
                );
            }
        }

        if (request.getAvailableQuantity() != null
                && request.getAvailableQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Available quantity cannot be negative"
            );
        }
    }


    // =========================================================
    // GET PRODUCT
    // =========================================================

   

    // =========================================================
    // STORE DETAILS
    // =========================================================

    @Override
    public StoreDetails getStoreDetails(String storeId) {

        return vendorDao.getStoreDetails(storeId);
    }
}