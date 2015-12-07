package com.summerbrochtrup.mylegislators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.zipCodeEditText) EditText mZipCodeEditText;
    @Bind(R.id.submitButton) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @

        //mSubmitButton.setOnClickListener();
    }


}
