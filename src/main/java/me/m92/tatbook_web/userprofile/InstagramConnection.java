package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.converters.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "instagram_connection")
public class InstagramConnection {

    @Id
    @TableGenerator(
            name = "instagram_connection_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "instagram_connection_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "instagram_connection_gen")
    @Column(name = "instagram_connection_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tattooist_profile_id")
    private TattooistProfile tattooistProfile;

    private String token;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    public InstagramConnection() {}

    protected InstagramConnection(TattooistProfile tattooistProfile, String token, LocalDateTime expireDate) {
        this.tattooistProfile = tattooistProfile;
        this.token = token;
        this.expireDate = expireDate;
    }

    public static InstagramConnection of(TattooistProfile tattooistProfile, String token, LocalDateTime expireDate) {
        InstagramConnection connection = new InstagramConnection(tattooistProfile, token, expireDate);
        tattooistProfile.connectInstagram(connection);
        return connection;
    }

    public Long getId() {
        return id;
    }

    public TattooistProfile getTattooistProfile() {
        return tattooistProfile;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }
}
