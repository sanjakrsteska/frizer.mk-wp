package mk.frizer.utilities.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;

import java.io.IOException;

public class BusinessOwnerSerializer extends JsonSerializer<BusinessOwner> {
    @Override
    public void serialize(BusinessOwner owner, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", owner.getId());

        // Start writing baseUser object
        jsonGenerator.writeObjectFieldStart("baseUser");
        jsonGenerator.writeNumberField("id", owner.getBaseUser().getId());
        jsonGenerator.writeStringField("email", owner.getBaseUser().getEmail());
//        jsonGenerator.writeStringField("password", owner.getBaseUser().getPassword());
        jsonGenerator.writeStringField("firstName", owner.getBaseUser().getFirstName());
        jsonGenerator.writeStringField("lastName", owner.getBaseUser().getLastName());
        jsonGenerator.writeStringField("phoneNumber", owner.getBaseUser().getPhoneNumber());
        jsonGenerator.writeStringField("roles", owner.getBaseUser().getRoles().toString());
        // Add other fields of baseUser as needed
        jsonGenerator.writeEndObject(); // End writing baseUser object

        jsonGenerator.writeArrayFieldStart("salonList");
        for (Salon salon : owner.getSalonList()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", salon.getId());
            jsonGenerator.writeStringField("name", salon.getName());
            jsonGenerator.writeStringField("location", salon.getLocation());
            jsonGenerator.writeStringField("phoneNumber", salon.getPhoneNumber());
            jsonGenerator.writeNumberField("owner", salon.getOwner().getId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}

