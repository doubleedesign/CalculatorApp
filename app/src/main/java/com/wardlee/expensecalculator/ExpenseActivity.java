package com.wardlee.expensecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        // Find the label textview in the layout
        TextView label = findViewById(R.id.textView_expenseLabel);

        // Get the text that was passed in when this activity was started
        String labelText = getIntent().getStringExtra("label");

        // Set the label text
        label.setText("Enter your " + labelText.toLowerCase() + " expense amount");

        // Find the imageview in the layout
        ImageView image = findViewById(R.id.imageView_icon);

        // Get the image name that was passed in
        String imageName = getIntent().getStringExtra("imageName");

        // Get the ID of the matching drawable
        Context context = image.getContext();
        int imageID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        // Set the image
        image.setImageResource(imageID);
    }


    /**
     * Method to process the expense input when the button is clicked
     * Sends the input back to the activity that called this one (MainActivity)
     * @param view
     */
    public void addExpense(View view) {

        // Get the user's input
        EditText inputText = findViewById(R.id.editText_inputAmount);
        String input = inputText.getText().toString();

        // If the input isn't empty, put it into the result that is sent back to MainActivity using a new intent
        // If the input is empty, this will skip down to finishing the activity
        // (sending an empty string causes an error that crashes the app)
        if(!input.isEmpty()) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("amount", input);
            setResult(MainActivity.RESULT_OK, resultIntent);
        }

        // Finish the activity - go back to previous (i.e. MainActivity)
        finish();
    }
}