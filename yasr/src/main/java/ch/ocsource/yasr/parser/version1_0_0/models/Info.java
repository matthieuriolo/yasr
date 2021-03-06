package ch.ocsource.yasr.parser.version1_0_0.models;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;

import ch.ocsource.yasr.jackson.JsonInformation;
import ch.ocsource.yasr.jackson.JsonLocationInformation;
import ch.ocsource.yasr.parser.ModelBase;

public class Info extends ModelBase {
	@JsonInformation
	public JsonLocationInformation jsonInformation;
	
	@JsonBackReference
	public Document parent;
	
	public String name;
	public String description;
	public String version;
	public OffsetDateTime created;
	
	public Info() {}
	public Info(final String version) {
		this.version = version;
	}
	
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	static Info test(String content) {
		return new Info(content);
	}
	
	@Override
	public JsonLocationInformation getJsonLocationInformation() {
		return jsonInformation;
	}
}
