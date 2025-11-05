package com.codingshuttle.youtube.hospitalManagement;

import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.CreateYachtResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Yacht;
import com.codingshuttle.youtube.hospitalManagement.repository.YachtRepository;
import com.codingshuttle.youtube.hospitalManagement.service.YachtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class YachtTests {

    @Autowired
    private YachtRepository yachtRepository;

    @Autowired
    private YachtService yachtService;

    @Test
    public void testInsertYacht() {
        CreateYachtRequestDto dto = new CreateYachtRequestDto();
        dto.setName("Blue Wavee");
        dto.setColor("Blue");
        dto.setPricePerHour(5000.0);

        CreateYachtResponseDto response = yachtService.createNewYacht(dto);

        log.info("Created Yacht: {}", response);

        Assertions.assertNotNull(response.getId(), "Yacht ID should not be null after saving");
        Assertions.assertEquals("Blue Wave", response.getName());
    }

    @Test
    public void testGetAllYachts() {
        List<Yacht> yachts = yachtRepository.findAll();
        log.info("Total yachts found: {}", yachts.size());

        for (Yacht y : yachts) {
            log.info("Yacht -> ID: {}, Name: {}, Color: {}, Price: {}", y.getId(), y.getName(), y.getColor(), y.getPrice_per_hour_current());
        }

        Assertions.assertFalse(yachts.isEmpty(), "Yacht list should not be empty");
    }

    @Test
    public void testGetSingleYachtByName() {
        String yachtName = "Blue Wave";
        Yacht yacht = yachtRepository.findByName(yachtName);

        log.info("Fetched Yacht: {}", yacht);

        Assertions.assertNotNull(yacht, "Yacht should exist with name " + yachtName);
        Assertions.assertEquals(yachtName, yacht.getName());
    }

    @Test
    public void testGetSingleYachtById() {
        // Assuming the yacht created in previous test exists
        Yacht firstYacht = yachtRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertNotNull(firstYacht, "There should be at least one yacht in DB");

        Optional<Yacht> yacht = yachtRepository.findById(firstYacht.getId());
        log.info("Fetched Yacht by ID: {}", yacht.orElse(null));

        Assertions.assertTrue(yacht.isPresent(), "Yacht should exist for given ID");
        Assertions.assertEquals(firstYacht.getId(), yacht.get().getId());
    }
}
