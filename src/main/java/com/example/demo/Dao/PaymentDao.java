package com.example.demo.Dao;

import com.example.demo.Models.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class PaymentDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public PaymentDao() {
    }

    public Integer insertPaymentDetails(PaymentDetails paymentDetails) throws SQLException {
        final String insertSignUpDetails = "INSERT INTO paymentDetailTable (userId,subscriptionId,startDate,endDate,paymentStatus,paymentType, status)"
                + "VALUES (?,?,?,?,?,?,?)";
        KeyHolder kHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                int iCount = 1;
                PreparedStatement ps = connection.prepareStatement(insertSignUpDetails,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(iCount++, paymentDetails.getUserId());
                ps.setString(iCount++, paymentDetails.getSubscriptionId());
                ps.setDate(iCount++, paymentDetails.getStartDate());
                ps.setDate(iCount++, paymentDetails.getEndDate());
                ps.setString(iCount++, paymentDetails.getPaymemntStatus());
                ps.setString(iCount++, paymentDetails.getPaymentType());
                ps.setString(iCount++, paymentDetails.getStatus());
                return ps;
            }

        }, kHolder);
        int signUpId = Integer.parseInt(kHolder.getKey().toString());
        return signUpId;
    }
}