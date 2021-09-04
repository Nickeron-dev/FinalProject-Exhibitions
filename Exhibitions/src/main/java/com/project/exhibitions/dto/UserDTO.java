package com.project.exhibitions.dto;

import lombok.*;

/**
 * @author Illia Koshkin
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    private String email;
    private String username;
    private String password;
}
