package com.kameleoon_trial_task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Upvote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_upvote")
    private Boolean isUpvote;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Quote quote;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
