// Refund Management JavaScript Functions

class RefundManager {
    constructor() {
        this.baseUrl = '/api/refunds';
        this.token = localStorage.getItem('authToken');
    }

    async makeRequest(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const defaultOptions = {
            headers: {
                'Authorization': `Bearer ${this.token}`,
                'Content-Type': 'application/json'
            }
        };

        const response = await fetch(url, { ...defaultOptions, ...options });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.error || 'Request failed');
        }

        return response.json();
    }

    // Get all refunds
    async getAllRefunds() {
        return this.makeRequest('');
    }

    // Get refund by ID
    async getRefundById(id) {
        return this.makeRequest(`/${id}`);
    }

    // Get refund by reference
    async getRefundByReference(reference) {
        return this.makeRequest(`/reference/${reference}`);
    }

    // Create refund
    async createRefund(refundData) {
        return this.makeRequest('', {
            method: 'POST',
            body: JSON.stringify(refundData)
        });
    }

    // Update refund
    async updateRefund(id, updateData) {
        return this.makeRequest(`/${id}`, {
            method: 'PUT',
            body: JSON.stringify(updateData)
        });
    }

    // Approve refund
    async approveRefund(id, adminNotes = '') {
        return this.makeRequest(`/${id}/approve`, {
            method: 'POST',
            body: JSON.stringify({ adminNotes })
        });
    }

    // Reject refund
    async rejectRefund(id, adminNotes = '') {
        return this.makeRequest(`/${id}/reject`, {
            method: 'POST',
            body: JSON.stringify({ adminNotes })
        });
    }

    // Process refund
    async processRefund(id, processData) {
        return this.makeRequest(`/${id}/process`, {
            method: 'POST',
            body: JSON.stringify(processData)
        });
    }

    // Complete refund
    async completeRefund(id, adminNotes = '') {
        return this.makeRequest(`/${id}/complete`, {
            method: 'POST',
            body: JSON.stringify({ adminNotes })
        });
    }

    // Cancel refund
    async cancelRefund(id) {
        return this.makeRequest(`/${id}/cancel`, {
            method: 'POST'
        });
    }

    // Get refunds by status
    async getRefundsByStatus(status) {
        return this.makeRequest(`/status/${status}`);
    }

    // Get pending refunds
    async getPendingRefunds() {
        return this.makeRequest('/pending');
    }

    // Get refunds by user
    async getRefundsByUser(username) {
        return this.makeRequest(`/user/${username}`);
    }

    // Get refunds by payment
    async getRefundsByPayment(paymentId) {
        return this.makeRequest(`/payment/${paymentId}`);
    }

    // Get refunds by booking
    async getRefundsByBooking(bookingId) {
        return this.makeRequest(`/booking/${bookingId}`);
    }

    // Get refunds by event booking
    async getRefundsByEventBooking(eventBookingId) {
        return this.makeRequest(`/event-booking/${eventBookingId}`);
    }

    // Get refund statistics
    async getRefundStats() {
        return this.makeRequest('/stats');
    }

    // Format currency
    formatCurrency(amount) {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    }

    // Format date
    formatDate(dateString) {
        return new Date(dateString).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    // Get status badge class
    getStatusClass(status) {
        const statusClasses = {
            'PENDING': 'bg-warning',
            'APPROVED': 'bg-info',
            'PROCESSING': 'bg-primary',
            'COMPLETED': 'bg-success',
            'REJECTED': 'bg-danger',
            'CANCELLED': 'bg-secondary',
            'FAILED': 'bg-danger'
        };
        return statusClasses[status] || 'bg-secondary';
    }

    // Get status icon
    getStatusIcon(status) {
        const statusIcons = {
            'PENDING': 'fas fa-clock',
            'APPROVED': 'fas fa-check-circle',
            'PROCESSING': 'fas fa-cog',
            'COMPLETED': 'fas fa-check-double',
            'REJECTED': 'fas fa-times-circle',
            'CANCELLED': 'fas fa-ban',
            'FAILED': 'fas fa-exclamation-triangle'
        };
        return statusIcons[status] || 'fas fa-question-circle';
    }

    // Validate refund amount
    validateRefundAmount(amount, maxAmount) {
        if (isNaN(amount) || amount <= 0) {
            return 'Refund amount must be greater than 0';
        }
        if (amount > maxAmount) {
            return `Refund amount cannot exceed ${this.formatCurrency(maxAmount)}`;
        }
        return null;
    }

    // Show notification
    showNotification(message, type = 'info') {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
        notification.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
        notification.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        document.body.appendChild(notification);
        
        // Auto remove after 5 seconds
        setTimeout(() => {
            if (notification.parentNode) {
                notification.parentNode.removeChild(notification);
            }
        }, 5000);
    }

    // Show loading spinner
    showLoading(element) {
        const originalContent = element.innerHTML;
        element.innerHTML = `
            <div class="d-flex justify-content-center align-items-center">
                <div class="spinner-border spinner-border-sm me-2" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                Loading...
            </div>
        `;
        return originalContent;
    }

    // Hide loading spinner
    hideLoading(element, originalContent) {
        element.innerHTML = originalContent;
    }

    // Confirm action
    async confirmAction(message, action) {
        if (confirm(message)) {
            try {
                await action();
                this.showNotification('Action completed successfully', 'success');
            } catch (error) {
                this.showNotification(`Error: ${error.message}`, 'danger');
            }
        }
    }

    // Filter refunds
    filterRefunds(refunds, filters) {
        return refunds.filter(refund => {
            if (filters.status && refund.refundStatus !== filters.status) {
                return false;
            }
            if (filters.type && refund.refundType !== filters.type) {
                return false;
            }
            if (filters.dateFrom && new Date(refund.requestedDate) < new Date(filters.dateFrom)) {
                return false;
            }
            if (filters.dateTo && new Date(refund.requestedDate) > new Date(filters.dateTo)) {
                return false;
            }
            return true;
        });
    }

    // Export refunds to CSV
    exportToCSV(refunds, filename = 'refunds.csv') {
        if (refunds.length === 0) {
            this.showNotification('No data to export', 'warning');
            return;
        }

        const headers = [
            'Refund ID',
            'Payment ID',
            'Booking Reference',
            'Customer Name',
            'Refund Amount',
            'Refund Type',
            'Status',
            'Requested Date',
            'Processed Date',
            'Refund Date'
        ];

        const csvContent = [
            headers.join(','),
            ...refunds.map(refund => [
                refund.refundReference,
                refund.paymentId,
                refund.bookingReference || refund.eventBookingReference || '',
                refund.customerName || '',
                refund.refundAmount,
                refund.refundType,
                refund.refundStatus,
                this.formatDate(refund.requestedDate),
                refund.processedDate ? this.formatDate(refund.processedDate) : '',
                refund.refundDate ? this.formatDate(refund.refundDate) : ''
            ].join(','))
        ].join('\n');

        const blob = new Blob([csvContent], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = filename;
        link.click();
        window.URL.revokeObjectURL(url);
    }

    // Generate refund report
    async generateReport(filters = {}) {
        try {
            const refunds = await this.getAllRefunds();
            const filteredRefunds = this.filterRefunds(refunds, filters);
            
            const report = {
                generatedAt: new Date().toISOString(),
                totalRefunds: filteredRefunds.length,
                totalAmount: filteredRefunds.reduce((sum, refund) => sum + parseFloat(refund.refundAmount), 0),
                statusBreakdown: {},
                typeBreakdown: {},
                refunds: filteredRefunds
            };

            // Calculate status breakdown
            filteredRefunds.forEach(refund => {
                const status = refund.refundStatus;
                if (!report.statusBreakdown[status]) {
                    report.statusBreakdown[status] = { count: 0, amount: 0 };
                }
                report.statusBreakdown[status].count++;
                report.statusBreakdown[status].amount += parseFloat(refund.refundAmount);
            });

            // Calculate type breakdown
            filteredRefunds.forEach(refund => {
                const type = refund.refundType;
                if (!report.typeBreakdown[type]) {
                    report.typeBreakdown[type] = { count: 0, amount: 0 };
                }
                report.typeBreakdown[type].count++;
                report.typeBreakdown[type].amount += parseFloat(refund.refundAmount);
            });

            return report;
        } catch (error) {
            console.error('Error generating report:', error);
            throw error;
        }
    }
}

// Initialize refund manager
const refundManager = new RefundManager();

// Utility functions for common operations
function formatRefundStatus(status) {
    return status.replace('_', ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
}

function formatRefundType(type) {
    return type.replace('_', ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
}

function getRefundPriority(status) {
    const priorities = {
        'PENDING': 1,
        'APPROVED': 2,
        'PROCESSING': 3,
        'COMPLETED': 4,
        'REJECTED': 5,
        'CANCELLED': 6,
        'FAILED': 7
    };
    return priorities[status] || 8;
}

function sortRefunds(refunds, sortBy = 'requestedDate', order = 'desc') {
    return refunds.sort((a, b) => {
        let aValue, bValue;
        
        switch (sortBy) {
            case 'amount':
                aValue = parseFloat(a.refundAmount);
                bValue = parseFloat(b.refundAmount);
                break;
            case 'status':
                aValue = getRefundPriority(a.refundStatus);
                bValue = getRefundPriority(b.refundStatus);
                break;
            case 'requestedDate':
            default:
                aValue = new Date(a.requestedDate);
                bValue = new Date(b.requestedDate);
                break;
        }
        
        if (order === 'asc') {
            return aValue > bValue ? 1 : -1;
        } else {
            return aValue < bValue ? 1 : -1;
        }
    });
}

// Export functions for use in HTML pages
window.RefundManager = RefundManager;
window.refundManager = refundManager;
window.formatRefundStatus = formatRefundStatus;
window.formatRefundType = formatRefundType;
window.sortRefunds = sortRefunds;

