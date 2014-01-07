package com.acompagno.example;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Example4Fragment extends Fragment 
{
	private int matrixSize;
	private double[][] matrixValues;
	private int spinnerChoice = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		//Create a ScrollView This will be our main view 
		ScrollView scroll = new ScrollView(getActivity());

		//Create a linear layout 
		final LinearLayout linearMain = new LinearLayout(getActivity());
		//Set the orientation to vertical 
		linearMain.setOrientation(LinearLayout.VERTICAL);
		//Add the main linear layout to our scrollview 
		scroll.addView(linearMain);

		//Create a new edit text. we will use this to get the size the user wants
		EditText matrixSizeInput = new EditText(getActivity());
		//Makes the numbers keyboard pop up instead of the standard keyboard when the user clicks on the edit text 
		matrixSizeInput.setInputType(InputType.TYPE_CLASS_NUMBER);
		//clears the focus when the view first pops up
		matrixSizeInput.clearFocus();
		//Set the layout parameters of the edit text -1 = fill parent 
		matrixSizeInput.setLayoutParams(new LinearLayout.LayoutParams(-1,LayoutParams.WRAP_CONTENT, 1f)); //width,height,weight
		//Set what happens when the user types
		matrixSizeInput.addTextChangedListener(new TextWatcher()
		{
			/* 
			 * (non-Javadoc)
			 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
			 */
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				//Check if the string is actually an integer, if this isnt the the app will crash
				if (isInteger(s.toString()))
				{
					//Set the variable matrixSize to what the user entered
					matrixSize = Integer.parseInt(s.toString());
				}
			}

			/*
			 * (non-Javadoc)
			 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
			 */
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			/*
			 * (non-Javadoc)
			 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
			 */
			@Override
			public void afterTextChanged(Editable s){}
		});

		 //Creates the Dialog which asks the user for the sizes 
		final AlertDialog dialog_ask_size = new AlertDialog.Builder(getActivity()) 
		.setTitle("Choose matrix size (max = 5)") //Set the text of the dialog 
		.setCancelable(false) //MAkes it so the user cant close the dialog by clicking outside of it or by pressing back
		.setView(matrixSizeInput) //Sets the viuew that will be inside the dialog. in this case we use the edittext we just created
		.setPositiveButton("Go", new DialogInterface.OnClickListener() //Sets what the positive button does  
		{
			//put nothign in here since we want to completely override this later on.
			//Default behavior makes the dialog go away whenever this is clicked
			//We want to stop that incase the user puts an invalid value for the size of the matrix 
			public void onClick(DialogInterface dialog, int which) {} 
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{ 
				//Call a method from main activity to chang the title of the actionbar
				((MainActivity) getActivity()).changeTitleFromFragment("Android Example");
				//Switch back to the mainpage fragment
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainPageFragment()).commit();
			}
		})
		.create();

		//Overrides the usual behavior of the positive button
		dialog_ask_size.setOnShowListener(new DialogInterface.OnShowListener() 
		{
			/*
			 * (non-Javadoc)
			 * @see android.content.DialogInterface.OnShowListener#onShow(android.content.DialogInterface)
			 */
			@Override
			public void onShow(DialogInterface dialog) 
			{
				//Get the positive button
				Button button = dialog_ask_size.getButton(AlertDialog.BUTTON_POSITIVE);
				//set an onclick listener to the positive button 
				button.setOnClickListener(new View.OnClickListener() 
				{
					/*
					 * (non-Javadoc)
					 * @see android.view.View.OnClickListener#onClick(android.view.View)
					 */
					@Override
					public void onClick(View view) 
					{
						//if the size entered is less than or equal to 5 and greater than 1 
						if (matrixSize <= 5 && matrixSize > 1)
						{
							//initialize the 2d array thats is going to hold the values of matrix
							matrixValues = new double[matrixSize][matrixSize];
							//create the matrix and add it to the main linear layout
							linearMain.addView(createMatrix(matrixSize , getActivity()));
							
							//Create a spinner for the different matrix operations
							Spinner spinner = new Spinner(getActivity());
							//Create a list with all the items that will be in the spinner
							final List<String> spinnerOptions = new ArrayList<String>();
							spinnerOptions.add("Determinant");
							spinnerOptions.add("Trace");
							spinnerOptions.add("Rank");
							spinnerOptions.add("One Norm");
							
							//Create an array adapter for the spinner
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, spinnerOptions);
							//Set the style of the items in the spinner
							dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							//Set the adapter of the spinner to the adapter we created 
							spinner.setAdapter(dataAdapter);
							
							//set what happens when an item is selected in the spinner 
							spinner.setOnItemSelectedListener(new OnItemSelectedListener()
							{
								@Override
								public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
								{
									//Save what item is chosen in the spinner
									 spinnerChoice = position;
								}

								@Override
								public void onNothingSelected(AdapterView<?> parentView) {}
							});

							final TextView resultText = new TextView(getActivity());
							//Set the size of the textview 
							resultText.setTextSize(30);
							//Center the text
							resultText.setGravity(Gravity.CENTER);
							//Set the padding on the Result Textview
							resultText.setPadding(0 , 10 , 0 , 10);
							
							//Create button which is used to carry out the selected matrix operation
							Button btn = new Button(getActivity());
							//Set the text of the button
							btn.setText("Complete Operation");
							//Set what happens when the button is clicked
							btn.setOnClickListener(new View.OnClickListener() 
							{
								@Override
								public void onClick(View arg0) 
								{
									//Change the text of the text we created in the beginning of the code
									completeMatrixOperation(spinnerChoice, matrixValues , resultText, spinnerOptions);
								}

							});
							
							//Add the views to the main linear layout
							linearMain.addView(spinner);
							linearMain.addView(btn);
							linearMain.addView(resultText);
							
							//close the dialog 
							dialog_ask_size.dismiss();
						}
						
						//if the size is too big
						else if (matrixSize > 5)
						{
							//make a toast telling the use the size entered is too large 
							Toast.makeText(getActivity(),"Size must be Less than 6", Toast.LENGTH_SHORT).show();
						}
						
						//if the size is too small 
						else if (matrixSize < 2) 
						{
							//make a toast telling the use the size entered is too small
							Toast.makeText(getActivity(), "Size must be greater than 1", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
		
		//Show the dialog
		dialog_ask_size.show();
		
		//return scroll, this is out main view
		return scroll;
	}

	//This method creates the matrix 
	public LinearLayout createMatrix(int matrixSize , Context context)
	{
		//Create the main vertical linear layout this will hold all the rows of the matrix  
		LinearLayout linearMatrixMain = new LinearLayout(context);
		//Set the orientation to vertical 
		linearMatrixMain.setOrientation(LinearLayout.VERTICAL);
		//Set layout params -1 = fill parent 
		linearMatrixMain.setLayoutParams(new LinearLayout.LayoutParams(-1,-1, 1f));//width,height,weigth
		
		//We create the matrix in these loops
		for (int i = 0 ; i < matrixSize ; i++)
		{
			final int tempI = i;
			
			//Create a new linear layout each of these is going to be a row in the matrix
			LinearLayout row = new LinearLayout(context);
			//set the orientation to horizotal
			row.setOrientation(LinearLayout.HORIZONTAL);
			
			for (int a = 0 ; a < matrixSize ; a++ )
			{
				final int tempA = a;
				
				//create a new text field 
				EditText field = new EditText(context);
				//make it so it cant be more than one line
				field.setSingleLine();
				//Clear focus
				field.clearFocus();
				//make it so the number keyboard shows up instead of the text keyboard  
				field.setInputType(InputType.TYPE_CLASS_PHONE);
				//set the layout params -1 = fill parent 
				field.setLayoutParams(new LinearLayout.LayoutParams(-1,-1, 1f));
				
				//Set what happens when you type
				field.addTextChangedListener(new TextWatcher()
				{
					/*  
					 * (non-Javadoc)
					 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
					 */
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count)
					{
						//Makes sure that what is entered is a double 
						if (isDouble(s.toString()))
						{
							//Sets the value entered in the matrix 
							matrixValues[tempI][tempA] = Double.parseDouble(s.toString());
						}
					}

					/*
					 * (non-Javadoc)
					 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
					 */
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

					/*
					 * (non-Javadoc)
					 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
					 */
					@Override
					public void afterTextChanged(Editable s){}
				});
				//add the field to the row
				row.addView(field);
			}
			//add the row to the vertical linear layout 
			linearMatrixMain.addView(row);
		}
		//return the linear layout which contains the matrix 
		return linearMatrixMain;
	}

	//This method checks if the string is a integer
	public static boolean isInteger(String s) 
	{
		try 
		{ 
			Integer.parseInt(s); 
		}
		catch(NumberFormatException e) 
		{ 
			return false; 
		}
		return true;
	}

	//This method checks if the string is a double 
	public static boolean isDouble(String s) 
	{
		try 
		{ 
			Double.parseDouble(s); 
		}
		catch(NumberFormatException e) 
		{ 
			return false; 
		}
		return true;
	}
	
	public static void completeMatrixOperation(int spinnerOption , double[][] matrixValues , TextView result , List<String> operationOptions)
	{
		//Initalize a matrix object from the JAMA library
		//Use the values from the EditTexts
		Matrix matrix = new Matrix(matrixValues);
		switch(spinnerOption)
		{
		//Calculate Determinant 
		case 0:
			result.setText(operationOptions.get(spinnerOption)+" = "+matrix.det());
			break;
		//Calculate Trace
		case 1:
			result.setText(operationOptions.get(spinnerOption)+" = "+matrix.trace());
			break;	
		//Calculate Rank
		case 2:
			result.setText(operationOptions.get(spinnerOption)+" = "+matrix.rank());
			break;
		//Calculate OneNorm
		case 3:
			result.setText(operationOptions.get(spinnerOption)+" = "+matrix.norm1());
			break;				
		}
	}
}