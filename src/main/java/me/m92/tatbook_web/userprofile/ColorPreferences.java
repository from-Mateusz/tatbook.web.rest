package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.converters.BooleanToIntegerConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Embeddable
public class ColorPreferences {

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "keen_on_shades_of_grey_tattoos")
    private boolean shadesOfGrey;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "keen_on_colorful_tattoos")
    private boolean colorful;

    public ColorPreferences() { }

    public ColorPreferences(boolean shadesOfGrey, boolean colorful) {
        this.shadesOfGrey = shadesOfGrey;
        this.colorful = colorful;
    }

    public static ColorPreferences every() {
        return new ColorPreferences(true, true);
    }

    public static ColorPreferences onlyShadesOfGrey() {
        return new ColorPreferences(true, false);
    }

    public static ColorPreferences onlyColorful() {
        return new ColorPreferences(false, true);
    }

    public boolean preferShadesOfGrey() {
        return shadesOfGrey;
    }

    public boolean preferColorful() {
        return colorful;
    }
}
