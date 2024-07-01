package com.ecf.zevent.converter;

import com.ecf.zevent.model.ThematiqueType;
import com.ecf.zevent.repository.LiveRepositoryCustomImpl;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ListThematiqueTypeAttributeConverter implements AttributeConverter<List<ThematiqueType>, String> {
    @Override
    public String convertToDatabaseColumn(List<ThematiqueType> thematiqueTypes) {
        if(thematiqueTypes == null || thematiqueTypes.isEmpty()) return "";
        return thematiqueTypes.stream()
                .map(ThematiqueType::getKey)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<ThematiqueType> convertToEntityAttribute(String data) {
        if(data == null || data.isEmpty()) return List.of();
        return Stream.of(data.split(","))
                .map(Integer::valueOf)
                .map(ThematiqueType::getByKey)
                .toList();

    }
}
