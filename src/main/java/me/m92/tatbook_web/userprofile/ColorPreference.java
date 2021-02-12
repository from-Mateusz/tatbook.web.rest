package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.converters.BooleanToIntegerConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Embeddable
public class ColorPreference {

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "keen_on_shades_of_grey_tattoos")
    private boolean shadesOfGrey;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "keen_on_colorful_tattoos")
    private boolean colorful;

    public ColorPreference() { }

    public ColorPreference(boolean shadesOfGrey, boolean colorful) {
        this.shadesOfGrey = shadesOfGrey;
        this.colorful = colorful;
    }

    public static ColorPreference every() {
        return new ColorPreference(true, true);
    }

    public static ColorPreference onlyShadesOfGrey() {
        return new ColorPreference(true, false);
    }

    public static ColorPreference onlyColorful() {
        return new ColorPreference(false, true);
    }

    public boolean preferShadesOfGrey() {
        return shadesOfGrey;
    }

    public boolean preferColorful() {
        return colorful;
    }
}
