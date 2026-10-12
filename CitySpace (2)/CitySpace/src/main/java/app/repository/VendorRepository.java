package app.repository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.ProductUpdateRequest;
import app.entity.StoreDetails;
import app.entity.StoreProduct;
import app.mapper.StoreDetailsRowMapper;
import app.mapper.StoreProductRowMapper;

@Repository
public class VendorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StoreDetailsRowMapper storeDetailsRowMapper;

    @Autowired
    private StoreProductRowMapper storeProductRowMapper;


    @Value("${store.addProduct}")
    private String addProductQuery;

    @Value("${store.updateProduct}")
    private String updateProductQuery;

    @Value("${store.addProductVariant}")
    private String addProductVariantQuery;

    @Value("${store.getProductById}")
    private String getProductByIdQuery;

    @Value("${store.getStoreDetails}")
    private String getStoreDetailsQuery;


    @Value("${store.getStoreName}")
    private String getStoreNameQuery;

    @Value("${store.updateStoreName}")
    private String updateStoreNameQuery;

    @Value("${store.getStoreLogo}")
    private String getStoreLogoQuery;

    @Value("${store.updateStoreLogo}")
    private String updateStoreLogoQuery;

    @Value("${store.getStoreDescription}")
    private String getStoreDescriptionQuery;

    @Value("${store.updateStoreDescription}")
    private String updateStoreDescriptionQuery;


    public int addProduct(StoreProduct product) {

        return jdbcTemplate.update(
                addProductQuery,
                product.getProductId(),
                product.getSubcategoryId(),
                product.getProductName(),
                product.getShortDescription(),
                product.getProductDescription(),
                product.getSku(),
                product.getBrand(),
                product.getProductDate(),
                product.getProductLocation(),
                product.getProductTags(),
                product.getProductType(),
                product.getProductPrice(),
                product.getOriginalPrice(),
                product.getDiscountPercentage(),
                product.getStockStatus(),
                product.getAvailableQuantity(),
                product.getProductImage(),
                product.getRating(),
                product.getReviewCount(),
                product.getStatus(),
                product.getCreatedBy()
        );
    }


    public int updateProduct(
            String productId,
            ProductUpdateRequest request,
            String updatedBy) {

        return jdbcTemplate.update(
                updateProductQuery,
                request.getVendorId(),
                request.getSubcategoryId(),
                request.getProductName(),
                request.getShortDescription(),
                request.getProductDescription(),
                request.getSku(),
                request.getBrand(),
                request.getProductDate(),
                request.getProductLocation(),
                request.getProductTags(),
                request.getProductType(),
                request.getProductImage(),
                request.getOriginalPrice(),
                request.getProductPrice(),
                request.getDiscountPercentage(),
                request.getStockStatus(),
                request.getAvailableQuantity(),
                request.getStatus(),
                updatedBy,
                productId
        );
    }


    public int addProductVariant(
            String productId,
            String variantId,
            String size,
            String color,
            String variantSku,
            BigDecimal variantPrice,
            Integer variantStock,
            String createdBy) {

        return jdbcTemplate.update(
                addProductVariantQuery,
                variantId,
                productId,
                size,
                color,
                variantSku,
                variantPrice,
                variantStock,
                createdBy
        );
    }


    public StoreProduct getProductById(String productId) {

        return jdbcTemplate.queryForObject(
                getProductByIdQuery,
                new StoreProductRowMapper(),
                productId
        );
    }


    public StoreDetails getStoreDetails(String storeId) {

        return jdbcTemplate.queryForObject(
                getStoreDetailsQuery,
                storeDetailsRowMapper,
                storeId
        );
    }


    public StoreDetails getStoreName(String storeId) {

        return jdbcTemplate.queryForObject(
                getStoreNameQuery,
                storeDetailsRowMapper,
                storeId
        );
    }


    public int updateStoreName(
            String storeId,
            String storeName,
            String storeUrl,
            String updatedBy) {

        return jdbcTemplate.update(
                updateStoreNameQuery,
                storeName,
                storeUrl,
                updatedBy,
                storeId
        );
    }


    public StoreDetails getStoreLogo(String storeId) {

        return jdbcTemplate.queryForObject(
                getStoreLogoQuery,
                storeDetailsRowMapper,
                storeId
        );
    }


    public int updateStoreLogo(
            String storeId,
            String storeLogo,
            String updatedBy) {

        return jdbcTemplate.update(
                updateStoreLogoQuery,
                storeLogo,
                updatedBy,
                storeId
        );
    }


    public StoreDetails getStoreDescription(String storeId) {

        return jdbcTemplate.queryForObject(
                getStoreDescriptionQuery,
                storeDetailsRowMapper,
                storeId
        );
    }


    public int updateStoreDescription(
            String storeId,
            String shortDescription,
            String aboutStore,
            String storeKeywords,
            String updatedBy) {

        return jdbcTemplate.update(
                updateStoreDescriptionQuery,
                shortDescription,
                aboutStore,
                storeKeywords,
                updatedBy,
                storeId
        );
    }

}