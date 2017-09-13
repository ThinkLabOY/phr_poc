package org.ech.phr.model.fhir;

import java.util.List;

import org.ech.phr.model.fhir.Code;
import org.ech.phr.model.fhir.Meta;
import org.ech.phr.model.fhir.Reference;
import org.ech.phr.model.fhir.Text;
import org.ech.phr.model.fhir.ValueQuantity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Observation {

    public String resourceType;
    public String id;
    public Meta meta;
    public Text text;
    public String status;
    public List<Code> category;
    public Code code;
    public Reference subject;
    public Reference context;
    public String effectiveDateTime;
    public ValueQuantity valueQuantity;

}
