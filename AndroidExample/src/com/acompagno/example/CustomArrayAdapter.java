package com.acompagno.example;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<String> 
{
  private final Activity context;
  private final String[] names;

  //Here are the views in each item 
  static class ViewHolder
  {
    public TextView text;
    public TextView subText;
    public ImageView icon;
  }

  public CustomArrayAdapter(Activity context, String[] names)
  {
    super(context, R.layout.list_view_item, names);
    this.context = context;
    this.names = names;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) 
  {
    View rowView = convertView;
    if (rowView == null) 
    {
      LayoutInflater inflater = context.getLayoutInflater();
      //Get the layout from the xml file 
      rowView = inflater.inflate(R.layout.list_view_item, null);
      
      //initalize viewholder
      ViewHolder viewHolder = new ViewHolder();
      //get the main textview
      viewHolder.text = (TextView) rowView.findViewById(R.id.text);
      //get the subtext textview
      viewHolder.subText = (TextView) rowView.findViewById(R.id.sub_text);
      //get the icon imageview 
      viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
      
      //set the tag as the viewholder
      rowView.setTag(viewHolder);
    }

    //Grab the viewholder from the tag
    ViewHolder holder = (ViewHolder) rowView.getTag();
    //Set the text of the main text 
    holder.text.setText(names[position]);
    //set the text of the sub text
    holder.subText.setText("This is "+names[position]);
    //set the image of the imageview 
    holder.icon.setImageResource(R.drawable.ic_launcher_trans);
    
    return rowView;
  }
} 