package com.darianbeluzzo.mongodbsimpleblogapi.service;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.User;
import com.darianbeluzzo.mongodbsimpleblogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsernameLikeIgnoreCase(username);
    }

    public User save(final User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public void deleteById(final String id) throws ResourceNotFoundException {
        checkIfUserExists(id);
        userRepository.deleteById(id);
    }

    public void checkIfUserExists(String id) throws ResourceNotFoundException {
        var userExists = userRepository.existsById(id);
        if (!userExists) {
            throw new ResourceNotFoundException("The given user does not exist");
        }
    }

    public User update(final User user) throws ResourceNotFoundException {
        checkIfUserExists(user.getId());
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
