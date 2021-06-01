package com.example.demo.Controller;

import com.example.demo.Models.Payment;
import com.example.demo.Models.PaymentDetails;
import com.example.demo.Service.PaymentService;
import com.example.demo.Service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@EnableAutoConfiguration
public class PaymentIntentController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PaymentService paymentService;

    @CrossOrigin
    @RequestMapping(value = "/createPaymentIntent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentIntent(@RequestBody Payment payment) throws SQLException, IOException {
        return signUpService.paymentIntent(payment);
    }

    @CrossOrigin
    @RequestMapping(value = "/paymentDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentDetails(@RequestBody PaymentDetails paymentDetails) throws SQLException, IOException {
        return paymentService.paymentDetail(paymentDetails);
    }

}