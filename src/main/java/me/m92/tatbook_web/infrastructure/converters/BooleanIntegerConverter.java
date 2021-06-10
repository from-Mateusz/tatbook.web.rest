package me.m92.tatbook_web.infrastructure.converters;

import javax.persistence.AttributeConverter;

public class BooleanIntegerConverter implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return null == attribute ? null : (attribute ? 1 : 0);
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return null == dbData ? null : (dbData > 0);
    }
}
