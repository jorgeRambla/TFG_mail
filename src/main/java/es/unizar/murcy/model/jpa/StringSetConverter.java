package es.unizar.murcy.model.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        return String.join(",", set);
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        return new HashSet<>(Arrays.asList(joined.split(DELIMITER)));
    }

}