package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.tattoo.Project;
import me.m92.tatbook_web.studio.TattooStudio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "tattooist")
@NamedQueries({
        @NamedQuery(name = "tattooist.profile.findAll", query = "SELECT tattooist FROM TattooistProfile tattooist"),
        @NamedQuery(name = "tattooist.profile.findByEmail", query = "SELECT tattooist FROM TattooistProfile tattooist WHERE tattooist.email = :email")
})
public class TattooistProfile extends UserProfile implements Verifiable<TattooistProfileVerification> {

    @Id
    @TableGenerator(
            name = "tattooist_profile_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "tattooist_profile_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "tattooist_profile_gen")
    @Column(name = "tattooist_id")
    private Long id;

    @OneToMany(mappedBy = "tattooistProfile")
    private Collection<Project> projects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private TattooStudio studio;

    @Column(name = "owner")
    private boolean studioOwner;

    @OneToOne(mappedBy = "tattooistProfile", cascade = CascadeType.ALL)
    private TattooistProfileBio bio;

    @OneToMany(mappedBy = "tattooistProfile", cascade = CascadeType.ALL)
    private Collection<InstagramConnection> instagramConnections = new ArrayList<>();

    @OneToOne(mappedBy = "tattooistProfile", cascade = CascadeType.ALL)
    private TattooistProfileVerification verification;

    @Embedded
    private ProfilePhoto photo;

    public TattooistProfile() {}

    protected TattooistProfile(FullName name, Email email, Mobile mobile, String password, Long id, Collection<Project> projects, TattooStudio studio, boolean studioOwner, TattooistProfileBio bio) {
        super(name, email, mobile, password);
        this.id = id;
        this.projects = projects;
        this.studio = studio;
        this.studioOwner = studioOwner;
        this.bio = bio;
    }

    @Override
    public ROLES getRole() {
        return ROLES.TATTOOIST;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void changePseudonym(String pseudonym) {
        if(!hasBio()) {
            this.bio = new TattooistProfileBio.Builder(this)
                                              .withPseudonym(pseudonym)
                                              .build();
        }
        else {
            this.bio.changePseudonym(pseudonym);
        }
    }

    public void changeAboutDescription(String about) {
        if(!hasBio()) {
            this.bio = new TattooistProfileBio.Builder(this)
                                              .withAbout(about)
                                              .build();
        }
        else {
            this.bio.changeAbout(about);
        }
    }

    public void changeTattooColorPreference(ColorPreference preference) {
        if(!hasBio()) {
            this.bio = new TattooistProfileBio.Builder(this)
                                              .withTattooColorPreference(preference)
                                              .build();
        }
        else {
            this.bio.changeTattooColorPreference(preference);
        }
    }

    public void changeBio(TattooistProfileBio bio) {
        this.bio = bio;
        bio.setTattooistProfile(this);
    }

    private boolean hasBio() {
        return null != bio;
    }

    public TattooStudio getStudio() {
        return studio;
    }

    public boolean isStudioOwner() {
        return studioOwner;
    }

    public void ownStudio(TattooStudio studio) {
        this.changeStudio(studio);
        studioOwner = true;
    }

    public void changeStudio(TattooStudio studio) {
        if(isStudioOwner()) {
            studioOwner = false;
        }
        this.studio = studio;
    }

    public void connectInstagram(InstagramConnection connection) {
        this.instagramConnections.add(connection);
    }

    @Override
    public void beginVerification() {
        if(null != this.verification) {
            throw new IllegalStateException("Already under verification or verified");
        }
        this.verification = TattooistProfileVerification.of(this);
    }

    @Override
    public void receiveVerification(TattooistProfileVerification verification) {
        this.verification = verification;
    }

    @Override
    public boolean isVerified() {
        return verification.isVerified() && !verification.isRejected();
    }

    @Override
    public boolean isRejected() {
        return verification.isRejected();
    }
}


