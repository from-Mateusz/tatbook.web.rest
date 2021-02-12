package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.tattoo.Style;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class StylePreference {

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    public StylePreference() {}

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
