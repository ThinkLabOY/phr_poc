package org.ech.phr.model.hbase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ech.phr.model.hbase.generic.JsonDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends JsonDto {

	private String personId;
	private String personIdOid;
	private String phrId;
	private String phrIdOid;
	private String masterId;
	private String masterIdOid;	
	
	@JsonIgnore
	private Organisation organisation;

	public Person(String personId, String personIdOid) {
		super();
		this.personId = personId;
		this.personIdOid = personIdOid;
	}
}
