package com.example.cs3270a8;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.entities.Assignment;
import com.example.cs3270a8.db.entities.Courses;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class AssignmentListFragment extends Fragment{

    private View root;
    private ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    Assignment assignments;

    public AssignmentListFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.course_assignment_list, container, false);
        listView = root.findViewById(R.id.assignmentList);

        list = new ArrayList<>();

        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);

        return root;
    }

    public class getAssignments extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {

            String rawJson;
            Assignment[] assignmentsList;

            try {
                URL url = new URL("https://weber.instructure.com/api/v1/courses");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + Authorization.AUTH_KEY);

                connection.connect();

                //Delete Database
                AppDatabase.getInstance(getContext()).coursesDAO().
                        deleteAllCourses();

                int status = connection.getResponseCode();

                switch (status) {

                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        Log.d("TestAddCourse", "Adding courses to RecyclerView");
                        //Add courses to Database
                        assignmentsList = parseJson(br);

                        list.add(assignmentsList.toString());
                }

            } catch (MalformedURLException err) {

                Log.d("TestErr", err.toString() + "Malformed Exception");

            } catch (IOException err) {

                Log.d("TestErr", err.toString() + "Exception");
            }
            return null;
        }

        private Assignment[] parseJson(BufferedReader bufferedReader) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Assignment[] assignments = null;

            try {
                assignments = gson.fromJson(bufferedReader, Assignment[].class);
            } catch (Exception err) {
                Log.d("TestParse", "Json parse Crashed");
            }

            return assignments;
        }
    }
}
