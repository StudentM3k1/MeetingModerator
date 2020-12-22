package de.iubh.meetingmoderatorapp.controller;


import org.threeten.bp.*;
import java.util.ArrayList;
import org.json.*;
import de.iubh.meetingmoderatorapp.model.*;
import de.iubh.meetingmoderatorapp.model.enumerations.*;

public class JSONHelper {


    public static String MeetingToJSON(Meeting meeting) throws Exception {

        JSONObject jsonobj = new JSONObject();
        jsonobj.put("id", meeting.getId());
        jsonobj.put("passedTime", meeting.getPassedTime());
        jsonobj.put("status", MeetingStatus.getInt(meeting.getMeetingStatus()));
        jsonobj.put("ort", meeting.getOrt());

        JSONObject meetingSettings = new JSONObject();
        meetingSettings.put("meetingTitle", meeting.getSettings().getMeetingTitle());
        meetingSettings.put("startTime", meeting.getSettings().getStartTime());
        meetingSettings.put("duration", meeting.getSettings().getDuration());
        meetingSettings.put("moderatorId", meeting.getSettings().getModeratorId());
        meetingSettings.put("participantId", meeting.getSettings().getParticipantId());
        meetingSettings.put("startTime", meeting.getSettings().getStartTime().toEpochSecond(ZoneOffset.UTC));
        jsonobj.put("meetingSettings", meetingSettings);

        JSONObject agenda = new JSONObject();
        JSONArray agenda_points = new JSONArray();
        for (AgendaPoint agendaPoint : meeting.getAgenda().getAgendaPoints()) {
            JSONObject agendaPoint_json = new JSONObject();
            agendaPoint_json.put("id", agendaPoint.getId());
            agendaPoint_json.put("title", agendaPoint.getTitle());
            agendaPoint_json.put("note", agendaPoint.getNote());
            agendaPoint_json.put("availableTime", agendaPoint.getAvailableTime());
            agendaPoint_json.put("status", AgendaPointStatus.getInt(agendaPoint.getStatus()));

            JSONObject participant = new JSONObject();
            JSONObject user = new JSONObject();
            user.put("id", agendaPoint.getActualSpeaker().getUser().getId());
            user.put("firstname", agendaPoint.getActualSpeaker().getUser().getFirstname());
            user.put("lastname", agendaPoint.getActualSpeaker().getUser().getLastname());
            user.put("mail", agendaPoint.getActualSpeaker().getUser().getMail());
            participant.put("user", user);
            participant.put("id", agendaPoint.getActualSpeaker().getId());
            participant.put("type", ParticipantType.getInt(agendaPoint.getActualSpeaker().getType()));
            agendaPoint_json.put("actualSpeaker", participant);
            agenda_points.put(agendaPoint_json);
        }
        agenda.put("agendaPoints", agenda_points);
        jsonobj.put("agenda", agenda);

        JSONArray participants = new JSONArray();
        for (Participant participant : meeting.getParticipants()) {
            JSONObject participant_json = new JSONObject();
            JSONObject user = new JSONObject();
            user.put("id", participant.getUser().getId());
            user.put("firstname", participant.getUser().getFirstname());
            user.put("lastname", participant.getUser().getLastname());
            user.put("mail", participant.getUser().getMail());
            participant_json.put("user", user);
            participant_json.put("id", participant.getId());
            participant_json.put("type", ParticipantType.getInt(participant.getType()));
            participants.put(participant_json);
        }
        jsonobj.put("participants", participants);

        return jsonobj.toString();
    }

    public static Meeting JSONToMeeting(String json) throws Exception{
        Meeting meeting = new Meeting();
        MeetingSettings settings = new MeetingSettings();

        Agenda agenda = new Agenda();
        ArrayList<Participant> p = new ArrayList<Participant>();

        try {
            JSONObject json_obj = new JSONObject(json);
            meeting.setPassedTime(json_obj.getLong("passedTime"));
            meeting.setId(json_obj.getLong("id"));
            meeting.setMeetingStatus(MeetingStatus.getMeetingStatus(json_obj.getInt("status")));
            meeting.setOrt(json_obj.getString("ort"));

            JSONObject json_settings = json_obj.getJSONObject("meetingSettings");
            settings.setDuration(json_settings.getLong("duration"));
            settings.setParticipantId(json_settings.getString("participantId"));
            settings.setModeratorId(json_settings.getString("moderatorId"));
            settings.setMeetingTitle(json_settings.getString("meetingTitle"));
            settings.setStartTime(LocalDateTime.ofEpochSecond(json_settings.getLong("startTime"), 0, ZoneOffset.UTC));

            JSONObject json_agenda = json_obj.getJSONObject("agenda");
            JSONArray json_agendaPoints = json_agenda.getJSONArray("agendaPoints");
            for (int i = 0; i < json_agendaPoints.length(); i++) {
                AgendaPoint agendaPoint = new AgendaPoint();
                JSONObject json_agendaPoint = json_agendaPoints.getJSONObject(i);
                agendaPoint.setId(json_agendaPoint.getLong("id"));
                agendaPoint.setTitle(json_agendaPoint.getString("title"));
                agendaPoint.setNote(json_agendaPoint.getString("note"));
                agendaPoint.setAvailableTime(json_agendaPoint.getLong("availableTime"));
                agendaPoint.setStatus(AgendaPointStatus.getAgendaPointStatus(json_agendaPoint.getInt("status")));
                User user = new User();
                Participant participant = new Participant();
                JSONObject json_participant = json_agendaPoint.getJSONObject("actualSpeaker");
                participant.setId(json_participant.getLong("id"));
                participant.setType(ParticipantType.getParticipantType(json_participant.getInt("type")));
                JSONObject json_user = json_participant.getJSONObject("user");
                user.setFirstname(json_user.getString("firstname"));
                user.setId(json_user.getLong("id"));
                user.setLastname(json_user.getString("lastname"));
                user.setMail(json_user.getString("mail"));
                participant.setUser(user);
                agendaPoint.setActualSpeaker(participant);

                agenda.getAgendaPoints().add(agendaPoint);
            }

            JSONArray json_participants = json_obj.getJSONArray("participants");

            for (int i = 0; i < json_participants.length(); i++) {
                User user = new User();
                Participant participant = new Participant();

                JSONObject json_participant = json_participants.getJSONObject(i);
                participant.setId(json_participant.getLong("id"));
                participant.setType(ParticipantType.getParticipantType(json_participant.getInt("type")));

                JSONObject json_user = json_participant.getJSONObject("user");
                user.setFirstname(json_user.getString("firstname"));
                user.setId(json_user.getLong("id"));
                user.setLastname(json_user.getString("lastname"));
                user.setMail(json_user.getString("mail"));

                participant.setUser(user);

                meeting.getParticipants().add(participant);
            }

        } catch (JSONException jsonex) {

            throw new Exception("JSON kann nicht gelesen werden",jsonex.getCause());
        }

        meeting.setAgenda(agenda);
        meeting.setSettings(settings);

        return meeting;
    }

    public static LocalDateTime JSONToLastChange(String json)throws Exception {
        JSONObject jsonobj = new JSONObject(json);
        return LocalDateTime.ofEpochSecond(jsonobj.getLong("lastChange"), 0, ZoneOffset.UTC);
    }

    public static String LastChangeToJSON(LocalDateTime localDateTime) throws Exception {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("lastChange", localDateTime.toEpochSecond(ZoneOffset.UTC));
        return jsonobj.toString();
    }

    public static String StateToJSON(AgendaPoint agendaPoint) throws Exception{
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("id", agendaPoint.getId());
        jsonobj.put("title", agendaPoint.getTitle());
        jsonobj.put("note", agendaPoint.getNote());
        jsonobj.put("availableTime", agendaPoint.getAvailableTime());
        jsonobj.put("status", AgendaPointStatus.getInt(agendaPoint.getStatus()));
        return jsonobj.toString();

    }

    public static AgendaPoint JSONToState(String json) throws Exception{
        AgendaPoint agendaPoint = new AgendaPoint();
        JSONObject json_agendaPoint = new JSONObject(json);
        agendaPoint.setId(json_agendaPoint.getLong("id"));
        agendaPoint.setTitle(json_agendaPoint.getString("title"));
        agendaPoint.setNote(json_agendaPoint.getString("note"));
        agendaPoint.setAvailableTime(json_agendaPoint.getLong("availableTime"));
        agendaPoint.setStatus(AgendaPointStatus.getAgendaPointStatus(json_agendaPoint.getInt("status")));
        return agendaPoint;
    }

    public static long JSONToSync(String json) throws Exception{
        JSONObject jsonobj = new JSONObject(json);
        return jsonobj.getLong("passedTime");
    }

    public static String SyncToJSON(long ticks)throws Exception {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("passedTime", ticks);
        return jsonobj.toString();
    }


}