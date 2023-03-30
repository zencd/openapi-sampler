package su.funk.openapi.sampler;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.util.SchemaTypeUtil;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @see SchemaTypeUtil
 */
class ScalarSampler {

    static final String STRING_DEFAULT = "string";
    static final OffsetDateTime DATE = OffsetDateTime.parse("2023-03-29T21:14:08Z");

    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.from(ZoneOffset.UTC));
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    public Object sample(Schema<?> schema) {
        if (schema.getEnum() != null) {
            return enumeration(schema);
        }
        if (schema.getType() == null) {
            return null;
        }
        return switch (schema.getType()) {
            case "string" -> string(schema);
            case "number" -> number(schema);
            case "integer" -> integer(schema);
            case "boolean" -> bool(schema);
            default -> null;
        };
    }

    private Object enumeration(Schema<?> schema) {
        if (schema.getEnum().isEmpty()) {
            return null;
        } else {
            return schema.getEnum().get(0);
        }
    }

    private Object bool(Schema<?> schema) {
        return false;
    }

    private Object integer(Schema<?> schema) {
        if (schema.getMinimum() != null) {
            return schema.getMinimum();
        }
        return BigDecimal.ZERO;
    }

    private Object number(Schema<?> schema) {
        return 0.0;
    }

    private Object string(Schema<?> schema) {
        if (schema.getFormat() == null) {
            return STRING_DEFAULT;
        }
        return switch (schema.getFormat()) {
            case "date" -> ScalarSampler.DATE_FORMATTER.format(DATE);
            case "date-time" -> ScalarSampler.DATE_TIME_FORMATTER.format(DATE);
            case "password" -> "qwerty";
            case "byte", "binary" -> "aGVsbG8xCg==";
            case "email" -> "info@example.com";
            case "uuid", "guid" -> UUID.randomUUID().toString();
            default -> STRING_DEFAULT;
        };
    }

}
