package br.com.digisystem.commom;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	// Configura um Bean para o Spring, assim ele pode ser injetado em outros componentes
	 @Bean
	 public ModelMapper modelMapper() {
		 return new ModelMapper();
	 }
}
