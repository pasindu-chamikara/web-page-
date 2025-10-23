package com.sliit.goldenpalmresort.dto;

import com.sliit.goldenpalmresort.model.Refund;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RefundRequest {
    
    @NotNull(message = "Payment ID is required")
    private Long paymentId;
    
    @NotNull(message = "Refund amount is required")
    @DecimalMin(value = "0.01", message = "Refund amount must be greater than 0")
    private BigDecimal refundAmount;
    
    @NotNull(message = "Refund type is required")
    private Refund.RefundType refundType;
    
    @NotBlank(message = "Refund reason is required")
    private String refundReason;
    
    private String refundMethod;
    
    private String notes;
    
    private Long bookingId;
    
    private Long eventBookingId;
    
    // Constructors
    public RefundRequest() {}
    
    public RefundRequest(Long paymentId, BigDecimal refundAmount, Refund.RefundType refundType, String refundReason) {
        this.paymentId = paymentId;
        this.refundAmount = refundAmount;
        this.refundType = refundType;
        this.refundReason = refundReason;
    }
    
    // Getters and Setters
    public Long getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
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
}

