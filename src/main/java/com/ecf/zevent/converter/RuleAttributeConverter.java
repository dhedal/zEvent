package com.ecf.zevent.converter;

import com.ecf.zevent.model.Rule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RuleAttributeConverter implements AttributeConverter<Rule, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Rule rule) {
        return rule != null ? rule.getKey() : null;
    }

    @Override
    public Rule convertToEntityAttribute(Integer key) {
        return Stream.of(Rule.values()).filter(rule -> rule.getKey().equals(key))
                .findFirst().orElse(null);
    }
}
