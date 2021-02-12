package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.TattooStyleProjection;
import me.m92.tatbook_web.tattoo.mapper.StyleMapper;
import me.m92.tatbook_web.userprofile.StylePreference;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class StylePreferenceMapper {

    @Autowired
    private StyleMapper styleMapper;

    public StylePreference mapFrom(final TattooStyleProjection styleProjection) {
        final StylePreference stylePreference = new StylePreference();
        stylePreference.setStyle(styleMapper.mapFrom(styleProjection));
        return stylePreference;
    }
}
