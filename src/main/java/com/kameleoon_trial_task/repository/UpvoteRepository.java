package com.kameleoon_trial_task.repository;

import com.kameleoon_trial_task.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    Upvote findByQuoteIdAndUserId(Long quoteId, Long userId);

}
