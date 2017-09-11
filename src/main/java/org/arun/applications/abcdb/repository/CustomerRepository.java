package org.arun.applications.abcdb.repository;

import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;

public interface CustomerRepository extends Repository<Customer, String> {

	Iterable<Customer> findByName(String name);
	Iterable<Customer> findByAge(int age);
	Iterable<Customer> findByAgeBetween(int ageFrom, int ageTo);
	Iterable<Customer> findByRegion(Region region);
	
}
