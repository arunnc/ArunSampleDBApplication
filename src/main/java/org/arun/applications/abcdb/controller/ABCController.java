package org.arun.applications.abcdb.controller;

import org.apache.log4j.Logger;
import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;
import org.arun.applications.abcdb.service.ABCDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class ABCController {

	@Autowired private ABCDBService service;
	
	private static final Logger LOG = Logger.getLogger(ABCController.class);
	
	@GetMapping("/customers")
	public ResponseEntity<Iterable<Customer>> getCustomers(){
		try{
			Iterable<Customer> customerList=null;
			
			customerList = service.getCustomers();
			
			if(customerList!=null){
				return new ResponseEntity<Iterable<Customer>>(customerList, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<Customer>>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e){
			LOG.error("Exception while retrieving customers: "+e.getMessage(), e);
			return new ResponseEntity<Iterable<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/regions")
	public ResponseEntity<Iterable<Region>> getRegions(){
		try{
			Iterable<Region> regionList=null;
		
			regionList = service.getRegions();
			
			if(regionList!=null) {
				return new ResponseEntity<Iterable<Region>>(regionList, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<Region>>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e){
			LOG.error("Exception while retrieving regions: "+e.getMessage(), e);
			return new ResponseEntity<Iterable<Region>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/regions/{region}")
	public ResponseEntity<Region> getRegion(@PathVariable String region){
		try{
			Region foundRegion=null;
			
			foundRegion = service.getRegion(region);
			
			if(foundRegion!=null){
				return new ResponseEntity<Region>(foundRegion, HttpStatus.OK);
			} else {
				return new ResponseEntity<Region>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e){
			LOG.error("Exception while retrieving specific region("+region+"): "+e.getMessage(), e);
			return new ResponseEntity<Region>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/regions/{region}/customers")
//	public ResponseEntity<Iterable<Customer>> getRegionCustomers(@PathVariable String region){
//		try{
//			Region foundRegion=null;
//			
//			foundRegion = service.getRegion(region);
//			
//			if(foundRegion!=null && foundRegion.getCustomersInRegion()!=null && foundRegion.getCustomersInRegion().size()>0){
//				
//				Iterable<Customer> customersInRegion = foundRegion.getCustomersInRegion().values();
//				
//				return new ResponseEntity<Iterable<Customer>>(customersInRegion, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Iterable<Customer>>(HttpStatus.NOT_FOUND);
//			}
//		} catch(Exception e){
//			LOG.error("Exception while retrieving region customers: "+e.getMessage(), e);
//			return new ResponseEntity<Iterable<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@GetMapping("/regions/{region}/customers")
	public ResponseEntity<Iterable<Customer>> getRegionFilteredCustomers(@PathVariable String region, @RequestParam( required=false,defaultValue="0",name="ageFrom" ) int ageFrom, @RequestParam( required=false,defaultValue=""+Integer.MAX_VALUE,name="ageTo" )  int ageTo ){
		try{
			if(ageFrom>ageTo){
				// basic validation
				return new ResponseEntity<Iterable<Customer>>(HttpStatus.BAD_REQUEST);
			}
			
			Iterable<Customer> customersFound = service.getRegionWithSpecificAgeCustomers(region, ageFrom, ageTo);
			
			if(customersFound!=null){
				return new ResponseEntity<Iterable<Customer>>(customersFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<Customer>>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e){
			LOG.error("Exception while retrieving region age-specific customers: "+e.getMessage(), e);
			return new ResponseEntity<Iterable<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String customerId){
		try{
			Customer customerFound = service.getCustomer(customerId);
			
			if(customerFound!=null){
				return new ResponseEntity<Customer>(customerFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
			}
		
		} catch (Exception e){
			LOG.error("Exception while retrieving specific customer ("+customerId+"): "+e.getMessage(), e);
			return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/createOrUpdate")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer, UriComponentsBuilder uriBuilder){
		try{
			Customer customerCreated = service.saveCustomer(customer);
			
			if(customerCreated==null){
				new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setLocation(uriBuilder.path("/customers/{customerId}").buildAndExpand(customerCreated.getId()).toUri());
			
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
		} catch (Exception e){
			LOG.error("Exception while creating a customer ("+customer+"): "+e.getMessage(), e);
			return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
