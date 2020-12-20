package de.iubh.meetingmoderatorapp.controller;



import android.view.View;

import androidx.appcompat.widget.ActionBarContextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.activities.Act_IDEingabe;
import de.iubh.meetingmoderatorapp.model.Meeting;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPClient {
    String m;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();



    // POST Meeting to create
    public String postMeeting(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                assert response.body() != null;
                m = response.body().string();

            }
        });
        return m;
    }

    // GET Meeting per ID
    public String getMeeting(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               if(response.code() != 200) {
                   m = "Err: HTTP Code: " + response.code();
               } else if (response.code() == 200) {
                    m = response.body().string();
                } else {
                   m="";
               }
            }
        });
        return m;
    }
}