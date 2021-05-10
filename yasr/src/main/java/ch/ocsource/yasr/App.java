package ch.ocsource.yasr;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.ocsource.yasr.exceptions.VersionNotFoundException;
import ch.ocsource.yasr.parser.VersionFactory;

public class App {
	private static final String TESTFILE = "/test.yaml";
	
	public static void main(String[] args)
			throws JsonProcessingException, IOException, URISyntaxException, VersionNotFoundException {
		final var url = App.class.getResource(TESTFILE);
		final var file = new File(url.toURI());
		final var converter = VersionFactory.getConverter(file);
		converter.read(file);
	}
}
