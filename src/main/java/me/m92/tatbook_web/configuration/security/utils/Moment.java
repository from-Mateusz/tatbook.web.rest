package me.m92.tatbook_web.configuration.security.utils;

import java.util.Date;

public class Moment {

    private Date date;

    private Moment() {
        this.date = new Date();
    }

    private Moment(Date date) {
        this.date = date;
    }

    public static Moment getInstance() {
        return new Moment();
    }

    public Moment delayByMinutes(long minutes) {
        return new Moment(new Date(date.getTime() + (minutes * 60 * 1000)));
    }

    public Date getDate() {
        return this.date;
    }

    public long getTime() { return this.date.getTime(); }
}
