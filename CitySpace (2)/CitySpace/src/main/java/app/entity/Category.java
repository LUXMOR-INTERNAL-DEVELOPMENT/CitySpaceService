package app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "category_table")
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "category_description")
    private String categoryDescription;

    @Column(name = "category_price")
    private BigDecimal categoryPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}