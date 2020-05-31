package com.duminska.ultimatetasklist.user;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserAuth {
    private String email;
    private String password;
}
