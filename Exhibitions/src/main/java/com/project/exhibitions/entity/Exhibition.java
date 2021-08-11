package com.project.exhibitions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Getter
@Setter

@Entity
@Table(name = "exhibitions")
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "startTimeEveryDay", nullable = false)
    private LocalTime startTimeEveryDay;

    @Column(name = "endTimeEveryDay", nullable = false)
    private LocalTime endTimeEveryDay;

    @Column(name = "price", nullable = false)
    private Integer price;

}
