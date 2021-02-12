package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.tattoo.Project;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "client.profile.findAll", query = "SELECT client FROM ClientProfile client"),
        @NamedQuery(name = "client.profile.findByEmail", query = "SELECT client FROM ClientProfile client WHERE client.email = :email")
})
public class ClientProfile extends UserProfile {

    @Id
    @TableGenerator(
            name = "client_profile_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "client_profile_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "client_profile_gen")
    @Column(name = "client_id")
    private Long id;

    @OneToMany(mappedBy = "clientProfile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<Project> projects = new ArrayList<>();

    @ElementCollection(targetClass = StylePreference.class)
    @CollectionTable(name = "client_style", joinColumns = @JoinColumn(name = "client_id"))
    private Collection styles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "health_condition_declaration_id")
    private HealthCondition healthCondition;

    @Embedded
    private ColorPreferences colorPreferences;

    public ClientProfile() {super();}

    protected ClientProfile(FullName name, Email email, Mobile mobile, String password) {
        super(name, email, mobile, password);
    }

    @Override
    public ROLES getRole() {
        return ROLES.CLIENT;
    }

    @Override
    public Long getId() {
        return id;
    }

    public ColorPreferences getColorPreferences() {
        return colorPreferences;
    }

    public HealthCondition getHealthCondition() { return healthCondition; }

    public void changeColorPreferences(ColorPreferences preferences) {
        this.colorPreferences = colorPreferences;
    }

    public void changeStylePreferences(Collection<StylePreference> styles) {
        this.styles = styles;
    }

    public void describeHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }
}
