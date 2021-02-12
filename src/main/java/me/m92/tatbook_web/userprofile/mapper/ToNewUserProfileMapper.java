package me.m92.tatbook_web.userprofile.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "name", expression = "java(me.m92.tatbook_web.user_profile.FullName.of(userProfileProjection.getFirstName(), userProfileProjection.getLastName()))")
@Mapping(target = "email", expression = "java(me.m92.tatbook_web.user_profile.Email.of(userProfileProjection.getEmail()))")
@Mapping(target = "mobile", expression = "java(me.m92.tatbook_web.user_profile.Mobile.of(userProfileProjection.getMobile()))")
@Mapping(target = "password", source = "hPassword")
public @interface ToNewUserProfileMapper {

}
