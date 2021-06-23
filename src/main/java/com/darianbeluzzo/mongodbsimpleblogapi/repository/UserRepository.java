package com.darianbeluzzo.mongodbsimpleblogapi.repository;

import com.darianbeluzzo.mongodbsimpleblogapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsernameLikeIgnoreCase(String username);
}
