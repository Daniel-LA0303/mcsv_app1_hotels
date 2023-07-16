package com.auth.authservicev2.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class AuthUserDto {

    private String userName;

    private String password;

}
