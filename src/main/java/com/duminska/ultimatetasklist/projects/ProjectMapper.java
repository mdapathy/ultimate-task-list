package com.duminska.ultimatetasklist.projects;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int i) throws SQLException {

        return Project.builder()
                .userId(rs.getString("user_id"))
                .projectId(rs.getString("project_id"))
                .name(rs.getString("name"))
                .color(rs.getString("color"))
                .parentProjectId(rs.getString("parent_project_id"))
                .isFavourite(rs.getBoolean("favourite"))
                .build();
    }
}
