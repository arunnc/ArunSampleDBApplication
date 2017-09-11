package org.arun.applications.abcdb.repository;

import java.util.function.Predicate;

import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;

public interface RegionRepository extends Repository<Region, String> {

	Region findByName(String name);
	
	Region findByIDAndApplyFilterOnCustomers(String id, Predicate<Customer> predicate);
	
}
