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
Open swagger Ui for REST:
http://localhost:8081/swagger-ui.html

1. Registering organisation:
	1. 
http://localhost:8081/swagger-ui.html#!/organisation-controller/saveOrganisationUsingPOST
{
  "id": "ORG1",
  "url": "localhost:8082"
}
	2.
    http://localhost:8081/fhir/Organisation?organisation.identifier.value=ORG1
Should return:
    {"id":"ORG1","url":"localhost:8082"}

2. Registering person:
	1.
http://localhost:8081/swagger-ui.html#!/person-controller/savePersonUsingPOST
    {
        "organisation": {
        "id": "ORG1"
      },
      "personId": "1",
      "personIdOid": "OID1"
    }
	2.
    http://localhost:8081/fhir/Person?patient.identifier.value=1&patient.identifier.system=OID1&organisation.identifier.value=ORG1
Should return:
    {"personId":"1","personIdOid":"OID1","organisation":null}

3. Performing Observation query:
    http://localhost:8081/fhir/Observation?patient.identifier.value=1&patient.identifier.system=OID1&organisation.identifier.value=ORG1&code.code=3141-9&code.system=http%3A%2F%2Floinc.org
