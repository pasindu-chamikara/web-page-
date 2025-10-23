package com.sliit.goldenpalmresort.dto;

import com.sliit.goldenpalmresort.model.Refund;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RefundResponse {
    
    private Long id;
    private String refundReference;
    private Long paymentId;
    private Long bookingId;
    private Long eventBookingId;
    private String bookingReference;
    private String eventBookingReference;
    private BigDecimal refundAmount;
    private Refund.RefundStatus refundStatus;
    private Refund.RefundType refundType;
    private String refundReason;
    private String refundMethod;
    private String refundTransactionId;
    private String requestedBy;
    private String processedBy;
    private String approvedBy;
    private LocalDateTime requestedDate;
    private LocalDateTime processedDate;
    private LocalDateTime approvedDate;
    private LocalDateTime refundDate;
    private String notes;
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Payment details
    private BigDecimal originalPaymentAmount;
    private String paymentMethod;
    private String paymentTransactionId;
    
    // User details
    private String customerName;
    private String customerEmail;
    
    // Constructors
    public RefundResponse() {}
    
    public RefundResponse(Refund refund) {
        this.id = refund.getId();
        this.refundReference = refund.getRefundReference();
        this.paymentId = refund.getPayment() != null ? refund.getPayment().getId() : null;
        this.bookingId = refund.getBooking() != null ? refund.getBooking().getId() : null;
        this.eventBookingId = refund.getEventBooking() != null ? refund.getEventBooking().getId() : null;
        this.bookingReference = refund.getBooking() != null ? refund.getBooking().getBookingReference() : null;
        this.eventBookingReference = refund.getEventBooking() != null ? refund.getEventBooking().getBookingReference() : null;
        this.refundAmount = refund.getRefundAmount();
        this.refundStatus = refund.getRefundStatus();
        this.refundType = refund.getRefundType();
        this.refundReason = refund.getRefundReason();
        this.refundMethod = refund.getRefundMethod();
        this.refundTransactionId = refund.getRefundTransactionId();
        this.requestedBy = refund.getRequestedBy();
        this.processedBy = refund.getProcessedBy();
        this.approvedBy = refund.getApprovedBy();
        this.requestedDate = refund.getRequestedDate();
        this.processedDate = refund.getProcessedDate();
        this.approvedDate = refund.getApprovedDate();
        this.refundDate = refund.getRefundDate();
        this.notes = refund.getNotes();
        this.adminNotes = refund.getAdminNotes();
        this.createdAt = refund.getCreatedAt();
        this.updatedAt = refund.getUpdatedAt();
        
        // Set payment details
        if (refund.getPayment() != null) {
            this.originalPaymentAmount = refund.getPayment().getAmount();
            this.paymentMethod = refund.getPayment().getPaymentMethod().name();
            this.paymentTransactionId = refund.getPayment().getTransactionId();
        }
        
        // Set user details
        if (refund.getBooking() != null && refund.getBooking().getUser() != null) {
            this.customerName = refund.getBooking().getUser().getFirstName() + " " + refund.getBooking().getUser().getLastName();
            this.customerEmail = refund.getBooking().getUser().getEmail();
        } else if (refund.getEventBooking() != null && refund.getEventBooking().getUser() != null) {
            this.customerName = refund.getEventBooking().getUser().getFirstName() + " " + refund.getEventBooking().getUser().getLastName();
            this.customerEmail = refund.getEventBooking().getUser().getEmail();
        }
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
    
    public Long getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public Long getEventBookingId() {
        return eventBookingId;
    }
    
    public void setEventBookingId(Long eventBookingId) {
        this.eventBookingId = eventBookingId;
    }
    
    public String getBookingReference() {
        return bookingReference;
    }
    
    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
    
    public String getEventBookingReference() {
        return eventBookingReference;
    }
    
    public void setEventBookingReference(String eventBookingReference) {
        this.eventBookingReference = eventBookingReference;
    }
    
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public Refund.RefundStatus getRefundStatus() {
        return refundStatus;
    }
    
    public void setRefundStatus(Refund.RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    public Refund.RefundType getRefundType() {
        return refundType;
    }
    
    public void setRefundType(Refund.RefundType refundType) {
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
    
    public BigDecimal getOriginalPaymentAmount() {
        return originalPaymentAmount;
    }
    
    public void setOriginalPaymentAmount(BigDecimal originalPaymentAmount) {
        this.originalPaymentAmount = originalPaymentAmount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }
    
    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

