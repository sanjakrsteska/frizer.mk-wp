package mk.frizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import mk.frizer.model.*;
import mk.frizer.utilities.serializers.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class FrizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrizerApplication.class, args);
	}

	@Bean
	public ObjectMapper customObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(BusinessOwner.class, new BusinessOwnerSerializer());
		module.addSerializer(Salon.class, new SalonSerializer());
		module.addSerializer(Tag.class, new TagSerializer());
		module.addSerializer(BaseUser.class, new BaseUserSerializer());
		module.addSerializer(Treatment.class, new TreatmentSerializer());
		module.addSerializer(Appointment.class, new AppointmentSerializer());
		objectMapper.registerModule(module);

		objectMapper.registerModule(new ParameterNamesModule())
					.registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule());

		return objectMapper;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}


}
