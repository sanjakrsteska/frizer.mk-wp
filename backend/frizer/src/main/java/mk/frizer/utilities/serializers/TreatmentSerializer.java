package mk.frizer.utilities.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.Treatment;

import java.io.IOException;

public class TreatmentSerializer extends JsonSerializer<Treatment> {
    @Override
    public void serialize(Treatment treatment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", treatment.getId());
        jsonGenerator.writeStringField("name", treatment.getName());
        jsonGenerator.writeNumberField("salon", treatment.getSalon().getId());
        jsonGenerator.writeNumberField("price", treatment.getPrice());
        jsonGenerator.writeEndObject();
    }
}
