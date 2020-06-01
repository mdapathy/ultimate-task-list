package com.duminska.ultimatetasklist.projects;

import com.duminska.ultimatetasklist.config.constants.SqlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProjectDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    String initProjectsForNewUser(String userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            SqlConstants.PROJECT_INIT_PROJECT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKeys()).get("project_id").toString();

    }

    Project addProject(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            SqlConstants.PROJECT_ADD_PROJECT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getUserId());
            ps.setString(2, project.getName());
            ps.setString(3, project.getParentProjectId());
            ps.setString(4, project.getColor());
            ps.setBoolean(5, project.isFavourite());

            return ps;
        }, keyHolder);
        project.setProjectId(Objects.requireNonNull(keyHolder.getKeys()).get("project_id").toString());
        return project;
    }

    void deleteProject(String projectId) {
        jdbcTemplate.update(SqlConstants.PROJECT_DELETE_PROJECT, projectId);

    }

    void editProject(Project project) {
        jdbcTemplate.update(SqlConstants.PROJECT_EDIT_PROJECT
                , project.getName(), project.getParentProjectId(),
                project.getUserId(),
                project.getColor(),
                project.isFavourite(),
                project.getProjectId(), project.getUserId());

    }

    List<Project> getUserProjects(String userId) {
        try {
            return jdbcTemplate.query(SqlConstants.PROJECT_GET_USER_PROJECTS, new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }

    }

    Project getProjectById(String projectId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.PROJECT_GET_PROJECT_BY_ID, new Object[]{projectId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }


    List<Project> getFavouriteProjects(String userId) {
        try {
            return jdbcTemplate.query(SqlConstants.PROJECT_GET_FAVOURITE_PROJECTS, new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }
}
