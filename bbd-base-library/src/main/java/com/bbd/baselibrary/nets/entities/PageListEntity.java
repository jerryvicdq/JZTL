package com.bbd.baselibrary.nets.entities;

import java.util.List;

/**
 *
 *created by Damon on 2017/7/4 9:20
 *
 *description: 
 *
 */
public class PageListEntity<T> {
    private List<T> content;

    public PageListEntity() {
    }

    public PageListEntity(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
