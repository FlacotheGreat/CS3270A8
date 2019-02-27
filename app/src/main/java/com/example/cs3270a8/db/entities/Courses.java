package com.example.cs3270a8.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Courses {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "id")
    public String id;

    public String name;
    public String course_code;
    public String start_at;
    public String end_at;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse_code() {
        return course_code; }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    @Override
    public String toString() {
        return "Courses{" +
                        "_id=" + _id +
                        ", id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", course_code='" + course_code + '\'' +
                        ", start_at='" + start_at + '\'' +
                        ", end_at='" + end_at + '\'' + "}\n";
    }
}
