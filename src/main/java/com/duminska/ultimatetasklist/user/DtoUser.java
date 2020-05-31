package com.duminska.ultimatetasklist.user;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    public String id;
    private String email;
    private String password;
    private boolean isActivated;
    private Date accCreationDate;


    public static DtoUser fromUser(User user) {
        return DtoUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActivated(user.isActivated())
                .accCreationDate(user.getAccCreationDate())
                .build();
    }
}
