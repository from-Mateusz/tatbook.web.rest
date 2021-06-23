package me.m92.tatbook_web.security.jwt;

import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;

@Entity
@Table(name = "pub_key")
public class PersonalProfilePublicKey {

    @TableGenerator(name = "PublicKeyIdGenerator",
    table = "id_generator",
    pkColumnName = "generator_name",
    valueColumnName = "generator_value",
    pkColumnValue = "pub_key_id_gen",
    initialValue = 2000,
    allocationSize = 99)
    @Id
    @GeneratedValue(generator = "PublicKeyIdGenerator")
    private Long id;

    @ManyToOne
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
