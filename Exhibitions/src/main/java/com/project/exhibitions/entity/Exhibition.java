package com.project.exhibitions.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ExhibitionState state;

}
