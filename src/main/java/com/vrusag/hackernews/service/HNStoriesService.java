package com.vrusag.hackernews.service;

import com.vrusag.hackernews.config.Constants;
import com.vrusag.hackernews.model.Stories;
import com.vrusag.hackernews.model.User;
import com.vrusag.hackernews.repository.HackerNewsUserRepo;
import com.vrusag.hackernews.repository.HackerStoriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HNStoriesService {

    @Autowired
    HackerStoriesRepository hackerStoriesRepository;
    @Autowired
    HackerNewsUserRepo userRepo;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RestTemplate template;

    @Cacheable(value = "topStories")
    public List<Stories> getTopStories() {
        List<Stories> sl = hackerStoriesRepository.findAll();
        List<Stories> storiesList = sl.stream().
                sorted(Comparator.comparing(Stories::getScore).reversed()).limit(10).collect(Collectors.toList());
        return storiesList;
    }

    @Cacheable(value = "comments", key = "#id")
    public List<Stories> getComments(String id) {
        Stories stories;
        stories = mongoTemplate.findById(id, Stories.class);
        if (Objects.isNull(stories)) {
            stories = apiCallForFetchItem(id);
        }
        String[] kids = stories.getKids();
        ArrayList<Stories> sList = new ArrayList<>();
        for (String s : kids) {
            log.info("kid: " + s);
            sList.add(apiCallForFetchItem(s));
        }
        // TODO:  Given story sorted by a total number of child comments, Each comment should contain comment text, the user’s hacker news handle.
//        return  sList.stream().sorted(Comparator.comparing(s->s.getKids().length)).limit(10).collect(Collectors.toList());

        return sList.stream().limit(10).collect(Collectors.toList());
    }

    @Cacheable(value = "user", key = "#id")
    public Optional<User> getUser(String id) {
        return userRepo.findById(id);
    }

    public String[] apiCallForTopStories() {
        String[] response = template.getForObject(Constants.BASE_URL + Constants.VERSION + Constants.TOP_STORIES, String[].class);
        return response;
    }

    public Stories apiCallForFetchItem(String id) {
        Stories response = template.getForObject(Constants.BASE_URL + Constants.VERSION + Constants.ITEM + id + ".json", Stories.class);
        return response;
    }
}
