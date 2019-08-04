package com.example.dezhiwang.studycollection.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.dezhiwang.studycollection.R;

public class ProgressDialogFragment extends DialogFragment {

    private View view;
    private AlertDialog.Builder builder;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        setCancelable(true);
//        view = inflater.inflate(R.layout.fragment_dialog, container);
//        return view;
//    }

    public static ProgressDialogFragment getInstance(){
        return new ProgressDialogFragment();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Sign in",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {

                            }
                        }).setNegativeButton("Cancel", null);
        return builder.create();

    }


}
