package com.example.demo.Models;

import java.sql.Date;

public class PaymentDetails {
    private String userId;
    private String subscriptionId;
    private Date startDate;
    private Date endDate;
    private String paymemntStatus;
    private String paymentType;
    private String status;


    // Getter Methods

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    // Setter Methods

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPaymemntStatus() {
        return paymemntStatus;
    }

    public void setPaymemntStatus(String paymemntStatus) {
        this.paymemntStatus = paymemntStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}