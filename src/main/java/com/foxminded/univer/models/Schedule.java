package com.foxminded.univer.models;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Lecture> lectures = new ArrayList<>();

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
