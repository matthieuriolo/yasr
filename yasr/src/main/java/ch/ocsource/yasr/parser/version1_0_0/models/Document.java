package ch.ocsource.yasr.parser.version1_0_0.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ch.ocsource.yasr.jackson.JsonInformation;
import ch.ocsource.yasr.jackson.JsonLocationInformation;
import ch.ocsource.yasr.parser.ModelBase;

public class Document extends ModelBase {
	@JsonInformation
	public JsonLocationInformation jsonInformation;
	
	public String version;
	@JsonManagedReference
	public Info info;
	public List<String> imports;
	
	@Override
	public JsonLocationInformation getJsonLocationInformation() {
		return jsonInformation;
	}
}
