package ec.com.edimca.authentication.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapperConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
				.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
	}
}