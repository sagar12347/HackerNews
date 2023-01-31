package com.vrusag.hackernews.controller;

import com.vrusag.hackernews.model.Stories;
import com.vrusag.hackernews.service.HNStoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HackerNewsController {

    @Autowired
    HNStoriesService service;

    @GetMapping("/top-stories")
    public ResponseEntity<List<Stories>> getTopStories() {
        return new ResponseEntity<>(service.getTopStories(), HttpStatus.OK);
    }

    @GetMapping("/past-stories")
    public List<Stories> getPastStories() {
        return service.getTopStories();

    }

    @GetMapping("/comments")
    public List<Stories> getComments(@RequestParam String id) {
        return service.getComments(id);

    }
}
