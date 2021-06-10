package me.m92.tatbook_web.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consultation {

    private ConsultationDueDate plannedDate;

    private List<LeaderConsultationMessage> leaderMessages;

    private List<ParticipantConsultationMessage> participantMessages;

    private Consultation() {}

    private Consultation(ConsultationDueDate plannedDate) {
        this.plannedDate = plannedDate;
        this.leaderMessages = new ArrayList<>();
        this.participantMessages = new ArrayList<>();
    }

    public static Consultation create(ConsultationDueDate plannedDate) {
        return new Consultation(plannedDate);
    }

    public void addLeaderMessage(LeaderConsultationMessage message) {
        this.leaderMessages.add(message);
    }

    public void addParticipantMessage(ParticipantConsultationMessage message) {
        this.participantMessages.add(message);
    }

    public List<ConsultationMessage> getConsultationMessages() {
        List<ConsultationMessage> consultationMessages = new ArrayList<>();
        consultationMessages.addAll(leaderMessages);
        consultationMessages.addAll(participantMessages);

        Collections.sort(consultationMessages);
        return consultationMessages;
    }
}
