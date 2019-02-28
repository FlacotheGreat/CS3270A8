package com.example.cs3270a8.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.cs3270a8.db.entities.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {

    @Insert
    void insertCourse(Courses ... courses);

    @Update
    void updateCourses(Courses courses);

    @Delete
    void deleteCourse(Courses courses);

    @Query("Select * from Courses")
    LiveData<List<Courses>> getCourses();

    @Query("Select * from Courses Where course_code LIKE :course_code")
    Courses getCourseDetail(String course_code);


}
