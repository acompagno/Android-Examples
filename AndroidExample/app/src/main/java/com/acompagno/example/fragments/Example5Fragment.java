package com.acompagno.example.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.acompagno.example.CustomArrayAdapter;

public class Example5Fragment extends ListFragment {

    //Items that will be in the listview
    String[] listViewItems = new String[]{"Item 0", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8",
            "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16", "Item 17", "Item 18", "Item 19",
            "Item 20", "Item 21", "Item 22", "Item 23", "Item 24", "Item 25", "Item 26", "Item 27", "Item 28", "Item 29", "Item 30",
            "Item 31", "Item 32", "Item 33", "Item 34", "Item 35", "Item 36", "Item 37", "Item 38", "Item 39", "Item 40", "Item 41",};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Create the custom adapter
        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), listViewItems);
        //Set the adapter of the list to the adapter we just created
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Define what happens when an item is clicked
        Toast.makeText(getActivity(), "" + l.getItemAtPosition(position) + " Selected", Toast.LENGTH_SHORT).show();
    }
}
