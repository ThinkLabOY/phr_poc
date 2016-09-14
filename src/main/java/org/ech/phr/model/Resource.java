package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource extends JsonDto {
	
	private Type type;
	
	private String resourceId;
	private String resourceIdOid;
}
