package com.acompagno.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Example1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Example 1");
        //Array containing the string for the buttons
        final String[] texts = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
                "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop"};

        //Get the ScrollView  defined in the xml file so we can add views to it.
        ScrollView scroll = new ScrollView(getActivity());

        //Create new linear layout to add the views
        //Views cant be added directly to a scroll view
        LinearLayout ll = new LinearLayout(getActivity());
        //Set orientation of the linear layout
        ll.setOrientation(LinearLayout.VERTICAL);
        //Set the gravity of the linear. Center the items in the linear layout
        ll.setGravity(Gravity.CENTER);
        //Add the linear layout to the scrollview
        scroll.addView(ll);

        //Create a new text view. The final allows us to call to it inside the "setOnClickListener" later on
        final TextView text = new TextView(getActivity());
        //Set the text of the textview
        text.setText("Press Some Buttons");
        //Set the size of the textview
        text.setTextSize(30);
        //Center the text
        text.setGravity(Gravity.CENTER);
        //Add the textview to the linear layout
        ll.addView(text);

        //For loop that goes through 10 iterations (0-9)
        for (int i = 0; i < texts.length; i++) {
            //Kinda hard to explain, we need final to access i in the "onClick"  method, but we cant use ++ on a final int.
            //So we set i to temp, which is final.
            final int temp = i;

            //Create a new button
            Button btn = new Button(getActivity());
            //Set the layout params of the button to wrap content.
            btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            //Set the padding of the buttons, helps set the size of the button
            btn.setPadding(100, 0, 100, 0);
            //Set the text of the button.
            btn.setText("Button " + (i + 1));

            //getActivity() is defines what the
            // button does when it is clicked. You dont really need to understand getActivity() very well.
            //Just add whatever you want it to do in the "onClick" method
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //Change the text of the text we created in the beginning of the code
                    text.setText(texts[temp]);
                }

            });
            //Add the button we just created to the linear layout
            ll.addView(btn);
        }
        return scroll;
    }
}