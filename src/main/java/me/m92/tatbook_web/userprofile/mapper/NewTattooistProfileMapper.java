package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.NewUserProfileProjection;
import me.m92.tatbook_web.userprofile.TattooistProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewTattooistProfileMapper {
    @ToNewUserProfileMapper
    TattooistProfile mapToTattooistProfile(NewUserProfileProjection userProfileProjection, String hPassword);
}
