package com.example.demo.Dao;

import com.example.demo.Models.UserDetails;
import com.example.demo.Service.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Map;

@Repository
public class SignUpDao {


    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PasswordUtils passwordUtils;
    Logger _logger = LoggerFactory.getLogger(SignUpDao.class);

    public SignUpDao() {
    }

    public Integer insertSignUpDetails(UserDetails userDetails) throws SQLException {
        String myPassword = userDetails.getPassword();
        // Generate Salt. The generated value can be stored in DB.
        _logger.info("myPassword" + " " + myPassword);
        String salt = PasswordUtils.getSalt(30);
        // Protect user's password. The generated value can be stored in DB.
        _logger.info("salt" + " " + salt);
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
        // Print out protected password
        _logger.info("mySecurePassword" + " " + mySecurePassword);
        final String insertSignUpDetails = "INSERT INTO signUpTable (firstName,lastName,companyName,address,emailId,password, birthDate, salt)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder kHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                int iCount = 1;
                PreparedStatement ps = connection.prepareStatement(insertSignUpDetails,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(iCount++, userDetails.getFirstName());
                ps.setString(iCount++, userDetails.getLastName());
                ps.setString(iCount++, userDetails.getCompanyName());
                ps.setString(iCount++, userDetails.getAddress());
                ps.setString(iCount++, userDetails.getEmailId());
                ps.setString(iCount++, mySecurePassword);
                ps.setDate(iCount++, userDetails.getBirthDate());
                ps.setString(iCount++, salt);
                return ps;
            }

        }, kHolder);
        int signUpId = Integer.parseInt(kHolder.getKey().toString());
        return signUpId;
    }

    public boolean isEmailIdPresent(String emailId) {
        try {
            final String validation = "SELECT count(*) FROM signUpTable WHERE emailid = ?";
            int signUpConfigurationList = jdbcTemplate.queryForObject(validation, new Object[]{emailId},
                    int.class);
            if (signUpConfigurationList >= 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return false;
    }

    public List<Map<String, Object>> userStoredDetails(String emailId) {
        final String sSelectReportsQuery = "SELECT * FROM signUpTable WHERE emailId = ?";
        List<Map<String, Object>> userDetails = jdbcTemplate.queryForList(sSelectReportsQuery, emailId);
        return userDetails;
    }

    public List<Map<String, Object>> getSubscription(String emailId) {
        final String sSelectReportsQuery = "SELECT * FROM paymentDetailTable WHERE userId = ?";
        List<Map<String, Object>> configlist = jdbcTemplate.queryForList(sSelectReportsQuery, emailId);
        return configlist;
    }

}

