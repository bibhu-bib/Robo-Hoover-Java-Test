package com.yoti.hoover.utils;

public class JsonResourceObjectMapper<T> {
    private Class<T> model;

    public JsonResourceObjectMapper(Class<T> model) {
        this.model = model;
    }


}