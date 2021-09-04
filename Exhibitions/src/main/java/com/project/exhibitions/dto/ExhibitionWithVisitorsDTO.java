package com.project.exhibitions.dto;

import com.project.exhibitions.entity.ExhibitionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Illia Koshkin
 */
@Getter
@Setter
@Builder
public class ExhibitionWithVisitorsDTO {
    private Integer id;

    private String topic;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer price;

    private ExhibitionState state;

    private Integer visitors;
}
