package com.duminska.ultimatetasklist.tasks;

import lombok.*;

import java.util.Date;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Task {
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


}
