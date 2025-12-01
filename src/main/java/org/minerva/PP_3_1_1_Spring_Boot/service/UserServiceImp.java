package org.minerva.PP_3_1_1_Spring_Boot.service;

import org.minerva.PP_3_1_1_Spring_Boot.repository.UserRepository;
import org.minerva.PP_3_1_1_Spring_Boot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private final Logger logger;

    public UserServiceImp (UserRepository userRepository) {
        this.userRepository = userRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Transactional
    public User getUserByID (long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public List <User> getAllUsers () {
        Iterable <User> users = userRepository.findAll();
        List <User> userList = StreamSupport.stream(users.spliterator(), false).toList();
        logger.info("Found {} users", userList.size());
        return userList;
    }

    @Transactional
    public void addUser (User user) {
        logger.debug("Try save/update user {}", user);
        User result = userRepository.save(user);
        logger.info("User saved/updated: {}", result);
    }

    @Transactional
    public void deleteUser (long id) {
        userRepository.deleteById(id);
        logger.info("User with ID={} deleted", id);
    }

    @Transactional
    public boolean existsEmail (String email) {
        logger.debug("Check if user with email {} exists", email);
        Optional <User> user = userRepository.findByEmail(email);
        logger.debug("User with email exists - {}", user.isPresent());
        return user.isPresent();
    }
}
