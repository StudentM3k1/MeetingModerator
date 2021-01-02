package de.iubh.meetingmoderatorapp.controller;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface Callback {

    public void onResponse(Call call, Response response);
    public void onFailure(Call call, IOException e);
}
