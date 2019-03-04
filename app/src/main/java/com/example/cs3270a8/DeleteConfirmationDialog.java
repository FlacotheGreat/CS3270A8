package com.example.cs3270a8;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270a8.db.AppDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteConfirmationDialog extends DialogFragment {


    public DeleteConfirmationDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("This will permanetly delete the course.")
                .setTitle("Are you Sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("TestDialog", "Yes button clicked delete course");

//                        AppDatabase.getInstance(getContext())
//                                .coursesDAO()
//                                .deleteCourse(course);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("TestDialog", "Cancel clicked do nothing");

                    }
                });

       return builder.create();
    }
}
