package com.insider.test.API.models;

import lombok.Data;
import java.util.List;

@Data
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;


    @Data
    public static class Category {
        private int id;
        private String name;
    }

    @Data
    public static class Tag {
        private int id;
        private String name;
    }
}