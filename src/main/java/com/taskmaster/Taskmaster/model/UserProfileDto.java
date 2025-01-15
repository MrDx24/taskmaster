package com.taskmaster.Taskmaster.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserProfileDto {

    private String email;
    private String profilePicture;

}
