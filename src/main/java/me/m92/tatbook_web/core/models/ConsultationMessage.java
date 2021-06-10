package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public abstract class ConsultationMessage<T extends PersonalProfile> implements Comparable<ConsultationMessage> {

    private String content;

    private LocalDateTime sentDate;

    private T author;

    protected ConsultationMessage() {}

    protected ConsultationMessage(String content, LocalDateTime sentDate, T author) {
        this.content = content;
        this.sentDate = sentDate;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public T getAuthor() {
        return author;
    }

    public void setAuthor(T author) {
        this.author = author;
    }

    @Override
    public int compareTo(ConsultationMessage otherConsultationMessage) {
        if(sentDate.isBefore(otherConsultationMessage.sentDate)) {
            return -1;
        }

        if(sentDate.isAfter(otherConsultationMessage.sentDate)) {
            return 1;
        }

        return 0;
    }
}
