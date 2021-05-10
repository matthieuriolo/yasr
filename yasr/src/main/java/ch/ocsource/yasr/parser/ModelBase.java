package ch.ocsource.yasr.parser;

import ch.ocsource.yasr.jackson.JsonLocationInformation;

public abstract class ModelBase {
	public abstract JsonLocationInformation getJsonLocationInformation();
	
	@Override
	public String toString() {
		return getJsonLocationInformation().toString(getClass().toString());
	}
}
