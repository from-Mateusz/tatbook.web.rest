package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.NewUserProfileProjection;
import me.m92.tatbook_web.userprofile.ClientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewClientProfileMapper {
    @ToNewUserProfileMapper
    ClientProfile mapToClientProfile(NewUserProfileProjection userProfileProjection, String hPassword);
}
