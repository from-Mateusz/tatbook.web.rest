package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.DomainEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.*;

@MappedSuperclass
public abstract class UserProfile extends DomainEntity implements UserDetails {

    @Embedded
    private FullName name;

    @Embedded
    private Email email;

    @Embedded
    private Mobile mobile;

    @Column(name = "hash")
    private String password;

    public static enum ROLES {
        CLIENT, TATTOOIST, MANAGER, ADMINISTRATOR
    }

    protected UserProfile() {}

    protected UserProfile(FullName name, Email email, Mobile mobile, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public FullName getName() {
        return name;
    }

    public void setName(FullName name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return name.getFirstName();
    }

    public String getLastName() {
        return name.getLastName();
    }

    public String getEmailAddress() {
        return email.getAddress();
    }

    public String getMobileNumber() {
        return mobile.getNumber();
    }

    public abstract ROLES getRole();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + getRole().name());
        grantedAuthorities.add(authority);
        return Collections.unmodifiableCollection(grantedAuthorities);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isSameAs(UserProfile userProfile) {
        if(null == userProfile) {
            return false;
        }
        return Objects.equals(this.getId(), userProfile.getId())
                && Objects.equals(this.getEmail(), userProfile.getEmail());
    }
}
