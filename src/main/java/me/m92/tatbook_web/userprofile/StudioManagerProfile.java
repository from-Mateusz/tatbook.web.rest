package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.studio.TattooStudio;

import javax.persistence.*;

@Entity
@Table(name = "studio_manager")
public class StudioManagerProfile extends UserProfile {

    @Id
    @TableGenerator(
            name = "manager_profile_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "studio_manager_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "tattooist_profile_gen")
    @Column(name = "studio_manager_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private TattooStudio studio;

    @OneToOne(mappedBy = "studioManagerProfile")
    private StudioManagerProfileVerification verification;

    @Override
    public ROLES getRole() {
        return ROLES.MANAGER;
    }

    @Override
    public Long getId() {
        return id;
    }
}
