# Prerequisites:

1. Download and install Docker
https://store.docker.com/search?type=edition&offering=community
2. Download and install Java, Git, Maven

# Environment setup:

Run batches in the following order:
1. phr_build.bat
2. docker_build_hbase.bat
3. docker_build_phr.bat
4. docker_create_network.bat
5. docker_run_hbase.bat (in a seperate command window)
6. docker_run_phr.bat (in a seperate command window)
7. create_data_structure.bat

# Testing environment:
Open swagger Ui for documented REST api:
http://localhost:8081/swagger-ui.html

1. Registering endpoint managed by organisation:
   1. Register endpoint managed by organization
    http://localhost:8081/swagger-ui.html#!/endpoint-controller/saveEndpointUsingPOST
    {
      "resourceType": "Endpoint",
      "managingOrganization": {
        "reference": "Organization/ORG1"
      },
      "address": "http://org1.org/fhir"
    }
   2. Query registered endpoints managed by organization
    http://localhost:8081/swagger-ui.html#!/endpoint-controller/findEndpointUsingGET
    managingOrganization: ORG1
    Should return:
    {
      "resourceType": "Endpoint",
      "managingOrganization": {
        "reference": "Organization/ORG1"
      },
      "address": "http://org1.org/fhir"
    }
2. Registering patient:
   1. Register patient managed by organization
   http://localhost:8081/swagger-ui.html#!/patient-controller/savePatientUsingPOST
    {
      "resourceType": "Patient",
      "identifier": [
        {
          "system": "http://www.politsei.ee/",
          "use": "usual",
          "value": "37804230234"
        }
      ],
      "managingOrganization": {
        "reference": "Organization/ORG1"
      }
    }
   2. Query registered patient(s) (managed by organization)
    http://localhost:8081/swagger-ui.html#!/patient-controller/findPatientUsingGET
	id: 37804230234
	patient.identifier.system: http://www.politsei.ee/
    Should return:
    [
      {
        "resourceType": "Patient",
        "identifier": [
          {
            "use": "usual",
            "system": "http://www.politsei.ee/",
            "value": "37804230234"
          }
        ],
        "managingOrganization": {
          "reference": "Organization/ORG1"
        }
      }
    ]
3. Performing Observation query:
    http://localhost:8081/swagger-ui.html#!/observation-controller/getObservationsUsingGET
	patient.identifier.value: 37804230234
	patient.identifier.system: http://www.politsei.ee/
	organisation.identifier.value: ORG1
	code.code: 8302-2
	code.system: http://loinc.org

	Will perform subqueries:
	http://org1.org/fhir/Observation?patient.identifier.value=37804230234&patient.identifier.system=http://www.politsei.ee/&organisation.identifier.value=ORG1&code.code=8302-2&code.system=http://loinc.org
	for each organization managing specified user data (using the registered endpoint for the organization).
	Results from different organizations are combined and returned.

