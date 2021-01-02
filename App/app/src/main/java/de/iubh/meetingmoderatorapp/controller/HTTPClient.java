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
    static final String URL="http://meetingmoderator.me/MeetingModeratorServer/Meeting/";
    //static final String URL ="http:10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
                                                    .writeTimeout(2, TimeUnit.SECONDS)
                                                    .readTimeout(2, TimeUnit.SECONDS)
                                                    .build();
    Object sender = null;


    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            if (sender != null) {
                ((CallbackHandler) sender).onFailureCallback(call, e);
                sender = null;
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (sender != null)
            {
                ((CallbackHandler)sender).onResponseCallback(call, response);
                sender = null;
            }
        }
    };

    // POST Meeting to create
    public void postMeeting(String json, Object sender) {
        this.sender = sender;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .tag("CreateMeeting")
                .url(URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET Meeting per ID
    public void getMeeting(String url, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetMeetingMod")
                .url(URL +  "Moderator/" + url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET Meeting per ID
    public void getMeetingUser(String url, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetMeetingUser")
                .url(URL +  "User/" + url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET Change for User
    public void getUserChange(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetChangeUser")
                .url(URL + "User/" + id + "/Change")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET Change for Mod
    public void getModChange(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetChangeMod")
                .url(URL + "Moderator/" + id + "/Change")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }


    // GET State for User
    public void getUserState(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetStateUser")
                .url(URL + "User/" + id + "/State")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET State for Mod
    public void getModState(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetStateMod")
                .url(URL + "Moderator/" + id + "/State")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }


    // GET Sync for User
    public void getUserSync(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetSyncUser")
                .url(URL + "User/" + id + "/Sync")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // GET Sync for Mod
    public void getModSync(String id, Object sender) {
        this.sender = sender;
        Request request = new Request.Builder()
                .tag("GetSyncMod")
                .url(URL + "Moderator/" + id + "/Sync")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // POST Moderator Start
    public void postStartModerator(String id, Object sender) {
        this.sender = sender;
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .tag("StartMeeting")
                .url(URL + "Moderator/" + id + "/Start")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // POST Next User
    public void postNextUser(String id, Object sender) {
        this.sender = sender;
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .tag("NextUser")
                .url(URL + "User/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // POST Moderator Next
    public void postNextModerator(String id, Object sender) {
        this.sender = sender;
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .tag("NextMod")
                .url(URL + "Moderator/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}