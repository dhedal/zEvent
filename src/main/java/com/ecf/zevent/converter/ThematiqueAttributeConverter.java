package com.ecf.zevent.converter;

import com.ecf.zevent.model.ThematiqueType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ThematiqueAttributeConverter implements AttributeConverter<ThematiqueType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ThematiqueType thematiqueType) {
        return thematiqueType != null ? thematiqueType.getKey() : null;
    }

    @Override
    public ThematiqueType convertToEntityAttribute(Integer key) {
        return Stream.of(ThematiqueType.values())
                .filter(thematiqueType -> thematiqueType.getKey().equals(key))
                .findFirst().orElse(null);
    }
}
