package de.iubh.meetingmoderatorapp.controller;



import android.view.View;

import androidx.appcompat.widget.ActionBarContextView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

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
    //static String URL="http://meetingmoderator.me/MeetingModeratorServer/Meeting/";
    static String URL ="http:10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();



    // POST Meeting to create
    public String postMeeting(String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                m = "Err: Nope in postMeeting HTTPMethod";
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

    // GET Meeting per ID
    public String getMeeting(String url) {
        Request request = new Request.Builder()
                .url(URL + url)
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


    // GET State for User
    public String getUserChange(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/Change")
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

    // GET State for Mod
    public String getModChange(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Change")
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


    // GET State for User
    public String getUserState(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/State")
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

    // GET State for Mod
    public String getModState(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/State")
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


    // GET Sync for User
    public String getUserSync(String id) {
        Request request = new Request.Builder()
                .url(URL + "/User/" + id + "/Start")
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

    // GET Sync for Mod
    public String getModSync(String id) {
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Start")
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



    // POST Moderator Start
    public String postStartModerator(String json, String id) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Start")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                m = "Err: Nope in postMeeting HTTPMethod";
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


    // POST Next User
    public String postNextUser(String id) {
        RequestBody body = RequestBody.create(JSON, id);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                m = "Err: Nope in postMeeting HTTPMethod";
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

    // POST Moderator Next
    public String postNextModerator(String json, String id) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL + "/Moderator/" + id + "/Next")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                m = "Err: Nope in postMeeting HTTPMethod";
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