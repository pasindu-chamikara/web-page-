package com.sliit.goldenpalmresort.controller;

import com.sliit.goldenpalmresort.dto.RefundRequest;
import com.sliit.goldenpalmresort.dto.RefundResponse;
import com.sliit.goldenpalmresort.dto.RefundUpdateRequest;
import com.sliit.goldenpalmresort.model.Refund;
import com.sliit.goldenpalmresort.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/refunds")
@CrossOrigin(origins = "*")
public class RefundController {
    
    @Autowired
    private RefundService refundService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> createRefund(@Valid @RequestBody RefundRequest refundRequest, 
                                        Authentication authentication) {
        try {
            String requestedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.createRefund(refundRequest, requestedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getAllRefunds() {
        List<RefundResponse> refunds = refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> getRefundById(@PathVariable Long id) {
        try {
            RefundResponse refund = refundService.getRefundById(id);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping("/reference/{refundReference}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> getRefundByReference(@PathVariable String refundReference) {
        try {
            RefundResponse refund = refundService.getRefundByReference(refundReference);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> updateRefund(@PathVariable Long id, 
                                        @Valid @RequestBody RefundUpdateRequest updateRequest,
                                        Authentication authentication) {
        try {
            String processedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.updateRefund(id, updateRequest, processedBy);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getRefundsByStatus(@PathVariable Refund.RefundStatus status) {
        List<RefundResponse> refunds = refundService.getRefundsByStatus(status);
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getPendingRefunds() {
        List<RefundResponse> refunds = refundService.getPendingRefunds();
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getRefundsByUser(@PathVariable String username) {
        List<RefundResponse> refunds = refundService.getRefundsByUser(username);
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/payment/{paymentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getRefundsByPayment(@PathVariable Long paymentId) {
        List<RefundResponse> refunds = refundService.getRefundsByPayment(paymentId);
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getRefundsByBooking(@PathVariable Long bookingId) {
        List<RefundResponse> refunds = refundService.getRefundsByBooking(bookingId);
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/event-booking/{eventBookingId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'FRONT_DESK', 'PAYMENT_OFFICER')")
    public ResponseEntity<List<RefundResponse>> getRefundsByEventBooking(@PathVariable Long eventBookingId) {
        List<RefundResponse> refunds = refundService.getRefundsByEventBooking(eventBookingId);
        return ResponseEntity.ok(refunds);
    }
    
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> cancelRefund(@PathVariable Long id, Authentication authentication) {
        try {
            String cancelledBy = authentication != null ? authentication.getName() : "system";
            refundService.cancelRefund(id, cancelledBy);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Refund cancelled successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getRefundStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("pendingCount", refundService.getRefundCountByStatus(Refund.RefundStatus.PENDING));
        stats.put("approvedCount", refundService.getRefundCountByStatus(Refund.RefundStatus.APPROVED));
        stats.put("completedCount", refundService.getRefundCountByStatus(Refund.RefundStatus.COMPLETED));
        stats.put("rejectedCount", refundService.getRefundCountByStatus(Refund.RefundStatus.REJECTED));
        stats.put("cancelledCount", refundService.getRefundCountByStatus(Refund.RefundStatus.CANCELLED));
        
        stats.put("pendingAmount", refundService.getTotalRefundAmountByStatus(Refund.RefundStatus.PENDING));
        stats.put("approvedAmount", refundService.getTotalRefundAmountByStatus(Refund.RefundStatus.APPROVED));
        stats.put("completedAmount", refundService.getTotalRefundAmountByStatus(Refund.RefundStatus.COMPLETED));
        stats.put("rejectedAmount", refundService.getTotalRefundAmountByStatus(Refund.RefundStatus.REJECTED));
        stats.put("cancelledAmount", refundService.getTotalRefundAmountByStatus(Refund.RefundStatus.CANCELLED));
        
        return ResponseEntity.ok(stats);
    }
    
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> approveRefund(@PathVariable Long id, 
                                         @RequestBody(required = false) Map<String, String> request,
                                         Authentication authentication) {
        try {
            RefundUpdateRequest updateRequest = new RefundUpdateRequest(Refund.RefundStatus.APPROVED);
            if (request != null && request.containsKey("adminNotes")) {
                updateRequest.setAdminNotes(request.get("adminNotes"));
            }
            
            String approvedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.updateRefund(id, updateRequest, approvedBy);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> rejectRefund(@PathVariable Long id, 
                                        @RequestBody Map<String, String> request,
                                        Authentication authentication) {
        try {
            RefundUpdateRequest updateRequest = new RefundUpdateRequest(Refund.RefundStatus.REJECTED);
            updateRequest.setAdminNotes(request.get("adminNotes"));
            
            String rejectedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.updateRefund(id, updateRequest, rejectedBy);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{id}/process")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> processRefund(@PathVariable Long id, 
                                         @RequestBody Map<String, String> request,
                                         Authentication authentication) {
        try {
            RefundUpdateRequest updateRequest = new RefundUpdateRequest(Refund.RefundStatus.PROCESSING);
            if (request.containsKey("refundMethod")) {
                updateRequest.setRefundMethod(request.get("refundMethod"));
            }
            if (request.containsKey("refundTransactionId")) {
                updateRequest.setRefundTransactionId(request.get("refundTransactionId"));
            }
            if (request.containsKey("adminNotes")) {
                updateRequest.setAdminNotes(request.get("adminNotes"));
            }
            
            String processedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.updateRefund(id, updateRequest, processedBy);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PAYMENT_OFFICER')")
    public ResponseEntity<?> completeRefund(@PathVariable Long id, 
                                          @RequestBody(required = false) Map<String, String> request,
                                          Authentication authentication) {
        try {
            RefundUpdateRequest updateRequest = new RefundUpdateRequest(Refund.RefundStatus.COMPLETED);
            if (request != null && request.containsKey("adminNotes")) {
                updateRequest.setAdminNotes(request.get("adminNotes"));
            }
            
            String completedBy = authentication != null ? authentication.getName() : "system";
            RefundResponse refund = refundService.updateRefund(id, updateRequest, completedBy);
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
