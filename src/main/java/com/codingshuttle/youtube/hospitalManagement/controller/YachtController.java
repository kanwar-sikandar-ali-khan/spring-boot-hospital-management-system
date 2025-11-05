package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtResponseDto;
import com.codingshuttle.youtube.hospitalManagement.service.AppointmentService;
import com.codingshuttle.youtube.hospitalManagement.service.YachtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yachts")
@RequiredArgsConstructor
@Tag(name = "Yachts APIs", description = "From Controller")
public class YachtController {
    private final YachtService yachtService;


    @PostMapping("/create")
    public ResponseEntity<CreateYachtResponseDto> createNewYacht(@RequestBody CreateYachtRequestDto createYachtRequestDto) {
        System.out.println("Received Yacht: " + createYachtRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(yachtService.createNewYacht(createYachtRequestDto));

    }
}

