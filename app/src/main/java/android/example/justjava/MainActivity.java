/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package android.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends Activity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
        public void submitOrder(View view) {
            EditText nameField = (EditText) findViewById(R.id.name_field);
            String name = nameField.getText().toString();
            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
            CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckBox.isChecked();
            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData (Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if(intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
        }
     private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate)
     {
         String priceMessage = "Name: " + name;
         priceMessage = priceMessage + "\nAdd Whipped Cream? " + addWhippedCream;
         priceMessage = priceMessage + "\nAdd Chocolate? " + addChocolate;
         priceMessage = priceMessage + "\nQuantity: " + quantity;
         priceMessage = priceMessage + "\nTotal: Rs " + calculatePrice(addWhippedCream, addChocolate);
         priceMessage = priceMessage + "\nThank you!";
         return priceMessage;
     }
    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 20;
        if(addWhippedCream)
        {
            basePrice = basePrice + 15 ;
        }

        if(addChocolate)
        {
            basePrice = basePrice + 30 ;
        }
        return quantity * basePrice;
    }
    public void increment(View view){
        if(quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    public void decrement(View view){
        if(quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.num_text_view);
        quantityTextView.setText("" + number);
    }
}