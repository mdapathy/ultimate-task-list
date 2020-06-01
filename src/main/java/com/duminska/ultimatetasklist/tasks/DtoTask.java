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
    private String priorityId;
    private String projectId;
    private String parentTaskId;
    private Date deadline;
    private Date recurring;
    private int timesPostponed;
    private boolean isDone;
}
