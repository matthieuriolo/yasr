package ch.ocsource.yasr.parser.version1_0_0;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import ch.ocsource.yasr.parser.ConverterBase;
import ch.ocsource.yasr.parser.version1_0_0.models.Document;

public class Converter extends ConverterBase implements ch.ocsource.yasr.parser.Converter {
	public final static String version = "1.0.0";
	private Document doc;
	
	@Override
	public void read(final File file) throws JsonParseException, JsonMappingException, IOException {
		final var mapper = getMapper(file);
		doc = mapper.readValue(file, Document.class);
		System.out.println(doc);
	}

	@Override
	public void validate() {
		validateModel(doc);
	}
}
