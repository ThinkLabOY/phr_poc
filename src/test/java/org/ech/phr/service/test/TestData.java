package org.ech.phr.service.test;

import org.ech.phr.model.Organisation;
import org.ech.phr.model.Person;
import org.ech.phr.model.Resource;
import org.ech.phr.model.Type;

public class TestData {

	public static Person getPersonOne() {
		return new Person("1111", "pOid");
	}

	public static Person getPersonTwo() {
		return  new Person("2222", "pOid");
	}
	
	public static Resource getResourceOne() {
		return new Resource(getDataProviderOne(), getTypeOne(), "resource1", "rOid");
	}

	public static Resource getResourceTwo() {
		return new Resource(getDataProviderTwo(), getTypeOne(), "resource2", "rOid");
	}

	public static Resource getResourceThree() {
		return new Resource(getDataProviderThree(), getTypeTwo(), "resource3", "rOid");
	}

	public static Resource getResourceFour() {
		return new Resource(getDataProviderOne(), getTypeOne(), "resource4", "rOid");
	}

	public static Resource getResourceFive() {
		return new Resource(getDataProviderTwo(), getTypeTwo(), "resource5", "rOid");
	}
	
	public static Organisation getDataProviderOne() {
		return new Organisation("dataProviderEndpoint1", "dOid", null, null, "https://dataProvider1.net");
	}

	public static Organisation getDataProviderTwo() {
		return new Organisation("dataProviderEndpoint2", "dOid", null, null, "https://dataProvider2.net");
	}

	public static Organisation getDataProviderThree() {
		return new Organisation("dataProviderEndpoint3", "dOid", null, null, "https://dataProvider3.net");
	}

	public static Type getTypeOne() {
		return new Type("type1", "tOid1");
	}
	
	public static Type getTypeTwo() {
		return new Type("type2", "tOid1");
	}

}
