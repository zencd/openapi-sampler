package su.funk.openapi.sampler;

import io.swagger.v3.oas.models.media.Schema;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class PropertySampler {

    static PropertySampler INSTANCE = new PropertySampler();

    public Object sample(Schema<?> schema) {
        if (schema.getType() == null) {
            return null;
        }
        if (schema.getEnum() != null) {
            if (schema.getEnum().isEmpty()) {
                return null;
            } else {
                return schema.getEnum().get(0); // first value
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
            case "date" -> OpenApiSampler.DATE_FORMATTER.format(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            case "date-time" -> OpenApiSampler.DATE_TIME_FORMATTER.format(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            case "password" -> "qwerty";
            case "byte" -> "aGVsbG8xCg==";
            case "email" -> "info@example.com";
            case "uuid", "guid" -> UUID.randomUUID().toString();
            default -> "string";
        };
    }

}
