package de.iubh.meetingmoderatorapp.model;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Timer;

import de.iubh.meetingmoderatorapp.model.enumerations.*;

public class Meeting {

    private long id;
    private Agenda agenda = new Agenda();
    private MeetingSettings settings = new MeetingSettings();
    private ArrayList<Participant> participants = new ArrayList<Participant>();
    private MeetingStatus meetingStatus = MeetingStatus.Planned;
    private long passedTime;
    private String ort;

    private LocalDateTime lastChange = LocalDateTime.now();
    private Timer runningMeetingManager;
    private AgendaPoint runningAgendaPoint;

    // Nur für interne Benutzung
    public Meeting() {

    }

    public Meeting(long id, Agenda agenda, MeetingSettings settings, ArrayList<Participant> participants,
                   MeetingStatus meetingStatus, String ort) {
        this.id = id;
        this.agenda = agenda;
        this.settings = settings;
        this.participants = participants;
        this.meetingStatus = meetingStatus;
        this.ort = ort;
    }

    public MeetingStatus getMeetingStatus() {
        return meetingStatus;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public MeetingSettings getSettings() {
        return settings;
    }

    public void setSettings(MeetingSettings settings) {
        this.settings = settings;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(long passedTime) {
        this.passedTime = passedTime;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setMeetingStatus(MeetingStatus meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDateTime lastChange) {
        this.lastChange = lastChange;
    }

    public AgendaPoint getRunningAgendaPoint()
    {
        return runningAgendaPoint;

    }

}
