package su.funk.openapi.sampler;

import io.swagger.v3.oas.models.media.Schema;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

class PropertySampler {

    static OffsetDateTime DATE = OffsetDateTime.parse("2023-03-29T21:14:08Z");

    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.from(ZoneOffset.UTC));
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    static PropertySampler INSTANCE = new PropertySampler();

    public Object sample(Schema<?> schema) {
        if (schema.getType() == null) {
            return null;
        }
        if (schema.getEnum() != null) {
            if (schema.getEnum().isEmpty()) {
                return null;
            } else {
                return schema.getEnum().get(0);
            }
        }
        return switch (schema.getType()) {
            case "string" -> string(schema);
            case "number" -> number(schema);
            case "integer" -> integer(schema);
            case "boolean" -> bool(schema);
            default -> null;
        };
    }

    private Object bool(Schema<?> schema) {
        return false;
    }

    private Object integer(Schema<?> schema) {
        return 0;
    }

    private Object number(Schema<?> schema) {
        return 0.0;
    }

    private Object string(Schema<?> schema) {
        if (schema.getFormat() == null) {
            return "string";
        }
        return switch (schema.getFormat()) {
            case "date" -> PropertySampler.DATE_FORMATTER.format(DATE);
            case "date-time" -> PropertySampler.DATE_TIME_FORMATTER.format(DATE);
            case "password" -> "qwerty";
            case "byte" -> "aGVsbG8xCg==";
            case "email" -> "info@example.com";
            case "uuid", "guid" -> UUID.randomUUID().toString();
            default -> "string";
        };
    }

}
