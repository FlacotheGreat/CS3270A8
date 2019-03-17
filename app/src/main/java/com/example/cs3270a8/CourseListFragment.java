package com.example.cs3270a8;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Courses;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment implements CoursesRecyclerAdapter.OnCourseClicked{

    private View root;
    private RecyclerView recyclerView;
    private CoursesRecyclerAdapter adapter;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = root.findViewById(R.id.recyclerCoursesList);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.course_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_download:
                getOnlineCourses task = new getOnlineCourses();
                task.execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();


        adapter = new CoursesRecyclerAdapter(new ArrayList<Courses>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        ViewModelProviders.of(getActivity())
                .get(CoursesFragmentViewModel.class)
                .getCoursesList(getContext())
                .observe(this, new Observer<List<Courses>>() {
                    @Override
                    public void onChanged(@Nullable List<Courses> courses) {
                        if(courses != null){
                            adapter.setCoursesList(courses);
                        }
                    }
                });

    }

    @Override
    public Fragment getCourseClicked(Courses courses) {

            Log.d("TestCourseClicked","Made it to main CourseListFragment");
            CourseViewFragment cvf = new CourseViewFragment();
            cvf.clickedCourse(courses);

            return cvf;
    }

    public class getOnlineCourses extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {

            String rawJson;
            Courses[] courseList;

            try {
                URL url = new URL("https://weber.instructure.com/api/v1/courses");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization","Bearer " + Authorization.AUTH_KEY);

                connection.connect();

                //Delete Database
                AppDatabase.getInstance(getContext())
                        .coursesDAO().
                        deleteAllCourses();

                int status = connection.getResponseCode();

                switch (status){

                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()));

                        Log.d("TestAddCourse", "Adding courses to RecyclerView");
                        //Add courses to Database
                        courseList = parseJson(br);

                        AppDatabase.getInstance(getContext())
                                .coursesDAO()
                                .insertCourse(courseList);
                }

            } catch (MalformedURLException err){

            } catch (IOException err){

            }
            return null;
        }

        private Courses[] parseJson(BufferedReader bufferedReader)
        {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Courses[] courses = null;

            try{
                courses = gson.fromJson(bufferedReader,Courses[].class);
            } catch (Exception err)
            {
                Log.d("TestParse", "Json parse Crashed");
            }

            return courses;
        }
    }
}
