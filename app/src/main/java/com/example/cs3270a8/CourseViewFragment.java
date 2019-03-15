package com.example.cs3270a8;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Courses;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseViewFragment extends Fragment {

    private View root;
    private Courses course;
    private EditText editId, editCName, editCCode, editSDate,editEDate;
    private Button saveBtn;
    final static int aName = 1;
    private FragmentManager fm;

    public CourseViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_course_view, container, false);

        editId = root.findViewById(R.id.editCourseId);
        editCName = root.findViewById(R.id.editCName);
        editCCode = root.findViewById(R.id.editCourse);
        editSDate = root.findViewById(R.id.editStartDate);
        editEDate = root.findViewById(R.id.editEndDate);
        saveBtn = root.findViewById(R.id.save_btn);

        saveBtn.setVisibility(View.INVISIBLE);
        editId.setEnabled(false);
        editCName.setEnabled(false);
        editCCode.setEnabled(false);
        editSDate.setEnabled(false);
        editEDate.setEnabled(false);



        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Log.d("TestClickedCourse","When are we created?");

        addCoursesToText();

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.view_edit_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_edit:
                //Do something
                Log.d("TestOptions", "Edit button pressed");

                editId.setEnabled(true);
                editCName.setEnabled(true);
                editCCode.setEnabled(true);
                editSDate.setEnabled(true);
                editEDate.setEnabled(true);

                saveBtn.setVisibility(View.VISIBLE);

                return true;
            case R.id.action_delete:
                //Bring up dialog
                Log.d("TestOptions", "Delete button pressed");
                DeleteConfirmationDialog deleteConfirmationDialog = new DeleteConfirmationDialog();
                AppCompatActivity activity = (AppCompatActivity) getContext();

                deleteConfirmationDialog.setTargetFragment(this,aName);
                deleteConfirmationDialog.show(activity.getSupportFragmentManager(),"DeleteDialog");



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickedCourse(Courses courses)
    {

        if(courses != null){
            Log.d("TestCourseClicked", "CourseView has:" + courses.toString());
            this.course = courses;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == aName && resultCode == 0){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    AppDatabase.getInstance(getContext())
                            .coursesDAO()
                            .deleteCourse(course);

                }
            }).start();
        }
    }

    private void addCoursesToText() {

        Log.d("TestCourseClicked", "AddCoursesToText called");
        if (this.course != null){
            Log.d("TestCourseView", "Entering in the following course to Edit text" + course.toString());
            editId.setText(course.id);
            editCName.setText(course.name);
            editCCode.setText(course.course_code);
            editSDate.setText(course.start_at);
            editEDate.setText(course.end_at);

            Log.d("TestCourseClicked", "CourseView has:" + course.toString());
        }
    }
}
