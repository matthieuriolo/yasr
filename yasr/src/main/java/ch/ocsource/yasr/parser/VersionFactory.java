package ch.ocsource.yasr.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ch.ocsource.yasr.exceptions.VersionNotFoundException;

final public class VersionFactory {
	private final static String VERSION_POINTER = "/version";

	public static Converter getConverter(final File file)
			throws JsonProcessingException, IOException, VersionNotFoundException {
		final var mapper = new ObjectMapper(new YAMLFactory());
		final var versionNode = mapper.readTree(file).at(VERSION_POINTER);

		if (versionNode.isMissingNode()) {
			throw new VersionNotFoundException();
		}

		if (!versionNode.isTextual()) {
			throw new VersionNotFoundException();
		}

		final var converters = getConverters();
		final var version = versionNode.asText();
		if (!converters.containsKey(version)) {
			throw new VersionNotFoundException();
		}

		return converters.get(version).get();
	}
	
	static Map<String, Supplier<? extends Converter>> getConverters() {
		Map<String, Supplier<? extends Converter>> converters = new HashMap<>();
		converters.put(
				ch.ocsource.yasr.parser.version1_0_0.Converter.version,
				() -> new ch.ocsource.yasr.parser.version1_0_0.Converter());
		return converters;
	}
}
