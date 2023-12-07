package com.kameleoon_trial_task.service.quote;

import com.kameleoon_trial_task.dto.QuoteDto;
import com.kameleoon_trial_task.dto.UserDto;
import com.kameleoon_trial_task.model.Quote;
import com.kameleoon_trial_task.model.Upvote;
import com.kameleoon_trial_task.model.User;
import com.kameleoon_trial_task.repository.QuoteRepository;
import com.kameleoon_trial_task.repository.UpvoteRepository;
import com.kameleoon_trial_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final UpvoteRepository upvoteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository,
                            UpvoteRepository upvoteRepository,
                            UserRepository userRepository,
                            ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.upvoteRepository = upvoteRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuoteDto getRandomQuote() {
        Quote quote = quoteRepository.getRandomQuote();

        return convertToQuoteDto(quote);
    }

    private int calculateGap(Quote quote) {
        int upvoteCount = quoteRepository.getUpvoteCount(quote);
        int downvoteCount = quoteRepository.getDownvoteCount(quote);
        return upvoteCount - downvoteCount;
    }

    @Override
    public List<QuoteDto> getTop10BestQuotes() {
        List<Quote> allQuotes = quoteRepository.findAll();

        Map<Quote, Integer> quoteGapMap = new HashMap<>();
        for (Quote quote : allQuotes) {
            int upvoteCount = quoteRepository.getUpvoteCount(quote);
            int downvoteCount = quoteRepository.getDownvoteCount(quote);
            int gap = upvoteCount - downvoteCount;
            quoteGapMap.put(quote, gap);
        }

        List<Quote> sortedQuotes = quoteGapMap.entrySet().stream()
                .sorted(Map.Entry.<Quote, Integer>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        return sortedQuotes.stream()
                .map(this::convertToQuoteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuoteDto> getTop10WorstQuotes() {
        List<Quote> allQuotes = quoteRepository.findAll();

        Map<Quote, Integer> quoteGapMap = new HashMap<>();
        for (Quote quote : allQuotes) {
            int upvoteCount = quoteRepository.getUpvoteCount(quote);
            int downvoteCount = quoteRepository.getDownvoteCount(quote);
            int gap = upvoteCount - downvoteCount;
            quoteGapMap.put(quote, gap);
        }

        List<Quote> sortedQuotes = quoteGapMap.entrySet().stream()
                .sorted(Map.Entry.<Quote, Integer>comparingByValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        return sortedQuotes.stream()
                .map(this::convertToQuoteDto)
                .collect(Collectors.toList());
    }

    @Override
    @Async
    @Transactional
    public void writeQuote(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID:" + userId));

        Quote quote = new Quote();
        quote.setContent(content);
        quote.setUser(user);

        quoteRepository.save(quote);
    }

    @Override
    @Async
    @Transactional
    public void editQuote(Long quoteId, String newContent) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException("Quote not found with ID: " + quoteId));

        LocalDateTime updatedAt = LocalDateTime.now();

        quote.setContent(newContent);
        quote.setUpdatedAt(updatedAt);
    }

    @Override
    @Async
    @Transactional
    public void deleteQuote(Long quoteId) {
        quoteRepository.deleteById(quoteId);
    }

    @Override
    @Async
    @Transactional
    public void upvoteQuote(Long quoteId, Long userId, Boolean isUpvote) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException("Quote not found with ID: " + quoteId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID:" + userId));

        Upvote existingUpvote = upvoteRepository.findByQuoteIdAndUserId(quoteId, userId);

        if (existingUpvote != null) {
            if (existingUpvote.getIsUpvote().equals(isUpvote)) {
                upvoteRepository.delete(existingUpvote);
            } else {
                existingUpvote.setIsUpvote(isUpvote);
            }
        } else {
            Upvote upvote = new Upvote();
            upvote.setUser(user);
            upvote.setQuote(quote);
            upvote.setIsUpvote(isUpvote);

            upvoteRepository.save(upvote);
        }

        quoteRepository.save(quote);
    }

    private QuoteDto convertToQuoteDto(Quote quote) {
        QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);

        if (quote.getUser() != null) {
            UserDto userDto = modelMapper.map(quote.getUser(), UserDto.class);
            quoteDto.setUser(userDto);
        }

        quoteDto.setUpvoteNumber(quoteRepository.getUpvoteCount(quote));
        quoteDto.setDownvoteNumber(quoteRepository.getDownvoteCount(quote));

        return quoteDto;
    }

}
