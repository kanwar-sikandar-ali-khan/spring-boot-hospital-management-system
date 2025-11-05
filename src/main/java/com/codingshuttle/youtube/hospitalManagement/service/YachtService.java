package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Yacht;
import com.codingshuttle.youtube.hospitalManagement.repository.YachtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YachtService {

    private final YachtRepository yachtRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public CreateYachtResponseDto createNewYacht(CreateYachtRequestDto createYachtRequestDto) {

        Yacht yacht = yachtRepository.findByName(createYachtRequestDto.getName());

        if (yacht != null)
            throw new IllegalArgumentException("Yacht already exists");

        yacht = Yacht.builder()
                .name(createYachtRequestDto.getName())
                .color(createYachtRequestDto.getColor())
                .price_per_hour_current(createYachtRequestDto.getPricePerHour())
                .build();

        System.out.println("before saved: " + yacht);

        yacht = yachtRepository.save(yacht);

        System.out.println("after Yacht saved: " + yacht);

        return modelMapper.map(yacht, CreateYachtResponseDto.class);
    }
}
