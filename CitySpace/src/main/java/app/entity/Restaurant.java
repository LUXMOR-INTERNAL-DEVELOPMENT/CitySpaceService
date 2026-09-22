package app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_table")
public class Restaurant {

    @Id
    @Column(name = "table_id")
    private String tableId;

    private String vendorId;
    
    private String tableNumber;
    
    private int capacity;

    private String tableStatus;
}