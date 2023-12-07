package com.kameleoon_trial_task.controller;

import com.kameleoon_trial_task.controller.request.QuoteRequest;
import com.kameleoon_trial_task.dto.QuoteDto;
import com.kameleoon_trial_task.service.quote.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/random")
    public ResponseEntity<QuoteDto> getRandomQuote() {
        QuoteDto randomQuote = quoteService.getRandomQuote();
        return ResponseEntity.ok(randomQuote);
    }

    @GetMapping("/top10Best")
    public ResponseEntity<List<QuoteDto>> getTop10BestQuotes() {
        List<QuoteDto> top10BestQuotes = quoteService.getTop10BestQuotes();
        return ResponseEntity.ok(top10BestQuotes);
    }

    @GetMapping("/top10Worst")
    public ResponseEntity<List<QuoteDto>> getTop10WorstQuotes() {
        List<QuoteDto> top10WorstQuotes = quoteService.getTop10WorstQuotes();
        return ResponseEntity.ok(top10WorstQuotes);
    }

    @PostMapping("/write")
    public ResponseEntity<Void> writeQuote(@RequestBody QuoteRequest quoteRequest) {
        quoteService.writeQuote(
                quoteRequest.getUserId(),
                quoteRequest.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/edit/{quoteId}")
    public ResponseEntity<Void> editQuote(@PathVariable Long quoteId, @RequestBody QuoteRequest quoteRequest) {
        quoteService.editQuote(
                quoteId,
                quoteRequest.getContent()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{quoteId}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long quoteId) {
        quoteService.deleteQuote(quoteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upvote/{quoteId}")
    public ResponseEntity<Void> upvoteQuote(@PathVariable Long quoteId,
                                            @RequestParam Long userId,
                                            @RequestParam Boolean isUpvote) {
        quoteService.upvoteQuote(quoteId, userId, isUpvote);
        return ResponseEntity.ok().build();
    }
}
