package mk.frizer.utilities.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.Salon;
import mk.frizer.model.Tag;

import java.io.IOException;

public class TagSerializer extends JsonSerializer<Tag> {
    @Override
    public void serialize(Tag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", tag.getId());
        jsonGenerator.writeStringField("name", tag.getName());
        jsonGenerator.writeArrayFieldStart("salonIdsWithTag");
        for (Salon salon : tag.getSalonsWithTag()) {
            jsonGenerator.writeNumber(salon.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
