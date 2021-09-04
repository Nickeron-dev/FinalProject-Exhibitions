package com.project.exhibitions.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
