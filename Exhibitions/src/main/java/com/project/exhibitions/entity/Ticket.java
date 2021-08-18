package com.project.exhibitions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "exhibitionTopic")
    private String exhibitionTopic;

    @Column(name = "exhibitionId")
    private Integer exhibitionId;
}
