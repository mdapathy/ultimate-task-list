package com.duminska.ultimatetasklist.projects;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DtoProject {
    private String userId;
    private String projectId;
    private String name;
    private String color;
    private String parentProjectId;
    private boolean isFavourite;
}
