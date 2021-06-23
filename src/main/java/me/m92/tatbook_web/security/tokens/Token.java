package me.m92.tatbook_web.security.tokens;

import me.m92.tatbook_web.core.models.PersonalProfile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@DiscriminatorColumn(name = "token_category")
public class Token {

    @TableGenerator(name = "TokenIdGenerator",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_value",
            pkColumnValue = "token_id_gen",
            initialValue = 1000,
            allocationSize = 99)
    @Id
    @GeneratedValue(generator = "TokenIdGenerator")
    private Long id;

    private String value;

    @Column(name = "use_date")
    private LocalDateTime useDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @ManyToOne
    @JoinColumn(name = "personal_profile_id")
    private PersonalProfile owner;

    protected Token() {}

    protected Token(String value) {
        this.value = value;
    }

    public boolean isExpired() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(expireDate);
    }

    public boolean isUsed() {
        return null != useDate;
    }

    void changeExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                '}';
    }
}
