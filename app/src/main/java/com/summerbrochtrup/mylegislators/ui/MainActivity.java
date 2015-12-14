package com.summerbrochtrup.mylegislators.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.summerbrochtrup.mylegislators.models.LegislatorFinder;
import com.summerbrochtrup.mylegislators.R;
import com.summerbrochtrup.mylegislators.models.Legislator;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Legislator> mLegislators = new ArrayList<>();

    @Bind(R.id.zipCodeEditText) EditText mZipCodeEditText;
    @Bind(R.id.submitButton) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLegislatorsList();
            }
        });
    }

    public void getLegislatorsList() {
        String zipCode = mZipCodeEditText.getText().toString();
        final LegislatorFinder legislatorFinder = new LegislatorFinder(this);
        legislatorFinder.getLegislators(zipCode, new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                mLegislators = legislatorFinder.processResults(response);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mLegislators.size() == 0) {
                            Toast.makeText(MainActivity.this, "Invalid Zip Code", Toast.LENGTH_LONG).show();
                        }
                        ArrayList<String> legislatorNames = new ArrayList<>();
                        for (Legislator legislator : mLegislators) {
                            legislatorNames.add(legislator.getName());
                        }
                        TextView logoTextView = (TextView) findViewById(R.id.logoTextView);
                        logoTextView.setText(legislatorNames.toString());
                    }
                });
            }
        });
    }
}
