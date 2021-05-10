package ch.ocsource.yasr.jackson;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonLocation;

public class JsonLocationInformation {
	private static final String UNKNOWN_BEAN = "<unknown>";
	
	private Path path;
	private JsonLocation beanLocation;
	private Map<String, JsonLocation> propertyLocations = new HashMap<>();
	
	public JsonLocationInformation(final Path path, final JsonLocation beanLocation) {
		this.path = path;
		this.beanLocation = beanLocation;
	}
	
	public Path getPath() {
		return path;
	}
	
	public String getAbsolutePath() {
		return path.toAbsolutePath().toString();
	}
	
	public JsonLocation getBeanLocation() {
		return beanLocation;
	}
	
	public Map<String, JsonLocation> getPropertyLocations() {
		return propertyLocations;
	}
	
	public void addPropertyLocation(final String propertyName, final JsonLocation location) {
		propertyLocations.put(propertyName, location);
	}
	
	public JsonLocation getPropertyLocation(final String propertyName) {
		return propertyLocations.get(propertyName);
	}
	
	private String locationFormatter(final JsonLocation loc) {
		return "Line: " + loc.getLineNr() + " Column: " + loc.getColumnNr() + "\n";
	}
	
	@Override
	public String toString() {
		return toString(UNKNOWN_BEAN);
	}

	public String toString(final String className) {
		final var str = new StringBuilder();
		str.append("In file: ");
		str.append(path.toAbsolutePath());
		str.append("\n");
		str.append("Bean ");
		str.append(className);
		str.append(" ");
		str.append(locationFormatter(beanLocation));
		str.append("Properties:\n");
		for(final var pair : getPropertyLocations().entrySet()) {
			str.append("- " + pair.getKey() + " ");
			str.append(locationFormatter(pair.getValue()));
		}
		return str.toString();
	}
}