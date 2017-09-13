package org.ech.phr.model.fhir;

public enum ResourceTypeEnum {

    ENDPOINT ("Endpoint"),
    ORGANIZATION ("Organization"),
    TYPE ("Type"),
    PATIENT ("Patient");

    private final String text;
    
    ResourceTypeEnum(String text) {
        this.text = text;
    }

	public String getText() {
		return text;
	}

}
