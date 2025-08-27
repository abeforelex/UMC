package ru.mts.media.platform.umc.domain.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@DgsScalar(name = "LocalDateTime")
public class LocalDateTimeScalar implements Coercing<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String serialize(Object dataFetcherResult) {
        if (dataFetcherResult instanceof LocalDateTime ldt) {
            return FORMATTER.format(ldt);
        }
        throw new CoercingSerializeException("Value is not a valid LocalDateTime: " + dataFetcherResult);
    }

    @Override
    public LocalDateTime parseValue(Object input) {
        if (input instanceof String text) {
            try {
                return LocalDateTime.parse(text, FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseValueException("Unable to parse LocalDateTime from value: " + text, ex);
            }
        }
        throw new CoercingParseValueException("Value must be a String to parse LocalDateTime.");
    }

    @Override
    public LocalDateTime parseLiteral(Object input) {
        if (input instanceof StringValue stringValue) {
            try {
                return LocalDateTime.parse(stringValue.getValue(), FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseLiteralException("Unable to parse LocalDateTime from literal: " + stringValue.getValue(), ex);
            }
        }
        throw new CoercingParseLiteralException("Literal must be a StringValue to parse LocalDateTime.");
    }
}
