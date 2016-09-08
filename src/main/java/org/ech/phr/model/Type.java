package org.ech.phr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ech.phr.model.generic.JsonDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type extends JsonDto {
	
	private String typeCode;
	private String typeCodeOid;
}
