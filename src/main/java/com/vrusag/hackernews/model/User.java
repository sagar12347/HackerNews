package com.vrusag.hackernews.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class User {
    @Id
    private String id;
    private String about;
    private Date created;
    private String delay;
    private String karma;
    private String submitted;

}
