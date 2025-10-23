package com.sliit.goldenpalmresort.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
public class Refund {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "refund_reference", unique = true, nullable = false)
    private String refundReference;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_booking_id")
    private EventBooking eventBooking;
    
    @Column(name = "refund_amount", nullable = false)
    private BigDecimal refundAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status", nullable = false)
    private RefundStatus refundStatus = RefundStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "refund_type", nullable = false)
    private RefundType refundType;
    
    @Column(name = "refund_reason", nullable = false)
    private String refundReason;
    
    @Column(name = "refund_method")
    private String refundMethod;
    
    @Column(name = "refund_transaction_id")
    private String refundTransactionId;
    
    @Column(name = "requested_by")
    private String requestedBy;
    
    @Column(name = "processed_by")
    private String processedBy;
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "requested_date")
    private LocalDateTime requestedDate;
    
    @Column(name = "processed_date")
    private LocalDateTime processedDate;
    
    @Column(name = "approved_date")
    private LocalDateTime approvedDate;
    
    @Column(name = "refund_date")
    private LocalDateTime refundDate;
    
    @Column(name = "notes")
    private String notes;
    
    @Column(name = "admin_notes")
    private String adminNotes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum RefundStatus {
        PENDING, APPROVED, REJECTED, PROCESSING, COMPLETED, FAILED, CANCELLED
    }
    
    public enum RefundType {
        FULL_REFUND, PARTIAL_REFUND, CANCELLATION_REFUND, NO_SHOW_REFUND, SERVICE_ISSUE_REFUND
    }
    
    // Constructors
    public Refund() {}
    
    public Refund(Payment payment, BigDecimal refundAmount, RefundType refundType, String refundReason) {
        this.payment = payment;
        this.refundAmount = refundAmount;
        this.refundType = refundType;
        this.refundReason = refundReason;
        this.refundStatus = RefundStatus.PENDING;
        this.refundReference = generateRefundReference();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (refundReference == null) {
            refundReference = generateRefundReference();
        }
        if (requestedDate == null) {
            requestedDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateRefundReference() {
        return "RF" + LocalDateTime.now().getYear() + 
               String.format("%02d", LocalDateTime.now().getMonthValue()) +
               String.format("%02d", LocalDateTime.now().getDayOfMonth()) +
               String.format("%03d", (int)(Math.random() * 1000));
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRefundReference() {
        return refundReference;
    }
    
    public void setRefundReference(String refundReference) {
        this.refundReference = refundReference;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public EventBooking getEventBooking() {
        return eventBooking;
    }
    
    public void setEventBooking(EventBooking eventBooking) {
        this.eventBooking = eventBooking;
    }
    
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public RefundStatus getRefundStatus() {
        return refundStatus;
    }
    
    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    public RefundType getRefundType() {
        return refundType;
    }
    
    public void setRefundType(RefundType refundType) {
        this.refundType = refundType;
    }
    
    public String getRefundReason() {
        return refundReason;
    }
    
    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
    
    public String getRefundMethod() {
        return refundMethod;
    }
    
    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }
    
    public String getRefundTransactionId() {
        return refundTransactionId;
    }
    
    public void setRefundTransactionId(String refundTransactionId) {
        this.refundTransactionId = refundTransactionId;
    }
    
    public String getRequestedBy() {
        return requestedBy;
    }
    
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    
    public String getProcessedBy() {
        return processedBy;
    }
    
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
    
    public String getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }
    
    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }
    
    public LocalDateTime getProcessedDate() {
        return processedDate;
    }
    
    public void setProcessedDate(LocalDateTime processedDate) {
        this.processedDate = processedDate;
    }
    
    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }
    
    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }
    
    public LocalDateTime getRefundDate() {
        return refundDate;
    }
    
    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getAdminNotes() {
        return adminNotes;
    }
    
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

