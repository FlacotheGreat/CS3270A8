package com.example.cs3270a8;

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


    public CoursesRecyclerAdapter(@NonNull List<Courses> courses){

        this.coursesList = courses;

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

        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder coursesViewHolder, int i) {

        Courses courses = coursesList.get(i);

        if(courses!=null){

            coursesViewHolder.coursesName.setText(courses.getName());

            coursesViewHolder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Log.d("TestClickCourse", "Course name was clicked");

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

    public class CoursesViewHolder extends RecyclerView.ViewHolder{

        public View itemRoot;
        public Courses courses;
        public TextView coursesName;

        public CoursesViewHolder (@NonNull View itemView) {

            super(itemView);

            itemRoot = itemView;

            coursesName = itemRoot.findViewById(R.id.courseNameText);
        }

    }

}
