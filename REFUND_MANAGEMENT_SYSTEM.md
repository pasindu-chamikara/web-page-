# Refund Management System - Golden Palm Resort

## Overview

The Refund Management System is a comprehensive solution for handling refund requests and processing refunds for the Golden Palm Resort booking system. It supports both room bookings and event bookings with a complete workflow from request to completion.

## Features

### Core Functionality
- **Refund Request Creation**: Users can create refund requests for eligible payments
- **Multi-level Approval Process**: Pending → Approved → Processing → Completed workflow
- **Multiple Refund Types**: Full, Partial, Cancellation, No-show, and Service Issue refunds
- **Role-based Access Control**: Different permissions for Admin, Manager, Front Desk, and Payment Officer roles
- **Comprehensive Tracking**: Complete audit trail with timestamps and user tracking
- **Statistics Dashboard**: Real-time statistics and reporting

### Refund Types
1. **Full Refund**: Complete refund of the payment amount
2. **Partial Refund**: Partial refund of the payment amount
3. **Cancellation Refund**: Refund due to booking cancellation
4. **No Show Refund**: Refund due to customer no-show
5. **Service Issue Refund**: Refund due to service quality issues

### Refund Status Workflow
1. **PENDING**: Initial status when refund is requested
2. **APPROVED**: Refund has been approved by management
3. **PROCESSING**: Refund is being processed by payment officer
4. **COMPLETED**: Refund has been successfully processed
5. **REJECTED**: Refund request has been rejected
6. **CANCELLED**: Refund request has been cancelled
7. **FAILED**: Refund processing failed

## Technical Architecture

### Backend Components

#### Models
- **Refund**: Main entity with comprehensive refund information
- **Payment**: Updated with refund relationships
- **Booking**: Updated with refund relationships
- **EventBooking**: Updated with refund relationships

#### Repositories
- **RefundRepository**: Data access layer with custom queries for filtering and statistics

#### Services
- **RefundService**: Business logic layer with validation and workflow management

#### Controllers
- **RefundController**: REST API endpoints for refund management

#### DTOs
- **RefundRequest**: Input validation for refund creation
- **RefundResponse**: Comprehensive refund information for API responses
- **RefundUpdateRequest**: Input validation for refund updates

### Frontend Components

#### Pages
- **refund-management.html**: Admin/Manager dashboard for refund management
- **refund-request.html**: Customer-facing refund request form

#### JavaScript
- **refund-management.js**: Utility functions and API integration

### Database Schema

#### Refunds Table
```sql
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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## API Endpoints

### Refund Management
- `POST /api/refunds` - Create refund request
- `GET /api/refunds` - Get all refunds
- `GET /api/refunds/{id}` - Get refund by ID
- `GET /api/refunds/reference/{refundReference}` - Get refund by reference
- `PUT /api/refunds/{id}` - Update refund
- `GET /api/refunds/status/{status}` - Get refunds by status
- `GET /api/refunds/pending` - Get pending refunds
- `GET /api/refunds/user/{username}` - Get refunds by user
- `GET /api/refunds/payment/{paymentId}` - Get refunds by payment
- `GET /api/refunds/booking/{bookingId}` - Get refunds by booking
- `GET /api/refunds/event-booking/{eventBookingId}` - Get refunds by event booking

### Refund Actions
- `POST /api/refunds/{id}/approve` - Approve refund
- `POST /api/refunds/{id}/reject` - Reject refund
- `POST /api/refunds/{id}/process` - Process refund
- `POST /api/refunds/{id}/complete` - Complete refund
- `POST /api/refunds/{id}/cancel` - Cancel refund

### Statistics
- `GET /api/refunds/stats` - Get refund statistics

## Usage Guide

### For Administrators/Managers

1. **Access Refund Management**
   - Navigate to `refund-management.html`
   - View dashboard with statistics and pending refunds

2. **Review Refund Requests**
   - Filter refunds by status, type, or date range
   - View detailed refund information
   - Approve or reject refund requests

3. **Process Refunds**
   - Update refund status through the workflow
   - Add admin notes and processing details
   - Track refund progress

### For Payment Officers

1. **Process Approved Refunds**
   - View approved refunds
   - Update refund method and transaction details
   - Mark refunds as completed

2. **Track Refund Status**
   - Monitor refund processing progress
   - Update refund transaction information

### For Customers

1. **Request Refund**
   - Navigate to `refund-request.html`
   - Enter payment ID or booking reference
   - Fill out refund request form
   - Submit refund request

2. **Track Refund Status**
   - View refund request status
   - Receive email notifications for status updates

## Security Features

### Role-based Access Control
- **Admin**: Full access to all refund operations
- **Manager**: Can approve/reject refunds and view all refunds
- **Payment Officer**: Can process and complete refunds
- **Front Desk**: Can create refund requests and view refunds

### Data Validation
- Refund amount validation against payment amount
- Payment eligibility checks
- Required field validation
- Business rule enforcement

### Audit Trail
- Complete tracking of who performed what action
- Timestamp tracking for all status changes
- Admin notes for decision documentation

## Business Rules

### Refund Eligibility
- Payment must be in COMPLETED status
- Payment cannot be already fully refunded
- Booking/Event booking must not be checked out or cancelled

### Refund Amount Validation
- Refund amount must be positive
- Refund amount cannot exceed payment amount
- Total refunds for a payment cannot exceed payment amount

### Status Transitions
- PENDING → APPROVED/REJECTED/CANCELLED
- APPROVED → PROCESSING/CANCELLED
- PROCESSING → COMPLETED/FAILED/CANCELLED
- COMPLETED → (Final state)
- REJECTED → (Final state)
- CANCELLED → (Final state)
- FAILED → PROCESSING/CANCELLED

## Configuration

### Environment Variables
- Database connection settings
- JWT token configuration
- Email notification settings (if implemented)

### Application Properties
```properties
# Refund system configuration
refund.auto-approve-threshold=100.00
refund.max-refund-days=30
refund.notification.enabled=true
```

## Testing

### Unit Tests
- Service layer business logic testing
- Repository layer data access testing
- Controller layer API endpoint testing

### Integration Tests
- End-to-end refund workflow testing
- Database transaction testing
- Security role testing

## Deployment

### Prerequisites
- Java 17+
- Spring Boot 3.x
- MySQL 8.0+
- Maven 3.6+

### Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### Database Migration
The system uses Flyway for database migrations. The refund table will be created automatically on application startup.

## Monitoring and Logging

### Logging
- Refund creation and updates
- Status changes and approvals
- Error conditions and exceptions
- Security events

### Metrics
- Refund request volume
- Processing times
- Approval rates
- Error rates

## Future Enhancements

### Planned Features
- Email notifications for status changes
- Refund analytics and reporting
- Integration with external payment processors
- Automated refund processing for certain conditions
- Mobile app support
- Refund templates for common scenarios

### Scalability Considerations
- Database indexing for performance
- Caching for frequently accessed data
- Asynchronous processing for large volumes
- Microservices architecture for high availability

## Support and Maintenance

### Troubleshooting
- Check application logs for errors
- Verify database connectivity
- Validate user permissions
- Review refund eligibility rules

### Maintenance Tasks
- Regular database cleanup
- Performance monitoring
- Security updates
- Backup and recovery procedures

## Conclusion

The Refund Management System provides a comprehensive solution for handling refunds in the Golden Palm Resort booking system. With its role-based access control, complete audit trail, and flexible workflow, it ensures efficient and secure refund processing while maintaining data integrity and compliance with business rules.

