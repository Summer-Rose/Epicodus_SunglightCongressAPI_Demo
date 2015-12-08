package com.summerbrochtrup.mylegislators;

import android.content.Context;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Guest on 12/7/15.
 */
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
}
