package com.example.demo.Service;

import com.example.demo.Dao.SubscriptionDao;
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

import java.util.HashMap;
import java.util.Map;


@Service
public class SubscriptionService {

    @Autowired
    SubscriptionDao subscriptionDao;
    Logger _logger = LoggerFactory.getLogger(SubscriptionDao.class);


    public ResponseEntity<?> updateStatus(PaymentDetails paymentDetails) {
        Status status = new Status();
        Map<String, Object> response = new HashMap<>();
        if (paymentDetails.getUserId() != null && paymentDetails.getStatus() != null) {
            subscriptionDao.updateStatus(paymentDetails);
            status.setCode(Constants.SUCCESSFULL_STATUS_CODE);
            status.setId(paymentDetails.getUserId());
            status.setValue(Constants.STATUS_UPDATED_SUCCESSFULLY);
            response.put(Constants.STATUS_RESPONSE, status);
            _logger.info(Constants.STATUS_UPDATED_SUCCESSFULLY + " " + paymentDetails.getUserId());
            return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new JSONObject(response).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}