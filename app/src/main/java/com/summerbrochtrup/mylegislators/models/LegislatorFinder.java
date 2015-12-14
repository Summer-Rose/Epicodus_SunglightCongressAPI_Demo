package com.summerbrochtrup.mylegislators.models;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.summerbrochtrup.mylegislators.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LegislatorFinder {
    private Context context;

    public LegislatorFinder(Context context) {
        this.context = context;
    }

    public void getLegislators(String zip, Callback callback) {
        String url = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zip + "&apikey=" + context.getString(R.string.api_key);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Legislator> processResults(Response response) {
        ArrayList<Legislator> mLegislators = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject legislatorJSON = new JSONObject(jsonData);
                JSONArray representativesJSON = legislatorJSON.getJSONArray("results");

                for (int i = 0; i < representativesJSON.length(); i++) {
                    JSONObject representativeJSON = representativesJSON.getJSONObject(i);
                    Legislator newLegislator = new Legislator(representativeJSON);
                    mLegislators.add(newLegislator);
                }
            } else {
                Log.e("ERROR", response.message());
            }
        } catch (IOException e) {
            Log.e("ERROR", "Exception caught: ", e);
        } catch (JSONException e) {
            Log.e("ERROR", "Exception caught: ", e);
        }
        return mLegislators;
    }
}
