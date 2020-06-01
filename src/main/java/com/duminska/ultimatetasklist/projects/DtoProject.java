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


    public static Project toProject(DtoProject dtoProject) {
        return Project.builder()
                .color(dtoProject.getColor())
                .name(dtoProject.getName())
                .projectId(dtoProject.getProjectId())
                .userId(dtoProject.getUserId())
                .isFavourite(dtoProject.isFavourite())
                .projectId(dtoProject.getProjectId())
                .parentProjectId(dtoProject.getParentProjectId())
                .build();

    }


    public static DtoProject fromProject(Project project) {
        return DtoProject.builder()
                .color(project.getColor())
                .name(project.getName())
                .projectId(project.getProjectId())
                .userId(project.getUserId())
                .isFavourite(project.isFavourite())
                .projectId(project.getProjectId())
                .parentProjectId(project.getParentProjectId())
                .build();

    }
}
