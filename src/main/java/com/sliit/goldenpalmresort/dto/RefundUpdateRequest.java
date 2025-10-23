package com.sliit.goldenpalmresort.dto;

import com.sliit.goldenpalmresort.model.Refund;

import java.math.BigDecimal;

public class RefundUpdateRequest {
    
    private Refund.RefundStatus refundStatus;
    
    private String refundMethod;
    
    private String refundTransactionId;
    
    private String adminNotes;
    
    private String notes;
    
    private BigDecimal refundAmount;
    
    // Constructors
    public RefundUpdateRequest() {}
    
    public RefundUpdateRequest(Refund.RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    // Getters and Setters
    public Refund.RefundStatus getRefundStatus() {
        return refundStatus;
    }
    
    public void setRefundStatus(Refund.RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
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
    
    public String getAdminNotes() {
        return adminNotes;
    }
    
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}

