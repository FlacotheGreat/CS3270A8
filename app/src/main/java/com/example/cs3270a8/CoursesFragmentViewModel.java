package com.example.cs3270a8;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Courses;

import java.util.List;

public class CoursesFragmentViewModel extends ViewModel {

    private MutableLiveData<Courses> courses = new MutableLiveData<>();
    private LiveData<List<Courses>> coursesList;

    public MutableLiveData<Courses> getCourses(){return courses;}

    public LiveData<List<Courses>> getCoursesList(Context context){

        if(coursesList == null){

            coursesList = AppDatabase.getInstance(context)
                    .coursesDAO()
                    .getCourses();
        }

        return  coursesList;
    }

    public void setCourses(Courses courses){

        this.courses.postValue(courses);

    }

}
