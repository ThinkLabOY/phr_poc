package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation extends JsonDto {

	private String id;
	private String idOid;
	private String phrId;
	private String phrIdOid;
	private String url;
}
