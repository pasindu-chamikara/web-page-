-- Create refunds table
CREATE TABLE refunds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refund_reference VARCHAR(50) UNIQUE NOT NULL,
    payment_id BIGINT NOT NULL,
    booking_id BIGINT,
    event_booking_id BIGINT,
    refund_amount DECIMAL(10,2) NOT NULL,
    refund_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    refund_type VARCHAR(30) NOT NULL,
    refund_reason TEXT NOT NULL,
    refund_method VARCHAR(50),
    refund_transaction_id VARCHAR(100),
    requested_by VARCHAR(100),
    processed_by VARCHAR(100),
    approved_by VARCHAR(100),
    requested_date TIMESTAMP,
    processed_date TIMESTAMP,
    approved_date TIMESTAMP,
    refund_date TIMESTAMP,
    notes TEXT,
    admin_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE SET NULL,
    FOREIGN KEY (event_booking_id) REFERENCES event_bookings(id) ON DELETE SET NULL,
    
    INDEX idx_refund_reference (refund_reference),
    INDEX idx_payment_id (payment_id),
    INDEX idx_booking_id (booking_id),
    INDEX idx_event_booking_id (event_booking_id),
    INDEX idx_refund_status (refund_status),
    INDEX idx_refund_type (refund_type),
    INDEX idx_requested_by (requested_by),
    INDEX idx_requested_date (requested_date),
    INDEX idx_refund_date (refund_date)
);

-- Add constraints for refund status and type
ALTER TABLE refunds 
ADD CONSTRAINT chk_refund_status 
CHECK (refund_status IN ('PENDING', 'APPROVED', 'REJECTED', 'PROCESSING', 'COMPLETED', 'FAILED', 'CANCELLED'));

ALTER TABLE refunds 
ADD CONSTRAINT chk_refund_type 
CHECK (refund_type IN ('FULL_REFUND', 'PARTIAL_REFUND', 'CANCELLATION_REFUND', 'NO_SHOW_REFUND', 'SERVICE_ISSUE_REFUND'));

-- Add constraint to ensure at least one booking reference is present
ALTER TABLE refunds 
ADD CONSTRAINT chk_booking_reference 
CHECK (booking_id IS NOT NULL OR event_booking_id IS NOT NULL);

-- Add constraint to ensure refund amount is positive
ALTER TABLE refunds 
ADD CONSTRAINT chk_refund_amount 
CHECK (refund_amount > 0);

