package com.duminska.ultimatetasklist.tasks;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DtoTask {
    private String taskId;
    private String userId;
    private String name;
    private String color;
    private int priorityId;
    private String projectId;
    private String parentTaskId;
    private Date deadline;
    private Date recurring;
    private int timesPostponed;
    private boolean isDone;


    public static Task toTask(DtoTask dtoTask) {
        return Task.builder()
                .taskId(dtoTask.getTaskId())
                .color(dtoTask.getColor())
                .deadline(dtoTask.getDeadline())
                .isDone(dtoTask.isDone())
                .name(dtoTask.getName())
                .parentTaskId(dtoTask.getParentTaskId())
                .priorityId(dtoTask.getPriorityId())
                .projectId(dtoTask.getProjectId())
                .recurring(dtoTask.getRecurring())
                .timesPostponed(dtoTask.getTimesPostponed())
                .userId(dtoTask.getUserId())
                .build();

    }


    public static DtoTask fromTask(Task task) {
        return DtoTask.builder()
                .taskId(task.getTaskId())
                .color(task.getColor())
                .deadline(task.getDeadline())
                .isDone(task.isDone())
                .name(task.getName())
                .parentTaskId(task.getParentTaskId())
                .priorityId(task.getPriorityId())
                .projectId(task.getProjectId())
                .recurring(task.getRecurring())
                .timesPostponed(task.getTimesPostponed())
                .userId(task.getUserId())
                .build();

    }
}
