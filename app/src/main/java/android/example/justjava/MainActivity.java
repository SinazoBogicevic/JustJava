package android.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        //shown if quantity is more than 100
        if (quantity == 100) {
            //show error message as toast
            Toast.makeText(this, "You cannot have more than 100 cups", Toast.LENGTH_SHORT).show();
            //exit early
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the minusbutton is clicked.
     */
    public void decrement(View view) {
        //shown if quantity is more than 100
        if (quantity <= 1) {
            //show error message as toast
            Toast.makeText(this, "You cannot have fewer than 1 cup", Toast.LENGTH_SHORT).show();
            //exit early
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox hasWhippedCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = hasWhippedCheckbox.isChecked();

        CheckBox hasChocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate = hasChocolateCheckbox.isChecked();

        EditText userField = (EditText) findViewById(R.id.user_field);
        String userName = userField.getText().toString();
        if(userName.isEmpty()){
            Toast.makeText(this,"Please enter a name", Toast.LENGTH_SHORT).show();
        }else{
            int price = calculatePrice(hasWhippedCream, haschocolate);
            String priceMessage = createOrderSummary(price, hasWhippedCream, haschocolate, userName);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + userName);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream whether or not has whipped cream
     * @param addchocolate  whether or not has chocolate
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addchocolate) {
        int price = 5;

        // two if statements because they are independent of each other

        // adds $1 if user wants whipped cream
        if (addWhippedCream) {
            price = price + 1;
        }

        // adds $2 if user wants chocolate
        if (addchocolate) {
            price = price + 2;
        }

        return price * quantity;
    }

    /**
     * Creates an order summary
     *
     * @param price of order
     * @param addWhippedCream is whether or not user wants whipped cream
     * @param addChocolate is whether or not user wants chocolate
     * @return summary message of orders
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String message = "Name: " + name;
        message += "\nAdd whipped cream: " + addWhippedCream;
        message += "\nAdd chocoalte: " + addChocolate;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: $" + price;
        message += "\nThank you";
        return message;
    };

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}