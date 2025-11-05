package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "Yacht",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_yacht_name", columnNames = {"name"}),
        },
        indexes = {
                @Index(name = "idx_yacht_color", columnList = "color")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Yacht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created_at;


    private String name;

    private String color;

    @Column(name = "price_per_hour")
    private Double price_per_hour_current;


    // 👇 Add this method
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }
}
