//package app.entity;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "vendors")
//public class Vendor {
//
//    @Id
//    @Column(name = "vendor_id", length = 10)
//    private String vendorId;
//
//    @Column(name = "vendor_name", nullable = false, length = 100)
//    private String vendorName;
//
//    @Column(name = "vendor_email", nullable = false, unique = true, length = 100)
//    private String vendorEmail;
//
//    @Column(name = "vendor_contact", nullable = false, length = 15)
//    private String vendorContact;
//
//    @Column(name = "vendor_location", length = 100)
//    private String vendorLocation;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Column(name = "vendor_password", nullable = false, length = 255)
//    private String vendorPassword;
//
//    // Bank Details
//    @Column(name = "bank_account_number", length = 30)
//    private String bankAccountNumber;
//
//    @Column(name = "ifsc_code", length = 20)
//    private String ifscCode;
//
//    @Column(name = "account_holder_name", length = 100)
//    private String accountHolderName;
//
//    @Column(name = "bank_name", length = 100)
//    private String bankName;
//
//    // Business Details
//    @Column(name = "pan", length = 20)
//    private String pan;
//
//    @Column(name = "gst", length = 30)
//    private String gst;
//
//    // Rating
//    @Column(name = "rating", precision = 3, scale = 2)
//    private BigDecimal rating;
//
//    // Status
//    @Column(name = "status", length = 10)
//    private String status = "INACTIVE";
//
//    // Audit Details
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "created_by", nullable = false, length = 100)
//    private String createdBy;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "updated_by", length = 100)
//    private String updatedBy;
//
//    // Default Constructor
//    public Vendor() {
//    }
//
//    // Getters and Setters
//
//    public String getVendorId() {
//        return vendorId;
//    }
//
//    public void setVendorId(String vendorId) {
//        this.vendorId = vendorId;
//    }
//
//    public String getVendorName() {
//        return vendorName;
//    }
//
//    public void setVendorName(String vendorName) {
//        this.vendorName = vendorName;
//    }
//
//    public String getVendorEmail() {
//        return vendorEmail;
//    }
//
//    public void setVendorEmail(String vendorEmail) {
//        this.vendorEmail = vendorEmail;
//    }
//
//    public String getVendorContact() {
//        return vendorContact;
//    }
//
//    public void setVendorContact(String vendorContact) {
//        this.vendorContact = vendorContact;
//    }
//
//    public String getVendorLocation() {
//        return vendorLocation;
//    }
//
//    public void setVendorLocation(String vendorLocation) {
//        this.vendorLocation = vendorLocation;
//    }
//
//    public String getVendorPassword() {
//        return vendorPassword;
//    }
//
//    public void setVendorPassword(String vendorPassword) {
//        this.vendorPassword = vendorPassword;
//    }
//
//    public String getBankAccountNumber() {
//        return bankAccountNumber;
//    }
//
//    public void setBankAccountNumber(String bankAccountNumber) {
//        this.bankAccountNumber = bankAccountNumber;
//    }
//
//    public String getIfscCode() {
//        return ifscCode;
//    }
//
//    public void setIfscCode(String ifscCode) {
//        this.ifscCode = ifscCode;
//    }
//
//    public String getAccountHolderName() {
//        return accountHolderName;
//    }
//
//    public void setAccountHolderName(String accountHolderName) {
//        this.accountHolderName = accountHolderName;
//    }
//
//    public String getBankName() {
//        return bankName;
//    }
//
//    public void setBankName(String bankName) {
//        this.bankName = bankName;
//    }
//
//    public String getPan() {
//        return pan;
//    }
//
//    public void setPan(String pan) {
//        this.pan = pan;
//    }
//
//    public String getGst() {
//        return gst;
//    }
//
//    public void setGst(String gst) {
//        this.gst = gst;
//    }
//
//    // Rating Getter and Setter
//    public BigDecimal getRating() {
//        return rating;
//    }
//
//    public void setRating(BigDecimal rating) {
//        this.rating = rating;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getUpdatedBy() {
//        return updatedBy;
//    }
//
//    public void setUpdatedBy(String updatedBy) {
//        this.updatedBy = updatedBy;
//    }
//}