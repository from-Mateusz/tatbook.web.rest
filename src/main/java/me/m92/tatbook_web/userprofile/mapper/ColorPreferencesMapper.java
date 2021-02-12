package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.userprofile_configuration.ColorPreferencesConfiguration;
import me.m92.tatbook_web.userprofile.ColorPreferences;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ColorPreferencesMapper {

    public ColorPreferences mapFrom(ColorPreferencesConfiguration configuration) {
        if(configuration.isColorful() && configuration.isShadesOfGrey()) {
            return ColorPreferences.every();
        }
        if(configuration.isColorful()) {
            return ColorPreferences.onlyColorful();
        }
        if(configuration.isShadesOfGrey()) {
            return ColorPreferences.onlyColorful();
        }
        return null;
    }
}
