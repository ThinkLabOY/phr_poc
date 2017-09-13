
package org.ech.phr.model.fhir;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endpoint implements HasManagingOrganization {

	@ApiModelProperty(example = "Endpoint")
    public String resourceType;
    @NotNull
    public Reference managingOrganization;
    @NotNull
	@ApiModelProperty(example = "http://org1.org/fhir")
    public String address;

}
