package de.iubh.meetingmoderatorapp.controller;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.threeten.bp.LocalDateTime;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;


// die Views in den Methoden Parametern sind notwendig, damit die Snackbar an der richtigen Stelle angezeigt wird.

public class MeetingHelper {

    public Meeting updateMeetingMod (String meetingID, View snackbarview) {
        Meeting meeting = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Update konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                meeting = JSONHelper.JSONToMeeting(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return meeting;
    }

    public String getMeetingString (String meetingID, View snackbarview) {
        Meeting meeting = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Update konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.getResponseBody();
    }

    public AgendaPoint getAgendapointMod(String meetingID, View snackbarview) {
        AgendaPoint ap = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "neuer Agenapunkt konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                ap = JSONHelper.JSONToState(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ap;
    }

    public LocalDateTime getLastChangeMod(String meetingID, View snackbarview)  {
        LocalDateTime lastLocalChange = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModChange(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "ServerChangeReq fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                lastLocalChange = JSONHelper.JSONToLastChange(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastLocalChange;
    }

    public Long syncModServer(String meetingID, View snackbarview) {
        long passTime = 0;
        HTTPClient client = new HTTPClient();
        try {
            client.getModSync(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "ServerSync fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                passTime = JSONHelper.JSONToSync(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return passTime;
    }

    public void nextModAgendapoint(Meeting m, String meetingID, View snackbarview){
        HTTPClient client = new HTTPClient();
        try {
            String mJson = JSONHelper.MeetingToJSON(m);
            client.postNextModerator(mJson, meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "nächster Agendapunkt nicht möglich",
                        Snackbar.LENGTH_LONG)
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMeetingMod(Meeting m, String meetingID, View snackbarview){
        HTTPClient client = new HTTPClient();
        try {
            String mJson = JSONHelper.MeetingToJSON(m);
            client.postNextModerator(mJson, meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Metting Start nicht möglich",
                        Snackbar.LENGTH_LONG)
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Meeting updateMeetingUser (String meetingID, View snackbarview) {
        Meeting meeting = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Update konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                meeting = JSONHelper.JSONToMeeting(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return meeting;
    }

    public AgendaPoint getAgendapointUser(String meetingID, View snackbarview) {
        AgendaPoint ap = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "neuer Agenapunkt konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                ap = JSONHelper.JSONToState(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ap;
    }

    public LocalDateTime getLastChangeUser(String meetingID, View snackbarview)  {
        LocalDateTime lastLocalChange = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModChange(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "ServerChangeReq fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();

            } else {
                lastLocalChange = JSONHelper.JSONToLastChange(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastLocalChange;
    }

    public Long syncUserServer(String meetingID, View snackbarview) {
        long passTime = 0;
        HTTPClient client = new HTTPClient();
        try {
            client.getModSync(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "ServerSync fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                passTime = JSONHelper.JSONToSync(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return passTime;
    }

    public void nextUserAgendapoint(Meeting m, String meetingID, View snackbarview){
        HTTPClient client = new HTTPClient();
        try {
            String mJson = JSONHelper.MeetingToJSON(m);
            client.postNextModerator(mJson, meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "nächster Agendapunkt nicht möglich",
                        Snackbar.LENGTH_LONG)
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
