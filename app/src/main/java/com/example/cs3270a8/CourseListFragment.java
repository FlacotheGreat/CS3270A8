package com.example.cs3270a8;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270a8.db.entities.Courses;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private CoursesRecyclerAdapter adapter;
    private CoursesRecyclerAdapter.OnCourseClicked courseClicked;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerCoursesList);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();


        adapter = new CoursesRecyclerAdapter(new ArrayList<Courses>(), courseClicked);
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
}
