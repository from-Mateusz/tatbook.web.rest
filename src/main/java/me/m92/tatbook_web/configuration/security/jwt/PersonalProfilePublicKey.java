package me.m92.tatbook_web.configuration.security.jwt;

import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;

@Entity
@Table(name = "pub_key")
public class PersonalProfilePublicKey {

    private Long id;

    @OneToOne
    @JoinColumn(name = "personal_profile_id")
    private PersonalProfile personalProfile;

    @Lob
    @Column(name = "key_val")
    private byte[] key;

    @Convert(converter = BooleanIntegerConverter.class)
    private boolean retired;

    private PersonalProfilePublicKey() {}

    private PersonalProfilePublicKey(PersonalProfile personalProfile, byte[] key) {
        this.personalProfile = personalProfile;
        this.key = key;
    }

    public static PersonalProfilePublicKey create(PersonalProfile personalProfile, byte[] key) {
        return new PersonalProfilePublicKey(personalProfile, key);
    }

    public Long getId() {
        return id;
    }

    public PersonalProfile getPersonalProfile() {
        return personalProfile;
    }

    public byte[] getKey() {
        return key;
    }

    public boolean isRetired() {
        return retired;
    }

    public void retire(boolean retired) {
        this.retired = true;
    }
}
