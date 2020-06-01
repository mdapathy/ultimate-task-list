package com.duminska.ultimatetasklist.projects;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Project {
    private String userId;
    private String projectId;
    private String name;
    private String color;
    private String parentProjectId;
    private boolean isFavourite;

}
