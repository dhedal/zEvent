package com.ecf.zevent.converter;

import com.ecf.zevent.model.Pegi;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PegiAttributeConverter implements AttributeConverter<Pegi, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Pegi pegi) {
        return pegi != null ? pegi.getKey() : null;
    }

    @Override
    public Pegi convertToEntityAttribute(Integer key) {
        return Stream.of(Pegi.values()).filter(pegi -> pegi.getKey().equals(key))
                .findFirst().orElse(null);
    }
}
