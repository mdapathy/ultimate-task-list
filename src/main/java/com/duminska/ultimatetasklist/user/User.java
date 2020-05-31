package com.duminska.ultimatetasklist.user;

import lombok.*;

import java.util.Date;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private String id;
    private String email;
    private String password;
    private boolean isActivated;
    private String recoveryLink;
    private String activationLink;
    private Date accCreationDate;
    private Date recoverySentDate;

}
