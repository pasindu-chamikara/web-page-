package com.sliit.goldenpalmresort.service;

import com.sliit.goldenpalmresort.dto.RefundRequest;
import com.sliit.goldenpalmresort.dto.RefundResponse;
import com.sliit.goldenpalmresort.dto.RefundUpdateRequest;
import com.sliit.goldenpalmresort.exception.ResourceNotFoundException;
import com.sliit.goldenpalmresort.model.*;
import com.sliit.goldenpalmresort.repository.BookingRepository;
import com.sliit.goldenpalmresort.repository.EventBookingRepository;
import com.sliit.goldenpalmresort.repository.PaymentRepository;
import com.sliit.goldenpalmresort.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RefundService {
    
    @Autowired
    private RefundRepository refundRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    
    public RefundResponse createRefund(RefundRequest refundRequest, String requestedBy) {
        // Validate payment exists
        Payment payment = paymentRepository.findById(refundRequest.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + refundRequest.getPaymentId()));
        
        // Validate payment is eligible for refund
        if (!isPaymentEligibleForRefund(payment)) {
            throw new IllegalArgumentException("Payment is not eligible for refund");
        }
        
        // Validate refund amount
        validateRefundAmount(payment, refundRequest.getRefundAmount());
        
        // Create refund
        Refund refund = new Refund();
        refund.setPayment(payment);
        refund.setRefundAmount(refundRequest.getRefundAmount());
        refund.setRefundType(refundRequest.getRefundType());
        refund.setRefundReason(refundRequest.getRefundReason());
        refund.setRefundMethod(refundRequest.getRefundMethod());
        refund.setNotes(refundRequest.getNotes());
        refund.setRequestedBy(requestedBy);
        refund.setRefundStatus(Refund.RefundStatus.PENDING);
        
        // Set booking or event booking reference
        if (payment.getBooking() != null) {
            refund.setBooking(payment.getBooking());
        } else if (payment.getEventBooking() != null) {
            refund.setEventBooking(payment.getEventBooking());
        }
        
        Refund savedRefund = refundRepository.save(refund);
        
        // Update payment status if full refund
        if (refundRequest.getRefundAmount().compareTo(payment.getAmount()) == 0) {
            payment.setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        } else {
            payment.setPaymentStatus(Payment.PaymentStatus.PARTIALLY_REFUNDED);
        }
        paymentRepository.save(payment);
        
        return new RefundResponse(savedRefund);
    }
    
    public RefundResponse updateRefund(Long refundId, RefundUpdateRequest updateRequest, String processedBy) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + refundId));
        
        // Update refund status
        if (updateRequest.getRefundStatus() != null) {
            refund.setRefundStatus(updateRequest.getRefundStatus());
            
            // Set appropriate dates based on status
            switch (updateRequest.getRefundStatus()) {
                case APPROVED:
                    refund.setApprovedBy(processedBy);
                    refund.setApprovedDate(LocalDateTime.now());
                    break;
                case PROCESSING:
                    refund.setProcessedBy(processedBy);
                    refund.setProcessedDate(LocalDateTime.now());
                    break;
                case COMPLETED:
                    refund.setRefundDate(LocalDateTime.now());
                    break;
                case PENDING:
                case REJECTED:
                case CANCELLED:
                case FAILED:
                default:
                    // No specific date setting for these statuses
                    break;
            }
        }
        
        // Update other fields
        if (updateRequest.getRefundMethod() != null) {
            refund.setRefundMethod(updateRequest.getRefundMethod());
        }
        if (updateRequest.getRefundTransactionId() != null) {
            refund.setRefundTransactionId(updateRequest.getRefundTransactionId());
        }
        if (updateRequest.getAdminNotes() != null) {
            refund.setAdminNotes(updateRequest.getAdminNotes());
        }
        if (updateRequest.getNotes() != null) {
            refund.setNotes(updateRequest.getNotes());
        }
        if (updateRequest.getRefundAmount() != null) {
            refund.setRefundAmount(updateRequest.getRefundAmount());
        }
        
        Refund updatedRefund = refundRepository.save(refund);
        return new RefundResponse(updatedRefund);
    }
    
    public RefundResponse getRefundById(Long refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + refundId));
        return new RefundResponse(refund);
    }
    
    public RefundResponse getRefundByReference(String refundReference) {
        Refund refund = refundRepository.findByRefundReference(refundReference)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with reference: " + refundReference));
        return new RefundResponse(refund);
    }
    
    public List<RefundResponse> getAllRefunds() {
        return refundRepository.findAll().stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getRefundsByStatus(Refund.RefundStatus status) {
        return refundRepository.findByRefundStatus(status).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getRefundsByUser(String requestedBy) {
        return refundRepository.findByRequestedBy(requestedBy).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getRefundsByPayment(Long paymentId) {
        return refundRepository.findByPaymentId(paymentId).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getRefundsByBooking(Long bookingId) {
        return refundRepository.findByBookingId(bookingId).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getRefundsByEventBooking(Long eventBookingId) {
        return refundRepository.findByEventBookingId(eventBookingId).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RefundResponse> getPendingRefunds() {
        return refundRepository.findByRefundStatus(Refund.RefundStatus.PENDING).stream()
                .map(RefundResponse::new)
                .collect(Collectors.toList());
    }
    
    public void cancelRefund(Long refundId, String cancelledBy) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + refundId));
        
        if (refund.getRefundStatus() == Refund.RefundStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot cancel a completed refund");
        }
        
        refund.setRefundStatus(Refund.RefundStatus.CANCELLED);
        refund.setProcessedBy(cancelledBy);
        refund.setProcessedDate(LocalDateTime.now());
        refundRepository.save(refund);
        
        // Revert payment status if it was changed
        Payment payment = refund.getPayment();
        if (payment.getPaymentStatus() == Payment.PaymentStatus.REFUNDED || 
            payment.getPaymentStatus() == Payment.PaymentStatus.PARTIALLY_REFUNDED) {
            payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
            paymentRepository.save(payment);
        }
    }
    
    public BigDecimal getTotalRefundAmountByStatus(Refund.RefundStatus status) {
        BigDecimal total = refundRepository.sumRefundAmountByStatus(status);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public long getRefundCountByStatus(Refund.RefundStatus status) {
        return refundRepository.countByRefundStatus(status);
    }
    
    private boolean isPaymentEligibleForRefund(Payment payment) {
        // Payment must be completed
        if (payment.getPaymentStatus() != Payment.PaymentStatus.COMPLETED) {
            return false;
        }
        
        // Check if payment is not already fully refunded
        if (payment.getPaymentStatus() == Payment.PaymentStatus.REFUNDED) {
            return false;
        }
        
        // Check if booking/event booking is not already checked out or cancelled
        if (payment.getBooking() != null) {
            Booking booking = payment.getBooking();
            return booking.getStatus() != Booking.BookingStatus.CHECKED_OUT && 
                   booking.getStatus() != Booking.BookingStatus.CANCELLED;
        } else if (payment.getEventBooking() != null) {
            EventBooking eventBooking = payment.getEventBooking();
            return eventBooking.getStatus() != EventBooking.EventBookingStatus.CANCELLED;
        }
        
        return true;
    }
    
    private void validateRefundAmount(Payment payment, BigDecimal refundAmount) {
        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Refund amount must be greater than zero");
        }
        
        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot exceed payment amount");
        }
        
        // Check existing refunds for this payment
        List<Refund> existingRefunds = refundRepository.findActiveRefundsByPaymentId(payment.getId());
        BigDecimal totalRefunded = existingRefunds.stream()
                .map(Refund::getRefundAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalRefunded.add(refundAmount).compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Total refund amount cannot exceed payment amount");
        }
    }
}
