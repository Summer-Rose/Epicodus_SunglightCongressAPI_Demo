package com.summerbrochtrup.mylegislators;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Legislator mLegislator;
    private ArrayList<Legislator> mLegislators = new ArrayList<>();

    @Bind(R.id.zipCodeEditText)
    EditText mZipCodeEditText;
    @Bind(R.id.submitButton)
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLegislators();
            }
        });
    }

    public void getLegislators() {
        String zipCode = mZipCodeEditText.getText().toString();
        LegislatorFinder legislatorFinder = new LegislatorFinder(this);
        legislatorFinder.getLegislators(zipCode, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //HANDLE FAILURE HERE
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> legislatorNames = new ArrayList<String>();
                        for (Legislator legislator : mLegislators) {
                            legislatorNames.add(legislator.getName());
                        }
                        TextView logoTextView = (TextView) findViewById(R.id.logoTextView);
                        logoTextView.setText(legislatorNames.toString());
                    }
                });

                try {
                    String jsonData = response.body().string();
                    Log.v("JSON DATA", jsonData);
                    if (response.isSuccessful()) {
                        JSONObject legislator = new JSONObject(jsonData);
                        JSONArray representatives = legislator.getJSONArray("results");
                        for (int i = 0; i < representatives.length(); i++) {
                            JSONObject representativeJSON = representatives.getJSONObject(i);
                            String name = representativeJSON.getString("first_name") + " " + representativeJSON.getString("last_name");;
                            Log.d("FIRST NAME", name);
                            Legislator newLegislator = new Legislator();
                            newLegislator.setName(name);
                            mLegislators.add(newLegislator);
                        }

                    } else {

                    }
                } catch (IOException e) {
                    Log.e("ERROR", "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e("ERROR", "Exception caught: ", e);
                }
            }
        });
    }
}
