package de.iubh.meetingmoderatorapp.model;


import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.model.*;
import de.iubh.meetingmoderatorapp.model.enumerations.*;

public class Meeting {

    private long id = 0;
    private Agenda agenda = new Agenda();
    private MeetingSettings settings = new MeetingSettings();
    private ArrayList<Participant> participants = new ArrayList<>();
    private MeetingStatus meetingStatus = MeetingStatus.Planned;
    private long passedTime = 0;
    private String ort;
    private AgendaPoint runningPoint = new AgendaPoint();
    private LocalDateTime lastChange = LocalDateTime.now();

    // Nur f?r interne Benutzung
    public Meeting() {

    }

    public Meeting(long id, Agenda agenda, MeetingSettings settings, ArrayList<Participant> participants,
                   MeetingStatus meetingStatus, long passedTime, String ort) {
        this.id = id;
        this.agenda = agenda;
        this.settings = settings;
        this.participants = participants;
        this.meetingStatus = meetingStatus;
        this.passedTime = passedTime;
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

    public void checkChanges(Meeting newMeeting)
    {
        checkAgenda(newMeeting.getAgenda());
        checkParticipants(newMeeting.getParticipants());
    }


    public void checkAgenda(Agenda newAgenda) {






    }

    public void checkParticipants(ArrayList<Participant> newParticipants) {






    }

    public void startMeeting() {

        setMeetingStatus(MeetingStatus.Running);
        setLastChange(LocalDateTime.now());
        setRunningPoint(nextPoint());

    }

    public AgendaPoint nextPoint() {
        setLastChange(LocalDateTime.now());
        AgendaPoint nextPoint = new AgendaPoint();
        for (AgendaPoint agendaPoint : agenda.getAgendaPoints()) {
            if (agendaPoint.getStatus() == AgendaPointStatus.Running) {
                agendaPoint.setStatus(AgendaPointStatus.Done);
                // N?chsten Punkt suchen, der gr??er ist
                nextPoint = agendaPoint;
                for (AgendaPoint agendaPoint_ : agenda.getAgendaPoints()) {
                    if (agendaPoint_.getSort() <= nextPoint.getSort())
                        nextPoint = agendaPoint_;
                }
                nextPoint.setStatus(AgendaPointStatus.Running);
                return nextPoint;
            }
        }

        // Wenn kein laufender Punkt gefunden wurde, niedrigsten suchen
        if (agenda.getAgendaPoints().size() > 0) nextPoint = agenda.getAgendaPoints().get(0);
        for (AgendaPoint agendaPoint : agenda.getAgendaPoints()) {
            if (agendaPoint.getSort() <= nextPoint.getSort())
                nextPoint = agendaPoint;
        }
        if (nextPoint == agenda.getAgendaPoints().get(0)) return null;
        return nextPoint;
    }

    public void closeMeeting() {

        setMeetingStatus(MeetingStatus.Done);
        setLastChange(LocalDateTime.now());
        setRunningPoint(new AgendaPoint());
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

    public AgendaPoint getRunningPoint() {
        return runningPoint;
    }

    public void setRunningPoint(AgendaPoint runningPoint) {
        this.runningPoint = runningPoint;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDateTime lastChange) {
        this.lastChange = lastChange;
    }

}
