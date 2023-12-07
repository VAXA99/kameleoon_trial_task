package com.kameleoon_trial_task.controller.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class QuoteRequest {
    private Long userId;
    private String content;
}
