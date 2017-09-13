
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
public class Identifier {

	@ApiModelProperty(example = "usual")
    public String use;
    @NotNull
	@ApiModelProperty(example = "http://www.politsei.ee/")
    public String system;
    @NotNull
	@ApiModelProperty(example = "37804230234")
    public String value;

}
