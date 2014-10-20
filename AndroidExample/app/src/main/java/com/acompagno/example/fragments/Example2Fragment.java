package com.acompagno.example.fragments;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class Example2Fragment extends Fragment {

    private Map<String, String> strings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializes a hashmap where the string from the text fields will be stored
        strings = new HashMap<String, String>();

        //Create a new scrollview this is the main view were gonna use.
        ScrollView scroll = new ScrollView(getActivity());

        //Create new linear layout to add the views
        //Views cant be added directly to a scroll view
        LinearLayout ll = new LinearLayout(getActivity());
        //Set orientation of the linear layout
        ll.setOrientation(LinearLayout.VERTICAL);
        //add the linearlayout to the scrollview
        scroll.addView(ll);


        //Create a new text view. The final allows us to call to it inside the "setOnClickListener" later on
        final TextView text = new TextView(getActivity());
        //Set the text of the textview
        text.setText("Type in the text fields and press the button");
        //Set the size of the textview
        text.setTextSize(30);
        //Center the text
        text.setGravity(Gravity.CENTER);
        //Add the textview to the linear layout
        ll.addView(text);

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            //Create a linear layout, these will be horizontal so we can have the text field and the button on the same line
            LinearLayout linearHorizontal = new LinearLayout(getActivity());
            //set the orientation of the linear layout to horizontal
            linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearHorizontal.setGravity(Gravity.CENTER);

            //initialize a spot in the hashmap for getActivity() specific field
            strings.put("field" + i, "");

            //create the text field
            //createEditText is a method that handles creating the edit text. this helps make the code less cluttered 
            EditText field = createEditText(i);

            //Create a new button
            Button btn = new Button(getActivity());
            //Set the text of the button.
            btn.setText("Change Text");

            // defines what the button does when it is clicked.
            //Just add whatever you want it to do in the "onClick" method
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //Change the text of the text we created in the beginning of the code
                    text.setText(strings.get("field" + temp));
                }

            });

            //Add the views we just created to the horizontal linear layout
            linearHorizontal.addView(field);
            linearHorizontal.addView(btn);

            //now add the horizontal linear layout to the main vertical one
            ll.addView(linearHorizontal);
        }

        //since scroll is the main view that contains all other views, we return it
        return scroll;
    }

    //Method creates, sets up, and returns an edit text
    private EditText createEditText(final int i) {
        //Set the define sizing for a view. we're going to use this sizing for the edit text
        LayoutParams lparams = new LayoutParams(400, LayoutParams.WRAP_CONTENT); // Width , height

        //create the edittext
        EditText edittext = new EditText(getActivity());

        //set the its sizing to the parameters we just created
        edittext.setLayoutParams(lparams);

        //Sets the max lines of the field
        edittext.setSingleLine();

        //Set what happens when you type
        edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Store what is written in the field in the hashmap
                //s contains whats in the text field.
                strings.put("field" + i, s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Return the exittext we just created
        return edittext;
    }
}