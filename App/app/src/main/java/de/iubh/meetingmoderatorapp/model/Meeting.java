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




    public void MeetingTick() {
        passedTime += 1;
        runningAgendaPoint.setRunningTime(runningAgendaPoint.getRunningTime() + 1);
        try {
            if (runningAgendaPoint.getRunningTime() >= runningAgendaPoint.getAvailableTime())
                nextPoint();
        } catch (Exception e) {
            // Wird im nächsten Durchlauf erneut versucht.
        }
    }

    public void nextPoint() throws Exception {
        runningAgendaPoint = new AgendaPoint();
        setLastChange(LocalDateTime.now());

        // Nach laufendem AgendaPunkt suchen
        for (AgendaPoint agendaPoint : agenda.getAgendaPoints()) {
            if (agendaPoint.getStatus() == AgendaPointStatus.Running) {
                runningAgendaPoint = agendaPoint;
                break;
            }
        }

        // Wenn kein laufender Agendapunkt gefunden, oder alle Sprecher gesprochen haben
        // -> Nächsten Punkt
        if (runningAgendaPoint.getId() == 0 || runningAgendaPoint.getDoneSpeaker().size() == participants.size()) {
            setLastChange(LocalDateTime.now());

            runningAgendaPoint.setStatus(AgendaPointStatus.Done);

            // Nächsten Punkt suchen, der größer ist
            AgendaPoint nextPoint = new AgendaPoint();
            nextPoint.setSort(Long.MAX_VALUE);

            for (AgendaPoint agendaPoint_ : agenda.getAgendaPoints()) {
                if (agendaPoint_.getSort() <= nextPoint.getSort()
                        && agendaPoint_.getStatus() == AgendaPointStatus.Planned) {
                    nextPoint = agendaPoint_;
                }
            }
            if (nextPoint.getId() != 0) {
                nextPoint.setStatus(AgendaPointStatus.Running);
                AgendaPoint.setNextSpeaker(participants, nextPoint);
                nextPoint.setAvailableTime(nextPoint.getAvailableTime() / participants.size());
                runningAgendaPoint = nextPoint;

            } else
                closeMeeting();
        } else {
            runningAgendaPoint.setRunningTime(0);
            AgendaPoint.setNextSpeaker(participants, runningAgendaPoint);
        }
    }

    public void closeMeeting() throws Exception {
        runningMeetingManager.cancel();
        runningAgendaPoint.setId(0);
        setMeetingStatus(MeetingStatus.Done);
        setLastChange(LocalDateTime.now());
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

    public AgendaPoint getRunningAgendaPoint() {
        return runningAgendaPoint;

    }

}
