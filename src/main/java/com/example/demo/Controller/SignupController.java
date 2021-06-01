package com.example.demo.Controller;

import com.example.demo.Models.UserDetails;
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
public class SignupController {

    @Autowired
    private SignUpService signUpService;

    @CrossOrigin
    @RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUpRequest(@RequestBody UserDetails userDetails) throws SQLException, IOException {
        return signUpService.createSignUp(userDetails);

    }
}
