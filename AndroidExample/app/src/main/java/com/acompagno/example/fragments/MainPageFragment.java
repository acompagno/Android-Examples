package com.acompagno.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scroll = new ScrollView(getActivity());

        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);

        String[] titles = {"Example 1", "Example 2", "Example 3", "Example 4", "Example 5"};
        String[] descriptions = {
                "Includes Buttons created programically and shows how to define what those buttons do", //Example1
                "Includes Views created programically and shows how to store text entered in an EditText and use it.",//Example2
                "Dynamically creates a square matrix based on a value entered by the user",//Example3
                "Expands on Example 3 by adding 4 Matrix operations from the JAMA Package",//Example4
                "ListView example with custom array adapter"//Example5
        };

        for (int i = 0; i < titles.length; i++) {
            TextView exampleTitle = new TextView(getActivity());
            exampleTitle.setText(titles[i]);
            exampleTitle.setTextSize(25);
            exampleTitle.setGravity(Gravity.CENTER);
            ll.addView(exampleTitle);

            TextView exampleText = new TextView(getActivity());
            exampleText.setText(descriptions[i]);
            exampleText.setTextSize(18);
            exampleText.setGravity(Gravity.CENTER);
            ll.addView(exampleText);
        }

        scroll.addView(ll);
        return scroll;
    }
}