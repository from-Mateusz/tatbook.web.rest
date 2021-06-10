package me.m92.tatbook_web.core.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "token_category")
public class TokenCategory {

    private Long id;

    private String name;

    private Long longevity;

    public enum SupportedNames {
        MOBILE_NUMBER_VERIFICATION
    }

    private TokenCategory() {}

    private TokenCategory(Long id, String name, Long longevity) {
        this.id = id;
        this.name = name;
        this.longevity = longevity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getLongevity() {
        return longevity;
    }
}
