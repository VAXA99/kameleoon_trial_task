package com.kameleoon_trial_task.repository;

import com.kameleoon_trial_task.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT COUNT(u) FROM Upvote u WHERE u.quote = :quote AND u.isUpvote = true")
    Integer getUpvoteCount(@Param("quote") Quote quote);

    @Query("SELECT COUNT(u) FROM Upvote u WHERE u.quote = :quote AND u.isUpvote = false")
    Integer getDownvoteCount(@Param("quote") Quote quote);

    @Query("SELECT q FROM Quote q ORDER BY FUNCTION('RAND') LIMIT 1")
    Quote getRandomQuote();

}
