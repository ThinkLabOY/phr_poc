package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person extends JsonDto {

	private String personId;
	private String personIdOid;
	private String phrId;
	private String phrIdOid;
	private String masterId;
	private String masterIdOid;

	public Person(String personId, String personIdOid) {
		super();
		this.personId = personId;
		this.personIdOid = personIdOid;
	}
}
