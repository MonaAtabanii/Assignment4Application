package com.example.Assignment4App;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener {

    // define variables for the widgets
    private EditText fahrenheitDegree;
    private TextView celsiusDegree;

    // define the SharedPreferences object
    private SharedPreferences savedValues;

    // define instance variables that should be saved
    private String fahrenheitString = "";
    private float celsius = -17.778f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        fahrenheitDegree = (EditText) findViewById(R.id.fahrenheitDegree);
        celsiusDegree = (TextView) findViewById(R.id.celsiusDegree);


        // set the listeners
        fahrenheitDegree.setOnEditorActionListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.putFloat("celsius", celsius);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        fahrenheitString = savedValues.getString("fahrenheitString", "");
        celsius = savedValues.getFloat("celsius", -17.778f);

        // set the fahrenheit degree on its widget
        fahrenheitDegree.setText(fahrenheitString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        // get the fahrenheit degree
        fahrenheitString = fahrenheitDegree.getText().toString();
        float fahrenheit;
        if (fahrenheitString.equals("")) {
            fahrenheit = 0;
        }
        else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }

        // calculate celsius
        float celsiusamount = (fahrenheit - 32) * 5 / 9;


        // display the result
        celsiusDegree.setText(String.valueOf(celsiusamount));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }


}