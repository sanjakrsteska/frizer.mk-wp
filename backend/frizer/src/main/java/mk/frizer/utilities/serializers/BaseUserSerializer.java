package mk.frizer.utilities.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.BaseUser;

import java.io.IOException;

public class BaseUserSerializer extends JsonSerializer<BaseUser> {
    @Override
    public void serialize(BaseUser baseUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", baseUser.getId());
        jsonGenerator.writeStringField("email", baseUser.getEmail());
        jsonGenerator.writeStringField("firstName", baseUser.getFirstName());
        jsonGenerator.writeStringField("lastName", baseUser.getLastName());
        jsonGenerator.writeStringField("phoneNumber", baseUser.getPhoneNumber());
        jsonGenerator.writeStringField("roles", baseUser.getRoles().toString());
        jsonGenerator.writeEndObject();
    }
}
