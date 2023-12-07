package com.kameleoon_trial_task.controller.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class AuthRequest {
    private String email;
    private String name;
    private String password;
}
