package com.duminska.ultimatetasklist.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginSuccessResponse {

    private boolean success;

    private String token;
    
}
