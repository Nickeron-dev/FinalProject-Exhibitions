package com.project.exhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Illia Koshkin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    private Integer userId;
    private Integer exhibitionId;
}
