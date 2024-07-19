package com.ecf.zevent.converter;

import com.ecf.zevent.model.enumerations.StreamerStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StreamerStatusAttributeConverter implements AttributeConverter<StreamerStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StreamerStatus streamerStatus) {
        return streamerStatus != null ? streamerStatus.getKey() : null;
    }

    @Override
    public StreamerStatus convertToEntityAttribute(Integer integer) {
        return Stream.of(StreamerStatus.values())
                .filter(streamerStatus -> streamerStatus.getKey().equals(integer))
                .findFirst().orElse(null);
    }
}
