package com.vrusag.hackernews.repository;

import com.vrusag.hackernews.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HackerNewsUserRepo extends MongoRepository<User, Integer> {
    Optional<User> findById(String id);

}
