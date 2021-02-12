package me.m92.tatbook_web.userprofile;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfilePhoto {

    @Column(name = "photo_url")
    private String url;

    public ProfilePhoto() {}

    protected ProfilePhoto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
