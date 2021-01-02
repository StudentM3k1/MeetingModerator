package de.iubh.meetingmoderatorapp.controller;

import de.iubh.meetingmoderatorapp.activities.Act_CreateMeeting;
import de.iubh.meetingmoderatorapp.model.Meeting;



public class MeetingHelper {

    public static void createMeeting(Meeting m, Object sender) {
        try {
            HTTPClient client = new HTTPClient();
            String meeting = JSONHelper.MeetingToJSON(m);
            client.postMeeting(meeting, sender);
        } catch (Exception e) {
            ((Act_CreateMeeting) sender).onFailureCreateMeeting("Meeting Daten können nicht umgewandelt werden.");
        }
    }

    public static void getMeetingMod(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getMeeting(meetingID, sender);
    }

    public static void getMeetingUser(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getMeetingUser(meetingID, sender);
    }

    public static void syncMod(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getModSync(meetingID, sender);
    }

    public static void getLastChangeMod(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getModChange(meetingID, sender);
    }

    public static void nextAgendapointMod(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.postNextModerator(meetingID, sender);
    }

    public static void getAgendapointMod(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getModState(meetingID, sender);
    }


    public static void startMeeting(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.postStartModerator(meetingID, sender);
    }

    public static void nextAgendapointUser(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getUserState(meetingID, sender);
    }

    public static void syncUser(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getUserSync(meetingID, sender);
    }


    public static void getLastChangeUser(String meetingID, Object sender) {
        HTTPClient client = new HTTPClient();
        client.getUserChange(meetingID, sender);
    }
}
