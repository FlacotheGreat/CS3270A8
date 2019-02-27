package com.example.cs3270a8;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Courses;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEditFragment extends Fragment {

    private View root;
    private TextInputEditText editId, editCName, editCourse, editStartDate, editEndDate;
    private FloatingActionButton savebtn;

    public CourseEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        editCName = root.findViewById(R.id.editCName);
        editCourse = root.findViewById(R.id.editCourse);
        editId = root.findViewById(R.id.editId);
        editStartDate = root.findViewById(R.id.editStartDate);
        editEndDate = root.findViewById(R.id.editEndDate);

        savebtn = root.findViewById(R.id.floatingActionButton);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Courses courses = new Courses();

                courses.setCourse_code(editCourse.getText().toString());
                courses.setName(editCName.getText().toString());
                courses.setId(editId.getText().toString());
                courses.setEnd_at(editEndDate.getText().toString());
                courses.setStart_at(editStartDate.getText().toString());

                editCourse.setText("");
                editCName.setText("");
                editId.setText("");
                editStartDate.setText("");
                editEndDate.setText("");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getContext())
                                .coursesDAO()
                                .insertCourse(courses);

                        List<Courses> coursesList = AppDatabase.getInstance(getContext())
                                                        .coursesDAO()
                                                        .getCourses();

                        Courses courseDetail = AppDatabase.getInstance(getContext())
                                                .coursesDAO()
                                                .getCourseDetail(courses.getCourse_code());

                        Log.d("Courses", coursesList.toString() + "\n");

                        Log.d("Courses", "Retrieving course details " + courseDetail.toString());
                    }
                }).start();

            }
        });

    }
}
