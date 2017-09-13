package org.ech.phr.model.hbase;

import org.ech.phr.model.hbase.generic.JsonDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource extends JsonDto {
	
	private Type type;
	
	private String resourceId;
	private String resourceIdOid;
}
