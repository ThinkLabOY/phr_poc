package org.ech.phr.model.hbase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ech.phr.model.hbase.generic.JsonDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organisation extends JsonDto {

	private String id;
	private String idOid;
	private String phrId;
	private String phrIdOid;
	private String url;
}
