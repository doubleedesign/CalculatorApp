package com.wardlee.expensecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    // Tag for debugging
    private static final String TAG = "MainActivity";

    // The total. BigDecimal is recommended for monetary values for accuracy
    private BigDecimal total = new BigDecimal("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get list of expense types
        // Note: Must be declared final in order to be accessed within the item click
        final ListView expenseListView = findViewById(R.id.expenseList);

        // Create an adapter to get the list from the resource XML file and put it in the listview
        String[] listItems = getResources().getStringArray(R.array.expense_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItems);
        expenseListView.setAdapter(adapter);

        // Add click listener to the items
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create the intent to go to the next activity
                Intent enterExpense = new Intent(MainActivity.this, ExpenseActivity.class);

                // Find out which item was clicked
                String selected = expenseListView.getItemAtPosition(position).toString();

                // Add the label name to be passed to the ExpenseActivity
                enterExpense.putExtra("label", selected);

                // Name the image to be passed to the ExpenseActivity
                String imageName = "";
                switch(selected) {
                    case "Housing":
                        imageName = "housing";
                        break;
                    case "Shopping":
                        imageName = "shopping";
                        break;
                    case "Eating out":
                        imageName = "eating";
                        break;
                    case "Travel":
                        imageName = "travel";
                        break;
                }
                enterExpense.putExtra("imageName", imageName);

                // Start the ExpenseActivity with the above intent and data
                startActivityForResult(enterExpense, 1);
            }
        });
    }


    /**
     * Utility method to update the total display
     */
    private void updateTotalDisplay() {
        TextView totalDisplay = findViewById(R.id.textView_total);

        // Round/format the total to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTotal = df.format(total);

        // Update the display field
        totalDisplay.setText("$" + formattedTotal);
    }


    /**
     * When a an amount is sent back from ExpenseActivity, process it and add it to the total
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == ExpenseActivity.RESULT_OK) {

            // Get the user's input from the child activity (ExpenseActivity)
            String sentText = data.getStringExtra("amount");

            if(sentText != null) {
                // Convert the input string to a double
                BigDecimal sentAmount = new BigDecimal(sentText);

                // Add it to the total
                total = total.add(sentAmount);

                // Update the field that shows the total
                updateTotalDisplay();
            }
        }
    }


    /**
     * Method to reset the total, called when reset button is clicked
     */
    public void resetTotal(View view) {
        total = total.ZERO;
        updateTotalDisplay();
    }
}
