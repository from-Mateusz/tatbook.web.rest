package me.m92.tatbook_web.configuration.security.jwt;

import me.m92.tatbook_web.core.models.PersonalProfile;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "SECURE_PUB_KEY")
public class SecurePublicKey {

    private Long id;

    private PersonalProfile personalProfile;

    @Lob
    private byte[] key;

    private boolean retired;

    private SecurePublicKey() {}

    private SecurePublicKey(PersonalProfile personalProfile, byte[] key) {
        this.personalProfile = personalProfile;
        this.key = key;
    }

    public static SecurePublicKey create(PersonalProfile personalProfile, byte[] key) {
        return new SecurePublicKey(personalProfile, key);
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
