package com.example.cs3270a8;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs3270a8.db.entities.Courses;

import java.util.List;

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.CoursesViewHolder> {

    private final List<Courses> coursesList;
    private OnCourseClicked onCourseClicked;

    interface OnCourseClicked{
        void getCourseClicked(Courses courses);
    }

    public CoursesRecyclerAdapter(@NonNull List<Courses> courses, OnCourseClicked onCourseClicked){

        this.coursesList = courses;
        this.onCourseClicked = onCourseClicked;
    }

    public void setCoursesList(List<Courses> courses){

        this.coursesList.clear();
        this.coursesList.addAll(courses);

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_courses,viewGroup,false);

        return new CoursesViewHolder(view, onCourseClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder coursesViewHolder, int i) {

        final Courses courses = coursesList.get(i);

        if(courses!=null){

            coursesViewHolder.coursesName.setText(courses.getName());

            coursesViewHolder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Log.d("TestClickCourse", "Course name was clicked");

                Log.d("TestClickCourse","Which course was clicked? maybe this one:" +courses.getName());

                    onCourseClicked.getCourseClicked(courses);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, new CourseViewFragment())
                            .addToBackStack(null)
                            .commit();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View itemRoot;
        public Courses courses;
        public TextView coursesName;
        OnCourseClicked onCourseClicked;

        public CoursesViewHolder (@NonNull View itemView, OnCourseClicked onCourseClicked) {

            super(itemView);

            itemRoot = itemView;
            coursesName = itemRoot.findViewById(R.id.courseNameText);
            this.onCourseClicked = onCourseClicked;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCourseClicked.getCourseClicked(courses);
        }
    }

}
