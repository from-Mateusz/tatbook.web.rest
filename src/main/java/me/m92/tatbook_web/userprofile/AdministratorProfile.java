package me.m92.tatbook_web.userprofile;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
@NamedQueries({
        @NamedQuery(name = "administrator.profile.findByEmail", query = "SELECT ap FROM AdministratorProfile ap WHERE ap.email = :email"),
        @NamedQuery(name = "administrator.profile.findById", query = "SELECT ap FROM AdministratorProfile ap WHERE ap.id = :id")
})
public class AdministratorProfile extends UserProfile {

    @Id
    @TableGenerator(
            name = "administrator_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "administrator_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "administrator_gen")
    @Column(name = "administrator_id")
    private Long id;

    private String pin;

    @Override
    public Long getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public ROLES getRole() {
        return ROLES.ADMINISTRATOR;
    }
}
