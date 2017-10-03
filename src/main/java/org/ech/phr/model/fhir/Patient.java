
package org.ech.phr.model.fhir;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Patient implements HasManagingOrganization {

	@ApiModelProperty(example = "Patient")
    public String resourceType;
    @NotNull
    @Size(min=1)
    public List<Identifier> identifier;
    @NotNull
    public Reference managingOrganization;
    
    @JsonIgnore
    public Identifier getFirstIdentifier() {
    	Identifier firstIdentifier = null;
    	if (identifier != null && !identifier.isEmpty()) {
    		firstIdentifier = identifier.get(0);
    	}
    	return firstIdentifier;
    }

    @JsonIgnore
    public Optional<Identifier> getIdentifier(String system) {
    	return identifier.stream().filter(identifier -> system.equals(identifier.getSystem())).findFirst();
    }
}
