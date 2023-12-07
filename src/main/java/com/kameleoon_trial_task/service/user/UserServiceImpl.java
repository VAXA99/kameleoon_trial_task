package com.kameleoon_trial_task.service.user;

import com.kameleoon_trial_task.model.User;
import com.kameleoon_trial_task.repository.QuoteRepository;
import com.kameleoon_trial_task.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           QuoteRepository quoteRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Async
    public void addUser(User user) {
        userRepository.save(user);
    }
}
