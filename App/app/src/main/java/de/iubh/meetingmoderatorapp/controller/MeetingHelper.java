package de.iubh.meetingmoderatorapp.controller;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.LocalDateTime;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;


// die Views in den Methoden Parametern sind notwendig, damit die Snackbar an der richtigen Stelle angezeigt wird.

public class MeetingHelper {

    public Meeting updateMeeting (String meetingID, View snachbarview) {
        Meeting meeting = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snachbarview,
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

    public AgendaPoint getAgendapoint(String meetingID, View snachbarview) {
        AgendaPoint ap = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModState(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snachbarview,
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

    public LocalDateTime getLastChange(String meetingID, View snachbarview)  {
        LocalDateTime lastLocalChange = null;
        HTTPClient client = new HTTPClient();
        try {
            client.getModChange(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snachbarview,
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

    public Long syncServer(String meetingID, View snachbarview) {
        long passTime = 0;
        HTTPClient client = new HTTPClient();
        try {
            client.getModSync(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        snachbarview,
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

}
