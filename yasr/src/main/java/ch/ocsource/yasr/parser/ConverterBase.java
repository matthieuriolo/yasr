package ch.ocsource.yasr.parser;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.ocsource.yasr.jackson.BeanDeserializerFactory;
import ch.ocsource.yasr.parser.validator.Validator;

public abstract class ConverterBase {
	protected ObjectMapper getMapper(final File file) {
		final var mapper = new ObjectMapper(new YAMLFactory(), null, new DefaultDeserializationContext.Impl(
				new BeanDeserializerFactory(file.toPath(), new DeserializerFactoryConfig())));
		mapper.findAndRegisterModules();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
	
	protected void validateModel(final ModelBase doc) {
		final var validator = new Validator();
		validator.validate(doc);
	}
}
