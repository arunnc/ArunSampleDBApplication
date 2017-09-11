package org.arun.applications.abcdb.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomerRespositoryImpl implements CustomerRepository {

	private static Map<String, Customer> customersDB = new HashMap<>();
	//private static List<Region> regionsDB = new ArrayList<>();
	
	@Override
	public Customer save(Customer entity) {
		
		if(entity==null) return entity;
		
		String id = entity.getId();
		if(StringUtils.isEmpty(id)){
			id = UUID.randomUUID().toString();
			entity.setId(id);
		}
		
		Customer savedEntity;
		synchronized(customersDB){
			savedEntity = customersDB.get(id);
			if(savedEntity==null){
				savedEntity = new Customer();
				savedEntity.setId(id);
				customersDB.put(id, savedEntity);
			}
		}
		
		synchronized(savedEntity){
			savedEntity.setAge(entity.getAge());
			savedEntity.setName(entity.getName());
			// TODO: check if needed to check region could be null by design or not after full implementation
			savedEntity.setBelongsToRegion(entity.getBelongsToRegion());
			
			return savedEntity;
		}
		
	}

	@Override
	public long count() {
		synchronized(customersDB){
			return customersDB.size();
		}
	}

	@Override
	public boolean delete(Customer entity) {
		synchronized(customersDB){
			if(customersDB.containsValue(entity)){	// default O(n) implementation
				customersDB.remove(entity.getId(), entity);
				return true;
			}
		}
		return false;
	}

	@Override
	public Customer findByID(String id) {
		synchronized(customersDB){
			return customersDB.get(id);
		}
	}

	@Override
	public Iterable<Customer> findAll() {
		synchronized(customersDB){
			return new ArrayList<Customer>(customersDB.values());
		}
	}

	@Override
	public List<Customer> findByName(String name) {
		List<Customer> list, returnList = new ArrayList<Customer>();
		synchronized(customersDB){
			list = new ArrayList<Customer>(customersDB.values());
		}
		
		list.forEach((Customer t) -> {
			
			if (t!=null && t.getName()!=null && t.getName().equals(name)){
				returnList.add(t);
			}
		});
		
		return returnList;
	}

	@Override
	public List<Customer> findByAge(int age) {
		List<Customer> list, returnList = new ArrayList<Customer>();
		synchronized(customersDB){
			list = new ArrayList<Customer>(customersDB.values());
		}
		
		list.forEach((Customer t) -> {		// O(n) implementation - need to see if any improvement can be made here.
			
			if (t!=null && t.getAge()==age){
				returnList.add(t);
			}
		});
		
		return returnList;
	}

	@Override
	public List<Customer> findByAgeBetween(int ageFrom, int ageTo) {
		List<Customer> list, returnList = new ArrayList<Customer>();
		synchronized(customersDB){
			list = new ArrayList<Customer>(customersDB.values());
		}
		
		list.forEach((Customer t) -> {		// O(n) implementation - need to see if any improvement can be made here.
			
			if (t!=null && t.getAge()>=ageFrom && t.getAge()<=ageTo){
				returnList.add(t);
			}
		});
		
		return returnList;
	}

	@Override
	public List<Customer> findByRegion(Region region) {
		List<Customer> list, returnList = new ArrayList<Customer>();
		synchronized(customersDB){
			list = new ArrayList<Customer>(customersDB.values());
		}
		
		list.forEach((Customer t) -> {		// O(n) implementation - need to see if any improvement can be made here.
			
			if (t!=null && t.getBelongsToRegion()!=null && t.getBelongsToRegion().equals(region)){
				returnList.add(t);
			}
		});
		
		return returnList;
	}
	
}
