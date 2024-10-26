package com.taskmaster.Taskmaster.model;


import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AttachmentDto {

    private UUID taskId;
    private String fileUrl;

}
