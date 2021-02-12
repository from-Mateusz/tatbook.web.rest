package me.m92.tatbook_web.userprofile;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FullName {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    protected FullName() { }

    protected FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static FullName of(String firstName, String lastName) {
        return new FullName(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
