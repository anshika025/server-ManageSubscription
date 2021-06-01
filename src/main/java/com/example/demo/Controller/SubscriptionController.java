package com.example.demo.Controller;

import com.example.demo.Models.PaymentDetails;
import com.example.demo.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@EnableAutoConfiguration
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @CrossOrigin
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@RequestBody PaymentDetails paymentDetails) throws SQLException {
        return subscriptionService.updateStatus(paymentDetails);
    }
}
