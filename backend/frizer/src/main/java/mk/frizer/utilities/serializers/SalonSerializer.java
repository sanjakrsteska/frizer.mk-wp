package mk.frizer.utilities.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.Employee;
import mk.frizer.model.Salon;
import mk.frizer.model.Tag;
import mk.frizer.model.Treatment;

import java.io.IOException;

public class SalonSerializer extends JsonSerializer<Salon> {
    @Override
    public void serialize(Salon salon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", salon.getId());
        jsonGenerator.writeStringField("name", salon.getName());
        jsonGenerator.writeStringField("description", salon.getDescription());
        jsonGenerator.writeStringField("location", salon.getLocation());
        jsonGenerator.writeStringField("phoneNumber", salon.getPhoneNumber());
        jsonGenerator.writeStringField("owner", salon.getOwner() != null ? salon.getOwner().getId().toString() : null);
        jsonGenerator.writeArrayFieldStart("employees");
        for (Employee employee: salon.getEmployees()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", employee.getId());
            jsonGenerator.writeStringField("name", employee.getBaseUser().getFirstName());
            jsonGenerator.writeNumberField("baseUserId", employee.getBaseUser().getId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("salonTreatments");
        for (Treatment treatment: salon.getSalonTreatments()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", treatment.getId());
            jsonGenerator.writeStringField("name", treatment.getName());
            jsonGenerator.writeNumberField("price", treatment.getPrice());
            jsonGenerator.writeNumberField("salon", treatment.getSalon().getId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("salonTags");
        for (Tag tag: salon.getTags()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", tag.getId());
            jsonGenerator.writeStringField("name", tag.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("imagePaths");
        for (String path: salon.getImagePaths()) {
            jsonGenerator.writeString(path);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}

