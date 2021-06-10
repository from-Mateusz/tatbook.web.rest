package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class LeaderConsultationMessage extends ConsultationMessage<TattooistProfile> {

    private LeaderConsultationMessage() {
        super();
    }

    private LeaderConsultationMessage(String content, LocalDateTime sentDate, TattooistProfile author) {
        super(content, sentDate, author);
    }

    public static LeaderConsultationMessage create(String content, LocalDateTime sentDate, TattooistProfile author) {
        return new LeaderConsultationMessage(content, sentDate, author);
    }
}
