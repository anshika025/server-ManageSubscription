package com.example.demo.Controller;

import com.example.demo.Models.UserDetails;
import com.example.demo.Service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@EnableAutoConfiguration
public class LoginController {

    @Autowired
    private SignUpService signUpService;

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginRequest(@RequestBody UserDetails loginDetail) throws SQLException {
        return signUpService.login(loginDetail);

    }
}
