package com.duminska.ultimatetasklist.config.constants;

public class SqlConstants {

    /*
            Sql queries for user dao

     */

    public static final String USER_GET_BY_EMAIL =
            "SELECT user_id, email, password," +
                    "is_activated, recovery_link, acc_creation_date, " +
                    "activation_link, recovery_sent_date from users WHERE email=?";

    public static final String USER_GET_BY_ACTIVATION_LINK =
            "SELECT user_id, email, password," +
                    "is_activated, recovery_link, acc_creation_date, " +
                    "activation_link, recovery_sent_date from users WHERE activation_link=?";

    public static final String USER_CREATE_USER =
            "INSERT INTO users (email, password, activation_link) values(?, ?, ?)";

    public static final String USER_ACTIVATE_USER =
            "UPDATE users SET is_activated = true where user_id = uuid(?)";

    public static final String USER_DELETE_USER =
            "DELETE from users where user_id = uuid(?)";

    /*
            Sql queries for project dao

     */

    public static final String PROJECT_INIT_PROJECT =
            "insert into projects (user_id, name) " +
                    "values (uuid(?), 'Inbox')";


    public static final String PROJECT_ADD_PROJECT =
            "insert into projects (user_id, name, parent_project_id, color, favourite) " +
                    "values (uuid(?), ?, uuid(?), ?, ?)";


    public static final String PROJECT_DELETE_PROJECT =
            "delete from projects where project_id=uuid(?)";

    public static final String PROJECT_EDIT_PROJECT =
            "update projects  set name = ?," +
                    "parent_project_id =? , color = ?, favourite = ? where project_id = uuid(?) " +
                    "and user_id = uuid(?)";


    public static final String PROJECT_GET_USER_PROJECTS = "select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) ";

    public static final String PROJECT_GET_PROJECT_BY_ID = "select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where project_id=uuid(?) ";

    public static final String PROJECT_GET_FAVOURITE_PROJECTS = "select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) and favourite=true";



    /*
            Sql queries for tasks dao

     */

    public static final String TASK_INIT_TASKS =
            "insert into tasks (user_id, name, priority_id, project_id)\n" +
                    "values (uuid(?), ?, 4, uuid(?));";

    public static final String TASK_EDIT_TASK =
            "update tasks set name = ?, priority_id = ?, " +
                    "project_id = ?, parent_task_id = ?, first_deadline_date = ?," +
                    "recurring_time = ?, times_postponed = ?" +
                    "where task_id=uuid(?)";

    public static final String TASK_ADD_TASK =
            "insert into tasks (user_id, name, priority_id, " +
                    "project_id, parent_task_id, first_deadline_date," +
                    " recurring_time, times_postponed, is_done) " +
                    "values (uuid(?), ?, ?,uuid(?),uuid(?),?,?,?,?)";

    public static final String TASK_GET_TASKS_BY_PROJECT =
            "select task_id, user_id, name, priority_id, " +
                    "project_id, parent_task_id, first_deadline_date," +
                    " recurring_time, times_postponed, is_done, " +
                    "priorities.color as color from tasks left join " +
                    "priorities on tasks.priority_id = priorities.value " +
                    "where project_id=uuid(?)";

    public static final String TASK_GET_TASKS_BY_PROJECT_COMPLETED =
            "select task_id, user_id, name, priority_id, " +
                    "project_id, parent_task_id, first_deadline_date," +
                    " recurring_time, times_postponed, is_done, " +
                    "priorities.color as color from tasks left join " +
                    "priorities on tasks.priority_id = priorities.value " +
                    "where project_id=uuid(?)  and is_done=true";

    public static final String TASK_GET_TASKS_BY_PROJECT_UNCOMPLETED =
            "select task_id, user_id, name, priority_id, " +
                    "project_id, parent_task_id, first_deadline_date," +
                    " recurring_time, times_postponed, is_done, " +
                    "priorities.color as color from tasks left join " +
                    "priorities on tasks.priority_id = priorities.value " +
                    "where project_id=uuid(?) and is_done=false";

    public static final String TASK_DELETE_TASK =
            "delete from tasks  where task_id=uuid(?)";


    public static final String TASK_MARK_DONE_TASK =
            "UPDATE tasks set is_done = true where task_id=uuid(?)";


    public static final String TASK_GET_BY_ID =
            "select task_id, user_id, name, priority_id, project_id," +
                    " parent_task_id, first_deadline_date, recurring_time," +
                    " times_postponed, is_done, priorities.color as color from tasks " +
                    "left join priorities on tasks.priority_id = priorities.value where" +
                    " task_id=uuid(?)";


}