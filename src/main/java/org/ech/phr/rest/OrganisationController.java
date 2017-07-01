package org.ech.phr.rest;

import org.ech.phr.model.Organisation;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.ech.phr.service.OrganisationService;

@Slf4j
@RestController
@RequestMapping("/fhir/Organisation")
public class OrganisationController {

	@Autowired
	private OrganisationService organisationService;

	@RequestMapping(method = RequestMethod.GET)
	public Organisation findOrganisation( @RequestParam("organisation.identifier.value")  String id) {
		Organisation organisation = null;
		try {
			organisation = organisationService.getOrganisationById(id, FhirUtil.OID_PHR);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		return organisation;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Organisation saveOrganisation(@RequestBody Organisation organisation) {

		try {
			organisation = organisationService.registerOrganisation(organisation.getId(), FhirUtil.OID_PHR, organisation.getUrl());
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}

		return organisation;
	}
}
