package com.kameleoon_trial_task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuoteDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
    private Integer upvoteNumber;
    private Integer downvoteNumber;
}
