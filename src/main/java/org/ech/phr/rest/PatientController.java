package org.ech.phr.rest;

import static org.ech.phr.model.fhir.ResourceTypeEnum.ORGANIZATION;
import static org.ech.phr.model.fhir.ResourceTypeEnum.PATIENT;
import static org.ech.phr.rest.ParameterConstants.ID;
import static org.ech.phr.rest.ParameterConstants.ORGANISATION_IDENTIFIER_VALUE;
import static org.ech.phr.rest.ParameterConstants.PATIENT_IDENTIFIER_SYSTEM;
import static org.ech.phr.util.FhirUtil.DELIMITER;
import static org.ech.phr.util.FhirUtil.OID_PHR;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.fhir.Identifier;
import org.ech.phr.model.fhir.Patient;
import org.ech.phr.model.fhir.Reference;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.model.hbase.Person;
import org.ech.phr.service.PersonService;
import org.ech.phr.service.ResourceService;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fhir/Patient")
public class PatientController {

	private static final String EXAMPLE = "{\"resourceType\": \"Patient\",\"identifier\": [{\"use\": \"usual\",\"system\": \"http://www.politsei.ee/\",\"value\": \"37804230234\"}],\"managingOrganization\": {\"reference\": \"Organization/ORG1\"}}";

	@Autowired
	private PersonService personService;

	@Autowired
	private ResourceService resourceService;

	@ApiOperation("Query registered patient(s) (managed by organization)")
	@RequestMapping(method = RequestMethod.GET, value = "/{" + ID + "}")
	public List<Patient> findPatient(
			@PathVariable(ID) @NotNull String id, 
			@RequestParam(PATIENT_IDENTIFIER_SYSTEM) @NotNull String oid, 
			@RequestParam(name=ORGANISATION_IDENTIFIER_VALUE, required= false) String organisationId) throws BusinessException {
		List<Patient> patientList = null;
		try {
			if (StringUtils.isNotBlank(organisationId)) {
				Person person = personService.findPerson(id, oid, organisationId, FhirUtil.OID_PHR);
				if (person != null) {
					patientList = new ArrayList<>();
					patientList.add(mapPatientFromPerson(person, organisationId));
				}
			}
			else {
				List<Person> personList = personService.findPerson(id, oid);
				if (CollectionUtils.isNotEmpty(personList)) {
					patientList = new ArrayList<>();
					for (Person person : personList) {
						if (person != null) {
							patientList.add(mapPatientFromPerson(person));
						}
					}
				}
			}
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
			throw e;
		}
		return patientList;
	}

	@ApiOperation("Registered patient managed by organization")
	@RequestMapping(method = RequestMethod.POST)
	public Patient savePatient(@ApiParam(required = true, value = EXAMPLE) @RequestBody @Valid Patient patient) throws BusinessException {
		Patient resultPatient = null;
		try {
			Person person = mapPersonFromPatient(patient);
			String organizationId = EndpointController.extractOrganizationId(patient);
			person = personService.findOrInsertPerson(person.getPersonId(),
					person.getPersonIdOid(), organizationId, OID_PHR);
			resourceService.putResourceReference(person.getPersonId(), person.getPersonIdOid(), organizationId, OID_PHR, PATIENT.getText(), OID_PHR, person.getPhrId(), person.getPhrIdOid());
			resultPatient = mapPatientFromPerson(person, organizationId);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
			throw e;
		}
		return resultPatient;
	}

	private Patient mapPatientFromPerson(Person person) {
		Organisation organization = person.getOrganisation();
		String organizationId = null;
		if (organization != null) {
			organizationId = organization.getId();
		}
		return mapPatientFromPerson(person, organizationId);
	}

	private Patient mapPatientFromPerson(Person person, String organizationId) {
		Identifier identifier = Identifier.builder()
				.system(person.getPersonIdOid())
				.value(person.getPersonId())
				.use("usual")
				.build();
		Reference managingOrganization = null;
		if (organizationId != null) {
			managingOrganization = Reference.builder()
					.reference(ORGANIZATION.getText() + DELIMITER + organizationId)
					.build();
		}
		Patient patient = Patient.builder()
				.resourceType(PATIENT.getText())
				.identifier(ImmutableList.of(identifier))
				.managingOrganization(managingOrganization)
				.build();
		return patient;
	}

	private Person mapPersonFromPatient(Patient patient) {
		Identifier identifier = patient.getFirstIdentifier();
		Person person = null;
		if (identifier != null) {
			person = Person.builder()
					.personId(identifier.getValue())
					.personIdOid(identifier.getSystem())
					.build();
		}
		return person;
	}

}
