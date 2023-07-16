package com.mcsv.hotel.hotelservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> listarStaffs(){
        List<String> staffs = Arrays.asList("staff1", "staff2", "staff3");
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }
}