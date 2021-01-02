package de.iubh.meetingmoderatorapp.controller;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface CallbackHandler {

    public void onFailureCallback(Call call, IOException e);

    public void onResponseCallback(Call call, Response response);
}
