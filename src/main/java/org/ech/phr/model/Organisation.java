package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation extends JsonDto {

	private String id;
	@JsonIgnore
	private String idOid;
	@JsonIgnore
	private String phrId;
	@JsonIgnore
	private String phrIdOid;
	private String url;
}
