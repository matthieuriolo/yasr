package ch.ocsource.yasr.parser;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Converter {
	public void read(final File file) throws JsonParseException, JsonMappingException, IOException;
	public void validate();
}
