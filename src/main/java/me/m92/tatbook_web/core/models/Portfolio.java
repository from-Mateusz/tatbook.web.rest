package me.m92.tatbook_web.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfolio {

    private List<MediaFile> mediaFiles;

    private Portfolio() {
        this.mediaFiles = new ArrayList<>();
    }

    public static Portfolio create() {
        return new Portfolio();
    }

    public void addMediaFile(MediaFile mediaFile) {
        this.mediaFiles.add(mediaFile);
    }

    public void removeMediaFile(MediaFile mediaFile) {
        this.mediaFiles.remove(mediaFile);
    }

    public List<MediaFile> getMediaFiles() {
        return Collections.unmodifiableList(mediaFiles);
    }
}
