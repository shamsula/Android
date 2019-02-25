// MainActivity.java
// Calculates a bill total based on a tip percentage
package com.shamsul.shamsulpricecalculator;

import android.os.Bundle; // for saving state information
import android.support.v7.app.AppCompatActivity; // base class
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting

// MainActivity class for the Tip Calculator app
public class Main2Activity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double taxPercent = 0.13; // initial tax percentage
    private double shipPercent = 0.10; // initial ship percentage
    private TextView amountTextView; // shows formatted bill amount
    private TextView percentTextView; // shows tax percentage
    private TextView percentTextView1; // shows ship percentage
    private TextView shipTextView; // shows calculated tip amount
    private TextView taxTextView; // shows calculated tax amount
    private TextView totalTextView; // shows calculated total bill amount

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main2); // inflate the GUI

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        percentTextView1 = (TextView) findViewById(R.id.percentTextView1);

        shipTextView = (TextView) findViewById(R.id.shipTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        shipTextView.setText(currencyFormat.format(0));
        taxTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

        SeekBar percentSeekBar1 =
                (SeekBar) findViewById(R.id.percentSeekBar1);
        percentSeekBar1.setOnSeekBarChangeListener(seekBarListener1);
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView
        String taxP = "Tax "+percentFormat.format(taxPercent);
        String shipP = "Ship "+percentFormat.format(shipPercent);
        percentTextView.setText(taxP);
        percentTextView1.setText(shipP);

        // calculate the tip and total
        double tax = billAmount * taxPercent;
        double ship = billAmount * shipPercent;
        if(ship<5){
            ship = 5;
        }
        double total = billAmount + tax + ship;

        // display tip and total formatted as currency
        shipTextView.setText(currencyFormat.format(ship));
        taxTextView.setText(currencyFormat.format(tax));
        totalTextView.setText(currencyFormat.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    taxPercent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private final OnSeekBarChangeListener seekBarListener1 =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    shipPercent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
}


