package com.example.demo.Service;

import com.example.demo.Dao.PaymentDao;
import com.example.demo.Models.Constants;
import com.example.demo.Models.PaymentDetails;
import com.example.demo.Models.Status;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentService {

    @Autowired
    PaymentDao paymentDao;
    Logger _logger = LoggerFactory.getLogger(PaymentService.class);

    public ResponseEntity<?> paymentDetail(PaymentDetails paymentDetails) {
        String userId = paymentDetails.getUserId();
        Status status = new Status();
        Map<String, Object> response = new HashMap<>();
        if (userId != null) {
            try {
                paymentDao.insertPaymentDetails(paymentDetails);
            } catch (SQLException e) {
                _logger.error("SQL Exception", e);
            }
            status.setCode(Constants.SUCCESSFULL_STATUS_CODE);
            status.setValue(Constants.DATA_SAVED_SUCCESSFULLY);
            status.setId(paymentDetails.getUserId());
            response.put(Constants.PAYMENT_RESPONSE, status);
            _logger.info(Constants.DATA_SAVED_SUCCESSFULLY + " " + paymentDetails.getUserId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        } else {
            status.setCode(Constants.FAILED_STATUS_CODE);
            status.setValue(Constants.DATA_NOT_SAVED_SUCCESSFULLY);
            status.setId(paymentDetails.getUserId());
            response.put(Constants.PAYMENT_RESPONSE, status);
            _logger.info(Constants.DATA_NOT_SAVED_SUCCESSFULLY + " " + paymentDetails.getUserId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        }
    }
}
