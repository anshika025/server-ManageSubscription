package com.example.demo.Service;

import com.example.demo.Dao.SignUpDao;
import com.example.demo.Dao.SubscriptionDao;
import com.example.demo.Models.Constants;
import com.example.demo.Models.Payment;
import com.example.demo.Models.Status;
import com.example.demo.Models.UserDetails;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SignUpService {

    @Autowired
    SignUpDao signUpDao;
    @Autowired
    SubscriptionDao subscriptionDao;
    Logger _logger = LoggerFactory.getLogger(SignUpService.class);


    public ResponseEntity<?> createSignUp(UserDetails userDetails) {
        Integer signUpId = userDetails.getId();
        Status status = new Status();
        Map<String, Object> response = new HashMap<>();
        if (signUpDao.isEmailIdPresent(userDetails.getEmailId())) {
            status.setCode(Constants.FAILED_STATUS_CODE);
            status.setValue(Constants.EMAILID_ALREADY_PRESENT);
            status.setId(userDetails.getEmailId());
            response.put(Constants.SIGN_UP_RESPONSE, status);
            _logger.info(Constants.DATA_SAVED_SUCCESSFULLY + " " + userDetails.getEmailId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        }
        if (signUpId == null) {
            try {
                signUpId = signUpDao.insertSignUpDetails(userDetails);
            } catch (SQLException e) {
                _logger.error("SQL Exception", e);
            }
            status.setCode(Constants.SUCCESSFULL_STATUS_CODE);
            status.setValue(Constants.DATA_SAVED_SUCCESSFULLY);
            status.setId(userDetails.getEmailId());
            response.put(Constants.SIGN_UP_RESPONSE, status);
            _logger.info(Constants.DATA_SAVED_SUCCESSFULLY + " " + userDetails.getEmailId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        } else {
            status.setCode(Constants.FAILED_STATUS_CODE);
            status.setValue(Constants.DATA_NOT_SAVED_SUCCESSFULLY);
            status.setId(userDetails.getEmailId());
            response.put(Constants.SIGN_UP_RESPONSE, status);
            _logger.info(Constants.DATA_NOT_SAVED_SUCCESSFULLY + " " + userDetails.getEmailId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> login(UserDetails loginDetail) {

        Status status = new Status();
        Map<String, Object> response = new HashMap<>();
        String emailId = loginDetail.getEmailId();
        String password = loginDetail.getPassword();

        if (emailId != "" && password != "") {
            List<Map<String, Object>> userList = signUpDao.userStoredDetails(emailId);
            if (!userList.isEmpty()) {
                String providedPassword = loginDetail.getPassword();
                // Encrypted and Base64 encoded password read from database
                String securePassword = (String) userList.get(0).get("password");
                _logger.info("SecurePassword" + " " + securePassword);
                // Salt value stored in database
                String salt = (String) userList.get(0).get("salt");
                _logger.info("Salt" + " " + salt);
                boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
                List<Map<String, Object>> subscriptionList = signUpDao.getSubscription(loginDetail.getEmailId());
                if (passwordMatch) {
                    status.setCode(Constants.SUCCESSFULL_STATUS_CODE);
                    status.setValue(Constants.LOGIN_SUCCESSFULLY);
                    status.setId(loginDetail.getEmailId());
                    status.setList(subscriptionList);
                    response.put(Constants.LOGIN_RESPONSE, status);
                    _logger.info(Constants.LOGIN_SUCCESSFULLY + " "
                            + loginDetail.getEmailId());
                    return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
                } else {
                    status.setCode(Constants.FAILED_STATUS_CODE);
                    status.setValue(Constants.LOGIN_FAILED);
                    status.setId(loginDetail.getEmailId());
                    response.put(Constants.LOGIN_RESPONSE, status);
                    _logger.error(Constants.LOGIN_FAILED + " " + loginDetail.getEmailId());
                    return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
                }
            } else {
                status.setCode(Constants.FAILED_STATUS_CODE);
                status.setValue(Constants.LOGIN_FAILED);
                status.setId(loginDetail.getEmailId());
                response.put(Constants.LOGIN_RESPONSE, status);
                _logger.info(Constants.LOGIN_FAILED + " " + loginDetail.getEmailId());
                return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
            }
        } else {
            status.setCode(Constants.FAILED_STATUS_CODE);
            status.setValue(Constants.LOGIN_FAILED);
            status.setId(loginDetail.getEmailId());
            response.put(Constants.LOGIN_RESPONSE, status);
            _logger.error(Constants.LOGIN_FAILED + " " + loginDetail.getEmailId());

        }
        return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> paymentIntent(Payment payment) throws SQLException, IOException {
        Status status = new Status();
        Long amount = null;
        Map<String, Object> response = new HashMap<>();
        if (payment.getItems().getId().contains("Yearly")) {
            amount = Constants.YEARLY;
        } else {
            amount = Constants.MONTHLY;
        }

        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount(amount)
                .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent intent = null;
        try {
            intent = PaymentIntent.create(createParams);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(intent.getClientSecret());
        status.setCode(Constants.SUCCESSFULL_STATUS_CODE);
        status.setValue(paymentResponse.clientSecret);
        status.setId(payment.getItems().getId());
        response.put(Constants.KEY_RESPONSE, status);
        _logger.info(Constants.KEY_RESPONSE + " " + payment.getItems().getId());
        return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
    }

    static class CreatePaymentResponse {
        private final String clientSecret;

        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
}