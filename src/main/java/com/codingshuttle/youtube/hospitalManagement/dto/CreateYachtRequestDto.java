package com.codingshuttle.youtube.hospitalManagement.dto;


import lombok.Data;

@Data
public class CreateYachtRequestDto {

    private String  name;
    private String color;
    private Double pricePerHour;
}
