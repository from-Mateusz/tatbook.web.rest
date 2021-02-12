package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.userprofile_configuration.BioConfiguration;
import me.m92.tatbook_web.userprofile.TattooistProfileBio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ColorPreferenceMapper.class})
public interface TattooistProfileBioMapper {

    @Mapping(target = "tattooistProfile", ignore = true)
    TattooistProfileBio mapFrom(BioConfiguration bioConfiguration);
}
