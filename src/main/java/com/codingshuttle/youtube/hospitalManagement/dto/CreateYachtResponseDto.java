package com.codingshuttle.youtube.hospitalManagement.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateYachtResponseDto {
    private Long id;
    private LocalDateTime created_at;
    private String  name;
    private String color;
    private Double price_per_hour_current;
}
