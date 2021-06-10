package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class ParticipantConsultationMessage extends ConsultationMessage<ClientProfile> {

    private ParticipantConsultationMessage() {
        super();
    }

    private ParticipantConsultationMessage(String content, LocalDateTime sentDate, ClientProfile author) {
        super(content, sentDate, author);
    }

    public static ParticipantConsultationMessage create(String content, LocalDateTime sentDate, ClientProfile author) {
        return new ParticipantConsultationMessage(content, sentDate, author);
    }
}
