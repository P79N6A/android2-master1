package com.wh.wang.scroopclassproject.newproject.ui.fragment.details_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseOrderDetailsFragment extends Fragment {


    public CourseOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_order_details, container, false);
    }

}
