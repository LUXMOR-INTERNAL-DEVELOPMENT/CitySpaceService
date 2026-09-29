package app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "store_subcategory")
public class StoreSubcategory {

    @Id
    @Column(name = "subcategory_id")
    private String subcategoryId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Column(name = "subcategory_name", nullable = false)
    private String subcategoryName;

    @Column(name = "subcategory_description")
    private String subcategoryDescription;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
