package de.iubh.meetingmoderatorapp.controller;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.LocalDateTime;

import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;


// die Views in den Methoden Parametern sind notwendig, damit die Snackbar an der richtigen Stelle angezeigt wird.

public class MeetingHelper {

    public Meeting postMeetingMod (Meeting m, View snackbarview) {
        String meeting;
        HTTPClient client = new HTTPClient();
        try {
            meeting = JSONHelper.MeetingToJSON(m);
            client.postMeeting(meeting);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Update konte nicht geladen werden " + client.getResponseCode() + " " + client.getResponseBody(),
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                m = JSONHelper.JSONToMeeting(client.getResponseBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public Meeting getMeetingMod (String meetingID, View snackbarview) {
        Meeting meeting = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getMeeting(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Update konte nicht geladen werden"
                                + "\nResCode: " + client.getResponseCode()
                                + "\nNachricht vom Server: "
                                + client.getResponseBody(),
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
        HTTPClient client = new HTTPClient();
        try {
            client.getMeeting(meetingID);
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
            client.postStartModerator(mJson, meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snackbarview,
                        "Meeting Start nicht möglich "
                        + client.getResponseCode()
                        + client.getResponseBody(),
                        Snackbar.LENGTH_LONG)
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Meeting getMeetingUser (String meetingID, View snackbarview) {
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
            client.getUserState(meetingID);
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
            client.getUserChange(meetingID);
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
            client.getUserSync(meetingID);
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
            String json = JSONHelper.MeetingToJSON(m);
            client.postNextUser(json, meetingID);
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
