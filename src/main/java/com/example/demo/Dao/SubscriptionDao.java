package com.example.demo.Dao;

import com.example.demo.Models.Constants;
import com.example.demo.Models.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public SubscriptionDao() {
    }

    public String updateStatus(PaymentDetails paymentDetails) {
        int iKey = 0;
        String userId = paymentDetails.getUserId();
        final String updateProfile = "UPDATE paymentDetailTable SET status=? WHERE userId = ?";
        iKey = jdbcTemplate.update(updateProfile,
                paymentDetails.getStatus()
                , userId);
        if (iKey == Constants.SUCCESS_KEY) {
            return userId;
        }
        return userId;
    }
}