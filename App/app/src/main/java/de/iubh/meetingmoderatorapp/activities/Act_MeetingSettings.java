package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import okhttp3.Call;
import okhttp3.Response;

public class Act_MeetingSettings extends AppCompatActivity implements CallbackHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_meetingsettings);
        AndroidThreeTen.init(this);
    }
    public void onFailureCallback(Call call, IOException e)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }

    }

    public void onResponseCallback(Call call, Response response)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }
    }
}
