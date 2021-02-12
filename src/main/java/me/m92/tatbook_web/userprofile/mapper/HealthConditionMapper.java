package me.m92.tatbook_web.userprofile.mapper;

import me.m92.tatbook_web.projection.HealthConditionProjection;
import me.m92.tatbook_web.userprofile.HealthCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HealthConditionMapper {

    HealthCondition mapToHealthCondition(HealthConditionProjection healthConditionProjection);

    HealthConditionProjection mapFromHealthCondition(HealthCondition healthCondition);
}
