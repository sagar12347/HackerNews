package com.vrusag.hackernews.service;


import com.vrusag.hackernews.model.Stories;
import com.vrusag.hackernews.repository.HackerStoriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    HNStoriesService service;
    @Autowired
    HackerStoriesRepository repository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Scheduled(fixedDelayString = "${caching.spring.storiesList}")
    @CachePut(value = "topStories")
    public List<Stories> updateTopStoriesInRepo() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        String[] ids = service.apiCallForTopStories();
        ArrayList<Stories> storiesArrayList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Stories stories = service.apiCallForFetchItem(ids[i]);
            storiesArrayList.add(stories);
            /*TODO: For Now no need to use DataBase all data will fetched from cache.
                     // mongoTemplate.insert(stories);*/
        }
        emptyTopStories();
        log.info("Excecuted 'updateTopStoriesInRepo Scheduler");
        return storiesArrayList;
    }

    @CacheEvict(value = "topStories", allEntries = true)
    public void emptyTopStories() {
        log.info("emptying topStories cache");
    }

    @CacheEvict(value = "comments", allEntries = true)
    @Scheduled(fixedDelayString = "${caching.spring.storiesList}")
    public void emptyComments() {
        log.info("emptying Comments cache");
    }

}
