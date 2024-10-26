package com.taskmaster.Taskmaster.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectDto {
    private String username;
    private String project_title;
}
