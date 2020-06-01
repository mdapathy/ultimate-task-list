package com.duminska.ultimatetasklist.projects;

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

    void initProjectsForNewUser(String userId) {
        jdbcTemplate.update(
                "insert into projects (user_id, name) " +
                        "values (?, 'Inbox')", userId);

    }

    DtoProject addProject(DtoProject project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into projects (user_id, name, parent_project_id, color, favourite) " +
                                    "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update(
                "delete from projects where project_id=uuid(?)", projectId);

    }

    void editProject(DtoProject project) {
        jdbcTemplate.update("update projects  set name = ?," +
                        " parent_project_id =? , color = ?, favourite = ? where project_id = ? and user_id = ?"
                , project.getName(), project.getParentProjectId(),
                project.getUserId(),
                project.getColor(),
                project.isFavourite(),
                project.getProjectId(), project.getUserId());

    }

    List<Project> getUserProjects(String userId) {
        try {
            return jdbcTemplate.query("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) ", new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }

    }

    Project getProjectById(String projectId) {
        try {
            return jdbcTemplate.queryForObject("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where project_id=uuid(?) ", new Object[]{projectId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }


    List<Project> getFavouriteProjects(String userId) {
        try {
            return jdbcTemplate.query("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) and favourite=true", new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }
}
