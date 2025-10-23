package com.sliit.goldenpalmresort.repository;

import com.sliit.goldenpalmresort.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    
    Optional<Refund> findByRefundReference(String refundReference);
    
    List<Refund> findByRefundStatus(Refund.RefundStatus status);
    
    List<Refund> findByRefundType(Refund.RefundType refundType);
    
    List<Refund> findByRequestedBy(String requestedBy);
    
    List<Refund> findByProcessedBy(String processedBy);
    
    List<Refund> findByApprovedBy(String approvedBy);
    
    @Query("SELECT r FROM Refund r WHERE r.payment.id = :paymentId")
    List<Refund> findByPaymentId(@Param("paymentId") Long paymentId);
    
    @Query("SELECT r FROM Refund r WHERE r.booking.id = :bookingId")
    List<Refund> findByBookingId(@Param("bookingId") Long bookingId);
    
    @Query("SELECT r FROM Refund r WHERE r.eventBooking.id = :eventBookingId")
    List<Refund> findByEventBookingId(@Param("eventBookingId") Long eventBookingId);
    
    @Query("SELECT r FROM Refund r WHERE r.requestedDate BETWEEN :startDate AND :endDate")
    List<Refund> findByRequestedDateBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT r FROM Refund r WHERE r.refundDate BETWEEN :startDate AND :endDate")
    List<Refund> findByRefundDateBetween(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT r FROM Refund r WHERE r.refundStatus IN :statuses")
    List<Refund> findByRefundStatusIn(@Param("statuses") List<Refund.RefundStatus> statuses);
    
    @Query("SELECT r FROM Refund r WHERE r.refundAmount >= :minAmount AND r.refundAmount <= :maxAmount")
    List<Refund> findByRefundAmountBetween(@Param("minAmount") java.math.BigDecimal minAmount, 
                                         @Param("maxAmount") java.math.BigDecimal maxAmount);
    
    @Query("SELECT COUNT(r) FROM Refund r WHERE r.refundStatus = :status")
    long countByRefundStatus(@Param("status") Refund.RefundStatus status);
    
    @Query("SELECT SUM(r.refundAmount) FROM Refund r WHERE r.refundStatus = :status")
    java.math.BigDecimal sumRefundAmountByStatus(@Param("status") Refund.RefundStatus status);
    
    @Query("SELECT r FROM Refund r WHERE r.refundStatus = :status AND r.requestedDate >= :fromDate")
    List<Refund> findPendingRefundsSince(@Param("status") Refund.RefundStatus status, 
                                       @Param("fromDate") LocalDateTime fromDate);
    
    @Query("SELECT r FROM Refund r WHERE r.payment.id = :paymentId AND r.refundStatus != 'CANCELLED'")
    List<Refund> findActiveRefundsByPaymentId(@Param("paymentId") Long paymentId);
    
    @Query("SELECT r FROM Refund r WHERE r.booking.id = :bookingId AND r.refundStatus != 'CANCELLED'")
    List<Refund> findActiveRefundsByBookingId(@Param("bookingId") Long bookingId);
    
    @Query("SELECT r FROM Refund r WHERE r.eventBooking.id = :eventBookingId AND r.refundStatus != 'CANCELLED'")
    List<Refund> findActiveRefundsByEventBookingId(@Param("eventBookingId") Long eventBookingId);
}

