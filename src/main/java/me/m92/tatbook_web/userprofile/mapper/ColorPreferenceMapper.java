package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.userprofile_configuration.ColorPreferenceConfiguration;
import me.m92.tatbook_web.userprofile.ColorPreference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ColorPreferenceMapper {

    public ColorPreference mapFrom(ColorPreferenceConfiguration configuration) {
        if(configuration.isColorful() && configuration.isShadesOfGrey()) {
            return ColorPreference.every();
        }
        if(configuration.isColorful()) {
            return ColorPreference.onlyColorful();
        }
        if(configuration.isShadesOfGrey()) {
            return ColorPreference.onlyColorful();
        }
        return null;
    }
}
