package org.arun.applications.abcdb.service;

import java.util.HashMap;
import java.util.Map;

import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;
import org.arun.applications.abcdb.repository.CustomerRepository;
import org.arun.applications.abcdb.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ABCDBService {

	@Autowired CustomerRepository customerRepo;
	@Autowired RegionRepository regionsRepo;
	
	public Iterable<Customer> getCustomers(){
		
		return customerRepo.findAll();
	}
	
	public Iterable<Region> getRegions(){
		return regionsRepo.findAll();
	}
	
	public Region getRegion(String regionName){
		return regionsRepo.findByID(regionName);
	}
	
	public Iterable<Customer> getRegionWithSpecificAgeCustomers(String regionName, int ageFrom, int ageTo){
		Region region = regionsRepo.findByIDAndApplyFilterOnCustomers(regionName, 
				filterCustomer -> ageFrom<=ageTo && filterCustomer!=null && filterCustomer.getAge()>=ageFrom && filterCustomer.getAge()<=ageTo);
		
		if(region!=null && region.getCustomersInRegion()!=null && region.getCustomersInRegion().size()>0){
			return region.getCustomersInRegion().values();
		} else {
			return null;
		}
	}
	
	public Customer getCustomer(String id){
		Customer returnedVal = customerRepo.findByID(id);
		
		if(returnedVal==null){
			Iterable<Customer> foundCustomers = customerRepo.findByName(id);
			
			for (Customer t : foundCustomers){
				// consider only first found customer;
				return t;
			}
			
		}
		return returnedVal;
	}
	
	public Customer saveCustomer(Customer customerToBeUpdated){
		if(customerToBeUpdated==null){
			return null;
		}
		
		Customer updatedCustomer = customerRepo.save(customerToBeUpdated);
		
		if(updatedCustomer.getBelongsToRegion()!=null){
			addCustomerToRegion(updatedCustomer);
		}
		
		return updatedCustomer;
	}
	
	public Region addCustomerToRegion(Customer customerToBeUpdated){
		Region regionToBeUpdated = null, 
				inputRegion = customerToBeUpdated.getBelongsToRegion();
		
		if(inputRegion.getName()!=null){
			regionToBeUpdated = regionsRepo.findByID(inputRegion.getName());
		}
		
		if(regionToBeUpdated==null){
			regionToBeUpdated = new Region(inputRegion);
		}
		
		Map<String, Customer> regionCustomers = regionToBeUpdated.getCustomersInRegion();
		
		if(regionCustomers==null){
			regionCustomers = new HashMap<String, Customer>();
			regionToBeUpdated.setCustomersInRegion(regionCustomers);
		}
		
		regionCustomers.put(customerToBeUpdated.getId(), customerToBeUpdated);
		
		return regionsRepo.save(regionToBeUpdated);
	}
	
	
	
}
