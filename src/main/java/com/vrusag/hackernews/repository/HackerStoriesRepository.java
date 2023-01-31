package com.vrusag.hackernews.repository;

import com.vrusag.hackernews.model.Stories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HackerStoriesRepository extends MongoRepository<Stories, Integer> {
    Optional<Stories> findById(String id);

}
