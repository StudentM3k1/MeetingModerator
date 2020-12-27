package de.iubh.meetingmoderatorapp.controller;



import android.view.View;

import androidx.appcompat.widget.ActionBarContextView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    static String URL="http://meetingmoderator.me/MeetingModeratorServer/Meeting/";
    //static String URL="http://15.237.128.178/MeetingModeratorServer/Meeting/";
    //static String URL ="http:10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    private String responseBody = "";
    private int responseCode = 0;
    private boolean responseReceived = false;

    public String getResponseBody() {
        return responseBody;
    }

    public boolean getResponseReceived()
    {
        return responseReceived;
    }

    public int getResponseCode() throws  Exception{
        if(responseCode == 0) {
            throw new Exception();
        }
        else return responseCode;
    }

    Callback resCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            responseCode = 410;
            responseReceived = true;
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            responseBody = response.body().string();
            responseCode = response.code();
            responseReceived = true;
        }
    };

    // POST Meeting to create
    public void postMeeting(String json) {
        responseReceived = false;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // GET Meeting per ID
    public void getMeeting(String url) {
        Request request = new Request.Builder()
                .url(URL + url)
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }


    // GET Change for User
    public void getUserChange(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/Change")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // GET Change for Mod
    public void getModChange(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Change")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }


    // GET State for User
    public void getUserState(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/State")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // GET State for Mod
    public void getModState(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/State")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }


    // GET Sync for User
    public void getUserSync(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/Start")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // GET Sync for Mod
    public void getModSync(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Start")
                .get()
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // POST Moderator Start
    public void postStartModerator(String json, String id) {
        responseReceived = false;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Start")
                .post(body)
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // POST Next User
    public void postNextUser(String id) {
        RequestBody body = RequestBody.create(JSON, id);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(resCallback);
    }

    // POST Moderator Next
    public void postNextModerator(String json, String id) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(resCallback);
    }
}