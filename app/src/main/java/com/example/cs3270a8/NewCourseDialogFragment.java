package com.example.cs3270a8;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Courses;

public class NewCourseDialogFragment extends DialogFragment {

    private TextInputEditText editId, editName, editCourseCode,editStartDate,editEndDate;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.dialog_new_course,container,false);

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();


        setHasOptionsMenu(false);

        editId = (TextInputEditText) root.findViewById(R.id.editId);
        editName = (TextInputEditText) root.findViewById(R.id.editCName);
        editCourseCode = (TextInputEditText) root.findViewById(R.id.editCourse);
        editStartDate = (TextInputEditText) root.findViewById(R.id.editStartDate);
        editEndDate = (TextInputEditText) root.findViewById(R.id.editEndDate);

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.savefab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Courses courses = new Courses();
                        courses.setId(editId.getText().toString());
                        courses.setName(editName.getText().toString());
                        courses.setCourse_code(editCourseCode.getText().toString());
                        courses.setStart_at(editStartDate.getText().toString());
                        courses.setEnd_at(editEndDate.getText().toString());

                        if(courses != null) {
                            AppDatabase.getInstance(getContext())
                                    .coursesDAO()
                                    .insertCourse(courses);
                        }
                    }
                }).start();

                dismiss();
            }
        });

        return  root;
    }

}
