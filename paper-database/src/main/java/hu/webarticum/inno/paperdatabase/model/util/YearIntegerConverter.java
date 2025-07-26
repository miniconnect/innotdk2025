package hu.webarticum.inno.paperdatabase.model.util;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YearIntegerConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year attribute) {
        return (attribute != null) ? attribute.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? Year.of(dbData) : null;
    }
}
