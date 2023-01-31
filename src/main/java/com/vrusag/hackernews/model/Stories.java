package com.vrusag.hackernews.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Stories {
    @Id
    private String id;
    private String by;
    private String title;
    private String[] kids;
    private String descendants;
    private String url;
    private String score;
    private Date time;
    private String type;


}
