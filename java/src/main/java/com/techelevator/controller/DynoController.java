package com.techelevator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DynoController {

    @RequestMapping(value = "/wake", method = RequestMethod.GET)
    public ResponseEntity<String> wakeUp() {
        return new ResponseEntity<>("Backend is running", HttpStatus.OK);
    }
}
