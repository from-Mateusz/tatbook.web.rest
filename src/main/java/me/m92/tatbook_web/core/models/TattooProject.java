package me.m92.tatbook_web.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TattooProject {

    private Long id;

    private String name;

    private String description;

    private TattooSize tattooSize;

    private String bodyPart;

    private Boolean colorful;

    private Boolean needConsultation;

    private List<TattooistProfile> tattooistProfiles;

    private List<TattooSessionDueDate> tattooSessionDueDates;

    private TattooProject() {}

    private TattooProject(String name, String description, TattooSize tattooSize, String bodyPart,
                         Boolean colorful, Boolean needConsultation) {
        this.name = name;
        this.description = description;
        this.tattooSize = tattooSize;
        this.bodyPart = bodyPart;
        this.colorful = colorful;
        this.needConsultation = needConsultation;
        this.tattooSessionDueDates = new ArrayList<>();
    }

    public static TattooProject create(String name, String description, TattooSize tattooSize, String bodyPart,
                                       Boolean colorful, Boolean needConsultation) {
        return new TattooProject(name, description, tattooSize, bodyPart, colorful, needConsultation);
    }

    public static TattooProject createEmpty() {
        return new TattooProject();
    }

    public List<TattooistProfile> getTattooistProfiles() {
        return Collections.unmodifiableList(tattooistProfiles);
    }

    public Long getId() {
        return id;
    }
}
