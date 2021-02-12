package me.m92.tatbook_web.userprofile;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Immutable
@Entity
@Table(name = "tattooist_and_clients_email_mobile")
@NamedQueries({
        @NamedQuery(name = "emails.and.mobiles.findByEmail", query = "SELECT em FROM EmailsAndMobilesView em WHERE em.email = :email"),
        @NamedQuery(name = "emails.and.mobiles.findByMobile", query = "SELECT em FROM EmailsAndMobilesView em WHERE em.mobile = :mobile"),
        @NamedQuery(name = "emails.and.mobiles.findByEmailAndMobile", query = "SELECT em FROM EmailsAndMobilesView em WHERE em.email = :email AND em.mobile = :mobile"),
})
public class EmailsAndMobilesView {

    @EmbeddedId
    private Email email;

    @Embedded
    private Mobile mobile;

    public Email getEmail() {
        return email;
    }

    public Mobile getMobile() {
        return mobile;
    }
}
