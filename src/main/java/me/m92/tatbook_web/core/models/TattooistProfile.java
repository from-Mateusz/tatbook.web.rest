package me.m92.tatbook_web.core.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tattooist_profile")
@DiscriminatorValue("tattooist")
public class TattooistProfile extends PersonalProfile {

    @OneToOne
    @JoinColumn(name = "tattooist_profile_acceptance_id")
    private TattooistProfileAcceptance acceptance;

    private String bio;

    private String instagramProfile;

    private Set<TattooStyle> masteredStyles;

    private Calendar calendar;

    private Portfolio portfolio;

    private List<Workplace> workplaces;

    public TattooistProfile() {
        super();
    }

    private TattooistProfile(String name, EmailAddress emailAddress, MobileNumber mobileNumber, Password password) {
        super(name, emailAddress, mobileNumber, password);
        this.instagramProfile = instagramProfile;
        this.masteredStyles = new HashSet<>();
        this.calendar = Calendar.create();
        this.portfolio = Portfolio.create();
        this.workplaces = new ArrayList<>();
    }

    public static TattooistProfile create(String name, EmailAddress emailAddress, MobileNumber mobileNumber, Password password) {
        return new TattooistProfile(name, emailAddress, mobileNumber, password);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInstagramProfile() {
        return instagramProfile;
    }

    public void setInstagramProfile(String instagramProfile) {
        this.instagramProfile = instagramProfile;
    }

    public void recordDueDateInCalendar(DueDate dueDate) {
        this.calendar.addDueDate(dueDate);
    }

    public void eraseDueDateFromCalendar(DueDate dueDate) {
        this.calendar.removeDueDate(dueDate);
    }

    public void freeDueDateFromCalendar(DueDate dueDate) {
        this.calendar.freeDueDate(dueDate);
    }

    public List<DueDate> getDueDatesFromCalendar(DueDate dueDate) {
        return this.calendar.getDueDates();
    }

    public void addFileToPortfolio(MediaFile mediaFile) {
        this.portfolio.addMediaFile(mediaFile);
    }

    public void removeFileFromPortfolio(MediaFile mediaFile) {
        this.portfolio.removeMediaFile(mediaFile);
    }

    public List<MediaFile> getPortfolioFiles() {
        return this.portfolio.getMediaFiles();
    }

    public void addWorkplace(Workplace workplace) {
        this.workplaces.add(workplace);
    }

    public void leaveWorkplace(Workplace workplace) {
        this.workplaces.remove(workplace);
    }

    void receiveAcceptance() {
        this.acceptance.accept();
    }

    void receiveRejection() {
        this.acceptance.reject();
    }

    public boolean isAccepted() {
        return acceptance.isAccepted();
    }

    public boolean isRejected() {
        return acceptance.isRejected();
    }

    @Override
    public List<String> getRoles() {
        if(this.roles.isEmpty()) {
            this.roles.add("tattooist");
        }
        return Collections.unmodifiableList(this.roles);
    }
}
