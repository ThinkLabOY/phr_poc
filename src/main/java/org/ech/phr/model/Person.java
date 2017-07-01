package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person extends JsonDto {

	private String personId;
	private String personIdOid;
	@JsonIgnore
	private String phrId;
	@JsonIgnore
	private String phrIdOid;
	@JsonIgnore
	private String masterId;
	@JsonIgnore
	private String masterIdOid;
	
	private Organisation organisation;
	

	public Person(String personId, String personIdOid) {
		super();
		this.personId = personId;
		this.personIdOid = personIdOid;
	}
}
