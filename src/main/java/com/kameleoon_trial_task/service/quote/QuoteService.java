package com.kameleoon_trial_task.service.quote;

import com.kameleoon_trial_task.dto.QuoteDto;

import java.util.List;

public interface QuoteService {
    QuoteDto getRandomQuote();

    List<QuoteDto> getTop10BestQuotes();

    List<QuoteDto> getTop10WorstQuotes();

    void writeQuote(Long userId, String content);

    void editQuote(Long quoteId, String newContent);

    void deleteQuote(Long quoteId);

    void upvoteQuote(Long quoteId, Long userId, Boolean isUpvote);
}
